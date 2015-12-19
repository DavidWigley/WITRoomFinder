package roomsearch;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Search {

	private File input;
	private Scanner reader;
	private ArrayList list = new ArrayList();
	public Search(){
		input = new File("fall2015.txt");
		try {
			reader = new Scanner(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Could not find the specified file");
		}
	}

	public void getEntries(){
		while(reader.hasNextLine()){
			//adds all the elementss in the text file to the array list
			list.add(reader.nextLine());
		}
	}

	/**
	 * Method used to remove some of the words that cause conflicts in searching
	 */
	public void trimEntries(){
		Keywords regex = new Keywords();
		String[] keys = regex.getKeywords();
		for (int i =0; i < list.size(); i++){
			for (int j =0; j < keys.length; j++){
				list.set(i, list.get(i).toString().replace(keys[j], ""));
			}
		}
	}

	/**
	 * Finds entries in the WIT courses that match a room and day
	 * @param room can search for specific room or just a building
	 * @param day 
	 */
	public void findEntries(String room, String[] day, int currentHour, int currentMinute){
		trimEntries();
		for (int i =0; i < list.size(); i++){
			String currentEntry = list.get(i).toString();
			if (!currentEntry.contains(room)){
				list.remove(i);
				i--;
			}else {
				boolean hasDay = false;
				for (int j = 0; j < day.length; j++){
					if (currentEntry.contains(day[j])){
						//cool
						hasDay=true;
					}
				}
				if (hasDay == false){
					list.remove(i);
					i--;
				}
			}
		}

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
			int startHour = Integer.parseInt(time);
			System.out.println("The start time was: " + startHour);
			System.out.println("The current time is: " + currentHour);
			if (startHour <= currentHour && startHour >= currentHour - 2){
				//a class started around this time
				char[] secondTime = {currentEntry.charAt(colonLocation+7), currentEntry.charAt(colonLocation+8)};
				time = Character.toString(secondTime[0]) + Character.toString(secondTime[1]);
				int endHour = Integer.parseInt(time);
				System.out.println("The end time was: " + endHour);
				if (endHour > currentHour){
					//theres a class going on. It has not ended yet
					list.remove(i);
					i--;
				}
			}

		}
	}

	/**
	 * Prints out entries that have matched
	 */
	public void returnEntries(){
		for (int i =0; i < list.size(); i++){
			System.out.println(list.get(i).toString());
		}
	}


}
