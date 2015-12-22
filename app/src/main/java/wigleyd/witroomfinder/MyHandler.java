package wigleyd.witroomfinder;

import java.io.InputStream;
import java.security.Key;
import java.util.ArrayList;

public class MyHandler {
	Keywords words = new Keywords();
	Search search;
	String building;
	String[]day;
	int currentHour, currentMinute;
	private static final String[]buildingChoices = {"Annex Central", "Annex North", "Annex South", "Beatty", "Dobbs Hall",
			"Ira Allen", "Kingman Hall", "Rubenstein Hall", "Watson Hall", "Wentworth Hall", "Willison Hall", "Williston Hall"};

	public MyHandler(String building, String day, int currentHour, int currentMinute, InputStream input) {
		this.building = building;
		decipherDay(day);
		this.currentHour = currentHour;
		this.currentMinute = currentMinute;
		search = new Search(input);
		performSearch();
		getUpdatedListings();
	}

	public void performSearch() {
		search.findEntries(building, day, currentHour, currentMinute);
		System.out.println("Just ran the search");
	}
	public String getClassrooms() {
		return search.getClassrooms();
	}

	public ArrayList getResults(){
		return search.getResultsList();
	}
	public void getUpdatedListings() {
		if (building.equalsIgnoreCase(buildingChoices[0])) {
			search.updateListings(words.annexCentral);
		}else if (building.equalsIgnoreCase(buildingChoices[1])){
			search.updateListings(words.annexNorth);
		}else if (building.equalsIgnoreCase(buildingChoices[2])){
			search.updateListings(words.annexSouth);
		}else if (building.equalsIgnoreCase(buildingChoices[3])){
			search.updateListings(words.beatty);
		}else if (building.equalsIgnoreCase(buildingChoices[4])){
			search.updateListings(words.dobbsHall);
		}else if (building.equalsIgnoreCase(buildingChoices[5])){
			search.updateListings(words.iraAllen);
		}else if (building.equalsIgnoreCase(buildingChoices[6])){
			search.updateListings(words.kingman);
		}else if (building.equalsIgnoreCase(buildingChoices[7])){
			search.updateListings(words.rubenstein);
		}else if (building.equalsIgnoreCase(buildingChoices[8])){
			search.updateListings(words.wastonHall);
		}else if (building.equalsIgnoreCase(buildingChoices[9])) {
			search.updateListings(words.wentworthHall);
		}else if (building.equalsIgnoreCase(buildingChoices[10])){
			search.updateListings(words.willisonHall);
		}else if (building.equalsIgnoreCase(buildingChoices[11])){
			search.updateListings(words.willistonHall);
		}else {
			System.out.println("PROBLEM");
		}
	}

	/**
	 * This part has a bug. Does not set day properly.
	 * @param input
	 */
	public void decipherDay(String input) {
		if (input.equalsIgnoreCase("Monday")) {
			System.out.println("I set it to 1");
			day = words.getMondayCases();
		}else if (input.equalsIgnoreCase("Tuesday")) {
			System.out.println("I set it to 2");
			day = words.getTuesdayCases();
		}else if (input.equalsIgnoreCase("Wednesday")) {
			System.out.println("I set it to 3");
			day = words.getWednesdayCases();
		}else if (input.equalsIgnoreCase("Thursday")) {
			System.out.println("I set it 4");
			day = words.getThursdayCases();
		}else if (input.equalsIgnoreCase("Friday")){
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
