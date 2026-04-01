package com.example.onthegoordering;

import java.io.Serializable;

public class Extra implements Serializable {
    String name;
    double price;

    public Extra(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
