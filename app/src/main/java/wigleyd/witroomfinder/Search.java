package wigleyd.witroomfinder;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Search {

	private Scanner reader;
	private ArrayList conflictList = new ArrayList();
	public ArrayList results = new ArrayList();
	private ArrayList allClassrooms = new ArrayList();
	private ArrayList detailedRooms = new ArrayList();
	private boolean shouldSearchTime = true;

	public Search(InputStream input){
		reader = new Scanner(input);
	}

	private void getEntries(){
		while(reader.hasNextLine()){
			//adds all the elements in the text file to the array conflictList
			conflictList.add(reader.nextLine());
		}
	}

	/**
	 * Method used to remove some of the words that cause conflicts in searching
	 */
	private void trimEntries(){
		Keywords regex = new Keywords();
		String[] keys = regex.getKeywords();
		for (int i =0; i < conflictList.size(); i++){
			for (int j =0; j < keys.length; j++){
				conflictList.set(i, conflictList.get(i).toString().replace(keys[j], ""));
			}
		}
	}

	public void skipTimeSearch() {
		shouldSearchTime = false;
	}

	/**
	 * Finds entries in the WIT courses that match a room and day.
	 * The logic is setup so it will remove items from the conflictList that are in different buildings, days, or at different times
	 * @param room can search for specific room or just a building
	 * @param day 
	 */
	public void findEntries(String room, String[] day, int currentHour, int currentMinute) {

		//im really sorry for doing this but for the moment going to hardcode the searching minute.
		//No one will notice it only affects searching logic.
		currentMinute = 0;
		getEntries();
		trimEntries();
		System.out.println("Starting the search there are " + conflictList.size() + " entries");
		boolean tripped = false;
		for (int i = 0; i < conflictList.size(); i++) {
			String currentEntry = conflictList.get(i).toString();
			if (!currentEntry.contains(room)) {
				tripped = true;
				//System.out.println(conflictList.get(i).toString() + "does not contain " + room);
			} else {
				System.out.println("A room matched the room number " + conflictList.get(i).toString() + room);
				boolean hasDay = false;
				for (int j = 0; j < day.length; j++) {
					if (currentEntry.contains(day[j])) {
						hasDay = true;
						System.out.println("That room is on the same day");
						detailedRooms.add(currentEntry);
					}
				}
				if (hasDay == false) {
					tripped = true;
				}
			}
			if (tripped) {
				conflictList.remove(i);
				i--;
				tripped = false;
			}
		}
		tripped = false;
		if (shouldSearchTime) {
			for (int i = 0; i < conflictList.size(); i++) {
				String currentEntry = conflictList.get(i).toString();
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
				//System.out.println("I'm on this entry: " + conflictList.get(i).toString());

				int startHour = Integer.parseInt(time);
				int endHour = Integer.parseInt(time2);
				int delta = (endHour - startHour);
				if (startHour <= 12 && endHour >= 1 && endHour < startHour) {
					//basically it went past 1 so I put it in military time to get a clean subtraction
					delta = endHour + 12 - startHour;
				}
				delta++;
				//add one because most classes end at 50, or 45 so Ill just round
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
					} else {
						tripped = true;
					}
				} else {
					tripped = true;
				}
				if (tripped) {
					conflictList.remove(i);
					i--;
					tripped = false;
				}
			}
		}
	}
	public void updateListings(String[] rooms) {
		for (int i =0; i< rooms.length; i++) {
			results.add(rooms[i]);
			allClassrooms.add(rooms[i]);
		}
		boolean tripped = false;
		for (int i =0; i <results.size(); i++){
			for (int j =0; j < conflictList.size(); j++) {
				if (conflictList.get(j).toString().contains(results.get(i).toString())){
					tripped = true;
				}
			}
			if(tripped == true){
				results.remove(i);
				i--;
				tripped = false;
			}
		}
	}

	/**
	 * Prints out entries that have matched. Used for debugging.
	 */
	public void printEntries(){
		for (int i =0; i < results.size(); i++){
			System.out.println(results.get(i).toString());
		}
	}

	public ArrayList getDetailedRooms(){
		return detailedRooms;
	}
	public ArrayList getResults() {
		return results;
	}
	public ArrayList getAllClassrooms() {
		return allClassrooms;
	}


}