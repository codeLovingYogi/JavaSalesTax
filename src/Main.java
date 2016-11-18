import java.io.FileNotFoundException;
import java.util.List;

/**
 * The Main class contains the main() method to launch the program.
 * 
 * It initiates the following:
 *
 * 1. Runs tests on classes in the program.
 * 2. Creation of a transaction
 * 3. Scans a list of transaction items by reading from an input .txt file. 
 *
 * Input files for the requested three cases are stored in the JavaSalesTax/Input 
 * folder.
 */

class Main {
	public static void main (String[] args) throws FileNotFoundException {
		// Run tests:
		System.out.println("*****Running tests*****");
		Tests.checkTransaction();
		Tests.checkProduct();
		Tests.checkTransactionItem();
		Tests.checkTaxRates();
		Tests.checkReceipt();

		// Run main functionality:
		String file = "../Input/Input1.txt";
		Transaction t1 = new Transaction();
		List<String> items = t1.scanItems(file);
		t1.setTransactionItems(items);
		System.out.println("*****Printing receipt for Input 1:*****");
		Receipt receipt1 = new Receipt(t1.getTransactionItems());
		receipt1.printReceipt();
		
		String file2 = "../Input/Input2.txt";
		Transaction t2 = new Transaction();
		List<String> items2 = t2.scanItems(file2);
		t2.setTransactionItems(items2);
		System.out.println("*****Printing receipt for Input 2:*****");
		Receipt receipt2 = new Receipt(t2.getTransactionItems());
		receipt2.printReceipt();

		String file3 = "../Input/Input3.txt";
		Transaction t3 = new Transaction();
		List<String> items3 = t3.scanItems(file3);
		t3.setTransactionItems(items3);
		System.out.println("*****Printing receipt for Input 3:*****");
		Receipt receipt3 = new Receipt(t3.getTransactionItems());
		receipt3.printReceipt();
	}
}

