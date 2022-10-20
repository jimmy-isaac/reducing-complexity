package package1;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Sale {
	
	private String id;
	private Company company;
	private SaleType saleType;
	


	
	public Sale(String id, Company company, SaleType saleType) {
		super();
		this.id = id;
		this.company = company;
		this.saleType = saleType;
	}




	public String getId() {
		return id;
	}




	public Company getCompany() {
		return company;
	}






	public SaleType getSaleType() {
		return saleType;
	}




	public BigDecimal calculateGrossPrice(List<SaleLineItem> saleItems) {
		BigDecimal totalPrice = BigDecimal.valueOf(0);
		BigDecimal totalTax = BigDecimal.valueOf(0);
		for (SaleLineItem item: saleItems) {
			totalPrice.add(item.calculateNetSalePrice());
			totalTax.add(item.getTax());
		}
		Optional<BigDecimal> additionalLineItem = getSaleType().addLineItems(totalPrice);
		BigDecimal grossPrice = totalPrice.add(totalTax).add(additionalLineItem.orElse(BigDecimal.ZERO));
		return grossPrice;

	}

}
