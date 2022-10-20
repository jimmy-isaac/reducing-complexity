package package1;

import java.util.List;
import java.util.Optional;

public enum Company {
	
	ABC_COMPANY("ABC Company Inc.", SystemOfUnits.IMPERIAL, Optional.of(SystemOfUnits.METRIC), true),
	XYZ_COMPANY("XYZ Company", SystemOfUnits.METRIC, Optional.empty(), false),
	LMN_INTERNATIONAL("LMN Company Inc.", SystemOfUnits.METRIC, Optional.of(SystemOfUnits.IMPERIAL), true);
	
	
	private String name;
	private SystemOfUnits primarySystemOfUnits;
	private Optional<SystemOfUnits> secondarySystemOfUnits;
	private boolean chargeForBags;
	
	private Company(String name, SystemOfUnits primarySystemOfUnits, Optional<SystemOfUnits> secondarySystemOfUnits,
			boolean chargeForBags) {
		this.name = name;
		this.primarySystemOfUnits = primarySystemOfUnits;
		this.secondarySystemOfUnits = secondarySystemOfUnits;
		this.chargeForBags = chargeForBags;
	}
	public String getName() {
		return name;
	}
	public SystemOfUnits getPrimarySystemOfUnits() {
		return primarySystemOfUnits;
	}
	public Optional<SystemOfUnits> getSecondarySystemOfUnits() {
		return secondarySystemOfUnits;
	}
	public boolean isChargeForBags() {
		return chargeForBags;
	}
	
	

}
