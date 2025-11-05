package app.model;

import app.exception.InvalidDataException;
import app.model.enums.Category;

public class Product {
    private final int id;
    private final String name;
    private final double price;
    private final Category category;

    public Product(int id, String name, double price, Category category) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Nome do produto não pode ser vazio.");
        }
        if (price <= 0.0) {
            throw new InvalidDataException("Preço do produto deve ser positivo.");
        }
        if (category == null) {
            throw new InvalidDataException("Categoria não pode ser nula.");
        }
        this.id = id;
        this.name = name.trim();
        this.price = price;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public Category getCategory() { return category; }

    @Override
    public String toString() {
        return String.format("Product{id=%d, name='%s', price=%.2f, category=%s}",
                id, name, price, category);
    }
}