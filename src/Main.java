import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Main class contains the main() method to launch the program.
 * 
 * It initiates the following:
 *
 * 1. Runs tests on classes in the program.
 * 2. Get files from input directory
 * 3. Loop through each file in directory
 * 4. Creation of a transaction
 * 5. Scans a list of transaction items by reading from an input .txt file. 
 *
 * Input files for the requested three cases are stored in the JavaSalesTax/Input 
 * folder.
 */

class Main {
	/**
     * The main method starts the program.
     */
	public static void main (String[] args) throws FileNotFoundException {
		final String directory = "../input/";
		final File folder = new File(directory);
		List<String> files = new ArrayList<String>();

		// Run tests:
		System.out.println("*******Running tests*******");
		Tests.checkTransaction();
		Tests.checkProduct();
		Tests.checkTransactionItem();
		Tests.checkTaxRates();
		Tests.checkReceipt();

		// Get files from input directory
		files = getFilesFromDirectory(folder);

		// Loop through each file in input folder
		for (String file: files) {
			String path = "";
			path = directory + file;
			// Create transaction for each file
			Transaction t = new Transaction();
			List<String> items = t.scanItems(path);
			t.setTransactionItems(items);
			// Print receipt for transaction
			System.out.println("*****Printing receipt for " + file + "*****");
			Receipt receipt = new Receipt(t.getTransactionItems());
			receipt.printReceipt();
		}
	}

	/**
     * The getFilesFromDirectory method reads files from a directory.
     */
	public static List<String> getFilesFromDirectory(final File folder) {
		List<String> files = new ArrayList<String>();
		
		System.out.println("Files found in input directory:");
		for (final File file: folder.listFiles()) {
			if (file.isDirectory()) {
				getFilesFromDirectory(file);
			} else {
				System.out.println(file.getName());
				files.add(file.getName());
			}
		}
		return files;
	}
}

