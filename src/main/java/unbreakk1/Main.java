package unbreakk1;

import java.math.BigDecimal;

public class Main
{
        public static void main(String[] args) {
            OrderListRepo orderRepo = new OrderListRepo();

            // Adding orders
            orderRepo.addOrder(new Order(1, 101, 2, new BigDecimal("2400.00"))); // Order for productId 101
            orderRepo.addOrder(new Order(2, 102, 1, new BigDecimal("800.00"))); // Order for productId 102
            orderRepo.addOrder(new Order(3, 103, 5, new BigDecimal("750.00"))); // Order for productId 103

            // Retrieve all orders
            System.out.println("All Orders: " + orderRepo.getAllOrders());

            // Retrieve a single order by ID
            orderRepo.getOrderById(2).ifPresent(order -> System.out.println("Order Found: " + order));

            // Remove an order by ID
            boolean isRemoved = orderRepo.removeOrder(1);
            System.out.println("Order Removed: " + isRemoved);

            // Retrieve all orders after removal
            System.out.println("All Orders After Removal: " + orderRepo.getAllOrders());
        }


}