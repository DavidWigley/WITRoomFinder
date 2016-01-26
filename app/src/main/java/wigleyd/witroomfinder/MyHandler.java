package wigleyd.witroomfinder;

import java.io.InputStream;
import java.util.ArrayList;

public class MyHandler {
	Keywords words = new Keywords();
	Search search;
	String building, buildingPass;
	String[]day, stringToUpdate;
	int currentHour, currentMinute;
	private static final String[]buildingChoices = {"Annex Central", "Annex North", "Annex South", "Beatty", "Dobbs Hall",
			"Ira Allen", "Kingman Hall", "Rubenstein Hall", "Watson Hall", "Wentworth Hall", "Willison Hall", "Williston Hall"};

	public MyHandler(String building, String day, int currentHour, int currentMinute, InputStream input) {
		this.building = building;
		decipherDay(day);
		this.currentHour = currentHour;
		this.currentMinute = currentMinute;
		search = new Search(input);
		fixStrings();
		performSearch();
		getUpdatedListings();
	}

	public void performSearch() {
		search.findEntries(buildingPass, day, currentHour, currentMinute);
	}

	public void fixStrings() {
		if (building.equalsIgnoreCase(buildingChoices[0])) {
			buildingPass = "ANXCN";
			stringToUpdate = words.annexCentral;
		}else if (building.equalsIgnoreCase(buildingChoices[1])){
			buildingPass = "ANXNO";
			stringToUpdate = words.annexNorth;
		}else if (building.equalsIgnoreCase(buildingChoices[2])){
			buildingPass = "ANXSO";
			stringToUpdate = words.annexSouth;
		}else if (building.equalsIgnoreCase(buildingChoices[3])){
			buildingPass = "BEATT";
			stringToUpdate = words.beatty;
		}else if (building.equalsIgnoreCase(buildingChoices[4])){
			buildingPass = "DOBBS";
			stringToUpdate = words.dobbsHall;
		}else if (building.equalsIgnoreCase(buildingChoices[5])){
			buildingPass = "IRALL";
			stringToUpdate = words.iraAllen;
		}else if (building.equalsIgnoreCase(buildingChoices[6])){
			buildingPass = "KNGMN";
			stringToUpdate = words.kingman;
		}else if (building.equalsIgnoreCase(buildingChoices[7])){
			buildingPass = "RBSTN";
			stringToUpdate = words.rubenstein;
		}else if (building.equalsIgnoreCase(buildingChoices[8])){
			buildingPass = "WATSN";
			stringToUpdate = words.wastonHall;
		}else if (building.equalsIgnoreCase(buildingChoices[9])) {
			buildingPass = "WENTW";
			stringToUpdate = words.wentworthHall;
		}else if (building.equalsIgnoreCase(buildingChoices[10])){
			buildingPass = "WILLS";
			stringToUpdate = words.willisonHall;
		}else if (building.equalsIgnoreCase(buildingChoices[11])){
			buildingPass = "WLSTN";
			stringToUpdate = words.willistonHall;
		}else {
			System.out.println("PROBLEM");
		}
	}

	public void getUpdatedListings() {
		search.updateListings(stringToUpdate);
	}

	/**
	 * This takes the day input and matches it to the arrays in keywords. Vital for searching.
	 * @param input is the day passed in from the Activity ResultsActivity
	 */
	public void decipherDay(String input) {
		if (input.equalsIgnoreCase("Monday")) {
			day = words.getMondayCases();
		}else if (input.equalsIgnoreCase("Tuesday")) {
			day = words.getTuesdayCases();
		}else if (input.equalsIgnoreCase("Wednesday")) {
			day = words.getWednesdayCases();
		}else if (input.equalsIgnoreCase("Thursday")) {
			day = words.getThursdayCases();
		}else if (input.equalsIgnoreCase("Friday")){
			day = words.getFridayCases();
		}
	}

	public ArrayList getResults(){
		return search.getResults();
	}
	public ArrayList getAllClassrooms() {
		return search.getAllClassrooms();
	}

}
