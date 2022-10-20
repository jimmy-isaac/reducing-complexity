package package1;

import java.math.BigDecimal;
import java.util.Optional;

public enum SaleType {
	ONLINE {Optional<BigDecimal> addLineItems(BigDecimal netPrice){return Optional.of(BigDecimal.valueOf(3.99));}},
	PHYSICAL {Optional<BigDecimal> addLineItems(BigDecimal netPrice){return Optional.empty();}};
	
	
	abstract  Optional<BigDecimal> addLineItems(BigDecimal netPrice);

}
