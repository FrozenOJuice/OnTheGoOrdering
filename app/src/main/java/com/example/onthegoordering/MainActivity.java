package com.example.onthegoordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MenuAdapter adapter;
    private ArrayList<MenuItem> fullList;
    private ArrayList<MenuItem> filteredList;
    private String currentCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setupInsets();

        fullList = createMenu();
        filteredList = new ArrayList<>(fullList);

        setupRecycler();
        setupCategoryButtons();
        setupSearch();
        setupCartButton();

        applyFilters();
    }

    private void setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    private void setupRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recyclerMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MenuAdapter(filteredList);
        recyclerView.setAdapter(adapter);
    }

    private void setupCategoryButtons() {
        findViewById(R.id.btnAll).setOnClickListener(v -> handleCategoryClick(v, "All"));
        findViewById(R.id.btnBurgers).setOnClickListener(v -> handleCategoryClick(v, "Burgers"));
        findViewById(R.id.btnPizza).setOnClickListener(v -> handleCategoryClick(v, "Pizza"));
        findViewById(R.id.btnWraps).setOnClickListener(v -> handleCategoryClick(v, "Wraps"));
        findViewById(R.id.btnVegan).setOnClickListener(v -> handleCategoryClick(v, "Vegan"));
        findViewById(R.id.btnDrinks).setOnClickListener(v -> handleCategoryClick(v, "Drinks"));
        findViewById(R.id.btnSides).setOnClickListener(v -> handleCategoryClick(v, "Sides"));
        findViewById(R.id.btnDesserts).setOnClickListener(v -> handleCategoryClick(v, "Desserts"));
    }

    private void setupSearch() {
        SearchView searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                applyFilters();
                return true;
            }
        });
    }

    private void setupCartButton() {
        findViewById(R.id.btnCart).setOnClickListener(v ->
                startActivity(new Intent(this, CartActivity.class))
        );
    }

    private void applyFilters() {
        filteredList.clear();

        String searchText = ((SearchView)findViewById(R.id.searchBar))
                .getQuery().toString().toLowerCase();

        for (MenuItem item : fullList) {

            boolean matchesCategory =
                    currentCategory.equals("All") ||
                            item.category.equalsIgnoreCase(currentCategory);

            boolean matchesSearch =
                    item.name.toLowerCase().contains(searchText);

            if (matchesCategory && matchesSearch) {
                filteredList.add(item);
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void handleCategoryClick(View v, String category) {
        resetButtons();
        currentCategory = category;

        v.setBackgroundResource(R.drawable.category_selected_bg);
        ((TextView)v).setTextColor(getColor(R.color.card));

        applyFilters();
    }

    private void resetButtons() {
        int[] ids = {
                R.id.btnAll, R.id.btnBurgers, R.id.btnPizza,
                R.id.btnDrinks, R.id.btnSides, R.id.btnDesserts,
                R.id.btnWraps, R.id.btnVegan
        };

        for (int id : ids) {
            TextView btn = findViewById(id);
            btn.setBackgroundResource(R.drawable.category_bg);
            btn.setTextColor(getColor(R.color.text_primary));
        }
    }

    // 🔥 Moved all menu creation out of onCreate
    private ArrayList<MenuItem> createMenu() {
        ArrayList<MenuItem> list = new ArrayList<>();

        ArrayList<Extra> cheeseburgerExtras = new ArrayList<>();
        cheeseburgerExtras.add(new Extra("Extra Cheese", 0.99));
        cheeseburgerExtras.add(new Extra("Extra Lettuce", 0.99));
        cheeseburgerExtras.add(new Extra("Extra Tomatoes", 0.99));
        cheeseburgerExtras.add(new Extra("Extra Sauce", 0.99));
        cheeseburgerExtras.add(new Extra("Extra Beef", 2.99));

        ArrayList<Extra> baconBurgerExtras = new ArrayList<>();
        baconBurgerExtras.add(new Extra("Extra Bacon", 1.99));
        baconBurgerExtras.add(new Extra("Extra Cheese", 0.99));
        baconBurgerExtras.add(new Extra("Extra Lettuce", 0.99));
        baconBurgerExtras.add(new Extra("Extra Tomatoes", 0.99));
        baconBurgerExtras.add(new Extra("Extra Sauce", 0.99));
        baconBurgerExtras.add(new Extra("Extra Beef", 2.99));

        ArrayList<Extra> pepperoniPizzaExtras = new ArrayList<>();
        pepperoniPizzaExtras.add(new Extra("Extra Pepperoni", 1.99));
        pepperoniPizzaExtras.add(new Extra("Extra Cheese", 1.49));
        pepperoniPizzaExtras.add(new Extra("Extra Sauce", 0.99));

        ArrayList<Extra> cheesePizzaExtras = new ArrayList<>();
        cheesePizzaExtras.add(new Extra("Extra Cheese", 1.49));
        cheesePizzaExtras.add(new Extra("Extra Sauce", 0.99));

        ArrayList<Extra> veggiePizzaExtras = new ArrayList<>();
        veggiePizzaExtras.add(new Extra("Extra Veggies", 1.49));
        veggiePizzaExtras.add(new Extra("Extra Cheese", 1.49));
        veggiePizzaExtras.add(new Extra("Extra Sauce", 0.99));

        ArrayList<Extra> chickenWrapExtras = new ArrayList<>();
        chickenWrapExtras.add(new Extra("Extra Chicken", 2.49));
        chickenWrapExtras.add(new Extra("Extra Lettuce", 0.99));
        chickenWrapExtras.add(new Extra("Extra Tomatoes", 0.99));
        chickenWrapExtras.add(new Extra("Extra Sauce", 0.99));
        chickenWrapExtras.add(new Extra("Extra Cheese", 1.49));

        ArrayList<Extra> beefWrapExtras = new ArrayList<>();
        beefWrapExtras.add(new Extra("Extra Beef", 2.49));
        beefWrapExtras.add(new Extra("Extra Lettuce", 0.99));
        beefWrapExtras.add(new Extra("Extra Tomatoes", 0.99));
        beefWrapExtras.add(new Extra("Extra Sauce", 0.99));
        beefWrapExtras.add(new Extra("Extra Cheese", 1.49));

        ArrayList<Extra> veggieWrapExtras = new ArrayList<>();
        veggieWrapExtras.add(new Extra("Extra Veggies", 1.49));
        veggieWrapExtras.add(new Extra("Extra Lettuce", 0.99));
        veggieWrapExtras.add(new Extra("Extra Tomatoes", 0.99));
        veggieWrapExtras.add(new Extra("Extra Sauce", 0.99));

        ArrayList<Extra> veganExtras = new ArrayList<>();
        veganExtras.add(new Extra("Extra Veggies", 1.49));
        veganExtras.add(new Extra("Extra Sauce", 0.99));

        ArrayList<Extra> drinkExtras = new ArrayList<>();
        drinkExtras.add(new Extra("Add Ice", 0.00));

        ArrayList<Extra> friesExtras = new ArrayList<>();
        friesExtras.add(new Extra("Extra Salt", 0.00));
        friesExtras.add(new Extra("Extra Sauce", 0.99));

        ArrayList<Extra> onionRingExtras = new ArrayList<>();
        onionRingExtras.add(new Extra("Extra Sauce", 0.99));

        ArrayList<Extra> caesarSaladExtras = new ArrayList<>();
        caesarSaladExtras.add(new Extra("Extra Chicken", 2.49));
        caesarSaladExtras.add(new Extra("Extra Cheese", 1.49));
        caesarSaladExtras.add(new Extra("Extra Sauce", 0.99));
        caesarSaladExtras.add(new Extra("Extra Croutons", 0.99));

        ArrayList<Extra> dessertExtras = new ArrayList<>();

        // Burgers
        list.add(new MenuItem("Cheeseburger", "Juicy beef with melted cheese", "$8.99", R.drawable.cheeseburger, "Burgers", cheeseburgerExtras));
        list.add(new MenuItem("Double Burger", "Two patties, extra filling", "$10.99", R.drawable.double_cheeseburger, "Burgers", cheeseburgerExtras));
        list.add(new MenuItem("Bacon Burger", "Crispy bacon and beef", "$9.99", R.drawable.bacon_cheeseburger, "Burgers", baconBurgerExtras));

        // Pizza
        list.add(new MenuItem("Pepperoni Pizza", "Classic pepperoni slices", "$12.99", R.drawable.pepperoni_pizza, "Pizza", pepperoniPizzaExtras));
        list.add(new MenuItem("Cheese Pizza", "Simple and cheesy", "$11.99", R.drawable.cheese_pizza, "Pizza", cheesePizzaExtras));
        list.add(new MenuItem("Veggie Pizza", "Loaded with fresh veggies", "$12.49", R.drawable.veggie_pizza, "Pizza", veggiePizzaExtras));

        // Wraps
        list.add(new MenuItem("Chicken Wrap", "Grilled chicken wrap", "$9.99", R.drawable.chicken_wrap, "Wraps", chickenWrapExtras));
        list.add(new MenuItem("Beef Wrap", "Savory beef wrap", "$10.49", R.drawable.beef_wrap, "Wraps", beefWrapExtras));
        list.add(new MenuItem("Veggie Wrap", "Fresh veggie wrap", "$8.99", R.drawable.veggie_wrap, "Wraps", veggieWrapExtras));

        // Vegan
        list.add(new MenuItem("Vegan Burger", "Plant-based burger", "$9.99", R.drawable.vegan_burger, "Vegan", veganExtras));
        list.add(new MenuItem("Vegan Bowl", "Healthy veggie bowl", "$10.99", R.drawable.vegan_bowl, "Vegan", veganExtras));

        // Drinks
        list.add(new MenuItem("Coke", "Refreshing cola drink", "$2.99", R.drawable.coke, "Drinks", drinkExtras));
        list.add(new MenuItem("Sprite", "Lemon-lime soda", "$2.99", R.drawable.sprite, "Drinks", drinkExtras));
        list.add(new MenuItem("Iced Tea", "Chilled refreshing tea", "$2.99", R.drawable.iced_tea, "Drinks", drinkExtras));

        // Sides
        list.add(new MenuItem("Fries", "Crispy golden fries", "$4.99", R.drawable.fries, "Sides", friesExtras));
        list.add(new MenuItem("Onion Rings", "Crispy battered rings", "$5.49", R.drawable.onion_rings, "Sides", onionRingExtras));
        list.add(new MenuItem("Caesar Salad", "Fresh romaine with Caesar dressing", "$6.99", R.drawable.caesar_salad, "Sides", caesarSaladExtras));

        // Desserts
        list.add(new MenuItem("Ice Cream", "Vanilla scoop", "$3.99", R.drawable.ice_cream, "Desserts", dessertExtras));
        list.add(new MenuItem("Brownie", "Rich chocolate brownie", "$3.49", R.drawable.brownie, "Desserts", dessertExtras));

        return list;
    }
}