package app.model;

import app.model.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final int id;
    private final Client client;
    private final List<OrderItem> items;
    private OrderStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime finishedAt;

    public Order(int id, Client client, List<OrderItem> items) {
        this.id = id;
        this.client = client;
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.ABERTO;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() { return id; }
    public Client getClient() { return client; }
    public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }
    public OrderStatus getStatus() { return status; }

    public synchronized void setStatus(OrderStatus status) {
        this.status = status;
        if (status == OrderStatus.FINALIZADO) {
            this.finishedAt = LocalDateTime.now();
        }
    }

    public double getTotal() {
        return items.stream().mapToDouble(OrderItem::getTotal).sum();
    }

    @Override
    public String toString() {
        return String.format("Order{id=%d, client=%s, status=%s, total=%.2f, items=%d}",
            id, client.getName(), status, getTotal(), items.size());
    }
}
