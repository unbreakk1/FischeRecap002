package unbreakk1;

import java.math.BigDecimal;
import java.util.*;

public class OrderMapRepo implements OrderRepo
{
    private final Map<Integer, Order> orderMap = new HashMap<>();

    @Override
    public void addOrder(Order order)
    {
        orderMap.put(order.id(), order);
    }

    @Override
    public boolean removeOrder(int id)
    {
        // Remove the order by ID, return `true` if removed or `false` if not found
        return orderMap.remove(id) != null;
    }

    @Override
    public Optional<Order> getOrderById(int id)
    {
        // Return an optional wrapped order
        return Optional.ofNullable(orderMap.get(id));
    }

    @Override
    public List<Order> getAllOrders()
    {
        // Convert map values (the orders) into a list
        return new ArrayList<>(orderMap.values());
    }

    @Override
    public boolean modifyOrderQuantity(int orderId, int newQuantity, BigDecimal productPrice) {
        Order existingOrder = orderMap.get(orderId);
        if (existingOrder == null) {
            return false; // Order not found
        }

        // Update the order with the new quantity and total price
        Order updatedOrder = new Order(existingOrder.id(), existingOrder.productId(), newQuantity, productPrice);
        orderMap.put(orderId, updatedOrder);
        return true;
    }


}
