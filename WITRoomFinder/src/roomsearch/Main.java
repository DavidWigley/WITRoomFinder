package roomsearch;

public class Main {

	public static void main(String[] args) { 
		Keywords words = new Keywords();
		String[] monday = words.getMondayCases();
		String[] tuesday = words.getTuesdayCases();
		String[] wednesday = words.getWednesdayCases();
		String[] thursday = words.getThursdayCases();
		String[] friday = words.getFridayCases();
		int currentHour = 12;
		int currentMinute = 0;
		Search search = new Search();
		//dynamically get room and day
		search.findEntries("ANXCN", tuesday, currentHour, currentMinute);
		search.updateListings(words.annexCentral);
		search.printEntries();
		
	}

}
