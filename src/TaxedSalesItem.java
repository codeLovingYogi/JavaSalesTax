import java.math.BigDecimal;

/**
 * The TaxedSalesItem class is an interface class for sales items requiring the 
 * calculation of sales tax.
 */

public interface TaxedSalesItem {
	void setSalesTax();

	BigDecimal getSalesTax();
}