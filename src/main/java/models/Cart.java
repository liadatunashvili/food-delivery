package models;

import java.math.BigDecimal;
import java.util.Arrays;

public class Cart {
    private Food[] cartItems = new Food[0];

    public Cart() {

    }

    public Food[] getCartItems() {
        return Arrays.copyOf(cartItems, cartItems.length);
    }

    public void setCartItems(Food[] cartItems) {
        this.cartItems = Arrays.copyOf(cartItems, cartItems.length);
    }

    public void addToCart(Food food){
        Food[] next = Arrays.copyOf(cartItems, cartItems.length + 1);
        next[next.length - 1] = food;
        cartItems = next;
    }


    public void removeFromCart(Food food){
        if (cartItems.length == 0){
            System.out.println("EMPTRY CART");
            return;
        }
        int index = -1;
        for (int i = 0; i < cartItems.length; i++) {
            if (cartItems[i] == food) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("could not find that item");
            return;
        }
        Food[] next = new Food[cartItems.length - 1];
        System.arraycopy(cartItems, 0, next, 0, index);
        System.arraycopy(cartItems, index + 1, next, index, cartItems.length - index - 1);
        cartItems = next;
    }
}
