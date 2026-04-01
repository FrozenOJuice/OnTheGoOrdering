package com.example.onthegoordering;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import java.util.ArrayList;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MenuAdapter adapter;
    ArrayList<MenuItem> fullList;
    ArrayList<MenuItem> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerMenu);

        fullList = new ArrayList<>();
        filteredList = new ArrayList<>();

        fullList.add(new MenuItem("Cheeseburger", "Juicy beef with melted cheese", "$8.99", R.drawable.cheeseburger, "Burgers"));
        fullList.add(new MenuItem("Double Burger", "Two patties, extra filling", "$10.99", R.drawable.double_cheeseburger, "Burgers"));
        fullList.add(new MenuItem("Bacon Burger", "Crispy bacon and beef", "$9.99", R.drawable.bacon_cheeseburger, "Burgers"));

        fullList.add(new MenuItem("Pepperoni Pizza", "Classic pepperoni slices", "$12.99", R.drawable.pepperoni_pizza, "Pizza"));
        fullList.add(new MenuItem("Cheese Pizza", "Simple and cheesy", "$11.99", R.drawable.cheese_pizza, "Pizza"));
        fullList.add(new MenuItem("Veggie Pizza", "Loaded with fresh veggies", "$12.49", R.drawable.veggie_pizza, "Pizza"));

        fullList.add(new MenuItem("Coke", "Refreshing cola drink", "$2.99", R.drawable.coke, "Drinks"));
        fullList.add(new MenuItem("Sprite", "Lemon-lime soda", "$2.99", R.drawable.sprite, "Drinks"));
        fullList.add(new MenuItem("Iced Tea", "Chilled refreshing tea", "$2.99", R.drawable.iced_tea, "Drinks"));

        fullList.add(new MenuItem("Fries", "Crispy golden fries", "$4.99", R.drawable.fries, "Sides"));
        fullList.add(new MenuItem("Onion Rings", "Crispy battered rings", "$5.49", R.drawable.onion_rings, "Sides"));
        fullList.add(new MenuItem("Caesar Salad", "Fresh romaine with Caesar dressing", "$6.99", R.drawable.caesar_salad, "Sides"));

        fullList.add(new MenuItem("Ice Cream", "Vanilla scoop", "$3.99", R.drawable.ice_cream, "Desserts"));
        fullList.add(new MenuItem("Brownie", "Rich chocolate brownie", "$3.49", R.drawable.brownie, "Desserts"));

        fullList.add(new MenuItem("Burger Combo", "Burger, fries, and drink", "$14.99", R.drawable.burger_combo, "Combos"));
        fullList.add(new MenuItem("Pizza Combo", "Pizza and drink", "$15.99", R.drawable.pizza_combo, "Combos"));

        fullList.add(new MenuItem("Vegan Burger", "Plant-based burger", "$9.99", R.drawable.vegan_burger, "Vegan"));
        fullList.add(new MenuItem("Vegan Bowl", "Healthy veggie bowl", "$10.99", R.drawable.vegan_bowl, "Vegan"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        filteredList.addAll(fullList);
        adapter = new MenuAdapter(filteredList);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnAll).setOnClickListener(v -> handleCategoryClick(v, "All"));
        findViewById(R.id.btnBurgers).setOnClickListener(v -> handleCategoryClick(v, "Burgers"));
        findViewById(R.id.btnPizza).setOnClickListener(v -> handleCategoryClick(v, "Pizza"));
        findViewById(R.id.btnDrinks).setOnClickListener(v -> handleCategoryClick(v, "Drinks"));
        findViewById(R.id.btnSides).setOnClickListener(v -> handleCategoryClick(v, "Sides"));
        findViewById(R.id.btnDesserts).setOnClickListener(v -> handleCategoryClick(v, "Desserts"));
        findViewById(R.id.btnCombos).setOnClickListener(v -> handleCategoryClick(v, "Combos"));
        findViewById(R.id.btnVegan).setOnClickListener(v -> handleCategoryClick(v, "Vegan"));
        handleCategoryClick(findViewById(R.id.btnAll), "All");
    }

    private void handleCategoryClick(View v, String category) {
        resetButtons();
        v.setBackgroundResource(R.drawable.category_selected_bg);
        ((TextView)v).setTextColor(getColor(R.color.card));
        filterByCategory(category);
    }

    private void filterByCategory(String category) {
        filteredList.clear();

        if (category.equals("All")) {
            filteredList.addAll(fullList);
        } else {
            for (MenuItem item : fullList) {
                if (item.category.equals(category)) {
                    filteredList.add(item);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void resetButtons() {
        int[] ids = {
                R.id.btnAll, R.id.btnBurgers, R.id.btnPizza,
                R.id.btnDrinks, R.id.btnSides, R.id.btnDesserts,
                R.id.btnCombos, R.id.btnVegan
        };

        for (int id : ids) {
            TextView btn = findViewById(id);
            btn.setBackgroundResource(R.drawable.category_bg);
            btn.setTextColor(getColor(R.color.text_primary));
        }
    }


}