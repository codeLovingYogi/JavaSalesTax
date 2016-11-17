import java.math.BigDecimal;

public class ImportItem extends TransactionItem implements TaxedItem {

	private BigDecimal taxTotal;
	private TaxRates tax = new TaxRates();

	public ImportItem(int quantity, String name, BigDecimal price, boolean exempt, boolean imported) {
		super(quantity, name, price, exempt, imported);
	}

	public void setTaxTotal() {
		this.taxTotal = this.getSubtotal().multiply(new BigDecimal(tax.getImportTax())).divide(new BigDecimal(100));
		this.taxTotal = this.roundTotal(this.taxTotal);
	}

	public BigDecimal getTaxTotal() {
		return this.taxTotal;
	}
}