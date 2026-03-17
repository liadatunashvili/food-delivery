package models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Food {

    private String name;
    private BigDecimal foodPrice;
    private int expiration;
    private final LocalDate creationDate;

    public Food(String name, BigDecimal foodPrice, int expiration) {
        this.name = name;
        this.foodPrice = foodPrice;
        this.expiration = expiration;
        this.creationDate = LocalDate.now();
    }

    public Food() {
        this.creationDate = LocalDate.now();
    }

    public boolean isExpired() {
        LocalDate expirationDate = creationDate.plusDays(expiration);
        if (LocalDate.now().isBefore(expirationDate)) {
            return true;
        } else {
            return false;
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

}
