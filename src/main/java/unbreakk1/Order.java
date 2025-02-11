package unbreakk1;

import java.math.BigDecimal;

public record Order(int id, int productId, int quantity, BigDecimal totalPrice)
{

    public static Order createOrder(int id, int productId, int quantity, BigDecimal productPrice)
    {
        BigDecimal totalPrice = productPrice.multiply(BigDecimal.valueOf(quantity));
        return new Order(id, productId, quantity, totalPrice);
    }


}
