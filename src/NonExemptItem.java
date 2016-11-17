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
	}

	public void setTaxTotal() {
		this.taxTotal = this.getSubtotal().multiply(new BigDecimal(tax.getBasicTax())).divide(new BigDecimal(100));
		this.taxTotal = this.roundTotal(this.taxTotal);
	}

	public BigDecimal getTaxTotal() {
		return this.taxTotal;
	}
}