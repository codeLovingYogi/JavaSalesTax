import java.math.BigDecimal;

/**
 * The NonExemptItem class is derived from the Transaction Item and Product 
 * classes. 
 * 
 * It implements the TaxedSalesItem interface to handle the calculation 
 * of sales and import taxes for the tax total needed to print a receipt.
 */

public class NonExemptItem extends TransactionItem implements TaxedSalesItem {

	private BigDecimal salesTax;
	private BigDecimal taxTotal;
	private TaxRates tax = new TaxRates();

	// Child class of TransactionItem, instances created from Tranaction class
	public NonExemptItem(int quantity, String name, BigDecimal price, boolean exempt, boolean imported) {
		super(quantity, name, price, exempt, imported);
	}

	public void setSalesTax() {
		this.salesTax = this.getSubtotal().multiply(new BigDecimal(tax.getBasicTax())).divide(new BigDecimal(100));
	};

	public void setTaxTotal() {
		this.setSalesTax();
		this.taxTotal = this.roundTotal(this.salesTax);
	}

	public BigDecimal getSalesTax() {
		return this.salesTax;
	}

	public BigDecimal getTaxTotal() {
		return this.taxTotal;
	}
}