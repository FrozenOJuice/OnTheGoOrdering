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
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {

    MenuAdapter adapter;
    ArrayList<MenuItem> fullList;
    ArrayList<MenuItem> filteredList;
    String currentCategory = "All";


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

        ArrayList<String> cheeseburgerExtras = new ArrayList<>();
        cheeseburgerExtras.add("Extra Cheese");
        cheeseburgerExtras.add("Extra Lettuce");
        cheeseburgerExtras.add("Extra Tomatoes");
        cheeseburgerExtras.add("Extra Sauce");
        cheeseburgerExtras.add("Extra Beef");

        ArrayList<String> baconBurgerExtras = new ArrayList<>();
        baconBurgerExtras.add("Extra Bacon");
        baconBurgerExtras.add("Extra Cheese");
        baconBurgerExtras.add("Extra Lettuce");
        baconBurgerExtras.add("Extra Tomatoes");
        baconBurgerExtras.add("Extra Sauce");
        baconBurgerExtras.add("Extra Beef");


        ArrayList<String> pepperoniPizzaExtras = new ArrayList<>();
        pepperoniPizzaExtras.add("Extra Pepperoni");
        pepperoniPizzaExtras.add("Extra Cheese");
        pepperoniPizzaExtras.add("Extra Sauce");

        ArrayList<String> cheesePizzaExtras = new ArrayList<>();
        cheesePizzaExtras.add("Extra Cheese");
        cheesePizzaExtras.add("Extra Sauce");

        ArrayList<String> veggiePizzaExtras = new ArrayList<>();
        veggiePizzaExtras.add("Extra Veggies");
        veggiePizzaExtras.add("Extra Cheese");
        veggiePizzaExtras.add("Extra Sauce");


        ArrayList<String> chickenWrapExtras = new ArrayList<>();
        chickenWrapExtras.add("Extra Chicken");
        chickenWrapExtras.add("Extra Lettuce");
        chickenWrapExtras.add("Extra Tomatoes");
        chickenWrapExtras.add("Extra Sauce");
        chickenWrapExtras.add("Extra Cheese");

        ArrayList<String> beefWrapExtras = new ArrayList<>();
        beefWrapExtras.add("Extra Beef");
        beefWrapExtras.add("Extra Lettuce");
        beefWrapExtras.add("Extra Tomatoes");
        beefWrapExtras.add("Extra Sauce");
        beefWrapExtras.add("Extra Cheese");

        ArrayList<String> veggieWrapExtras = new ArrayList<>();
        veggieWrapExtras.add("Extra Veggies");
        veggieWrapExtras.add("Extra Lettuce");
        veggieWrapExtras.add("Extra Tomatoes");
        veggieWrapExtras.add("Extra Sauce");


        ArrayList<String> veganExtras = new ArrayList<>();
        veganExtras.add("Extra Veggies");
        veganExtras.add("Extra Sauce");


        ArrayList<String> drinkExtras = new ArrayList<>();
        drinkExtras.add("Add Ice");


        ArrayList<String> friesExtras = new ArrayList<>();
        friesExtras.add("Extra Salt");
        friesExtras.add("Extra Sauce");

        ArrayList<String> onionRingExtras = new ArrayList<>();
        friesExtras.add("Extra Sauce");

        ArrayList<String> caesarSaladExtras = new ArrayList<>();
        caesarSaladExtras.add("Extra Chicken");
        caesarSaladExtras.add("Extra Cheese");
        caesarSaladExtras.add("Extra Sauce");
        caesarSaladExtras.add("Extra Croutons");

        ArrayList<String> dessertExtras = new ArrayList<>();


        fullList.add(new MenuItem("Cheeseburger", "Juicy beef with melted cheese", "$8.99", R.drawable.cheeseburger, "Burgers", cheeseburgerExtras));
        fullList.add(new MenuItem("Double Burger", "Two patties, extra filling", "$10.99", R.drawable.double_cheeseburger, "Burgers", cheeseburgerExtras));
        fullList.add(new MenuItem("Bacon Burger", "Crispy bacon and beef", "$9.99", R.drawable.bacon_cheeseburger, "Burgers", baconBurgerExtras));

        fullList.add(new MenuItem("Pepperoni Pizza", "Classic pepperoni slices", "$12.99", R.drawable.pepperoni_pizza, "Pizza", pepperoniPizzaExtras));
        fullList.add(new MenuItem("Cheese Pizza", "Simple and cheesy", "$11.99", R.drawable.cheese_pizza, "Pizza", cheesePizzaExtras));
        fullList.add(new MenuItem("Veggie Pizza", "Loaded with fresh veggies", "$12.49", R.drawable.veggie_pizza, "Pizza", veggiePizzaExtras));

        fullList.add(new MenuItem("Chicken Wrap", "Grilled chicken wrap", "$9.99", R.drawable.chicken_wrap, "Wraps", chickenWrapExtras));
        fullList.add(new MenuItem("Beef Wrap", "Savory beef wrap", "$10.49", R.drawable.beef_wrap, "Wraps", beefWrapExtras));
        fullList.add(new MenuItem("Veggie Wrap", "Fresh veggie wrap", "$8.99", R.drawable.veggie_wrap, "Wraps", veggieWrapExtras));

        fullList.add(new MenuItem("Vegan Burger", "Plant-based burger", "$9.99", R.drawable.vegan_burger, "Vegan", veganExtras));
        fullList.add(new MenuItem("Vegan Bowl", "Healthy veggie bowl", "$10.99", R.drawable.vegan_bowl, "Vegan", veganExtras));

        fullList.add(new MenuItem("Coke", "Refreshing cola drink", "$2.99", R.drawable.coke, "Drinks", drinkExtras));
        fullList.add(new MenuItem("Sprite", "Lemon-lime soda", "$2.99", R.drawable.sprite, "Drinks", drinkExtras));
        fullList.add(new MenuItem("Iced Tea", "Chilled refreshing tea", "$2.99", R.drawable.iced_tea, "Drinks", drinkExtras));

        fullList.add(new MenuItem("Fries", "Crispy golden fries", "$4.99", R.drawable.fries, "Sides", friesExtras));
        fullList.add(new MenuItem("Onion Rings", "Crispy battered rings", "$5.49", R.drawable.onion_rings, "Sides", onionRingExtras));
        fullList.add(new MenuItem("Caesar Salad", "Fresh romaine with Caesar dressing", "$6.99", R.drawable.caesar_salad, "Sides", caesarSaladExtras));

        fullList.add(new MenuItem("Ice Cream", "Vanilla scoop", "$3.99", R.drawable.ice_cream, "Desserts", dessertExtras));
        fullList.add(new MenuItem("Brownie", "Rich chocolate brownie", "$3.49", R.drawable.brownie, "Desserts", dessertExtras));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        filteredList.addAll(fullList);
        adapter = new MenuAdapter(filteredList);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnAll).setOnClickListener(v -> handleCategoryClick(v, "All"));
        findViewById(R.id.btnBurgers).setOnClickListener(v -> handleCategoryClick(v, "Burgers"));
        findViewById(R.id.btnPizza).setOnClickListener(v -> handleCategoryClick(v, "Pizza"));
        findViewById(R.id.btnWraps).setOnClickListener(v -> handleCategoryClick(v, "Wraps"));
        findViewById(R.id.btnVegan).setOnClickListener(v -> handleCategoryClick(v, "Vegan"));
        findViewById(R.id.btnDrinks).setOnClickListener(v -> handleCategoryClick(v, "Drinks"));
        findViewById(R.id.btnSides).setOnClickListener(v -> handleCategoryClick(v, "Sides"));
        findViewById(R.id.btnDesserts).setOnClickListener(v -> handleCategoryClick(v, "Desserts"));
        handleCategoryClick(findViewById(R.id.btnAll), "All");


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

    private void filterByCategory(String category) {
        currentCategory = category;
        applyFilters();
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

    private void handleCategoryClick(View v, String category) {
        resetButtons();
        v.setBackgroundResource(R.drawable.category_selected_bg);
        ((TextView)v).setTextColor(getColor(R.color.card));
        filterByCategory(category);
    }




}