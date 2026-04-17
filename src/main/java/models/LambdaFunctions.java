package models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.*;


public class LambdaFunctions {
    private static final Logger logger = LogManager.getLogger(LambdaFunctions.class);


    public static void processCart(List<Food> cartItems) {

        Predicate<Food> tooExpensive = food -> food.getFoodPrice().doubleValue() > 100;
        List<Food> ExpensiveItems = cartItems.stream().filter(tooExpensive).toList();

        Function<Food, String> foodName = Food::getName;
        List<String> names = ExpensiveItems.stream().map(foodName).toList();

        Consumer<String> printName = logger::info;
        names.forEach(printName);

        Supplier<Food> defaultFoodSupplier = () -> new Food("default", java.math.BigDecimal.ONE, 1);
        Food defaultFood = defaultFoodSupplier.get();

        BiFunction<Food, Food, Food> combineFood = (food1, food2) ->
                new Food(food1.getName() + "&" + food2.getName(), food1.getFoodPrice().add(food2.getFoodPrice()),
                        Math.max(food1.getExpiration(), food2.getExpiration()));

        Food combined = combineFood.apply(cartItems.get(0), defaultFood);

        BiConsumer<Food, Food> foodPair = (food1, food2) -> logger.info("pair: " + food1.getName() + " & " + food2.getName());
        foodPair.accept(cartItems.get(0), defaultFood);

    }

}
