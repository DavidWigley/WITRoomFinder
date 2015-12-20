package roomsearch;

public class Main {

	public static void main(String[] args) { 
		Keywords words = new Keywords();
		String[] monday = words.getMondayCases();
		String[] tuesday = words.getTuesdayCases();
		String[] wednesday = words.getWednesdayCases();
		String[] thursday = words.getThursdayCases();
		String[] friday = words.getFridayCases();
		int currentHour = 10;
		int currentMinute = 0;
		Search search = new Search();
		search.getEntries();
		//dynamically get room and day
		search.findEntries("ANXCN 106", tuesday, currentHour, currentMinute);
		search.printEntries();
		
	}

}
