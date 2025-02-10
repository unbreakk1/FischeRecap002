package unbreakk1;

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

}
