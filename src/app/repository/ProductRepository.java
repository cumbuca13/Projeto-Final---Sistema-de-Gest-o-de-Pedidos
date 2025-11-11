package app.repository;

import app.exception.EntityNotFoundException;
import app.model.Product;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProductRepository {
    private final Map<Integer, Product> products = new ConcurrentHashMap<>();

    public void save(Product p) {
        products.put(p.getId(), p);
    }

    public Product findById(int id) {
        Product p = products.get(id);
        if (p == null) throw new EntityNotFoundException("Produto nao encontrado: " + id);
        return p;
    }

    public Collection<Product> findAll() {
        return products.values();
    }

    public boolean exists(int id) {
        return products.containsKey(id);
    }
}
