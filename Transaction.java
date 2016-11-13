import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

public class Transaction {
	public static ArrayList<String> scanItems(String file) throws FileNotFoundException {
		ArrayList<String> scannedItems = new ArrayList<String>();
		Scanner scan = new Scanner(new File(file)).useDelimiter("\\n");
		while(scan.hasNext()) {
			scannedItems.add(scan.next());
		}
		scan.close();
		return(scannedItems);
	}

	public void getProductInfo(ArrayList<String> items) {
		for(String item: items) {
			System.out.println(item);
			int quantity;
			boolean imported = false;
			boolean exempt = false;
			ArrayList<String> getProductName = new ArrayList<String>();
			BigDecimal price = null;

			String[] info = item.split("\\s+");
			quantity = Integer.parseInt(info[0]);
			// System.out.println(Arrays.toString(info));
			for (int i = 1; i < info.length; i++) {
				//System.out.println(info[i]);
				if(info[i].equals("imported")) {
					imported = true;
				}
				if(!info[i].equals("at")) {
					getProductName.add(info[i]);
				} else {
					price = new BigDecimal(info[i + 1]);
					break;
				}
			}
			String name = String.join(" ", getProductName);
			System.out.println(quantity);
			System.out.println(name);
			System.out.println(imported);
			System.out.println(price);

			Product product = new Product(name, price, exempt, imported);
		}
	}
	public static void main (String[] args) throws FileNotFoundException {
		Tests.checkScanItems();
		Tests.checkProduct();
		// String file = "Input1.txt";
		String file = "Input2.txt";
		// String file = "Input3.txt";
		Transaction t1 = new Transaction();
		ArrayList<String> items = t1.scanItems(file);
		//System.out.println(items);
		t1.getProductInfo(items);
	}
}

class Product {
	private String name;
	private BigDecimal price;;
	private boolean exempt;
	private boolean imported;

	public Product(String name, BigDecimal price, boolean exempt, boolean imported) {
		this.name = name;
		this.price = price;
		this.exempt = exempt;
		this.imported = imported;
		// System.out.println("A product has been created");
	}

	public String name() {
		return this.name;
	}

	public BigDecimal price() {
		return this.price;
	}
}

class Tests {
	public static void checkScanItems () throws FileNotFoundException {
		ArrayList<String> expectedItems;
		int expectedInt;
		// Contents of Input3.txt:
		// 1 imported bottle of perfume at 27.99
		// 1 bottle of perfume at 18.99
		// 1 packet of headache pills at 9.75
		// 1 box of imported chocolates at 11.25
		String file = "Input3.txt";
		Transaction testTransaction = new Transaction();
		expectedInt = 4;
		expectedItems = new ArrayList<String>(
			Arrays.asList("1 imported bottle of perfume at 27.99", "1 bottle of perfume at 18.99", "1 packet of headache pills at 9.75", "1 box of imported chocolates at 11.25"));
		if(testTransaction.scanItems(file).size() != expectedInt) {
			System.out.println("Failed test: scanItems - number of items, "
				+ "Expected: " + expectedInt + ", Actual: " + testTransaction.scanItems(file).size());
		}
		if(!testTransaction.scanItems(file).equals(expectedItems)) {
			System.out.println("Failed test: scanItems - items, "
				+ "Expected: " + expectedItems + ", Actual: " + testTransaction.scanItems(file));
		}
	}

	public static void checkProduct () {
		String expectedString;
		BigDecimal expectedPrice;
		// Product:
		// 1 imported bottle of perfume: 54.65
		Product testProduct = new Product("imported bottle of perfume", new BigDecimal("54.65"), false, true);
		expectedString = "imported bottle of perfume";
		if(!testProduct.name().equals(expectedString)) {
			System.out.println("Failed test: getProductInfo - name, "
				+ "Expected: " + expectedString + ", Actual: " + testProduct.name());
		}
		expectedPrice = new BigDecimal("54.65");
		if(!testProduct.price().equals(expectedPrice)) {
			System.out.println("Failed test: getProductInfo - price, "
				+ "Expected: " + expectedPrice + ", Actual: " + testProduct.price());
		}
	}
}