package models;

public class WorkerProfile extends User {

    public WorkerProfile(String name, String email, String number, String hashedPassword) {
        super(name, email, number, hashedPassword);
    }

    @Override
    public String getRoleName() {
        return "Worker";
    }
}
