import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ImportItem extends TransactionItem implements TaxedItem {

	private BigDecimal taxTotal;
	private TaxRates tax = new TaxRates();
	//private BigDecimal subtotal;

	public ImportItem(int quantity, String name, BigDecimal price, boolean exempt, boolean imported) {
		super(quantity, name, price, exempt, imported);
		//System.out.println("ImportItem created");
	}

	public void setTaxTotal() {
		BigDecimal amount_rounded;

		this.taxTotal = this.getSubtotal().multiply(new BigDecimal(tax.getImportTax())).divide(new BigDecimal(100));
		amount_rounded = new BigDecimal(Math.ceil(this.taxTotal.doubleValue() * 20) / 20);
		// amount_rounded = this.taxTotal.multiply(new BigDecimal(20));
		// amount_rounded = amount_rounded.setScale(2, RoundingMode.CEILING);
		// amount_rounded = amount_rounded.divide(new BigDecimal(20));
		// amount_rounded.setScale(2, RoundingMode.HALF_UP);
		this.taxTotal = amount_rounded;
		// System.out.println(this.taxTotal);
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
		// System.out.println("import tax: " + this.taxTotal);
	}

	public BigDecimal getTaxTotal() {
		return this.taxTotal;
	}
}