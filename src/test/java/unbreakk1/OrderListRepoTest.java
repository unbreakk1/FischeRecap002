package unbreakk1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OrderListRepoTest
{

    private OrderListRepo orderRepo;

    @BeforeEach
    void setUp() {
        orderRepo = new OrderListRepo();
    }

    @Test
    void shouldAddAndRetrieveOrders()
    {
        Order order = Order.createOrder(1, 101, 2, new BigDecimal("1200.00"));

        // When adding an order
        orderRepo.addOrder(order);

        // Then the order should be retrievable by ID
        Optional<Order> retrievedOrder = orderRepo.getOrderById(1);
        assertThat(retrievedOrder).isPresent();
        assertThat(retrievedOrder.get().productId()).isEqualTo(101);
        assertThat(retrievedOrder.get().totalPrice()).isEqualByComparingTo("2400.00");
    }

    @Test
    void shouldRemoveOrderById()
    {
        Order order = Order.createOrder(1, 101, 2, new BigDecimal("1200.00"));

        // When adding and removing an order
        orderRepo.addOrder(order);
        boolean removed = orderRepo.removeOrder(1);

        // Then the order should be removed
        assertThat(removed).isTrue();
        assertThat(orderRepo.getOrderById(1)).isEmpty();
    }

    @Test
    void shouldReturnAllOrders()
    {
        Order order1 = Order.createOrder(1, 101, 2, new BigDecimal("1200.00"));
        Order order2 = Order.createOrder(2, 102, 1, new BigDecimal("800.00"));

        // When adding orders
        orderRepo.addOrder(order1);
        orderRepo.addOrder(order2);

        // Then all orders should be returned
        assertThat(orderRepo.getAllOrders())
                .hasSize(2)
                .extracting(Order::productId)
                .containsExactlyInAnyOrder(101, 102);
    }

    @Test
    void shouldModifyOrderQuantity()
    {
        Order order = Order.createOrder(1, 101, 2, new BigDecimal("1200.00"));
        orderRepo.addOrder(order);

        // When modifying the order quantity
        boolean modified = orderRepo.modifyOrderQuantity(1, 3, new BigDecimal("1200.00"));

        // Then the order should be updated
        assertThat(modified).isTrue();
        Optional<Order> updatedOrder = orderRepo.getOrderById(1);
        assertThat(updatedOrder).isPresent();
        assertThat(updatedOrder.get().quantity()).isEqualTo(3);
        assertThat(updatedOrder.get().totalPrice()).isEqualByComparingTo("3600.00");
    }
}