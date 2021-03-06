import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * The Tests class is used for the testing of classes in the program.
 */

public class Tests {
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
		String file = "../Input/Input3.txt";
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
		for(Product each: txnItems) {
			sum = sum.add(each.getPrice());
		}
		if(!sum.equals(expectedAmount)) {
			System.out.println("Failed test: Transaction, setTransactionItems, "
				+ "Expected total: " + expectedAmount + ", Actual: " + sum);
		}
	}

	public static void checkProduct() {
		String expectedString;
		BigDecimal expectedAmount;
		// Product:
		// 1 imported bottle of perfume: 54.65
		Product testProduct = new Product("imported bottle of perfume", new BigDecimal("54.65"), false, true);
		expectedString = "imported bottle of perfume";
		if(!testProduct.getName().equals(expectedString)) {
			System.out.println("Failed test: Product, getName, "
				+ "Expected: " + expectedString + ", Actual: " + testProduct.getName());
		}
		expectedAmount = new BigDecimal("54.65");
		if(!testProduct.getPrice().equals(expectedAmount)) {
			System.out.println("Failed test: Product, getPrice, "
				+ "Expected: " + expectedAmount + ", Actual: " + testProduct.getPrice());
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

	public static void checkTaxRates() {
		double expectedValue;

		TaxRates testTaxRates = new TaxRates();
		testTaxRates.setBasicTax(8);
		expectedValue = 8;
		if(testTaxRates.getBasicTax() != (expectedValue)) {
			System.out.println("Failed test: TaxRates, getBasicTax, "
				+ "Expected: " + expectedValue + ", Actual: " + testTaxRates.getBasicTax());
		}

		testTaxRates.setImportTax(15);
		expectedValue = 15;
		if(testTaxRates.getImportTax() != (expectedValue)) {
			System.out.println("Failed test: TaxRates, getImportTax, "
				+ "Expected: " + expectedValue + ", Actual: " + testTaxRates.getImportTax());
		}
	}

	public static void checkReceipt() {
		List<Product> testItems = new ArrayList<Product>();
		BigDecimal expectedAmount;

		ImportItem testTxnItem2 = new ImportItem(1, "imported box of chocolates", new BigDecimal("10.00"), true, true);
		NonExemptImportItem testTxnItem3 = new NonExemptImportItem(1, "imported bottle of perfume", new BigDecimal("47.50"), false, true);
		testItems.add(testTxnItem2);
		testItems.add(testTxnItem3);

		Receipt testReceipt = new Receipt(testItems);
		//testReceipt.printReceipt();
		expectedAmount = new BigDecimal(0);		// Expected zero unless we run printReceipt()
		if(!testReceipt.getReceiptSubtotal().equals(expectedAmount)) {
			System.out.println("Failed test: Receipt, getReceiptSubtotal, "
				+ "Expected: " + expectedAmount + ", Actual: " + testReceipt.getReceiptSubtotal());
		}
	}
}