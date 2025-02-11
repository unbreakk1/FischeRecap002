package unbreakk1;

import java.math.BigDecimal;

public class Main
{
    public static void main(String[] args)
    {
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo(); // You can switch between OrderMapRepo and OrderListRepo

        // Add some products to the product repository
        productRepo.addProduct(new Product(1, "Laptop", new BigDecimal("1200.00")));
        productRepo.addProduct(new Product(2, "Smartphone", new BigDecimal("800.00")));

        // Create ShopService
        ShopService shopService = new ShopService(productRepo, orderRepo);

        // Place orders
        shopService.placeOrder(1, 2); // Order 2 laptops
        shopService.placeOrder(2, 1); // Order 1 smartphone
        shopService.placeOrder(3, 1); // Invalid product ID

        System.out.println("All Orders: ");
        orderRepo.getAllOrders().forEach(System.out::println);

        shopService.modifyOrderQuantity(1, 3); // Update laptop order to 3 units

        System.out.println("Updated Orders: ");
        orderRepo.getAllOrders().forEach(System.out::println);
    }

}