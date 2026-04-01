package com.example.onthegoordering;

import java.util.ArrayList;

public class CartItem {
    String name;
    double basePrice;
    int quantity;
    ArrayList<Extra> extras;
    int image;

    public CartItem(String name, double basePrice, int quantity, ArrayList<Extra> extras, int image) {
        this.name = name;
        this.basePrice = basePrice;
        this.quantity = quantity;
        this.extras = extras;
        this.image = image;
    }

    public double getTotalPrice() {
        double total = basePrice;

        for (Extra extra : extras) {
            total += extra.price;
        }

        return total * quantity;
    }
}
