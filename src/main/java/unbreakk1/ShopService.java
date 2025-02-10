package unbreakk1;

import java.math.BigDecimal;
import java.util.Optional;

public class ShopService
{
        private final ProductRepo productRepo;
        private final OrderListRepo orderListRepo;
        private int orderCounter = 1; // A simple counter to generate order IDs

        public ShopService(ProductRepo productRepo, OrderListRepo orderListRepo) {
            this.productRepo = productRepo;
            this.orderListRepo = orderListRepo;
        }

        // Step 1: Implement the method to place a new order
        public void placeOrder(int productId, int quantity) {
            // Step 2: Check if the ordered product exists
            Optional<Product> productOptional = productRepo.getProductById(productId);

            if (productOptional.isEmpty()) {
                System.out.println("Product with ID " + productId + " does not exist.");
                return;
            }

            // Retrieve the product details
            Product product = productOptional.get();

            // Calculate the total price: price * quantity
            BigDecimal totalPrice = product.price().multiply(BigDecimal.valueOf(quantity));

            // Create a new order
            Order newOrder = new Order(orderCounter++, productId, quantity, totalPrice);

            // Add the order to the order repository
            orderListRepo.addOrder(newOrder);

            // Confirmation message
            System.out.println("Order placed successfully: " + newOrder);
        }

}
