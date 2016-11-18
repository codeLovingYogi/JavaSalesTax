import java.math.BigDecimal;

public class ImportItem extends TransactionItem implements TaxedImportItem {
	/**
	 * The ImportItem class is derived from the Transaction Item and Product classes.
	 * 
	 * It implements the TaxedImportItem interface to handle the calculation of 
	 * import taxes for the tax total needed to print a receipt.
	 */
	private BigDecimal importTax;
	private BigDecimal taxTotal;
	private TaxRates tax = new TaxRates();

	// Child class of TransactionItem, instances created from Transaction class
	public ImportItem(int quantity, String name, BigDecimal price, boolean exempt, boolean imported) {
		super(quantity, name, price, exempt, imported);
	}

	public void setImportTax() {
		this.importTax = this.getSubtotal().multiply(new BigDecimal(tax.getImportTax())).divide(new BigDecimal(100));
	};

	public void setTaxTotal() {
		this.setImportTax();
		this.taxTotal = this.roundTotal(this.importTax);
	}

	public BigDecimal getImportTax() {
		return this.taxTotal;
	}

	public BigDecimal getTaxTotal() {
		return this.taxTotal;
	}
}