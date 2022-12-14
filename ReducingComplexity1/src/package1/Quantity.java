package package1;

import java.math.BigDecimal;

public class Quantity {
	
	private BigDecimal quantity;
	private Unit units;
	
	
	public Quantity(BigDecimal quantity, Unit units) {
		super();
		this.quantity = quantity;
		this.units = units;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}

	public Unit getUnits() {
		return units;
	}

	public BigDecimal toReferenceUnits() {
		return quantity.multiply(units.getToReferenceUnitMultiplier());
	}

	 

	
	

}
