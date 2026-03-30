package models;

import java.util.List;
import java.util.function.*;


public class LambdaFunctions {

    public static void processCart(List<Food> cartItems) {

        Predicate<Food> tooExpensive = food -> food.getFoodPrice().doubleValue() > 100;
        List<Food> ExpensiveItems = cartItems.stream().filter(tooExpensive).toList();

        Function<Food, String> foodName = Food::getName;
        List<String> names = ExpensiveItems.stream().map(foodName).toList();

        Consumer<String> printName = System.out::println;
        names.forEach(printName);

        Supplier<Food> defaultFoodSupplier = () -> new Food("default", java.math.BigDecimal.ONE, 1);
        Food defaultFood = defaultFoodSupplier.get();

        BiFunction<Food, Food, Food> combineFood = (food1, food2) ->
                new Food(food1.getName() + "&" + food2.getName(), food1.getFoodPrice().add(food2.getFoodPrice()),
                        Math.max(food1.getExpiration(), food2.getExpiration()));

        Food combined = combineFood.apply(cartItems.get(0), defaultFood);

        BiConsumer<Food, Food> foodPair = (food1, food2) -> System.out.println("pair: " + food1.getName() + " & " + food2.getName());
        foodPair.accept(cartItems.get(0), defaultFood);

    }

}
