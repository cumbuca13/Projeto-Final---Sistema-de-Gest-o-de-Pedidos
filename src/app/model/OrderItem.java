package app.model;

import app.exception.InvalidDataException;

public class OrderItem {
    private final Product product;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        if (product == null) {
            throw new InvalidDataException("Produto do item não pode ser nulo.");
        }
        if (quantity <= 0) {
            throw new InvalidDataException("Quantidade deve ser positiva.");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    public double getTotal() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("OrderItem{product=%s, qty=%d, total=%.2f}",
            product.getName(), quantity, getTotal());
    }
}