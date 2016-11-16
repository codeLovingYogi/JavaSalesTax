import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

public class NonExemptItem extends TransactionItem implements TaxedItem {

	private BigDecimal taxTotal;
	private TaxRates tax = new TaxRates();


	public NonExemptItem(int quantity, String name, BigDecimal price, boolean exempt, boolean imported) {
		super(quantity, name, price, exempt, imported);
		System.out.println("NonExemptItem created");
	}

	public void setTaxTotal() {
	
		//System.out.println("Calculating sales tax");
		
		this.taxTotal = this.getSubtotal().multiply(new BigDecimal(tax.getBasicTax())).divide(new BigDecimal(100));
		System.out.println(this.getName());	
		System.out.println(this.getPrice());		
		System.out.println("sales tax: " + this.taxTotal);
	}

	public BigDecimal getTaxTotal() {
		return this.taxTotal;
	}
}