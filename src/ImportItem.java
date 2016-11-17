import java.math.BigDecimal;

public class ImportItem extends TransactionItem implements TaxedImportItem {

	private BigDecimal importTax;
	private BigDecimal taxTotal;
	private TaxRates tax = new TaxRates();

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