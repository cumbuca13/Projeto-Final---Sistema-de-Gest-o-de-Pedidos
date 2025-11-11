package app.service;

import app.exception.InvalidDataException;
import app.model.*;
import app.model.enums.Category;
import app.repository.ClientRepository;
import app.repository.OrderRepository;
import app.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuService {
    private final Scanner scanner = new Scanner(System.in);
    private final ClientRepository clientRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final OrderProcessor processor;

    private int nextClientId = 1;
    private int nextProductId = 1;
    private int nextOrderId = 1;

    public MenuService(ClientRepository cr, ProductRepository pr, OrderRepository or, OrderProcessor op) {
        this.clientRepo = cr;
        this.productRepo = pr;
        this.orderRepo = or;
        this.processor = op;
    }

    public void start() {
        processor.start();
        boolean running = true;
        while (running) {
            showMenu();
            String opt = scanner.nextLine().trim();
            try {
                switch (opt) {
                    case "1" -> createClient();
                    case "2" -> createProduct();
                    case "3" -> createOrder();
                    case "4" -> listClients();
                    case "5" -> listProducts();
                    case "6" -> listOrders();
                    case "0" -> {
                        running = false;
                        System.out.println("Saindo...");
                    }
                    default -> System.out.println("Opcao invalida.");
                }
            } catch (InvalidDataException ex) {
                System.out.println("Erro: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Erro inesperado: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        processor.stop();
    }

    private void showMenu() {
        System.out.println("\n=== Sistema de Gestao de Pedidos ===");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Cadastrar Produto");
        System.out.println("3. Criar Pedido");
        System.out.println("4. Listar Clientes");
        System.out.println("5. Listar Produtos");
        System.out.println("6. Listar Pedidos");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
    }

    private void createClient() {
        System.out.print("Nome: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        Client c = new Client(nextClientId++, name, email);
        clientRepo.save(c);
        System.out.println("Cliente cadastrado: " + c);
    }

    private void createProduct() {
        System.out.print("Nome do produto: ");
        String name = scanner.nextLine();
        System.out.print("Preco: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.println("Categorias: ");
        for (Category cat : Category.values()) {
            System.out.println(cat.ordinal() + " - " + cat);
        }
        System.out.print("Escolha categoria (numero): ");
        int idx = Integer.parseInt(scanner.nextLine());
        Category category = Category.values()[idx];
        Product p = new Product(nextProductId++, name, price, category);
        productRepo.save(p);
        System.out.println("Produto cadastrado: " + p);
    }

    private void createOrder() {
        System.out.print("ID do cliente: ");
        int clientId = Integer.parseInt(scanner.nextLine());
        var client = clientRepo.findById(clientId);

        List<OrderItem> items = new ArrayList<>();
        boolean adding = true;
        while (adding) {
            System.out.print("ID do produto (ou 0 para terminar): ");
            int pid = Integer.parseInt(scanner.nextLine());
            if (pid == 0) break;
            var product = productRepo.findById(pid);
            System.out.print("Quantidade: ");
            int qty = Integer.parseInt(scanner.nextLine());
            items.add(new OrderItem(product, qty));
        }
        if (items.isEmpty()) {
            System.out.println("Pedido precisa de pelo menos um item.");
            return;
        }
        var order = new Order(nextOrderId++, client, items);
        orderRepo.save(order);
        processor.enqueue(order); // manda pra fila (muda status para FILA)
        System.out.println("Pedido criado e enviado para fila. ID: " + order.getId()
                + " Total: " + order.getTotal());
    }

    private void listClients() {
        System.out.println("Clientes cadastrados:");
        clientRepo.findAll().forEach(System.out::println);
    }

    private void listProducts() {
        System.out.println("Produtos cadastrados:");
        productRepo.findAll().forEach(System.out::println);
    }

    private void listOrders() {
        System.out.println("Pedidos:");
        orderRepo.findAll().forEach(o -> {
            System.out.println(o);
        });
    }
}
