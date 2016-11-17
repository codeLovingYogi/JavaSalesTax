import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

public class TransactionItem extends Product {
	private int quantity;
	private BigDecimal subtotal;

	public TransactionItem(int quantity, String name, BigDecimal price, boolean exempt, boolean imported) {
		super(name, price, exempt, imported);
		this.quantity = quantity;
	}

	public BigDecimal roundTotal(BigDecimal totalAmount) {
		BigDecimal amount_rounded;
		amount_rounded = new BigDecimal(Math.ceil(totalAmount.doubleValue() * 20) / 20);
		return amount_rounded;
		// amount_rounded = new BigDecimal(Math.ceil(this.taxTotal.doubleValue() * 20) / 20);
		// amount_rounded = this.taxTotal.multiply(new BigDecimal(20));
				// amount_rounded = amount_rounded.divide(new BigDecimal(20));

		// amount_rounded = amount_rounded.setScale(2, RoundingMode.CEILING);
		// amount_rounded.setScale(2, RoundingMode.HALF_UP);

	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setSubtotal() {
		this.subtotal = this.getPrice().multiply(new BigDecimal(this.getQuantity()));
	}

	public BigDecimal getSubtotal() {
		return this.subtotal;
	}
}