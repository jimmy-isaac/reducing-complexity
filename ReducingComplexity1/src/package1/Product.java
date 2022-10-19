package package1;

public class Product {
	
	private String id;
	private String description;
	private UnitPrice unitSalePrice;
	private Quantity qtyInStock;
	
	public Product(String id, String description, UnitPrice unitSalePrice, Quantity qtyInStock) {
		super();
		this.id = id;
		this.description = description;
		this.unitSalePrice = unitSalePrice;
		this.qtyInStock = qtyInStock;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UnitPrice getUnitSalePrice() {
		return unitSalePrice;
	}

	public void setUnitsalePrice(UnitPrice unitSalePrice) {
		this.unitSalePrice = unitSalePrice;
	}

	public Quantity getQtyInStock() {
		return qtyInStock;
	}

	public void setQtyInStock(Quantity qtyInStock) {
		this.qtyInStock = qtyInStock;
	}
	
	
	
	
	


}
