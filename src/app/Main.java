package app;

import app.model.*;
import app.model.enums.Category;
import app.model.enums.OrderStatus;
import app.repository.*;
import app.service.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var clientRepo = new ClientRepository();
        var productRepo = new ProductRepository();
        var orderRepo = new OrderRepository();

        // --- Pré-população de dados ---
        prePopulate(clientRepo, productRepo, orderRepo);

        var processor = new OrderProcessor();
        processor.start(); // inicia processamento automático em thread separada

        var menu = new MenuService(clientRepo, productRepo, orderRepo, processor);
        menu.start();
    }

    private static void prePopulate(ClientRepository clientRepo, ProductRepository productRepo, OrderRepository orderRepo) {
        // --- Clientes ---
        var c1 = new Client(1, "Alice", "alice@example.com");
        var c2 = new Client(2, "Bruno", "bruno@example.com");
        var c3 = new Client(3, "Carla", "carla@example.com");
        clientRepo.save(c1);
        clientRepo.save(c2);
        clientRepo.save(c3);

        // --- Produtos ---
        var p1 = new Product(1, "Pizza Calabresa", 35.0, Category.ALIMENTOS);
        var p2 = new Product(2, "Notebook Dell", 4200.0, Category.ELETRONICOS);
        var p3 = new Product(3, "Livro Java POO", 89.9, Category.LIVROS);
        productRepo.save(p1);
        productRepo.save(p2);
        productRepo.save(p3);

        // --- Pedidos ---
        var o1 = new Order(1, c1, List.of(
                new OrderItem(p1, 1),
                new OrderItem(p3, 2)
        ));
        var o2 = new Order(2, c2, List.of(
                new OrderItem(p2, 1)
        ));
        var o3 = new Order(3, c3, List.of(
                new OrderItem(p3, 3)
        ));

        orderRepo.save(o1);
        orderRepo.save(o2);
        orderRepo.save(o3);

        // Define status inicial
        o1.setStatus(OrderStatus.FILA);
        o2.setStatus(OrderStatus.PROCESSANDO);
        o3.setStatus(OrderStatus.ABERTO);


        System.out.println(" Dados de exemplo carregados com sucesso!");
        System.out.println("   Clientes: " + clientRepo.findAll().size());
        System.out.println("   Produtos: " + productRepo.findAll().size());
        System.out.println("   Pedidos : " + orderRepo.findAll().size());
        System.out.println();
    }
}
