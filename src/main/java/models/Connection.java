package models;

public class Connection {
    private final int id;
    public Connection(int id) {
        this.id = id;
    }
    public void create(String data) {
        System.out.println("[Connection-" + id + "] CREATE: " + data);
    }
    public String get(int accountId) {
        return "[Connection-" + id + "] GET account#" + accountId + " => {name: 'MockUser', balance: 500}";
    }
    public void update(int accountId, String data) {
        System.out.println("[Connection-" + id + "] UPDATE account#" + accountId + " with: " + data);
    }
    public void delete(int accountId) {
        System.out.println("[Connection-" + id + "] DELETE account#" + accountId);
    }
    @Override
    public String toString() {
        return "Connection-" + id;
    }
}
