import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

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