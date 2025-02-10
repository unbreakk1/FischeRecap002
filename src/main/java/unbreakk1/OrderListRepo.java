package unbreakk1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderListRepo
{
    private final List<Order> orders = new ArrayList<>();

    // Method to add an order
    public void addOrder(Order order) {
        orders.add(order);
    }

    // Method to remove an order by ID
    public boolean removeOrder(int id) {
        return orders.removeIf(order -> order.id() == id);
    }

    // Method to get an order by ID
    public Optional<Order> getOrderById(int id)
    {
        return orders.stream()
                .filter(order -> order.id() == id)
                .findFirst();
    }

    // Method to get all orders
    public List<Order> getAllOrders()
    {
        return new ArrayList<>(orders); // Return a copy to ensure immutability
    }

}
