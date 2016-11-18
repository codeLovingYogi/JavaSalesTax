import java.math.BigDecimal;

/**
 * The TaxedImportItem class is an interface class for imported items requiring 
 * the calculation of import tax.
 */

public interface TaxedImportItem {	
	void setImportTax();

	BigDecimal getImportTax();
}