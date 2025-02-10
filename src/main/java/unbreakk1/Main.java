package unbreakk1;

import java.math.BigDecimal;

public class Main
{
    public static void main(String[] args)
    {
        ProductRepo productRepo = new ProductRepo();

        productRepo.addProduct(new Product(1, "Laptop", new BigDecimal("1200.00")));
        productRepo.addProduct(new Product(2, "Smartphone", new BigDecimal("800.00")));

        // Choose which OrderRepo implementation to use:
        // Uncomment one of the two lines below to choose the implementation:
        //OrderRepo orderRepo = new OrderListRepo(); // Use OrderListRepo
        OrderRepo orderRepo = new OrderMapRepo(); // Use OrderMapRepo

        // Create ShopService with the chosen OrderRepo implementation
        ShopService shopService = new ShopService(productRepo, orderRepo);

        // Place some orders
        shopService.placeOrder(1, 2); // Valid order for laptop
        shopService.placeOrder(3, 1); // Invalid order (product does not exist)
        shopService.placeOrder(2, 1); // Valid order for smartphone

        // Get and print all orders
        System.out.println("All Orders: " + orderRepo.getAllOrders());

        // Remove an order by ID
        boolean isRemoved = orderRepo.removeOrder(1); // Remove the first order
        System.out.println("Order Removed: " + isRemoved);

        // Get and print all remaining orders
        System.out.println("All Orders After Removal: " + orderRepo.getAllOrders());
    }





}