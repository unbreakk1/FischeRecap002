package unbreakk1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderListRepo implements OrderRepo {

    private final List<Order> orders = new ArrayList<>();

    @Override
    public void addOrder(Order order)
    {
        orders.add(order);
    }

    @Override
    public boolean removeOrder(int id)
    {
        return orders.removeIf(order -> order.id() == id);
    }

    @Override
    public Optional<Order> getOrderById(int id)
    {
        return orders.stream()
                .filter(order -> order.id() == id)
                .findFirst();
    }

    @Override
    public List<Order> getAllOrders()
    {
        return new ArrayList<>(orders); // Return a copy to ensure immutability
    }


}
