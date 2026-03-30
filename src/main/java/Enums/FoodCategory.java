package Enums;


public enum FoodCategory {

    BURGER("burger"), FRIES("fries"), DRINK("drink");
    private final String category;

    FoodCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}