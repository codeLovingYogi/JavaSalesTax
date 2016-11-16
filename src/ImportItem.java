import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

public class ImportItem extends TransactionItem implements TaxedItem {

	private BigDecimal taxTotal;
	private TaxRates tax = new TaxRates();
	//private BigDecimal subtotal;

	public ImportItem(int quantity, String name, BigDecimal price, boolean exempt, boolean imported) {
		super(quantity, name, price, exempt, imported);
		System.out.println("ImportItem created");
	}

	public void setTaxTotal() {
		this.taxTotal = this.getSubtotal().multiply(new BigDecimal(tax.getImportTax())).divide(new BigDecimal(100));
		System.out.println(this.getName());	
		System.out.println(this.getPrice());		
		System.out.println("import tax: " + this.taxTotal);
	}

	public BigDecimal getTaxTotal() {
		return this.taxTotal;
	}
}