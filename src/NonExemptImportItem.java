import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class NonExemptImportItem extends TransactionItem implements TaxedItem {

	private BigDecimal salesTax;
	private BigDecimal importTax;
	private BigDecimal taxTotal;
	private TaxRates tax = new TaxRates();


	public NonExemptImportItem(int quantity, String name, BigDecimal price, boolean exempt, boolean imported) {
		super(quantity, name, price, exempt, imported);
		// System.out.println("NonExemptImportItem created");
	}

	public void setSalesTax() {
		//System.out.println("Calculating import and sales tax");
		this.salesTax = this.getSubtotal().multiply(new BigDecimal(tax.getBasicTax())).divide(new BigDecimal(100));	
		
		// BigDecimal roundedTax = this.salesTax;
		// //System.out.println(roundedTax);

		// roundedTax = roundedTax.multiply(new BigDecimal(0.05));
		// roundedTax = roundedTax.divide(new BigDecimal(5));
		// roundedTax = roundedTax.setScale(2, RoundingMode.CEILING);
		// roundedTax = roundedTax.multiply(new BigDecimal(5));
		// System.out.println("rounded: " + roundedTax);

		// this.salesTax = roundedTax;
		// System.out.println("sales tax from nei item" + this.salesTax);
		// this.salesTax = this.salesTax.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setImportTax() {
		//System.out.println("Calculating import and sales tax");
		this.importTax = this.getSubtotal().multiply(new BigDecimal(tax.getImportTax())).divide(new BigDecimal(100));
	}

	public void setTaxTotal() {
		this.setSalesTax();
		this.setImportTax();
		this.taxTotal = this.salesTax.add(this.importTax);
		// System.out.println(this.;	
		// System.out.println(this.getPrice());	
		// System.out.println("import tax: " + this.importTax);		
		// System.out.println("sales tax: " + this.salesTax);	
		// System.out.println("total tax: " + this.taxTotal);		
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