import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Receipt {
	private TaxRates taxRates = new TaxRates();
	private List<Product> items = new ArrayList<Product>();
	private BigDecimal totalSalesTax = new BigDecimal(0);
	private BigDecimal totalImportTax = new BigDecimal(0);
	private BigDecimal receiptSubtotal = new BigDecimal(0);

	public Receipt(List<Product> items) {
		this.items = items;
		System.out.println("in receipt");
	}

	public void setTotalSalesTax() {
		for(Product item: this.items){
			if (item instanceof NonExemptItem) {
				((NonExemptItem)item).setTaxTotal();
				this.totalSalesTax = this.totalSalesTax.add(((NonExemptItem)item).getTaxTotal());
			}
			if (item instanceof NonExemptImportItem) {
				((NonExemptImportItem)item).setTaxTotal();
				this.totalSalesTax = this.totalSalesTax.add(((NonExemptImportItem)item).getSalesTax());
			}
			// item.setTaxTotal();
			// this.totalSalesTax = this.totalSalesTax.add(item.getTaxTotal());
		}
		System.out.println("sales tax total" + this.getTotalSalesTax());

	}

	public void setTotalImportTax() {
		for(Product item: this.items){
			if (item instanceof ImportItem) {
				// ((ImportItem)item).setTaxTotal();
				item.setTaxTotal();
				this.totalImportTax = this.totalImportTax.add(((ImportItem)item).getTaxTotal());
			}
			if (item instanceof NonExemptImportItem) {
				// ((NonExemptImportItem)item).setTaxTotal();
				item.setTaxTotal();
				this.totalImportTax = this.totalImportTax.add(((NonExemptImportItem)item).getImportTax());
			}
		}
		System.out.println("import tax total" + this.getTotalImportTax());

	}

	public void setReceiptSubtotal() {
		// System.out.println("setReceiptSubtotal");
		for(Product item: this.items){
			((TransactionItem)item).setSubtotal();
			this.receiptSubtotal = this.receiptSubtotal.add(((TransactionItem)item).getSubtotal());
			System.out.println("receipt subtotal" + this.getReceiptSubtotal());
		}
	}

	public void printReceipt() {
		System.out.println("Printing receipt");
		BigDecimal amount;
		BigDecimal amount_rounded;
		double centss;

		for(Product item: this.items) {
			if (item instanceof ImportItem) {
				amount = ((ImportItem)item).getSubtotal().add(((ImportItem)item).getTaxTotal());
				amount = amount.setScale(4, BigDecimal.ROUND_HALF_UP);
				// cents = amount.setScale(4, RoundingMode.CEILING);
				amount_rounded = amount.multiply(new BigDecimal(0.05));
				amount_rounded = amount_rounded.divide(new BigDecimal(5));
				amount_rounded = amount_rounded.setScale(2, RoundingMode.CEILING);
				amount_rounded = amount_rounded.multiply(new BigDecimal(5));
				amount_rounded = amount_rounded.add(((ImportItem)item).getSubtotal());

				// cents = Math.ceil(amount.multiply(new BigDecimal(0.05)));

				System.out.println(amount_rounded);
				System.out.println(((ImportItem)item).getQuantity() + " "
					+ item.getName() + ": "
					+ amount);
			}
			else if (item instanceof NonExemptItem) {
				amount = ((NonExemptItem)item).getSubtotal().add(((NonExemptItem)item).getTaxTotal());
				amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
				System.out.println(((NonExemptItem)item).getQuantity() + " "
					+ item.getName() + ": "
					+ amount);
			}
			else if (item instanceof NonExemptImportItem) {
				amount = ((NonExemptImportItem)item).getSubtotal().add(((NonExemptImportItem)item).getTaxTotal());
				amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
				System.out.println(((NonExemptImportItem)item).getQuantity() + " "
					+ item.getName() + ": "
					+ amount);
			} 
			else {
				System.out.println(((TransactionItem)item).getQuantity() + " "
					+ item.getName() + ": "
					+ ((TransactionItem)item).getSubtotal());;
			}
		}
		BigDecimal salesTax = this.getTotalSalesTax().add(this.getTotalImportTax());
		System.out.println("Sales Taxes: " + salesTax);
		BigDecimal total = this.getTotalSalesTax().add(this.getTotalImportTax()).add(this.getReceiptSubtotal());
		System.out.println("Total: " + total);
	}

	public BigDecimal getTotalSalesTax() {
		// System.out.println("getReceiptSubtotal");
		return this.totalSalesTax;

	}

	public BigDecimal getTotalImportTax() {
		// System.out.println("getReceiptSubtotal");
		return this.totalImportTax;

	}

	public BigDecimal getReceiptSubtotal() {
		// System.out.println("getReceiptSubtotal");
		return this.receiptSubtotal;

	}
}