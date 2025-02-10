package unbreakk1;

import java.math.BigDecimal;
import java.util.Optional;

public class ShopService
{

    private final ProductRepo productRepo;
    private final OrderRepo orderRepo; // Depend on abstraction
    private int orderCounter = 1;

    public ShopService(ProductRepo productRepo, OrderRepo orderRepo)
    {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public void placeOrder(int productId, int quantity)
    {
        Optional<Product> productOptional = productRepo.getProductById(productId);

        if (productOptional.isEmpty())
        {
            System.out.println("Product with ID " + productId + " does not exist.");
            return;
        }

        Product product = productOptional.get();
        BigDecimal totalPrice = product.price().multiply(BigDecimal.valueOf(quantity));
        Order newOrder = new Order(orderCounter++, productId, quantity, totalPrice);
        orderRepo.addOrder(newOrder);

        System.out.println("Order placed successfully: " + newOrder);
    }
}
