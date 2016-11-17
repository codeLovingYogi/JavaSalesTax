import java.math.BigDecimal;

public class NonExemptImportItem extends TransactionItem implements TaxedSalesItem, TaxedImportItem {

	private BigDecimal salesTax;
	private BigDecimal importTax;
	private BigDecimal taxTotal;
	private TaxRates tax = new TaxRates();


	public NonExemptImportItem(int quantity, String name, BigDecimal price, boolean exempt, boolean imported) {
		super(quantity, name, price, exempt, imported);
	}

	public void setSalesTax() {
		this.salesTax = this.getSubtotal().multiply(new BigDecimal(tax.getBasicTax())).divide(new BigDecimal(100));	
	}

	public void setImportTax() {
		this.importTax = this.getSubtotal().multiply(new BigDecimal(tax.getImportTax())).divide(new BigDecimal(100));
	}

	public void setTaxTotal() {
		this.setSalesTax();
		this.setImportTax();
		this.taxTotal = this.salesTax.add(this.importTax);
		this.taxTotal = this.roundTotal(this.taxTotal);	
	}

	public BigDecimal getSalesTax() {
		return this.salesTax;
	}

	public BigDecimal getImportTax() {
		return this.importTax;
	}

	public BigDecimal getTaxTotal() {
		return this.taxTotal;
	}
}