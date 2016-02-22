package wigleyd.witroomfinder;

public class Keywords {
	//I know this is super gross but its needed.
	String[] annexCentral = {"ANXCN 005", "ANXCN 007", "ANXCN 009", "ANXCN 012", "ANXCN 014", "ANXCN 101", "ANXCN 102", "ANXCN 103",
			"ANXCN 105", "ANXCN 106", "ANXCN 107", "ANXCN 108", "ANXCN 201", "ANXCN 202", "ANXCN 203", "ANXCN 207", "ANXCN 209", 
			"ANXCN 210", "ANXCN 301", "ANXCN 302", "ANXCN 305", "ANXCN 306", "ANXCN 307", "ANXCN 313", "ANXCN 314"};
	String[] annexNorth = {"ANXNO 001", "ANXNO 003", "ANXNO 009", "ANXNO 103", "ANXNO 200"};
	String[] annexSouth = {"ANXSO 002", "ANXSO 004", "ANXSO 006", "ANXSO 013", "ANXSO 015", "ANXSO 103", "ANXSO 201"};
	String[] iraAllen = {"IRALL 122", "IRALL 124", "IRALL 125", "IRALL 201", "IRALL 206", "IRALL 207", "IRALL 210", "IRALL 211", 
			"IRALL 212", "IRALL 326", "IRALL 329", "IRALL 330"};
	String[] beatty = {"BEATT 103", "BEATT 201", "BEATT 202", "BEATT 301", "BEATT 302", "BEATT 303", "BEATT 401", "BEATT 418",
			"BEATT 419", "BEATT 420", "BEATT 421", "BEATT 426"};
	String[] wentworthHall = {"WENTW 003", "WENTW 004", "WENTW 007", "WENTW 010", "WENTW 205", "WENTW 206", "WENTW 207", "WENTW 208",
			"WENTW 209", "WENTW 210", "WENTW 212", "WENTW 214", "WENTW 305", "WENTW 306", "WENTW 307", "WENTW 308", "WENTW 310", 
			"WENTW 312", "WENTW 314"};
	String[] dobbsHall = {"DOBBS 003", "DOBBS 006", "DOBBS 007", "DOBBS 008", "DOBBS 202A", "DOBBS 202B", "DOBBS 203", "DOBBS 302",
			"DOBBS 303", "DOBBS 306", "DOBBS 307", "DOBBS 308", "DOBBS 310"};
	String[] kingman = {"KNGMN 101", "KNGMN 102", "KNGMN 201", "KNGMN 202"};
	String[] wastonHall = {"WATSN 001", "WATSN 002", "WATSN 004", "WATSN 006"};
	String[] rubenstein = {"RBSTN 005", "RBSTN 101", "RBSTN 103", "RBSTN 104", "RBSTN 105", "RBSTN 201"};
	String[] willistonHall = {"WLSTN 001"};
	String[] willisonHall = {"WILLS 102", "WILLS 103", "WILLS 105"};
	String[] blank = {""};//should actually serve a purpose not actually garbage
	
	//essential
	String[] regex = {"WIT	", "COOP EDUCATION 1:", "COOP EDUCATION 2:", "VISUALIZATION 3:", "CAD 2:", "VISUAL PERCEPTION OF THE CITY:",
			"MYTH AMERICA:", "ENGR", "LITR", "VISUALIZATION 2: ADV PRSPECTIV", "CAD 1: SURFACE MODELING\tT", "DESIGN PERSP: TOPICS IN HISTOR"};

	String[] mondayCases = {"	M	","	MT", "	MW", "	MR", "	MF"};

	String[] tuesdayCases = {"	MT", "	T	",  "	TW", "TR	","TF	"};
	String[] wednesdayCases = {"	W	", "	MW", "TW", "	WR", "	WRF	"};
	String[] thursdayCases = {"R	", "RF"};
	String[] fridayCases = {"F	"}; //this will pick up last names that end with F well see how big that is.
	public String[] getKeywords(){
		return regex;
	}
	public String[] getMondayCases(){
		return mondayCases;
	}
	public String[] getTuesdayCases() {
		return tuesdayCases;
	}
	public String[] getWednesdayCases() {
		return wednesdayCases;
	}
	public String[] getThursdayCases() {
		return thursdayCases;
	}
	public String[] getFridayCases() {
		return fridayCases;
	}
}
