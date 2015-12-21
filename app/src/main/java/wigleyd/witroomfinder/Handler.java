package wigleyd.witroomfinder;

import java.security.Key;

public class Handler {
	Keywords words = new Keywords();
	Search search = new Search();
	String building;
	String[]day;
	int currentHour, currentMinute;

	public Handler(String building, String day, int currentHour, int currentMinute) {
		this.building = building;
		decipherDay(day);
		this.currentHour = currentHour;
		this.currentMinute = currentMinute;
		performSearch();
	}

	public void performSearch() {
		search.findEntries(building,day,currentHour,currentMinute);
	}

	public void decipherDay(String input) {
		if (input == "Monday") {
			day = words.getMondayCases();
		}else if (input == "Tuesday") {
			day = words.getTuesdayCases();
		}else if (input == "Wednesday") {
			day = words.getWednesdayCases();
		}else if (input == "Thursday") {
			day = words.getThursdayCases();
		}else if (input == "Friday"){
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
