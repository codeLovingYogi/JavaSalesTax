public class TaxRates {
	private double basicTax;
	private double importTax;

	public TaxRates() {
		this.basicTax = 10.0;
		this.importTax = 5.0;
	}

	public void setBasicTax(double percent) {
		this.basicTax = percent;
	}

	public void setImportTax(double percent) {
		this.importTax = percent;
	}

	public double getBasicTax() {
		return this.basicTax;
	}

	public double getImportTax() {
		return this.importTax;
	}
}
