package package1;

public enum SystemOfUnits {
	METRIC("International System of Units"),
	IMPERIAL("Imperial system of units"),
	USCS("United States customary units");
	
	public String getFormalName() {
		return formalName;
	}
	private final String  formalName;   
    SystemOfUnits(String formalName) {
        this.formalName = formalName;
    }
}
