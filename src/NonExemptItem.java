import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class NonExemptItem extends TransactionItem implements TaxedItem {

	private BigDecimal taxTotal;
	private TaxRates tax = new TaxRates();


	public NonExemptItem(int quantity, String name, BigDecimal price, boolean exempt, boolean imported) {
		super(quantity, name, price, exempt, imported);
		// System.out.println("NonExemptItem created");
	}

	public void setTaxTotal() {
	
		//System.out.println("Calculating sales tax");
		BigDecimal amount_rounded;

		this.taxTotal = this.getSubtotal().multiply(new BigDecimal(tax.getBasicTax())).divide(new BigDecimal(100));
		amount_rounded = new BigDecimal(Math.ceil(this.taxTotal.doubleValue() * 20) / 20);
		// amount_rounded.setScale(2, RoundingMode.HALF_UP);
		this.taxTotal = amount_rounded;
		
		// BigDecimal roundedTax = this.taxTotal;
		// //System.out.println(roundedTax);

		// roundedTax = roundedTax.multiply(new BigDecimal(0.05));
		// roundedTax = roundedTax.divide(new BigDecimal(5));
		// roundedTax = roundedTax.setScale(2, RoundingMode.CEILING);
		// roundedTax = roundedTax.multiply(new BigDecimal(5));
		// System.out.println("rounded: " + roundedTax);

		// this.taxTotal = roundedTax;

		// System.out.println(this.getName());	
		// System.out.println(this.getPrice());		
		// System.out.println("sales tax: " + this.taxTotal);
	}

	public BigDecimal getTaxTotal() {
		return this.taxTotal;
	}
}