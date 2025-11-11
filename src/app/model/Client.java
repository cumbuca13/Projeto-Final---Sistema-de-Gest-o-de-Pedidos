package app.model;

import app.exception.InvalidDataException;

public class Client {
    private final int id;
    private final String name;
    private final String email;

    public Client(int id, String name, String email) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Nome do cliente nao pode ser vazio.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidDataException("E-mail do cliente nao pode ser vazio.");
        }
        this.id = id;
        this.name = name.trim();
        this.email = email.trim();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("Client{id=%d, name='%s', email='%s'}", id, name, email);
    }
}
