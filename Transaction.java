import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

public class Transaction {
	private List<Product> transactionItems = new ArrayList<Product>();
	// self.tax_rates = TaxRates(basic_tax, import_tax)
	private List<String> exemptItems = new ArrayList<String>(
					Arrays.asList("book", "chocolate", "pills"));
	// self.sales_taxes = 0
	// self.import_taxes = 0
	// self.subtotal = 0

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
			System.out.println(quantity);
			System.out.println(name);
			System.out.println(price);
			System.out.println(exempt);
			System.out.println(imported);

			Product product = new Product(name, price, exempt, imported);
			this.transactionItems.add(product);
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

	public static void main (String[] args) throws FileNotFoundException {
		Tests.checkTransaction();
		Tests.checkProduct();
		// String file = "Input1.txt";
		String file = "Input2.txt";
		// String file = "Input3.txt";
		Transaction t1 = new Transaction();
		// t1.setExemptItem("test");
		// System.out.println(t1.getExemptItems());

		// t1.setExemptItem("book");
		// t1.setExemptItem("book");
		List<String> items = t1.scanItems(file);
		//System.out.println(items);
		t1.setTransactionItems(items);

	}
}

class Product {
	private String name;
	private BigDecimal price;
	private boolean exempt;
	private boolean imported;

	public Product(String name, BigDecimal price, boolean exempt, boolean imported) {
		this.name = name;
		this.price = price;
		this.exempt = exempt;
		this.imported = imported;
		// System.out.println("A product has been created");
	}

	public String getName() {
		return this.name;
	}

	public BigDecimal getPrice() {
		return this.price;
	}
}

class Tests {
	public static void checkTransaction () throws FileNotFoundException {
		List<String> expectedItems;
		String expectedItem;
		int expectedInt;
		boolean expectedBoolean;
		BigDecimal expectedAmount;
		// Contents of Input3.txt:
		// 1 imported bottle of perfume at 27.99
		// 1 bottle of perfume at 18.99
		// 1 packet of headache pills at 9.75
		// 1 box of imported chocolates at 11.25
		String file = "Input3.txt";
		Transaction testTransaction = new Transaction();
		
		List<String> scanned = testTransaction.scanItems(file);
		expectedInt = 4;
		if(scanned.size() != expectedInt) {
			System.out.println("Failed test: scanItems - number of items, "
				+ "Expected: " + expectedInt + ", Actual: " + scanned.size());
		}

		expectedItems = new ArrayList<String>(
			Arrays.asList("1 imported bottle of perfume at 27.99", "1 bottle of perfume at 18.99", "1 packet of headache pills at 9.75", "1 box of imported chocolates at 11.25"));
		if(!testTransaction.scanItems(file).equals(expectedItems)) {
			System.out.println("Failed test: scanItems - items, "
				+ "Expected: " + expectedItems + ", Actual: " + testTransaction.scanItems(file));
		}

		expectedItem = "New exempt item";
		testTransaction.setExemptItem(expectedItem);
		if(!testTransaction.getExemptItems().contains(expectedItem)) {
			System.out.println("Failed test: setExemptItem, "
				+ "Expected: " + expectedItem + ", Actual: " + testTransaction.getExemptItems());
		}

		expectedBoolean = true;
		if(testTransaction.isExempt("box of imported chocolates") != expectedBoolean) {
			System.out.println("Failed test: isExempt, "
				+ "Expected: " + expectedBoolean + ", Actual: " + testTransaction.isExempt("box of imported chocolates"));
		}

		if(testTransaction.isImported("box of imported chocolates") != expectedBoolean) {
			System.out.println("Failed test: isImported, "
				+ "Expected: " + expectedBoolean + ", Actual: " + testTransaction.isImported("box of imported chocolates"));
		}

		testTransaction.setTransactionItems(scanned);
		List<Product> txnItems = testTransaction.getTransactionItems();
		expectedAmount = new BigDecimal("67.98");
		BigDecimal sum = new BigDecimal(0);
		// System.out.println(testTransaction.getTransactionItems()); 
		for(Product each: txnItems) {
			sum = sum.add(each.getPrice());
			// System.out.println(each.getName());
			// System.out.println(each.getPrice());
		}
		if(!sum.equals(expectedAmount)) {
			System.out.println("Failed test: setTransactionItems, "
				+ "Expected total: " + expectedAmount + ", Actual: " + sum);
		}
		// System.out.println(sum);
	}

	public static void checkProduct () {
		String expectedString;
		BigDecimal expectedPrice;
		// Product:
		// 1 imported bottle of perfume: 54.65
		Product testProduct = new Product("imported bottle of perfume", new BigDecimal("54.65"), false, true);
		expectedString = "imported bottle of perfume";
		if(!testProduct.getName().equals(expectedString)) {
			System.out.println("Failed test: getProductInfo - name, "
				+ "Expected: " + expectedString + ", Actual: " + testProduct.getName());
		}
		expectedPrice = new BigDecimal("54.65");
		if(!testProduct.getPrice().equals(expectedPrice)) {
			System.out.println("Failed test: getProductInfo - price, "
				+ "Expected: " + expectedPrice + ", Actual: " + testProduct.getPrice());
		}
	}
}