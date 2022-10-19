package package1;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UnitPrice {
	
	private BigDecimal unitPrice;
	private Unit units;
	public UnitPrice(BigDecimal unitPrice, Unit units) {
		super();
		this.unitPrice = unitPrice;
		this.units = units;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public Unit getUnits() {
		return units;
	}
	

	
	public BigDecimal toReferenceUnits(Unit unit) {
		return unitPrice.divide(units.getToReferenceUnitMultiplier(unit), 4, RoundingMode.HALF_EVEN);
	}
	
	public BigDecimal toUnits(Unit unit) {
		return unitPrice.divide(units.getToUnitMultiplier(unit), 4, RoundingMode.HALF_EVEN);
	}
	

}
