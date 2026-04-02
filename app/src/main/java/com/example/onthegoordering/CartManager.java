package com.example.onthegoordering;

import java.util.ArrayList;

public class CartManager {
    public static ArrayList<CartItem> cart = new ArrayList<>();
    
    public interface CartChangeListener {
        void onCartChanged();
    }
    
    private static CartChangeListener listener;
    
    public static void setListener(CartChangeListener newListener) {
        listener = newListener;
    }

    public static ArrayList<CartItem> getCart() {
        return cart;
    }

    public static void addItem(CartItem newItem) {
        boolean merged = false;
        for (CartItem item : cart) {
            if (item.name.equals(newItem.name) && sameExtras(item.extras, newItem.extras)) {
                item.quantity += newItem.quantity;
                merged = true;
                break;
            }
        }
        if (!merged) {
            cart.add(newItem);
        }
        notifyChange();
    }

    public static void removeItem(int position) {
        if (position >= 0 && position < cart.size()) {
            cart.remove(position);
            notifyChange();
        }
    }

    public static void clearCart() {
        cart.clear();
        notifyChange();
    }

    public static double getTotal() {
        double total = 0;
        for (CartItem item : cart) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public static int getCartCount() {
        int count = 0;
        for (CartItem item : cart) {
            count += item.quantity;
        }
        return count;
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
        if (index >= 0 && index < cart.size()) {
            cart.remove(index);
            addItem(updatedItem);
        }
    }

    private static void notifyChange() {
        if (listener != null) {
            listener.onCartChanged();
        }
    }
}
