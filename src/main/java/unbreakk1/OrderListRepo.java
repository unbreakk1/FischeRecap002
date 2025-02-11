package unbreakk1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderListRepo implements OrderRepo
{

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

    @Override
    public boolean modifyOrderQuantity(int orderId, int newQuantity, BigDecimal productPrice)
    {
       Optional<Order> orderOptional = getOrderById(orderId);
        if (orderOptional.isEmpty()) {
            return false; // Order not found
        }

        // Remove the old order and add the order with the updated quantity
        Order existingOrder = orderOptional.get();
        orders.remove(existingOrder);

        // Create a new order with the updated quantity and total price
        Order updatedOrder = new Order(existingOrder.id(), existingOrder.productId(), newQuantity, productPrice);
        orders.add(updatedOrder);
        return true;
    }

}



