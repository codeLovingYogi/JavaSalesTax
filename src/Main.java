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
		String file = "Input1.txt";
		String file2 = "Input2.txt";
		String file3 = "Input3.txt";
		Transaction t1 = new Transaction();
		System.out.println("Printing receipt for Input 1:");
		List<String> items = t1.scanItems(file);
		t1.setTransactionItems(items);
		Receipt receipt1 = new Receipt(t1.getTransactionItems());
		receipt1.setReceiptSubtotal();
		receipt1.setTotalTransactionTax();
		receipt1.printReceipt();
		System.out.println("***************************");
		Transaction t2 = new Transaction();
		System.out.println("Printing receipt for Input 2:");
		List<String> items2 = t2.scanItems(file2);
		t2.setTransactionItems(items2);
		Receipt receipt2 = new Receipt(t2.getTransactionItems());
		receipt2.setReceiptSubtotal();
		receipt2.setTotalTransactionTax();
		receipt2.printReceipt();
		System.out.println("***************************");
		Transaction t3 = new Transaction();
		System.out.println("Printing receipt for Input 3:");
		List<String> items3 = t3.scanItems(file3);
		t3.setTransactionItems(items3);
		Receipt receipt3 = new Receipt(t3.getTransactionItems());
		receipt3.setReceiptSubtotal();
		receipt3.setTotalTransactionTax();
		receipt3.printReceipt();
	}
}

