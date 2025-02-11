package unbreakk1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ShopServiceTest {

    private ProductRepo productRepo;
    private OrderListRepo orderRepo;
    private ShopService shopService;

    @BeforeEach
    void setUp()
    {
        productRepo = new ProductRepo();
        orderRepo = new OrderListRepo();
        shopService = new ShopService(productRepo, orderRepo);

        productRepo.addProduct(new Product(1, "Laptop", new BigDecimal("1200.00")));
        productRepo.addProduct(new Product(2, "Smartphone", new BigDecimal("800.00")));
    }

    @Test
    void shouldPlaceOrderForValidProduct()
    {
        // When placing an order
        shopService.placeOrder(1, 2);

        // Then the order should be added
        assertThat(orderRepo.getAllOrders())
                .hasSize(1)
                .extracting(Order::totalPrice)
                .containsExactly(new BigDecimal("2400.00"));
    }

    @Test
    void shouldNotPlaceOrderForInvalidProduct()
    {
        // When placing an order for a non-existent product
        shopService.placeOrder(99, 1);

        // Then no order should be added
        assertThat(orderRepo.getAllOrders()).isEmpty();
    }

    @Test
    void shouldModifyOrderQuantity()
    {
        shopService.placeOrder(1, 2);

        // When modifying the order quantity
        shopService.modifyOrderQuantity(1, 3);

        // Then the quantity and total price should be updated
        assertThat(orderRepo.getOrderById(1))
                .isPresent()
                .get()
                .satisfies(order ->
                {
                    assertThat(order.quantity()).isEqualTo(3);
                    assertThat(order.totalPrice()).isEqualByComparingTo("3600.00");
                });
    }
}
