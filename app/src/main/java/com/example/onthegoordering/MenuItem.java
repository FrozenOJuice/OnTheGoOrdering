package com.example.onthegoordering;

import java.util.ArrayList;

public class MenuItem {
    String name;
    String description;
    String price;
    int image;
    String category;
    ArrayList<Extra> extras;

    public MenuItem(String name, String description, String price, int image, String category, ArrayList<Extra> extras) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.extras = extras;
    }
}


