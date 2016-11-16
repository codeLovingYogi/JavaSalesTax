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
				txnItem.calculateTax();
				this.transactionItems.add(txnItem);
			} else if(!exempt) {
				NonExemptItem txnItem = new NonExemptItem(quantity, name, price, exempt, imported);
				txnItem.calculateTax();
				this.transactionItems.add(txnItem);
			} else if(imported) {
				ImportItem txnItem = new ImportItem(quantity, name, price, exempt, imported);
				txnItem.calculateTax();
				this.transactionItems.add(txnItem);
			} else if(exempt){
				TransactionItem txnItem = new TransactionItem(quantity, name, price, exempt, imported);
				this.transactionItems.add(txnItem);
			}
		}
	}

	public void printReceipt() {
		System.out.println("Printing receipt");
		Receipt receipt = new Receipt(this.transactionItems);
		for(Product item: this.transactionItems) {
			//System.out.println("testing");
			// System.out.println(item.getQuantity());
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
		// Tests.checkTransaction();
		// Tests.checkProduct();
		// Tests.checkTransactionItem();
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
		t1.printReceipt();

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

class TransactionItem extends Product {
	private int quantity;
	private BigDecimal subtotal;

	public TransactionItem(int quantity, String name, BigDecimal price, boolean exempt, boolean imported) {
		super(name, price, exempt, imported);
		this.quantity = quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public BigDecimal setSubtotal() {
		subtotal = this.getPrice().multiply(new BigDecimal(this.getQuantity()));
		return subtotal;
	}
}

class NonExemptItem extends TransactionItem implements TaxedItem {

	private BigDecimal taxTotal;
	private BigDecimal subtotal;

	public NonExemptItem(int quantity, String name, BigDecimal price, boolean exempt, boolean imported) {
		super(quantity, name, price, exempt, imported);
		System.out.println("NonExemptItem created");
	}

	public void calculateTax() {
		BigDecimal salesTax;
		//System.out.println("Calculating sales tax");
		this.subtotal = this.setSubtotal();
		TaxRates tax = new TaxRates();
		salesTax = this.subtotal.multiply(new BigDecimal(tax.getBasicTax())).divide(new BigDecimal(100));
		this.taxTotal = salesTax;
		System.out.println(this.getName());	
		System.out.println(this.getPrice());		
		System.out.println("tax: " + this.taxTotal);
	}
}

class ImportItem extends TransactionItem implements TaxedItem {

	private BigDecimal taxTotal;
	private BigDecimal subtotal;

	public ImportItem(int quantity, String name, BigDecimal price, boolean exempt, boolean imported) {
		super(quantity, name, price, exempt, imported);
		System.out.println("ImportItem created");
	}

	public void calculateTax() {
		BigDecimal importTax;
		//System.out.println("Calculating import tax");
		this.subtotal = this.setSubtotal();
		TaxRates tax = new TaxRates();
		importTax = this.subtotal.multiply(new BigDecimal(tax.getImportTax())).divide(new BigDecimal(100));
		this.taxTotal = importTax;
		System.out.println(this.getName());	
		System.out.println(this.getPrice());		
		System.out.println("tax: " + this.taxTotal);
	}
}

class NonExemptImportItem extends TransactionItem implements TaxedItem {

	private BigDecimal taxTotal;
	private BigDecimal subtotal;

	public NonExemptImportItem(int quantity, String name, BigDecimal price, boolean exempt, boolean imported) {
		super(quantity, name, price, exempt, imported);
		System.out.println("NonExemptImportItem created");
	}

	public void calculateTax() {
		BigDecimal salesTax;
		BigDecimal importTax;
		//System.out.println("Calculating import and sales tax");
		this.subtotal = this.setSubtotal();
		TaxRates tax = new TaxRates();
		salesTax = this.subtotal.multiply(new BigDecimal(tax.getBasicTax())).divide(new BigDecimal(100));
		importTax = this.subtotal.multiply(new BigDecimal(tax.getImportTax())).divide(new BigDecimal(100));
		this.taxTotal = salesTax.add(importTax);
		System.out.println(this.getName());	
		System.out.println(this.getPrice());		
		System.out.println("tax: " + this.taxTotal);		
	}
}

interface TaxedItem {
	void calculateTax();
}

class Receipt {
	private TaxRates taxRates = new TaxRates();
	private List<Product> items = new ArrayList<Product>();
	private BigDecimal totalSalesTax;
	private BigDecimal totalImportTax;;
	private BigDecimal subtotal;

	public Receipt(List<Product> items) {
		this.items = items;
		for(Product item: items){
			//System.out.println(item.getName());
			// System.out.println(item.getQuantity());
			//System.out.println(item.getPrice());
			//System.out.println(item.calculateTax());


		}
		System.out.println("in receipt");
	}
}

class TaxRates {
	private double basicTax;
	private double importTax;

	public TaxRates() {
		this.basicTax = 10.0;
		this.importTax = 5.0;
	}

	public void setBasicTax(double percent) {
		this.basicTax = percent;
	}

	public void setImportTax(double percent) {
		this.importTax = percent;
	}

	public double getBasicTax() {
		return this.basicTax;
	}

	public double getImportTax() {
		return this.importTax;
	}
}

class Tests {
	public static void checkTransaction() throws FileNotFoundException {
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
			System.out.println("Failed test: Transaction, scanItems - number of items, "
				+ "Expected: " + expectedInt + ", Actual: " + scanned.size());
		}

		expectedItems = new ArrayList<String>(
			Arrays.asList("1 imported bottle of perfume at 27.99", "1 bottle of perfume at 18.99", "1 packet of headache pills at 9.75", "1 box of imported chocolates at 11.25"));
		if(!testTransaction.scanItems(file).equals(expectedItems)) {
			System.out.println("Failed test: Transaction, scanItems - items, "
				+ "Expected: " + expectedItems + ", Actual: " + testTransaction.scanItems(file));
		}

		expectedItem = "New exempt item";
		testTransaction.setExemptItem(expectedItem);
		if(!testTransaction.getExemptItems().contains(expectedItem)) {
			System.out.println("Failed test: Transaction, setExemptItem, "
				+ "Expected: " + expectedItem + ", Actual: " + testTransaction.getExemptItems());
		}

		expectedBoolean = true;
		if(testTransaction.isExempt("box of imported chocolates") != expectedBoolean) {
			System.out.println("Failed test: Transaction, isExempt, "
				+ "Expected: " + expectedBoolean + ", Actual: " + testTransaction.isExempt("box of imported chocolates"));
		}

		if(testTransaction.isImported("box of imported chocolates") != expectedBoolean) {
			System.out.println("Failed test: Transaction, isImported, "
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
			System.out.println("Failed test: Transaction, setTransactionItems, "
				+ "Expected total: " + expectedAmount + ", Actual: " + sum);
		}
		// System.out.println(sum);
	}

	public static void checkProduct() {
		String expectedString;
		BigDecimal expectedPrice;
		// Product:
		// 1 imported bottle of perfume: 54.65
		Product testProduct = new Product("imported bottle of perfume", new BigDecimal("54.65"), false, true);
		expectedString = "imported bottle of perfume";
		if(!testProduct.getName().equals(expectedString)) {
			System.out.println("Failed test: Product, getName, "
				+ "Expected: " + expectedString + ", Actual: " + testProduct.getName());
		}
		expectedPrice = new BigDecimal("54.65");
		if(!testProduct.getPrice().equals(expectedPrice)) {
			System.out.println("Failed test: Product, getPrice, "
				+ "Expected: " + expectedPrice + ", Actual: " + testProduct.getPrice());
		}
	}

	public static void checkTransactionItem() {
		String expectedString;
		int expectedInt;

		TransactionItem testTxnItem = new TransactionItem(2, "imported bottle of perfume", new BigDecimal("54.65"), false, true);
		expectedInt = 2;
		if(testTxnItem.getQuantity() != (expectedInt)) {
			System.out.println("Failed test: TransactionItem, getQuantity, "
				+ "Expected: " + expectedInt + ", Actual: " + testTxnItem.getQuantity());
		}

		expectedString = "imported bottle of perfume";
		if(!testTxnItem.getName().equals(expectedString)) {
			System.out.println("Failed test: TransactionItem, getName, "
				+ "Expected: " + expectedString + ", Actual: " + testTxnItem.getName());
		}
	}
}