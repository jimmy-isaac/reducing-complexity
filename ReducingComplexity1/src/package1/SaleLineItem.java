package package1;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SaleLineItem {

	private String id;
	private String productId;
	private Quantity qty;
	private UnitPrice unitSalePrice;
	private double taxRate;

	public SaleLineItem(String id, String productId, Quantity qty, UnitPrice unitSalePrice, double taxRate) {
		super();
		this.id = id;
		this.productId = productId;
		this.qty = qty;
		this.unitSalePrice = unitSalePrice;
		this.taxRate = taxRate;
	}

	public String getId() {
		return id;
	}

	public String getProductId() {
		return productId;
	}

	public Quantity getQty() {
		return qty;
	}

	public UnitPrice getUnitSalePrice() {
		return unitSalePrice;
	}

	public BigDecimal calculateNetSalePrice() {
		BigDecimal myqty = qty.getQuantity();
		BigDecimal unitPriceConverted = unitSalePrice.toUnits(qty.getUnits());

		BigDecimal result = myqty.multiply(unitPriceConverted).setScale(2, RoundingMode.HALF_EVEN);
		return result;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public BigDecimal getTax() {
		return this.calculateNetSalePrice().multiply(new BigDecimal(taxRate / 100.0)).setScale(2, RoundingMode.HALF_EVEN);
	}

	public static void main(String args[]) {
		test1();
		test2();
		test3();
	}
	// test 1
	public static void test1() {
		SaleLineItem saleLineItem = new SaleLineItem("1", "123", new Quantity(BigDecimal.valueOf(1.0), Unit.KILOGRAM),
				new UnitPrice(new BigDecimal(1.0), Unit.POUND), 3);
		BigDecimal result = saleLineItem.calculateNetSalePrice();
		BigDecimal expected = new BigDecimal(2.2046).setScale(2, RoundingMode.HALF_EVEN);
		int match = result.compareTo(expected.setScale(2, RoundingMode.HALF_EVEN));

		System.out.printf("result:%s expected:%s match:%s%n", result, expected, match);
	}
		// test 2
	public static void test2() {
		SaleLineItem saleLineItem = new SaleLineItem("2", "123", new Quantity(BigDecimal.valueOf(2.0), Unit.KILOGRAM),
				new UnitPrice(new BigDecimal(2.0), Unit.POUND), 3);
		BigDecimal result = saleLineItem.calculateNetSalePrice();
		BigDecimal expected = new BigDecimal(8.8184).setScale(2, RoundingMode.HALF_EVEN);
		int match = result.compareTo(expected.setScale(2, RoundingMode.HALF_EVEN));

		System.out.printf("result:%s expected:%s match:%s%n", result, expected, match);
	}
	public static void test3() {
		// test 3
		try {
			SaleLineItem saleLineItem = new SaleLineItem("2", "123",
					new Quantity(BigDecimal.valueOf(2.0), Unit.KILOMETER),
					new UnitPrice(new BigDecimal(2.0), Unit.POUND), 3);
			saleLineItem.calculateNetSalePrice();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			System.out.println("Throws illegal state exception as expected");
		}

	}

}
