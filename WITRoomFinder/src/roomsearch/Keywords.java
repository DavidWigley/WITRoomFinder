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
