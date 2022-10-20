package package1;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

public enum Unit {
	POUND(Measure.WEIGHT, SystemOfUnits.USCS, null,   BigDecimal.valueOf(1), null),
	OUNCE(Measure.WEIGHT, SystemOfUnits.USCS, Unit.POUND,  BigDecimal.valueOf(16), null),
	GALLON_LIQUID(Measure.VOLUME, SystemOfUnits.USCS, null,  BigDecimal.valueOf(1), null),
	LITRE(Measure.VOLUME, SystemOfUnits.METRIC, null,  BigDecimal.valueOf(1), null),
	IMPERIAL_GALLON(Measure.VOLUME, SystemOfUnits.IMPERIAL, null,  BigDecimal.valueOf(1), null),
	QUART_LIQUID(Measure.VOLUME, SystemOfUnits.USCS, Unit.GALLON_LIQUID,  BigDecimal.valueOf(4), null),
	PINT_LIQUID(Measure.VOLUME, SystemOfUnits.USCS, Unit.GALLON_LIQUID,  BigDecimal.valueOf(8), null),
	FLUID_OUNCE(Measure.VOLUME, SystemOfUnits.USCS, Unit.GALLON_LIQUID,  BigDecimal.valueOf(128), null),
	SECOND(Measure.TIME, SystemOfUnits.METRIC, null,  BigDecimal.valueOf(1), null),
	MINUTE(Measure.TIME, SystemOfUnits.METRIC, Unit.SECOND,  BigDecimal.valueOf(60), null),
	HOUR(Measure.TIME, SystemOfUnits.METRIC, Unit.SECOND,  BigDecimal.valueOf(3600), null),
	FOOT(Measure.LENGTH, SystemOfUnits.USCS, null,  BigDecimal.valueOf(1), null),
	YARD(Measure.LENGTH, SystemOfUnits.USCS, Unit.FOOT,  BigDecimal.valueOf(3), null),
	INCH(Measure.LENGTH, SystemOfUnits.USCS, Unit.FOOT,  BigDecimal.valueOf(12), null),
	METRE(Measure.LENGTH, SystemOfUnits.METRIC, null,  BigDecimal.valueOf(1), null),
	KILOMETER(Measure.LENGTH, SystemOfUnits.METRIC, Unit.METRE,  BigDecimal.valueOf(1000), null),
	MILLIMETER(Measure.LENGTH, SystemOfUnits.METRIC, Unit.METRE,  BigDecimal.valueOf(1000), null),
	DECIMETER(Measure.LENGTH, SystemOfUnits.METRIC, Unit.METRE,  BigDecimal.valueOf(10), null),
	KILOGRAM(Measure.WEIGHT, SystemOfUnits.METRIC, null,  BigDecimal.valueOf(1), null),
	GRAM(Measure.WEIGHT, SystemOfUnits.METRIC, Unit.KILOGRAM,  BigDecimal.valueOf(0.001), null);


	private final Measure measure;
	private final SystemOfUnits systemOfUnits;
	private  Unit referenceUnit;
	private final BigDecimal toReferenceUnitMultiplier;
	private Optional<Map<SystemOfUnits, Pair<Unit,BigDecimal>>> toSystemOfUnitConversionMultiplier;
	
	// following needed as enums are static and cannot refer to itself, until initialized.
	static {
		POUND.referenceUnit = POUND;
		POUND.toSystemOfUnitConversionMultiplier = Optional.of(Map.ofEntries(
				entry(SystemOfUnits.METRIC, new Pair<Unit, BigDecimal>(Unit.KILOGRAM,BigDecimal.valueOf(0.453592))), 
				entry(SystemOfUnits.IMPERIAL, new Pair<Unit, BigDecimal>(Unit.KILOGRAM,BigDecimal.valueOf(1.0))))
				);
		GALLON_LIQUID.referenceUnit = GALLON_LIQUID;
		GALLON_LIQUID.toSystemOfUnitConversionMultiplier = Optional.of(Map.ofEntries(
				entry(SystemOfUnits.METRIC, new Pair<Unit, BigDecimal>(Unit.LITRE,BigDecimal.valueOf(3.78541))), 
				entry(SystemOfUnits.IMPERIAL, new Pair<Unit, BigDecimal>(Unit.IMPERIAL_GALLON,BigDecimal.valueOf(0.83267))))
				);
		SECOND.referenceUnit = SECOND;
		FOOT.referenceUnit = FOOT;
		METRE.referenceUnit = METRE;
		KILOGRAM.referenceUnit = KILOGRAM;
	}

	private Unit(Measure measure, SystemOfUnits systemOfUnits, Unit referenceUnit,
			BigDecimal toReferenceUnitMultiplier, Optional<Map<SystemOfUnits, Pair<Unit, BigDecimal>>> toSystemOfUnitConversionMultiplier) {
		this.measure = measure;
		this.systemOfUnits = systemOfUnits;
		this.referenceUnit = referenceUnit;
		this.toReferenceUnitMultiplier = toReferenceUnitMultiplier;
		this.toSystemOfUnitConversionMultiplier = toSystemOfUnitConversionMultiplier;
	}

	public Measure getMeasure() {
		return measure;
	}

	public SystemOfUnits getSystemOfUnits() {
		return systemOfUnits;
	}

	public Unit getReferenceUnit() {
		return referenceUnit;
	}


	public BigDecimal getToReferenceUnitMultiplier() {
		return toReferenceUnitMultiplier;
	}
	
	public BigDecimal getToUnitMultiplier(Unit unit) throws IllegalStateException {
		if (!isConvertible(unit)) {
			throw new IllegalStateException(String.format("Can get multiplier only if unit being converted to has the same measure. " +
			"Here %s is being converted to %s", this.getMeasure(), unit.getMeasure()));
		}
		// first convert to reference unit of other unit
		// then divide by reference multiplier of of other unit
		BigDecimal toReferenceUnitMultiplierOther = this.getToReferenceUnitMultiplier(unit);
		BigDecimal toUnitMultiplier = unit.getToReferenceUnitMultiplier();
		
		return toReferenceUnitMultiplierOther.divide(toUnitMultiplier, 4, RoundingMode.HALF_EVEN);
	}

	public boolean isConvertible(Unit unit) {
		return this.getMeasure().equals(unit.getMeasure());
	}


	public Optional<Map<SystemOfUnits, Pair<Unit,BigDecimal>>> getToSystemOfUnitConversionMultiplier() {
		return toSystemOfUnitConversionMultiplier;
	}





	BigDecimal getToReferenceUnitMultiplier(Unit unit) {
		if (!isConvertible(unit)) {
			throw new IllegalStateException(String.format("Can get multiplier only if unit being converted to has the same measure. " +
			"Here %s is being converted to %s", this.getMeasure(), unit.getMeasure()));
		}
		BigDecimal conversionFactorInMyUnits = this.getToReferenceUnitMultiplier();
		if (this.systemOfUnits == unit.getSystemOfUnits()) {
			return conversionFactorInMyUnits;
		}
		BigDecimal systemOfUnitConversionMultiplier =  this.getReferenceUnit()
				.getToSystemOfUnitConversionMultiplier()
				.map(m -> m.get(unit.getSystemOfUnits()).getSecond())
				.orElseThrow(() -> new IllegalStateException("Non-null value needed for toSystemOfUnitConversionMultiplier"));
		return conversionFactorInMyUnits.multiply(systemOfUnitConversionMultiplier);
	}

}

