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