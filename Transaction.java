import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

public class Transaction {
	public ArrayList<String> scanItems(String file) throws FileNotFoundException {
		ArrayList<String> scannedItems = new ArrayList<String>();
		Scanner scan = new Scanner(new File(file)).useDelimiter("\\n");
		while(scan.hasNext()) {
			//System.out.println(scan.next());
			scannedItems.add(scan.next());
		}
		scan.close();
		// System.out.println(scannedItems);
		return(scannedItems);
	}

	public void getProductInfo(ArrayList<String> items) {
		for(String item: items) {
			System.out.println(item);
			Boolean imported = false;
			ArrayList<String> getProductName = new ArrayList<String>();
			BigDecimal price = null;
			String[] info = item.split("\\s+");
			int quantity = Integer.parseInt(info[0]);
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

		}
	}
	public static void main (String[] args) throws FileNotFoundException {
		String file1 = "Input2.txt";
		Transaction t1 = new Transaction();
		ArrayList<String> items = t1.scanItems(file1);
		//System.out.println(items);
		t1.getProductInfo(items);
	}
}