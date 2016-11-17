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

	private void setTotalTransactionTax() {
		for(Product item: this.items){
			item.setTaxTotal();
			this.totalTransactionTax = this.totalTransactionTax.add(item.getTaxTotal());
		}
	}

	private void setReceiptSubtotal() {
		for(Product item: this.items){
			((TransactionItem)item).setSubtotal();
			this.receiptSubtotal = this.receiptSubtotal.add(((TransactionItem)item).getSubtotal());
		}
	}

	public void printReceipt() {
		BigDecimal amount;
		BigDecimal salesTax;
		BigDecimal total;
		
		this.setReceiptSubtotal();
		this.setTotalTransactionTax();

		for(Product item: this.items) {
			if (item instanceof ImportItem) {
				amount = ((ImportItem)item).getSubtotal().add(item.getTaxTotal());
				System.out.println(((ImportItem)item).getQuantity() + " "
					+ item.getName() + ": "
					+ amount);
			}
			else if (item instanceof NonExemptItem) {
				amount = ((NonExemptItem)item).getSubtotal().add(item.getTaxTotal());
				System.out.println(((NonExemptItem)item).getQuantity() + " "
					+ item.getName() + ": "
					+ amount);
			}
			else if (item instanceof NonExemptImportItem) {
				amount = ((NonExemptImportItem)item).getSubtotal().add(item.getTaxTotal());
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
		salesTax = roundTotal(this.getTotalTransactionTax());
		System.out.println("Sales Taxes: " + salesTax);
		total = roundTotal(this.getTotalTransactionTax().add(this.getReceiptSubtotal()));
		System.out.println("Total: " + total);
	}

	BigDecimal roundTotal(BigDecimal amount) {
		BigDecimal amount_rounded;
		return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getTotalTransactionTax() {
		return this.totalTransactionTax;
	}

	public BigDecimal getReceiptSubtotal() {
		return this.receiptSubtotal;
	}
}