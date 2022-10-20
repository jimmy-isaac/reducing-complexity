package package1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

public class SaleLineItem {

	private String id;
	private Product product;
	private Quantity qty;
	private double taxRate;
	private Company company;

	public SaleLineItem(String id, Product product, Quantity qty, double taxRate, Company company) {
		super();
		this.id = id;
		this.product = product;
		this.qty = qty;
		this.taxRate = taxRate;
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	public String getId() {
		return id;
	}

	public Product getProduct() {
		return product;
	}

	public Quantity getQty() {
		return qty;
	}

	public Quantity getQtyInPrimaryReferenceUnits() {
		SystemOfUnits primarySystemOfUnits = company.getPrimarySystemOfUnits();
		BigDecimal multiplier = qty.getUnits().getToReferenceUnitMultiplier();
		Map<SystemOfUnits, Pair<Unit,BigDecimal>> refUnit = qty.getUnits().getReferenceUnit().getToSystemOfUnitConversionMultiplier().orElseThrow(() -> new IllegalStateException("Reference unit needs converison multiplier map"));
		BigDecimal multiplierToOther = refUnit.get(primarySystemOfUnits).getSecond();
		Unit primaryUnit = refUnit.get(primarySystemOfUnits).getFirst();

		return new Quantity(qty.getQuantity().multiply(multiplier).multiply(multiplierToOther).setScale(4, RoundingMode.HALF_EVEN), primaryUnit);
	}

	public BigDecimal calculateNetSalePrice() {
		BigDecimal myqty = qty.getQuantity();
		BigDecimal unitPriceConverted = getProduct().getUnitSalePrice().toUnits(qty.getUnits());

		BigDecimal result = myqty.multiply(unitPriceConverted).setScale(2, RoundingMode.HALF_EVEN);
		return result;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public BigDecimal getTax() {
		return this.calculateNetSalePrice().multiply(new BigDecimal(taxRate / 100.0)).setScale(2,
				RoundingMode.HALF_EVEN);
	}

	public static void main(String args[]) {
		test1();
		test2();
		test3();
	}

	public static void test1() {
		Product product1 = new Product("1", "product 1", new UnitPrice(BigDecimal.valueOf(1), Unit.KILOGRAM),
				new Quantity(BigDecimal.valueOf(1), Unit.KILOGRAM));
		SaleLineItem saleLineItem = new SaleLineItem("1", product1,
				new Quantity(BigDecimal.valueOf(1.0), Unit.KILOGRAM), 3, Company.ABC_COMPANY);
		BigDecimal result = saleLineItem.calculateNetSalePrice();
		BigDecimal expected = new BigDecimal(2.2046).setScale(2, RoundingMode.HALF_EVEN);
		int match = result.compareTo(expected.setScale(2, RoundingMode.HALF_EVEN));

		System.out.printf("result:%s expected:%s match:%s%n", result, expected, match);

	}

	public static void test2() {
		Product product1 = new Product("1", "product 1", new UnitPrice(BigDecimal.valueOf(2), Unit.KILOGRAM),
				new Quantity(BigDecimal.valueOf(1), Unit.KILOGRAM));
		SaleLineItem saleLineItem = new SaleLineItem("1", product1,
				new Quantity(BigDecimal.valueOf(2.0), Unit.KILOGRAM), 3, Company.LMN_INTERNATIONAL);
		BigDecimal result = saleLineItem.calculateNetSalePrice();
		BigDecimal expected = new BigDecimal(2.2046).setScale(2, RoundingMode.HALF_EVEN);
		int match = result.compareTo(expected.setScale(2, RoundingMode.HALF_EVEN));

		System.out.printf("result:%s expected:%s match:%s%n", result, expected, match);

	}

	public static void test3() {
		Product product1 = new Product("1", "product 1", new UnitPrice(BigDecimal.valueOf(2), Unit.KILOMETER),
				new Quantity(BigDecimal.valueOf(1), Unit.KILOGRAM));
		SaleLineItem saleLineItem = new SaleLineItem("1", product1,
				new Quantity(BigDecimal.valueOf(2.0), Unit.KILOGRAM), 3, Company.XYZ_COMPANY);

		BigDecimal result;
		try {
			result = saleLineItem.calculateNetSalePrice();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Throws illegal state exception as expected");
		}

	}

}
