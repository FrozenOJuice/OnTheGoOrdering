package com.example.onthegoordering;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class CustomizeActivity extends AppCompatActivity {

    private double basePrice;
    private int quantity = 1;

    private TextView totalText, quantityText, nameText, priceText, txtCartBadge;
    private View btnMinus;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private ArrayList<String> initialExtras = new ArrayList<>();
    private int initialQuantity;

    private int editIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customize);

        setupInsets();
        initViews();
        loadIntentData();
        setupExtras();
        setupButtons();
        
        CartManager.setListener(() -> updateCartBadge());
        updateCartBadge();
        
        updateTotal();
    }

    private void setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    private void initViews() {
        nameText = findViewById(R.id.customName);
        priceText = findViewById(R.id.customPrice);
        quantityText = findViewById(R.id.txtQuantity);
        totalText = findViewById(R.id.txtTotalPrice);
        btnMinus = findViewById(R.id.btnMinus);
        txtCartBadge = findViewById(R.id.txtCartBadge);
    }

    private void loadIntentData() {
        Intent intent = getIntent();

        editIndex = intent.getIntExtra("editIndex", -1);
        basePrice = intent.getDoubleExtra("price", 0);

        nameText.setText(intent.getStringExtra("name"));
        priceText.setText("$" + String.format("%.2f", basePrice));

        ((ImageView) findViewById(R.id.customImage))
                .setImageResource(intent.getIntExtra("image", 0));

        if (editIndex != -1) {
            quantity = intent.getIntExtra("quantity", 1);
        }

        initialQuantity = quantity;
    }

    private void setupExtras() {
        ArrayList<Extra> allExtras =
                (ArrayList<Extra>) getIntent().getSerializableExtra("extras");

        ArrayList<Extra> selectedExtras =
                (ArrayList<Extra>) getIntent().getSerializableExtra("selectedExtras");

        LinearLayout container = findViewById(R.id.extrasContainer);

        if (selectedExtras != null) {
            for (Extra e : selectedExtras) {
                initialExtras.add(e.name);
            }
        }

        if (allExtras == null) return;

        ColorStateList orangeList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary));

        for (Extra extra : allExtras) {
            CheckBox cb = new CheckBox(this);
            cb.setText(extra.name + " (+$" + extra.price + ")");
            cb.setTag(extra);
            
            cb.setButtonTintList(orangeList);

            if (initialExtras.contains(extra.name)) {
                cb.setChecked(true);
            }

            cb.setOnCheckedChangeListener((b, isChecked) -> updateTotal());

            container.addView(cb);
            checkBoxes.add(cb);
        }
    }

    private void setupButtons() {
        findViewById(R.id.btnPlus).setOnClickListener(v -> {
            quantity++;
            updateTotal();
        });

        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateTotal();
            }
        });

        findViewById(R.id.btnCart).setOnClickListener(v -> {
            Intent intent = new Intent(this, CartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> handleBack());

        Button btnAdd = findViewById(R.id.btnAddToCart);
        if (editIndex != -1) {
            btnAdd.setText("Update Item");
        }

        btnAdd.setOnClickListener(v -> saveItem());
    }

    private void updateCartBadge() {
        int count = CartManager.getCartCount();
        if (count > 0) {
            txtCartBadge.setText(String.valueOf(count));
            txtCartBadge.setVisibility(View.VISIBLE);
        } else {
            txtCartBadge.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CartManager.setListener(() -> updateCartBadge());
        updateCartBadge();
    }

    private void updateTotal() {
        double total = basePrice;

        for (CheckBox cb : checkBoxes) {
            if (cb.isChecked()) {
                Extra e = (Extra) cb.getTag();
                total += e.price;
            }
        }

        total *= quantity;
        totalText.setText("Total: $" + String.format("%.2f", total));
        
        quantityText.setText(String.valueOf(quantity));

        if (quantity <= 1) {
            btnMinus.setEnabled(false);
            btnMinus.setAlpha(0.3f);
        } else {
            btnMinus.setEnabled(true);
            btnMinus.setAlpha(1.0f);
        }
    }

    private void saveItem() {
        ArrayList<Extra> selected = new ArrayList<>();

        for (CheckBox cb : checkBoxes) {
            if (cb.isChecked()) {
                selected.add((Extra) cb.getTag());
            }
        }

        CartItem item = new CartItem(
                nameText.getText().toString(),
                basePrice,
                quantity,
                selected,
                (ArrayList<Extra>) getIntent().getSerializableExtra("extras"),
                getIntent().getIntExtra("image", 0)
        );

        if (editIndex != -1) {
            CartManager.updateItem(editIndex, item);
        } else {
            CartManager.addItem(item);
        }

        Toast.makeText(this,
                editIndex != -1 ? "Item updated" : "Item added",
                Toast.LENGTH_SHORT).show();

        finish();
    }

    private void handleBack() {
        if (!hasChanges()) {
            finish();
        } else {
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Discard changes?")
                    .setMessage("Your changes will not be saved.")
                    .setPositiveButton("Discard", (d, w) -> finish())
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    private boolean hasChanges() {
        if (quantity != initialQuantity) return true;

        ArrayList<String> current = new ArrayList<>();
        for (CheckBox cb : checkBoxes) {
            if (cb.isChecked()) {
                Extra e = (Extra) cb.getTag();
                current.add(e.name);
            }
        }

        if (current.size() != initialExtras.size()) return true;

        for (String s : current) {
            if (!initialExtras.contains(s)) return true;
        }

        for (String s : initialExtras) {
            if (!current.contains(s)) return true;
        }

        return false;
    }
}
