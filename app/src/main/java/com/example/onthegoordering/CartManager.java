package com.example.onthegoordering;

import java.util.ArrayList;

public class CartManager {
    public static ArrayList<CartItem> cart = new ArrayList<>();
    public static ArrayList<CartItem> getCart() {
        return cart;
    }

    public static void addItem(CartItem item) {
        cart.add(item);
    }

    public static void removeItem(int position) {
        cart.remove(position);
    }

    public static double getTotal() {
        double total = 0;
        for (CartItem item : cart) {
            total += item.getTotalPrice();
        }
        return total;
    }
}
