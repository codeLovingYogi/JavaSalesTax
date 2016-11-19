import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * The Receipt class performs the total calculations for each transaction to
 * generate a receipt. It produces the output requested for a transaction. 
 * 
 * Below is sample output:
 * 
 * 1 book : 12.49
 * 1 music CD: 16.49
 * 1 chocolate bar: 0.85
 * Sales Taxes: 1.50
 * Total: 29.83
 */

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

	/**
     * This method is used to get the tax total from each transaction
     * to calculate the total tax amount for transaction.
     */
	private void setTotalTransactionTax() {
		for(Product item: this.items){
			item.setTaxTotal();
			this.totalTransactionTax = this.totalTransactionTax.add(item.getTaxTotal());
		}
	}

	/**
     * This method is used to get the subtotal from each transaction
     * to calculate the total subtotal amount for transaction.
     */
	private void setReceiptSubtotal() {
		for(Product item: this.items){
			((TransactionItem)item).setSubtotal();
			this.receiptSubtotal = this.receiptSubtotal.add(((TransactionItem)item).getSubtotal());
		}
	}

	/**
     * This is the main method used to print a receipt with out to display the 
     * items of transaction, total price for each items, sales taxes for the
     * transaction, and total transaction amount. 
     */
	public void printReceipt() {
		BigDecimal amount;
		BigDecimal salesTax;
		BigDecimal total;
		
		this.setReceiptSubtotal();
		this.setTotalTransactionTax();

		/**
	     * For each item, add the subtotal and tax total together.
	     * Displays the quantity, item name, and total amount for item.
	     */	
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
		// Display the total sales and import taxes for all transaction items
		salesTax = roundTotal(this.getTotalTransactionTax());
		System.out.println("Sales Taxes: " + salesTax);
		// Display the total amount for transaction
		total = roundTotal(this.getTotalTransactionTax().add(this.getReceiptSubtotal()));
		System.out.println("Total: " + total);
	}

	/**
     * The roundTotal method performs rounding for prices stored as BigDecimal.
     */
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