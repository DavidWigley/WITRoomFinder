package wigleyd.witroomfinder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Search {

	private Scanner reader;
	private ArrayList list = new ArrayList();
	public ArrayList results = new ArrayList();
	private ArrayList allClassrooms = new ArrayList();

	public Search(InputStream input){
		reader = new Scanner(input);
	}

	private void getEntries(){
		int count =0;
		while(reader.hasNextLine()){
			//adds all the elements in the text file to the array list
			list.add(reader.nextLine());
			count++;
		}
	}

	/**
	 * Method used to remove some of the words that cause conflicts in searching
	 */
	private void trimEntries(){
		Keywords regex = new Keywords();
		String[] keys = regex.getKeywords();
		for (int i =0; i < list.size(); i++){
			for (int j =0; j < keys.length; j++){
				list.set(i, list.get(i).toString().replace(keys[j], ""));
			}
		}
	}

	/**
	 * Finds entries in the WIT courses that match a room and day.
	 * The logic is setup so it will remove items from the list that are in different buildings, days, or at different times
	 * @param room can search for specific room or just a building
	 * @param day 
	 */
	public void findEntries(String room, String[] day, int currentHour, int currentMinute){
		getEntries();
		trimEntries();
		boolean tripped = false;
		for (int i =0; i < list.size(); i++){
			String currentEntry = list.get(i).toString();
			if (!currentEntry.contains(room)){
				tripped = true;
			}else {
				boolean hasDay = false;
				for (int j = 0; j < day.length; j++){
					if (currentEntry.contains(day[j])){
						hasDay=true;
					}
				}
				if (hasDay == false){
				}
			}
			if(tripped) {
				list.remove(i);
				i--;
				tripped = false;
			}
		}
		tripped = false;
		for(int i = 0; i < list.size(); i++) {
			String currentEntry = list.get(i).toString();
			if (currentHour >=13) {
				//deals with times past 12
				currentHour-=12;
			}

			int colonLocation = currentEntry.indexOf(":");
			//this should return the first instance of the colon
			char[] firstTime = {currentEntry.charAt(colonLocation-2), currentEntry.charAt(colonLocation-1)};
			//ok so this adds the numbers I actually want to just take on that num at the end
			String time = Character.toString(firstTime[0]) + Character.toString(firstTime[1]);
			char[] secondTime = {currentEntry.charAt(colonLocation+7), currentEntry.charAt(colonLocation+8)};
			String time2 = Character.toString(secondTime[0]) + Character.toString(secondTime[1]);
			if(time.startsWith(" ")){
				time = Character.toString(firstTime[1]);
			}
			if (time2.startsWith(" ")){
				time2 = Character.toString(secondTime[1]);
			}
			//System.out.println("I'm on this entry: " + list.get(i).toString());

			int startHour = Integer.parseInt(time);
			int endHour = Integer.parseInt(time2);
			int delta = (endHour - startHour);
			if (startHour <=12 && endHour >=1 && endHour < startHour) {
				//basically it went past 1 so I put it in military time to get a clean subtraction
				delta = endHour+12 - startHour;
			}
			delta++;
			//add one because most classes end at 50, or 45 so Ill just round
			//System.out.println("The start time was: " + startHour);
			//System.out.println("The current time is: " + currentHour);
			if (startHour <= currentHour && startHour >= currentHour - delta){
				//System.out.println("Logic holds");
				//a class started around this time
				//System.out.println("The end time was: " + endHour);
				if (endHour >= currentHour){
					//theres a class going on at the same hour. It has not ended yet. So I keep it in the list. 
					//Unfortunately this means that a 9:50 class wont flag at 9. So we do a minute check
					char[] minuteTime = {currentEntry.charAt(colonLocation+10), currentEntry.charAt(colonLocation+11)};
					String timeMinute = Character.toString(minuteTime[0]) + Character.toString(minuteTime[1]);
					int endMinute = Integer.parseInt(timeMinute);
					//System.out.println("The end minute for this class is: " + endMinute);
					if (currentMinute < endMinute) {
						//theres a class going on for sure
						//System.out.println("There is a class going on");
					}else {
						//no class
						tripped = true;
					}
				}else {
					tripped = true;
				}
			}else {
				tripped = true;
			}
			if (tripped) {
				list.remove(i);
				i--;
				tripped = false;
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
			for (int j =0; j < list.size(); j++) {
				if (list.get(j).toString().contains(results.get(i).toString())){
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
	 * Prints out entries that have matched
	 */
	public void printEntries(){
		System.out.println("The resulting classrooms are as follows");
		for (int i =0; i < results.size(); i++){
			System.out.println(results.get(i).toString());
		}
	}

	public int getListSize(){
		return list.size();
	}

	public ArrayList getList() {
		return list;
	}

	public String getListStrings() {
		for (int i =0; i < list.size(); i++){
			return list.get(i).toString();
		}
		return null;
	}
	public ArrayList getResults() {
		return results;
	}
	public ArrayList getAllClassrooms() {
		return allClassrooms;
	}


}