package models;

import java.math.BigDecimal;

public class Employee extends User{

    //this is for delivery person and customer support employee
    private BigDecimal salary;

    public Employee(String name, String email, String number, String hashedPassword, BigDecimal salary) {
        super(name, email, number, hashedPassword);
        this.salary = salary;
    }

    public Employee(String name, String email, String number, String hashedPassword) {
        super(name, email, number, hashedPassword);
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
