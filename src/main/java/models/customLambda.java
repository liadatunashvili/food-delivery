package models;

public class customLambda {
    @FunctionalInterface
    public interface FoodDiscount {
        double applyDiscount(Food food, double discountPercent);
    }

    @FunctionalInterface
    public interface FoodFormatter {
        String format(Food food);
    }

    @FunctionalInterface
    public interface FoodChecker {
        boolean check(Food food);
    }
}