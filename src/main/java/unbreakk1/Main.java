package unbreakk1;

import java.math.BigDecimal;

public class Main
{
        public static void main(String[] args)
        {
            ProductRepo productRepo = new ProductRepo();
            OrderRepo orderRepo = new OrderListRepo(); // Use the OrderListRepo implementation

            productRepo.addProduct(new Product(1, "Laptop", new BigDecimal("1200.00")));
            productRepo.addProduct(new Product(2, "Smartphone", new BigDecimal("800.00")));

            ShopService shopService = new ShopService(productRepo, orderRepo);

            shopService.placeOrder(1, 2); // Successful order
            shopService.placeOrder(3, 1); // Invalid product ID
            shopService.placeOrder(2, 1); // Successful order

            System.out.println("All Orders: " + orderRepo.getAllOrders());
        }




}