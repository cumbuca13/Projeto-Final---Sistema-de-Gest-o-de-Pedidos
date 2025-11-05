package app.repository;

import app.model.Order;
import app.exception.EntityNotFoundException;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderRepository {
    private final Map<Integer, Order> orders = new ConcurrentHashMap<>();

    public void save(Order o) {
        orders.put(o.getId(), o);
    }

    public Order findById(int id) {
        Order o = orders.get(id);
        if (o == null) throw new EntityNotFoundException("Pedido não encontrado: " + id);
        return o;
    }

    public Collection<Order> findAll() {
        return orders.values();
    }
}
