import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

class Main {
	public static void main (String[] args) throws FileNotFoundException {
		// Tests.checkTransaction();
		// Tests.checkProduct();
		// Tests.checkTransactionItem();
		// String file = "Input1.txt";
		//String file = "Input2.txt";
		String file = "Input3.txt";
		Transaction t1 = new Transaction();
		// t1.setExemptItem("test");
		// System.out.println(t1.getExemptItems());

		// t1.setExemptItem("book");
		// t1.setExemptItem("book");
		List<String> items = t1.scanItems(file);
		//System.out.println(items);
		t1.setTransactionItems(items);
		//t1.printReceipt();
		//System.out.println("Printing receipt");
		Receipt receipt = new Receipt(t1.getTransactionItems());
		receipt.setReceiptSubtotal();
		receipt.setTotalSalesTax();
		receipt.setTotalImportTax();
		receipt.printReceipt();
	}
}

