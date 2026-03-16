package models;

public class ConsumerProfile extends User {

    public ConsumerProfile(String name, String email, String number, String hashedPassword){
        super(name, email, number, hashedPassword);
    }

    @Override
    public String getRoleName(){
        return "Consumer";
    }
}
