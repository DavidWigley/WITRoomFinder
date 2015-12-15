package roomsearch;

public class Main {

	public static void main(String[] args) { 
		Keywords words = new Keywords();
		String[] monday = words.getMondayCases();
		String[] tuesday = words.getTuesdayCases();
		String[] wednesday = words.getWednesdayCases();
		String[] thursday = words.getThursdayCases();
		String[] friday = words.getFridayCases();
		Search search = new Search();
		search.getEntries();
		//dynamically get room and day
		search.findEntries("ANXCN 106", tuesday);
		search.returnEntries();
		
		//next trick is isolating times and realizing inbetween times. IE I want a room at 10:15 need to check 10 ams and 9's etc.
	}

}
