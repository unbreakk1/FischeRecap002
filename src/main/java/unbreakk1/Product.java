package unbreakk1;

import java.math.BigDecimal;

public record Product(int id, String name, BigDecimal price, int quantity)
{
    // Decrease product quantity when an order is placed
    public Product decreaseQuantity(int amount)
    {
        if (this.quantity < amount) {
            throw new IllegalArgumentException("Not enough stock available.");
        }
        return new Product(this.id, this.name, this.price, this.quantity - amount);
    }
}
