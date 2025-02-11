package unbreakk1;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderRepo
{
    // Method to add a new order
    void addOrder(Order order);

    boolean removeOrder(int id);

    Optional<Order> getOrderById(int id);

    List<Order> getAllOrders();

    // New method to modify the quantity of an order
    boolean modifyOrderQuantity(int orderId, int newQuantity, BigDecimal productPrice);


}
