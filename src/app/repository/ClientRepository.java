package app.repository;

import app.model.Client;
import app.exception.EntityNotFoundException;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientRepository {
    private final Map<Integer, Client> clients = new ConcurrentHashMap<>();

    public void save(Client c) {
        clients.put(c.getId(), c);
    }

    public Client findById(int id) {
        Client c = clients.get(id);
        if (c == null) throw new EntityNotFoundException("Cliente não encontrado: " + id);
        return c;
    }

    public Collection<Client> findAll() {
        return clients.values();
    }

    public boolean exists(int id) {
        return clients.containsKey(id);
    }
}
