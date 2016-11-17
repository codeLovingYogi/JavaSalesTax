import java.math.BigDecimal;

public class NonExemptItem extends TransactionItem implements TaxedSalesItem {

	private BigDecimal salesTax;
	private BigDecimal taxTotal;
	private TaxRates tax = new TaxRates();

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

	public BigDecimal getTaxTotal() {
		return this.taxTotal;
	}
}