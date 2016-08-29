package wigleyd.witroomfinder;

import java.util.ArrayList;

/**
 * Created by frascog on 2/22/16.
 * Made terrible by wigleyd on 8/6/16.
 */
public class Classroom {

    private Building building;
    private String room;
    private boolean open;
    private String original;
    private ArrayList startTimes, endTimes;
    private int timeWanted, nearestTime;
    private int available;
    private boolean debug = true;

    public Classroom(boolean open, Building building, String room) {
        this.open = open;
        this.building = building;
        this.room = room;
    }

    public Classroom(boolean open, String name) {
        this.open = open;
        String[] items = name.split(" ");
        setBuilding(Building.getValue(name));
        setRoom(items[1]);
        this.original = name;
    }

    public Classroom(boolean open, String name, ArrayList startTimes, ArrayList endTimes, int timeWanted, int nearestTime) {
        this.open = open;
        String[] items = name.split(" ");
        setBuilding(Building.getValue(name));
        setRoom(items[1]);
        this.original = name;
        this.startTimes = startTimes;
        this.timeWanted = timeWanted;
        this.endTimes = endTimes;
        this.nearestTime = nearestTime;
    }


    public String getAvailability() {
        if (debug)System.out.println("MY name is " + getName());//Slim Shady
//        for (int i =0; i < startTimes.size(); i++) {
//            System.out.println("My start times are " + startTimes.get(i).toString());
//        }
//        for (int i =0; i < endTimes.size(); i++) {
//            System.out.println("My end times are " + endTimes.get(i).toString());
//        }
        if (startTimes.isEmpty()) {
            return "∞. MAY NOT BE A REAL CLASSROOM";
        } else {
            //fault protection something tripped after WENTW207. Odd
            if (nearestTime >= startTimes.size()) {
                nearestTime = startTimes.size()-1;
            }
            int closestHour = Integer.parseInt(startTimes.get(nearestTime).toString());
            //I spit it into military time
            if (closestHour >=1 && closestHour <=7){
                closestHour +=12;
            }
            if(debug)System.out.println("My closest hour is " + closestHour);
            if (timeWanted < closestHour) {
                if (debug)System.out.println("I am in the first loop");
                //ie its currently open so I get the start time
                if (isOpen()) {
                    if(debug)System.out.println("I am in the first sub loop");
                    available = closestHour;
                }else if (!isOpen()) {
                    if(debug)System.out.println("I am in the 2nd sub loop");
                    //verifying array is not out of bounds
                    if (nearestTime !=0) {
                        if(debug)System.out.println("I am in the 3rd sub loop");
                        closestHour = Integer.parseInt(endTimes.get(nearestTime-1).toString());
                        available = closestHour;
                        if(debug)System.out.println("Initial set was " + available);
                        available = verifyNoConsecutives(available);
                    }else {
                        if(debug)System.out.println("SHIT HAPPENED");
                        return "My logic fucked up. Manually check";//Lets try to limit these in the future Same underlying logic is fucking us. IT HAS TO BE WITH TIME CONVERSIONS
                    }
                }
            }else if (timeWanted > closestHour) {
                //Should be open for the rest of the day
                if(debug)System.out.println("I am in the weird sub loop");
                if (isOpen()) {
                    return "∞. No more classes scheduled.";
                }else {
                    available = Integer.parseInt(endTimes.get(nearestTime).toString());
                }
            } else{
                //its currently taken so I get the later time
                if(debug)System.out.println("I am in the 3rd loop");
                available = Integer.parseInt(endTimes.get(nearestTime).toString());
                available = verifyNoConsecutives(available);
                //Additional redundancy. Getting ridiculous with these mil checks
                if (available >=1 && available <=7) {
                    available+=12; //some entries are not in mil time so I double check
                }
                if (available < timeWanted) {
                    if(debug)System.out.println("I just said " + available + " was less than " + timeWanted);
                    return "Bug. Manually check times. Sorry";
                }
            }
            //convert back to 12hr format
            if (available > 12){
                //System.out.println("Converted by 12");
                available-=12;
            }

            return Integer.toString(available);
        }
    }

    /**
     * Logic for checking that if I say it is occupied till 12 a class does not start at 12.
     * Just checks for classes that will start as soon as 1 ends
     */
    private int verifyNoConsecutives(int oldValue){
        if (nearestTime !=0) {
            if (debug)System.out.println("I am in the 3rd sub loop");
            int oldStartTime = Integer.parseInt(startTimes.get(nearestTime).toString());
            if (oldStartTime >=1 &&oldStartTime <=7) {
                oldStartTime+=12; //some entries are not in mil time so I double check
            }
            int closestHour = Integer.parseInt(endTimes.get(nearestTime-1).toString());
            if (closestHour >=1 && closestHour <=7) {
                closestHour+=12; //some entries are not in mil time so I double check
            }
            if(debug)System.out.println("Initial set was " + available);
            if(debug)System.out.println("I am comparing " + oldStartTime + " and " + closestHour);
            while (oldStartTime == closestHour) {
                nearestTime+=1;
                if (nearestTime != startTimes.size()) {
                    oldStartTime = Integer.parseInt(startTimes.get(nearestTime).toString());
                    if (oldStartTime >=1 &&oldStartTime <=7) {
                        oldStartTime+=12; //some entries are not in mil time so I double check
                    }
                    closestHour = Integer.parseInt(endTimes.get(nearestTime-1).toString());
                    if (closestHour >=1 && closestHour <=7) {
                        closestHour+=12; //some entries are not in mil time so I double check
                    }
                    if(debug)System.out.println("I am comparing " + oldStartTime + " and " + closestHour);
                }else {
                    if(debug)System.out.println("They matched again but I'm at the index limit");
                    closestHour = Integer.parseInt(endTimes.get(endTimes.size()-1).toString());
                    if (closestHour >=1 && closestHour <=7) {
                        closestHour+=12; //some entries are not in mil time so I double check
                    }
                }
                available = closestHour;
            }
            if(debug)System.out.println("Final set was " + available);
            return available;
        }
        return oldValue; //safeGuard against failures
    }

    public void getDuration(int beginningTime){
        if (beginningTime >=1 && beginningTime <=7) {
            beginningTime+=12;//24hr conversion
        }
        int nextTime;
        if (nearestTime != startTimes.size()) {
            nextTime = Integer.parseInt(startTimes.get(nearestTime).toString());
        }else {
            nextTime = 0;//Never open?
            if(debug)System.out.println("Attempting exit");
            return;
        }
        if (nextTime >=1 && nextTime <=7) {
            nextTime+=12;
        }
        if (beginningTime > nextTime){
            if (nearestTime <= startTimes.size() -1){
                nearestTime+=1;
                nextTime = Integer.parseInt(startTimes.get(nearestTime).toString());
            }
        }
        if(debug)System.out.println("My original was " + beginningTime + " my next is " + nextTime);
    }
    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getName() {
        return this.building + " " + room;
    }

    public String getOriginal() {
        return original;
    }

    public int returnTimeIndex(){return nearestTime;}
}
