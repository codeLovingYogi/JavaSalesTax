import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

public class Product {
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

	public void setTaxTotal() {}

	public BigDecimal getTaxTotal() {
		return new BigDecimal(0);
	}

}