package app.service;

import app.model.Order;
import app.model.enums.OrderStatus;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderProcessor {
    private final BlockingQueue<Order> queue = new LinkedBlockingQueue<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean running = false;

    public void start() {
        running = true;
        executor.submit(this::processLoop);
    }

    public void stop() {
        running = false;
        executor.shutdownNow();
    }

    public void enqueue(Order order) {
        order.setStatus(OrderStatus.FILA);
        queue.offer(order);
    }

    private void processLoop() {
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                Order order = queue.take(); // bloqueia até obter
                order.setStatus(OrderStatus.PROCESSANDO);
                // Simula tempo de processamento: por item 1s (ajustável)
                int seconds = Math.max(1, order.getItems().size());
                Thread.sleep(seconds * 1000L);
                order.setStatus(OrderStatus.FINALIZADO);
                // aqui poderia gravar log ou notificar
                System.out.println("Pedido processado: " + order.getId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception ex) {
                System.err.println("Erro no processamento: " + ex.getMessage());
            }
        }
    }
}
