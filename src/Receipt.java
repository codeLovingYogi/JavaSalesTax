import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Receipt {
	private TaxRates taxRates = new TaxRates();
	private List<Product> items = new ArrayList<Product>();
	private BigDecimal totalTransactionTax = new BigDecimal(0);
	private BigDecimal totalSalesTax = new BigDecimal(0);
	private BigDecimal totalImportTax = new BigDecimal(0);
	private BigDecimal receiptSubtotal = new BigDecimal(0);

	public Receipt(List<Product> items) {
		this.items = items;
	}

	public void setTotalTransactionTax() {
		for(Product item: this.items){
			if (item instanceof ImportItem) {
				item.setTaxTotal();
				this.totalTransactionTax = this.totalTransactionTax.add(((ImportItem)item).getTaxTotal());
			} else if (item instanceof NonExemptItem) {
				((NonExemptItem)item).setTaxTotal();
				this.totalTransactionTax = this.totalTransactionTax.add(((NonExemptItem)item).getTaxTotal());
			} else if (item instanceof NonExemptImportItem) {
				((NonExemptImportItem)item).setTaxTotal();
				this.totalTransactionTax = this.totalTransactionTax.add(((NonExemptImportItem)item).getTaxTotal());
			}
		}
	}


	public void setTotalSalesTax() {
		for(Product item: this.items){
			if (item instanceof NonExemptItem) {
				((NonExemptItem)item).setTaxTotal();
				this.totalSalesTax = this.totalSalesTax.add(((NonExemptItem)item).getTaxTotal());
			}
			if (item instanceof NonExemptImportItem) {
				((NonExemptImportItem)item).setTaxTotal();
				this.totalSalesTax = this.totalSalesTax.add(((NonExemptImportItem)item).getTaxTotal());
			}
		}
	}

	public void setTotalImportTax() {
		for(Product item: this.items){
			if (item instanceof ImportItem) {
				item.setTaxTotal();
				this.totalImportTax = this.totalImportTax.add(((ImportItem)item).getTaxTotal());
			}
		}
	}

	public void setReceiptSubtotal() {
		for(Product item: this.items){
			((TransactionItem)item).setSubtotal();
			this.receiptSubtotal = this.receiptSubtotal.add(((TransactionItem)item).getSubtotal());
		}
	}

	public void printReceipt() {
		BigDecimal amount;
		BigDecimal amount_rounded;
		double centss;

		for(Product item: this.items) {
			if (item instanceof ImportItem) {
				amount = ((ImportItem)item).getSubtotal().add(((ImportItem)item).getTaxTotal());
				amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);				
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
		BigDecimal salesTax = this.getTotalTransactionTax();
		salesTax = salesTax.setScale(2, BigDecimal.ROUND_HALF_UP);
		System.out.println("Sales Taxes: " + salesTax);
		BigDecimal total = this.getTotalTransactionTax().add(this.getReceiptSubtotal());
		total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
		System.out.println("Total: " + total);
	}

	public BigDecimal getTotalTransactionTax() {
		return this.totalTransactionTax;

	}

	public BigDecimal getTotalSalesTax() {
		return this.totalSalesTax;

	}

	public BigDecimal getTotalImportTax() {
		return this.totalImportTax;

	}

	public BigDecimal getReceiptSubtotal() {
		return this.receiptSubtotal;
	}
}