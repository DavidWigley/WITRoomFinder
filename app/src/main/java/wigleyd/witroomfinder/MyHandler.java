package wigleyd.witroomfinder;

import java.io.InputStream;
import java.util.ArrayList;

public class MyHandler {
	private static final int DEFAULT_HOUR = 0;
	private static final int DEFAULT_MINUTE=0;

	private Search search;
	private String building, buildingPass;
	private String[]day, stringToUpdate;
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
	public MyHandler(String building, String day,InputStream input) {
		this.building = building;
		decipherDay(day);
		currentHour = DEFAULT_HOUR;
		currentMinute = DEFAULT_MINUTE;
		search = new Search(input);
		fixStrings();
		performSearch();
		getUpdatedListings();
	}

	public MyHandler(String building, String day,InputStream input, ArrayList scannerData) {
		this.building = building;
		decipherDay(day);
		currentHour = DEFAULT_HOUR;
		currentMinute = DEFAULT_MINUTE;
		search = new Search(input);
		search.setConflictList(scannerData);
		fixStrings();
		performSearch();
		getUpdatedListings();
		//search.printEntries();
	}


	public void performSearch() {
		search.findEntries(buildingPass, day, currentHour, currentMinute);
	}

	public void fixStrings() {
		if (building.equalsIgnoreCase(buildingChoices[0])) {
			buildingPass = "ANXCN";
			stringToUpdate = Keywords.annexCentral;
		}else if (building.equalsIgnoreCase(buildingChoices[1])){
			buildingPass = "ANXNO";
			stringToUpdate = Keywords.annexNorth;
		}else if (building.equalsIgnoreCase(buildingChoices[2])){
			buildingPass = "ANXSO";
			stringToUpdate = Keywords.annexSouth;
		}else if (building.equalsIgnoreCase(buildingChoices[3])){
			buildingPass = "BEATT";
			stringToUpdate = Keywords.beatty;
		}else if (building.equalsIgnoreCase(buildingChoices[4])){
			buildingPass = "DOBBS";
			stringToUpdate = Keywords.dobbsHall;
		}else if (building.equalsIgnoreCase(buildingChoices[5])){
			buildingPass = "IRALL";
			stringToUpdate = Keywords.iraAllen;
		}else if (building.equalsIgnoreCase(buildingChoices[6])){
			buildingPass = "KNGMN";
			stringToUpdate = Keywords.kingman;
		}else if (building.equalsIgnoreCase(buildingChoices[7])){
			buildingPass = "RBSTN";
			stringToUpdate = Keywords.rubenstein;
		}else if (building.equalsIgnoreCase(buildingChoices[8])){
			buildingPass = "WATSN";
			stringToUpdate = Keywords.wastonHall;
		}else if (building.equalsIgnoreCase(buildingChoices[9])) {
			buildingPass = "WENTW";
			stringToUpdate = Keywords.wentworthHall;
		}else if (building.equalsIgnoreCase(buildingChoices[10])){
			buildingPass = "WILLS";
			stringToUpdate = Keywords.willsonHall;
		}else if (building.equalsIgnoreCase(buildingChoices[11])){
			buildingPass = "WLSTN";
			stringToUpdate = Keywords.willistonHall;
		}else {
			buildingPass = building;
			stringToUpdate = Keywords.blank;
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
			day = Keywords.getMondayCases();
		}else if (input.equalsIgnoreCase("Tuesday")) {
			day = Keywords.getTuesdayCases();
		}else if (input.equalsIgnoreCase("Wednesday")) {
			day = Keywords.getWednesdayCases();
		}else if (input.equalsIgnoreCase("Thursday")) {
			day = Keywords.getThursdayCases();
		}else if (input.equalsIgnoreCase("Friday")){
			day = Keywords.getFridayCases();
		}else {

		}
	}

	public void skipTimeSearch() {
		search.skipTimeSearch();
	}

	public ArrayList getResults(){
		return search.getResults();
	}
	public ArrayList getAllClassrooms() {
		return search.getAllClassrooms();
	}
	public ArrayList getDetailedRooms(){
		return search.getDetailedRooms();
	}
	public ArrayList getRawScannerData() {return  search.getRawScannerData();}
}
