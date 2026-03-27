package models;

public class FoodBox<T> {
    private T value;

    public FoodBox(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(Food burger) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Foodbox{" + "value=" + value + "}";
    }
}
