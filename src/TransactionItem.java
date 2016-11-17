import java.math.BigDecimal;
import java.math.RoundingMode;

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
		amount_rounded = amount_rounded.divide(new BigDecimal(0.05), 0, RoundingMode.UP);
		amount_rounded = amount_rounded.multiply(new BigDecimal(0.05));
		amount_rounded = amount_rounded.setScale(2, RoundingMode.DOWN);
		// Rounding using doubles:
		// amount_rounded = new BigDecimal(Math.ceil(totalAmount.doubleValue() * 20) / 20);
		return amount_rounded;
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