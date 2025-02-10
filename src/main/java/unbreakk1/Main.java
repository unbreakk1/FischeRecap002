package unbreakk1;

import java.math.BigDecimal;

public class Main
{
        public static void main(String[] args)
        {
            ProductRepo productRepo = new ProductRepo();
            OrderListRepo orderListRepo = new OrderListRepo();

            productRepo.addProduct(new Product(1, "Laptop", new BigDecimal("1200.00")));
            productRepo.addProduct(new Product(2, "Smartphone", new BigDecimal("800.00")));

            ShopService shopService = new ShopService(productRepo, orderListRepo);

            shopService.placeOrder(1, 2); // Should succeed

            shopService.placeOrder(3, 1); // Should print an error message

            shopService.placeOrder(2, 1);

            System.out.println("All Orders: " + orderListRepo.getAllOrders());
        }



}