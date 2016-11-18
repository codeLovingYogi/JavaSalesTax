import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

/**
 * The Transaction class handles the scanning items from a transaction
 * and storing a list of transaction items, similar to a shopping cart.
 * 
 * It also tracks the items exempt from sales tax and allows for the update
 * of these exempt items.
 * 
 * It contains helper methods to determine whether a transaction item is
 * subject to sales and/or import tax.
 */

public class Transaction {
	private List<Product> transactionItems = new ArrayList<Product>();
	private List<String> exemptItems = new ArrayList<String>(
					Arrays.asList("book", "chocolate", "pills"));

	/**
     * The scanItems methods reads transaction items from an input file.
     */
	public static List<String> scanItems(String file) throws FileNotFoundException {
		List<String> scannedItems = new ArrayList<String>();
		Scanner scan = new Scanner(new File(file)).useDelimiter("\\n");
		while(scan.hasNext()) {
			scannedItems.add(scan.next());
		}
		scan.close();
		return scannedItems;
	}

	/**
     * The setTransactionItems method parses each line from input file to 
     * extract information to create product subclasses for the transaction.
     */
	public void setTransactionItems(List<String> scannedItems) {
		for(String item: scannedItems) {
			int quantity;
			boolean imported;
			boolean exempt;
			List<String> getProductName = new ArrayList<String>();
			BigDecimal price = null;

			// Find quantity of product
			String[] info = item.split("\\s+");
			quantity = Integer.parseInt(info[0]);

			// Find name and price of product
			for (int i = 1; i < info.length; i++) {
				if(!info[i].equals("at")) {
					getProductName.add(info[i]);
				} else {
					price = new BigDecimal(info[i + 1]);
					break;
				}
			}
			String name = String.join(" ", getProductName);
			// Determine if product is exempt from sales tax
			exempt = this.isExempt(name);
			// Determine if product is subject to import tax
			imported = this.isImported(name);

			// Create transaction item subclasses for each product
			if(!exempt && imported) {
				NonExemptImportItem txnItem = new NonExemptImportItem(quantity, name, price, exempt, imported);
				txnItem.setSubtotal();
				this.transactionItems.add(txnItem);
			} else if(!exempt) {
				NonExemptItem txnItem = new NonExemptItem(quantity, name, price, exempt, imported);
				txnItem.setSubtotal();
				this.transactionItems.add(txnItem);
			} else if(imported) {
				ImportItem txnItem = new ImportItem(quantity, name, price, exempt, imported);
				txnItem.setSubtotal();
				this.transactionItems.add(txnItem);
			} else if(exempt){
				TransactionItem txnItem = new TransactionItem(quantity, name, price, exempt, imported);
				txnItem.setSubtotal();
				this.transactionItems.add(txnItem);
			}
		}
	}

	/**
     * Helper method to determine if product is subject to sales tax 
     * as per exempt items list.
     */
	boolean isExempt(String name) {
		for(String item: exemptItems) {
			if (name.toLowerCase().contains(item.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
     * Helper method to determine if product is subject to import tax.
     */
	boolean isImported(String name) {
		if (name.toLowerCase().contains("imported")) {
			return true;
		}
		return false;
	}

	/**
     * Add exempt items to list.
     */
	public List<String> setExemptItem(String item) {
		this.exemptItems.add(item);
		return this.exemptItems;
	}

	public List<String> getExemptItems() {
		return this.exemptItems;
	}

	public List<Product> getTransactionItems() {
		return this.transactionItems;
	}
}
