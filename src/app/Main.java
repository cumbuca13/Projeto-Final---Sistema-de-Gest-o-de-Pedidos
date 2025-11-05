package app;

import app.model.Client;
import app.model.Product;
import app.model.enums.Category;
import app.repository.ClientRepository;
import app.repository.OrderRepository;
import app.repository.ProductRepository;
import app.service.MenuService;
import app.service.OrderProcessor;

public class Main {
    public static void main(String[] args) {
        var clientRepo = new ClientRepository();
        var productRepo = new ProductRepository();
        var orderRepo = new OrderRepository();

        // --- Pré-população de dados ---
        prePopulate(clientRepo, productRepo);

        var processor = new OrderProcessor();
        var menu = new MenuService(clientRepo, productRepo, orderRepo, processor);
        menu.start();
    }

    private static void prePopulate(ClientRepository clientRepo, ProductRepository productRepo) {
        // Clientes
        clientRepo.save(new Client(1, "Alice", "alice@example.com"));
        clientRepo.save(new Client(2, "Bruno", "bruno@example.com"));
        clientRepo.save(new Client(3, "Carla", "carla@example.com"));

        // Produtos
        productRepo.save(new Product(1, "Pizza Calabresa", 35.0, Category.ALIMENTOS));
        productRepo.save(new Product(2, "Notebook Dell", 4200.0, Category.ELETRONICOS));
        productRepo.save(new Product(3, "Livro Java POO", 89.9, Category.LIVROS));

        System.out.println("✅ Dados de exemplo carregados com sucesso!\n");
    }
}