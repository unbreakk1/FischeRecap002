package unbreakk1;

import java.math.BigDecimal;

public record Order(int id, int productId, int quantity, BigDecimal totalPrice)
{
}
