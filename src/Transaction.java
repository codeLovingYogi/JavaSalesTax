import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

public class Transaction {
	private List<Product> transactionItems = new ArrayList<Product>();
	private List<String> exemptItems = new ArrayList<String>(
					Arrays.asList("book", "chocolate", "pills"));

	public static List<String> scanItems(String file) throws FileNotFoundException {
		List<String> scannedItems = new ArrayList<String>();
		Scanner scan = new Scanner(new File(file)).useDelimiter("\\n");
		while(scan.hasNext()) {
			scannedItems.add(scan.next());
		}
		scan.close();
		return scannedItems;
	}

	public List<String> setExemptItem(String item) {
		this.exemptItems.add(item);
		return this.exemptItems;
	}

	public void setTransactionItems(List<String> scannedItems) {
		for(String item: scannedItems) {
			//System.out.println(item);
			int quantity;
			boolean imported;
			boolean exempt;
			List<String> getProductName = new ArrayList<String>();
			BigDecimal price = null;

			String[] info = item.split("\\s+");
			quantity = Integer.parseInt(info[0]);

			// System.out.println(Arrays.toString(info));
			for (int i = 1; i < info.length; i++) {
				//System.out.println(info[i]);
				if(!info[i].equals("at")) {
					getProductName.add(info[i]);
				} else {
					price = new BigDecimal(info[i + 1]);
					break;
				}
			}
			String name = String.join(" ", getProductName);
			exempt = this.isExempt(name);
			imported = this.isImported(name);
			// System.out.println(quantity);
			// System.out.println(name);
			// System.out.println(price);
			// System.out.println(exempt);
			// System.out.println(imported);
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

	

	boolean isExempt(String name) {
		for(String item: exemptItems) {
			if (name.toLowerCase().contains(item.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	boolean isImported(String name) {
		if (name.toLowerCase().contains("imported")) {
			return true;
		}
		return false;
	}

	public List<String> getExemptItems() {
		return this.exemptItems;
	}

	public List<Product> getTransactionItems() {
		return this.transactionItems;
	}
}
