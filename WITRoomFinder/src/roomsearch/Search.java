package roomsearch;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Search {

	private File input;
	private Scanner reader;
	private ArrayList list = new ArrayList();
	int count = 0;
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
			//list.set(i, list.get(i).toString().replace("WIT", ""));
			//list.set(i, list.get(i).toString().replace("C	", ""));
			//list.set(i, list.get(i).toString().replace("NR	", ""));
			for (int j =0; j < keys.length; j++){
				list.set(i, list.get(i).toString().replace(keys[j], ""));
				count++;
			}
		}
	}
	
	/**
	 * Finds entries in the WIT courses that match a room and day
	 * @param room
	 * @param day
	 */
	public void findEntries(String room, String[] day){
		trimEntries();
		for (int i =0; i < list.size(); i++){
			if (!list.get(i).toString().contains(room)){
				list.remove(i);
				i--;
			}else {
				boolean hasDay = false;
				for (int j = 0; j < day.length; j++){
					if (list.get(i).toString().contains(day[j])){
						//cool
						hasDay=true;
					}
				}
				if (hasDay == false){
					list.remove(i);
					i--;
				}
			}
			count++;
		}
	}
	
	/**
	 * Prints out entries that have matched
	 */
	public void returnEntries(){
		for (int i =0; i < list.size(); i++){
			System.out.println(list.get(i).toString());
		}
		System.out.println(count);
	}


}
