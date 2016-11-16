import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

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
			// if (item instanceof NonExemptItem) {
			// 	((NonExemptItem)item).setTaxTotal();
			// 	this.totalSalesTax = this.totalSalesTax.add(((NonExemptItem)item).getTaxTotal());
			// }
			// if (item instanceof NonExemptImportItem) {
			// 	((NonExemptImportItem)item).setTaxTotal();
			// 	this.totalSalesTax = this.totalSalesTax.add(((NonExemptImportItem)item).getSalesTax());
			// }
			item.setTaxTotal();
			this.totalSalesTax = this.totalSalesTax.add(item.getTaxTotal());
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
		for(Product item: this.items) {
			System.out.println(item.getName());
			if (item instanceof ImportItem) {
				System.out.println(((ImportItem)item).getQuantity());
				System.out.println(((ImportItem)item).getSubtotal());
				System.out.println(((ImportItem)item).getTaxTotal());

			}
			if (item instanceof NonExemptItem) {
				System.out.println(((NonExemptItem)item).getQuantity());
				System.out.println(((NonExemptItem)item).getSubtotal());
				System.out.println(((NonExemptItem)item).getTaxTotal());

			}
			if (item instanceof NonExemptImportItem) {
				System.out.println(((NonExemptImportItem)item).getQuantity());
				System.out.println(((NonExemptImportItem)item).getSubtotal());
				System.out.println(((NonExemptImportItem)item).getTaxTotal());

			}
		}
		System.out.println(this.getReceiptSubtotal());
		System.out.println(this.getTotalSalesTax());
		System.out.println(this.getTotalImportTax());
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