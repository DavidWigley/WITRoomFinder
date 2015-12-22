package wigleyd.witroomfinder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import android.R.raw;

public class Search {

	private Scanner reader;
	private ArrayList list = new ArrayList();
	private ArrayList results = new ArrayList();

	public Search(InputStream input){
		reader = new Scanner(input);
	}

	private void getEntries(){
		while(reader.hasNextLine()){
			//adds all the elementss in the text file to the array list
			list.add(reader.nextLine());
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
				//System.out.println("Currently looking at " + list.get(i).toString());
				list.set(i, list.get(i).toString().replace(keys[j], ""));
			}
		}
	}
	private void printMessage(int i) {
//		System.out.println("Just removed this entry:");
//		System.out.println(list.get(i));
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
						//cool
						hasDay=true;
					}
				}
				if (hasDay == false){
					tripped = true;
				}
			}
			if(tripped) {
				printMessage(i);
				list.remove(i);
				i--;
				tripped = false;
			}
		}
		tripped = false;
		for(int i = 0; i < list.size(); i++) {
			String currentEntry = list.get(i).toString();
			int hourBefore = currentHour - 1;
			//deals with case where the current hour is 1
			if (hourBefore == 0) {
				hourBefore = 12;
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
						System.out.println("There is a class going on");
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
				printMessage(i);
				list.remove(i);
				i--;
				tripped = false;
			}
		}
	}

	public void updateListings(String[] rooms) {
		for (int i =0; i< rooms.length; i++) {
			results.add(rooms[i]);
		}
		boolean tripped = false;
		int tripCounter = 0;
		for (int i =0; i <results.size(); i++){
			for (int j =0; j < list.size(); j++) {
				if (list.get(j).toString().contains(results.get(i).toString())){
					//System.out.println("The item: " + list.get(j).toString());
					//System.out.println("Contains: " + results.get(i).toString());
					tripped = true;
				}
			}
			if(tripped == true){
				printMessage(i);
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

	public String getClassrooms() {
		for (int i =0; i < results.size(); i++){
			return results.get(i).toString();
		}
		return null;
	}

	public ArrayList getResultsList() {
		return results;
	}

	public int getNumClassrooms() {
		return results.size();
	}



}
