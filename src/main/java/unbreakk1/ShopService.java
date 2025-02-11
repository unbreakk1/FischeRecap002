package unbreakk1;
import java.math.BigDecimal;
import java.util.Optional;

public class ShopService {

    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
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

        // Use the factory method to create the order
        Order newOrder = Order.createOrder(orderCounter++, productId, quantity, product.price());

        orderRepo.addOrder(newOrder);

        System.out.println("Order placed successfully: " + newOrder);
    }

    public boolean modifyOrderQuantity(int orderId, int newQuantity)
    {
        Optional<Order> orderOptional = orderRepo.getOrderById(orderId);
        if (orderOptional.isEmpty())
        {
            System.out.println("Order with ID " + orderId + " does not exist!");
            return false;
        }

        Order order = orderOptional.get();
        Optional<Product> productOptional = productRepo.getProductById(order.productId());

        if (productOptional.isEmpty())
        {
            System.out.println("Product for the order does not exist!");
            return false;
        }

        Product product = productOptional.get();
        boolean isModified = orderRepo.modifyOrderQuantity(orderId, newQuantity, product.price());
        if (isModified) {
            System.out.println("Order quantity updated successfully!");
        }
        return isModified;
    }
}
