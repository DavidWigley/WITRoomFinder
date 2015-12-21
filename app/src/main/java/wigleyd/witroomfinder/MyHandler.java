package wigleyd.witroomfinder;

import java.io.InputStream;
import java.security.Key;

public class MyHandler {
	Keywords words = new Keywords();
	Search search;
	String building;
	String[]day;
	int currentHour, currentMinute;

	public MyHandler(String building, String day, int currentHour, int currentMinute, InputStream input) {
		this.building = building;
		System.out.println("The day I was passed was: " + day);
		decipherDay(day);
		this.currentHour = currentHour;
		this.currentMinute = currentMinute;
		search = new Search(input);
		performSearch();
	}

	public void performSearch() {
		search.findEntries(building,day,currentHour,currentMinute);
		System.out.println("Just ran the search");
	}

	/**
	 * This part has a bug. Does not set day properly.
	 * @param input
	 */
	public void decipherDay(String input) {
		if (input == "Monday") {
			System.out.println("I set it to 1");
			day = words.getMondayCases();
		}else if (input == "Tuesday") {
			System.out.println("I set it to 2");
			day = words.getTuesdayCases();
		}else if (input == "Wednesday") {
			System.out.println("I set it to 3");
			day = words.getWednesdayCases();
		}else if (input == "Thursday") {
			System.out.println("I set it 4");
			day = words.getThursdayCases();
		}else if (input == "Friday"){
			System.out.println("I set it to 5");
			day = words.getFridayCases();
		}

	}

	public Keywords getKeywords() {
		return words;
	}
	public Search getSearch() {
		return search;
	}

}
