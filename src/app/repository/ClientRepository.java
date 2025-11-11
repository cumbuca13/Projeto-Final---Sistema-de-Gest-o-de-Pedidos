package app.repository;

import app.exception.EntityNotFoundException;
import app.model.Client;
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
        if (c == null) throw new EntityNotFoundException("Cliente nao encontrado: " + id);
        return c;
    }

    public Collection<Client> findAll() {
        return clients.values();
    }

    public boolean exists(int id) {
        return clients.containsKey(id);
    }
}
