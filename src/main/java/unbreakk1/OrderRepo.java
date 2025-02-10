package unbreakk1;

import java.util.List;
import java.util.Optional;

public interface OrderRepo
{
    // Method to add a new order
    void addOrder(Order order);

    // Method to remove an order by ID
    boolean removeOrder(int id);

    // Method to get a single order by ID
    Optional<Order> getOrderById(int id);

    // Method to get all orders
    List<Order> getAllOrders();

}
