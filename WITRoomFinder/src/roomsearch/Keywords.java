package roomsearch;

public class Keywords {
	/*unnecessary. It's cleaner but much less efficient.
	String[] regex = {"C	", "NR	","WIT	","ARCH	","BIOL	","BMED	", "BLDG	",
			"TBAN	", "CHEM	", "CIVE	", "COMM	", "COMP ", "TCON	",
			"CONM	", "TCMC	", "COOP	", "DSGN	", "ETHS	", "ECON	",
			"ELMC	", "ELEC	", "ENGR	", "ENGL	", "FMGT	", "TFMC	",
			"FYS	", "HIST	", "HUMN	", "INDS	", "INTD	", "TJEC	",
			"LITR	", "TMTO	", "MGMT	", "MANF	", "MATH	", "MECH	",
			"TCAD	", "PHIL	", "PHYS	", "POLS	", "TPMC	", "PSYC	",
			"TCRM	", "SOCL	", "SURV	", "TMGT	", "TWEL	"};
	*/
	
	String[] annexCentral = {"005", "007", "009", "012", "014", "101", "102", "103", "105", "106", "107", "108", "201", "202",
			"203", "207", "209", "210", "301", "302", "305", "306", "307", "313", "314"};
	String[] annexNorth = {"001", "003", "009", "103", "200"};
	String[] annexSouth = {"002", "004", "006", "013", "015", "103", "201"};
	String[] iraAllen = {"122", "124", "125", "201", "206", "207", "210", "211", "212", "326", "329", "330"};
	String[] beatty = {"103", "201", "202", "301", "302", "303", "401", "418", "419", "420", "421", "426"};
	String[] wentworthHall = {"003", "004", "007", "010", "205", "206", "207", "208", "209", "210", "212", "214", "305", "306",
			"307", "308", "310", "312", "314"};
	String[] dobbsHall = {"003", "006", "007", "008", "202A", "202B", "203", "302", "303", "306", "307", "308", "310"};
	String[] kingman = {"101", "102", "201", "202"};
	String[] wastonHall = {"001", "002", "004", "006"};
	String[] rubenstein = {"005", "101", "103", "104", "105", "201"};
	String[] willistonHall = {"001"};
	String[] willisonHall = {"102", "103", "105"};
	//essential
	String[] regex = {"WIT	"};
	String[] mondayCases = {"	M"};
	String[] tuesdayCases = {"	MT", "T	", "TR	","TF", "TW"}; //TF and TW are unique enough for me. That's why I don't include tab
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
