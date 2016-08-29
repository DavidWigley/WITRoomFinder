package wigleyd.witroomfinder;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Search {

    private Scanner reader;
    private ArrayList conflictList = new ArrayList();
    private ArrayList rawScannerData = new ArrayList();
    protected ArrayList results = new ArrayList();
    private ArrayList allClassrooms = new ArrayList();
    protected static ArrayList finalResults = new ArrayList();

    private boolean debug = false;
    //used for detailed searches.
    private ArrayList detailedRooms = new ArrayList();
    private boolean shouldSearchTime = true;

    protected Search(InputStream input) {
        reader = new Scanner(input);
    }

    private void getEntries() {
        //additional redundancy check dont really need. One condition check should be OK
        if (conflictList.isEmpty() && rawScannerData.isEmpty()) {
            while (reader.hasNextLine()) {
                //adds all the elements in the text file to the array conflictList
                conflictList.add(reader.nextLine());
            }
            reader.close();
        }
    }

    public ArrayList getRawScannerData() {
        return rawScannerData;
    }

    //Method that will allow bypass of constantly reading with scanner
    public void setConflictList(ArrayList rawList) {
        rawScannerData = rawList;
        conflictList = rawList;
    }

    /**
     * Method used to remove some of the words that cause conflicts in searching
     */
    private void trimEntries() {
        Keywords regex = new Keywords();
        String[] keys = regex.getKeywords();
        for (int i = 0; i < conflictList.size(); i++) {
            for (int j = 0; j < keys.length; j++) {
                conflictList.set(i, conflictList.get(i).toString().replace(keys[j], ""));
            }
        }
        rawScannerData = conflictList;
        conflictList = trimUpToDay(conflictList);
    }

    protected void skipTimeSearch() {
        shouldSearchTime = false;
    }

    /**
     * Finds entries in the WIT courses that match a room and day.
     * The logic is setup so it will remove items from the conflictList that are in different buildings, days, or at different times
     *
     * @param room can search for specific room or just a building
     * @param day
     */
    protected void findEntries(String room, String[] day, int currentHour, int currentMinute) {

        //im really sorry for doing this but for the moment going to hardcode the searching minute.
        //No one will notice it only affects searching logic. I noticed. Anyways its ok most classes end rounding up and I use that logic later on. Its a fair(ish) assumption
        currentMinute = 0;
        getEntries();
        trimEntries();
        boolean tripped = false;
        for (int i = 0; i < conflictList.size(); i++) {
            String currentEntry = conflictList.get(i).toString();
            if (!currentEntry.contains(room)) {
                tripped = true;
                if (debug)System.out.println("I am saying " + currentEntry + " Does not contain " + room);

            } else {
                boolean hasDay = false;
                for (int j = 0; j < day.length; j++) {
                    if (debug)System.out.println("Checking if " + currentEntry + "contains " + day[j]);
                    if (currentEntry.contains(day[j])) {
                        hasDay = true;
                        //add the class to the detailed list because its in the same building on same day.
                        //System.out.println("I added " + currentEntry + " to the detailed list");
                        detailedRooms.add(currentEntry);
                        if (debug)System.out.println("Added this guy: "+ currentEntry + " bc day");System.out.println("ARE YOU STILL IN MY LIST??? " + conflictList.get(i).toString());
                        break;
                    }else{
                        if (debug)System.out.println(currentEntry + " does not contain " + day[j]);

                        hasDay = false;
                    }
                }
                if (hasDay == false) {
                    tripped = true;
                }
            }
            if (tripped) {
                if (debug)System.out.println("Removed this guy " +  conflictList.get(i) + " because of day");
                conflictList.remove(i);
                i--;
                tripped = false;
            }
            if (i != -1 && debug) System.out.println("ARE YOU STILL IN MY LIST??? " + conflictList.get(i).toString());
        }
        tripped = false;
        //loop that asks if I care about the time the class is going on. When I get the detailed listings I dont care
        //about time so I skip this whole block and save time.
        if (shouldSearchTime) {
            System.out.println("Doing a time search");
            for (int i = 0; i < conflictList.size(); i++) {
                String currentEntry = conflictList.get(i).toString();
                System.out.println("My current Entry is" + currentEntry);
                int colonLocation = currentEntry.indexOf(":");
                //this should return the first instance of the colon
                char[] firstTime = {currentEntry.charAt(colonLocation - 2), currentEntry.charAt(colonLocation - 1)};
                //ok so this adds the numbers I actually want to just take on that num at the end
                String time = Character.toString(firstTime[0]) + Character.toString(firstTime[1]);
                char[] secondTime = {currentEntry.charAt(colonLocation + 7), currentEntry.charAt(colonLocation + 8)};
                String time2 = Character.toString(secondTime[0]) + Character.toString(secondTime[1]);
                if (time.startsWith(" ")) {
                    time = Character.toString(firstTime[1]);
                }
                if (time2.startsWith(" ")) {
                    time2 = Character.toString(secondTime[1]);
                }
                //debug print
                //System.out.println("I'm on this entry: " + conflictList.get(i).toString());

                int startHour = Integer.parseInt(time);
                int endHour = Integer.parseInt(time2);

                if (startHour >=1 && startHour <=7)startHour+=12; //24hr conversion
                if (endHour >=1 && endHour <=7)endHour+=12; //24 hr conversion
                int delta = (endHour - startHour);
                delta++;
                //add one because most classes end at 50, or 45 so Ill just round.
                if (startHour <= currentHour && startHour >= currentHour - delta) {
                    //a class started around this time
                    if (endHour >= currentHour) {
                        //theres a class going on at the same hour. It has not ended yet. So I keep it in the conflictList.
                        //Unfortunately this means that a 9:50 class wont flag at 9. So we do a minute check
                        char[] minuteTime = {currentEntry.charAt(colonLocation + 10), currentEntry.charAt(colonLocation + 11)};
                        String timeMinute = Character.toString(minuteTime[0]) + Character.toString(minuteTime[1]);
                        int endMinute = Integer.parseInt(timeMinute);
                        if (currentMinute >= endMinute) {
                            //No class going on, its ended. I remove it from my conflicts
                            tripped = true;
                        }
                        //So if the above condition does not pass it means there is a class going on.
                    } else {
                        tripped = true;
                    }
                } else {
                    tripped = true;
                    if(debug) {
                        System.out.println("My start hour was " + startHour);
                        System.out.println("My current hour was " + currentHour);
                        System.out.println("My delta was " + delta);
                        System.out.println("Tripped in my weird ass else");
                    }
                }
                if (tripped) {
                    if (debug)System.out.println("removed this guy " + conflictList.get(i).toString() + " bc time");
                    conflictList.remove(i);
                    i--;
                    tripped = false;
                }
            }
        }
    }

    public void updateListings(String[] rooms) {
        for (int i = 0; i < rooms.length; i++) {
            results.add(rooms[i]);
            //System.out.println("Added " + rooms[i] + " to the results");
            allClassrooms.add(rooms[i]);
        }
        boolean tripped = false;
        for (int i = 0; i < results.size(); i++) {
            for (int j = 0; j < conflictList.size(); j++) {
                if (conflictList.get(j).toString().contains("Ali Moazed") && conflictList.get(j).toString().contains("Aerospace")) {
                    debug = true;
                }else {
                    debug = true;//fuck you
                }
                if (debug)System.out.println("I'm currently checking " + conflictList.get(j).toString());
                if (conflictList.get(j).toString().contains(results.get(i).toString())) {
                    tripped = true;
                    if (debug)System.out.println("Said " + conflictList.get(j).toString() + " contains " + results.get(i).toString());
                    break;
                }
            }
            if (tripped == true) {
                if (debug) System.out.println("Just removed " + results.get(i).toString() + "from the results");
                results.remove(i);
                i--;
                tripped = false;
            }
        }
    }

    protected void updateListings(String[] rooms, boolean InitalRun) {
        for (int i = 0; i < rooms.length; i++) {
            results.add(rooms[i]);
            //System.out.println("Added " + rooms[i] + " to the results");
            allClassrooms.add(rooms[i]);
        }
        boolean tripped = false;
        for (int i = 0; i < results.size(); i++) {
            for (int j = 0; j < conflictList.size(); j++) {
                if (debug)System.out.println("I'm currently checking " + conflictList.get(j).toString());
                if (conflictList.get(j).toString().contains(results.get(i).toString())) {
                    tripped = true;
                    if (debug)System.out.println("Said " + conflictList.get(j).toString() + " contains " + results.get(i).toString());
                    break;
                }
            }
            if (tripped == true) {
                //System.out.println("Just removed " + results.get(i).toString() + "from the results");
                results.remove(i);
                i--;
                tripped = false;
            }
        }
        setResults();
    }
    /**
     * Method that will be responsible for trimming all the course registration data until it reaches the day
     * which is when I start caring about what it says. Basically everything before that is junk.
     */
    private ArrayList trimUpToDay(ArrayList originalList) {
        ArrayList trimmedList = new ArrayList();
        Keywords keywords = new Keywords();
        String[] currentSearch = null;
        boolean shouldBreak = false;
        final int DAYS_IN_WEEK = 5;
        for (int i = 0; i < originalList.size(); i++) {
            String currentString = originalList.get(i).toString();
            String newString = null;

            for (int j = 0; j < DAYS_IN_WEEK; j++) {
                if (j == 0) {
                    currentSearch = keywords.mondayCases;
                } else if (j == 1) {
                    currentSearch = keywords.tuesdayCases;
                } else if (j == 2) {
                    currentSearch = keywords.wednesdayCases;
                } else if (j == 3) {
                    currentSearch = keywords.thursdayCases;
                } else if (j == 4) {
                    currentSearch = keywords.fridayCases;
                }
                for (int caseNum = 0; caseNum < currentSearch.length; caseNum++) {
                    //I think I could technically replace this conditional with the first code block
                    //inside it but I'm reluctant to do that so I'm keeping it how it is.
                    if (currentString.contains(currentSearch[caseNum])) {
                        //gets the index where we find the string.
                        int index = currentString.indexOf(currentSearch[caseNum]);
                        //trim up to where the day is stated
                        newString = currentString.substring(index);
                        shouldBreak = true;
                        break;
                    }
                }
                //trying to break from more for loops so this doesn't take forever.
                if (shouldBreak) {
                    shouldBreak = false;
                    break;
                }
            }
            //System.out.println("Orig entry was: " + currentString);
            //System.out.println("New entry is: " + newString);

            //If the entry was garbage or had a TBA day I'm not going to add it because its junk anyways. I cant find a
            //room for a class that doesnt exist or has a TBD meeting day
            if (newString != null) {
                trimmedList.add(newString);
            }


        }

        return trimmedList;
    }

    public void setResults(){
        finalResults = results;
    }

    public ArrayList getFinalResults() {
        return finalResults;
    }
    /**
     * Prints out entries that have matched. Used for debugging.
     */
    public void printEntries() {
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).toString());
        }
    }

    public ArrayList getDetailedRooms() {
        return detailedRooms;
    }

    public ArrayList getResults() {
        return results;
    }

    public ArrayList getAllClassrooms() {
        return allClassrooms;
    }


}