package com.example.onthegoordering;

import java.util.ArrayList;

public class CartManager {
    public static ArrayList<CartItem> cart = new ArrayList<>();
    public static ArrayList<CartItem> getCart() {
        return cart;
    }

    public static void addItem(CartItem newItem) {

        for (CartItem item : cart) {

            if (item.name.equals(newItem.name) &&
                    sameExtras(item.extras, newItem.extras)) {

                item.quantity += newItem.quantity;
                return;
            }
        }

        cart.add(newItem);
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

    private static boolean sameExtras(ArrayList<Extra> a, ArrayList<Extra> b) {

        if (a.size() != b.size()) return false;

        for (Extra e1 : a) {
            boolean found = false;

            for (Extra e2 : b) {
                if (e1.name.equals(e2.name)) {
                    found = true;
                    break;
                }
            }

            if (!found) return false;
        }

        return true;
    }

    public static void updateItem(int index, CartItem updatedItem) {
        cart.remove(index);
        addItem(updatedItem);
    }
}
