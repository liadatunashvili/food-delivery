package models;

import exceptions.ExpiredFoodException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Food {

    private String name;
    private BigDecimal foodPrice;
    private int expiration;
    private final LocalDate creationDate;

    public Food(String name, BigDecimal foodPrice, int expiration) throws ExpiredFoodException {
        this.name = name;
        this.foodPrice = foodPrice;
        this.expiration = expiration;
        this.creationDate = LocalDate.now();
    }

    public Food() throws ExpiredFoodException {
        this.creationDate = LocalDate.now();
    }

    public boolean isExpired() throws ExpiredFoodException {
        LocalDate expirationDate = creationDate.plusDays(expiration);
        if (LocalDate.now().isAfter(expirationDate)) {
            throw new ExpiredFoodException("the food" + name + "is expired");
        } else {
            return true;
        }

    }


    public String getName() {
        return name;
    }

    public BigDecimal getFoodPrice() {
        return foodPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFoodprice(BigDecimal foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, foodPrice, expiration, creationDate);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Food other = (Food) obj;
        return Objects.equals(name, other.name) && 
               Objects.equals(foodPrice, other.foodPrice) &&
               Objects.equals(expiration, other.expiration) &&
               Objects.equals(creationDate, other.creationDate);
    }
}
