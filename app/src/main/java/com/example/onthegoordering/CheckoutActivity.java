package com.example.onthegoordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CheckoutActivity extends AppCompatActivity {

    RadioGroup orderTypeGroup;
    RadioButton radioPickup, radioDelivery;

    TextView pickupAddress, summaryText, totalText;
    LinearLayout deliverySection;

    EditText addressInput, nameInput, phoneInput;

    Button backToCartBtn, placeOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);

        setupInsets();

        orderTypeGroup = findViewById(R.id.orderTypeGroup);
        radioPickup = findViewById(R.id.radioPickup);
        radioDelivery = findViewById(R.id.radioDelivery);

        pickupAddress = findViewById(R.id.pickupAddress);
        deliverySection = findViewById(R.id.deliverySection);

        addressInput = findViewById(R.id.addressInput);
        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);

        summaryText = findViewById(R.id.summaryText);
        totalText = findViewById(R.id.totalText);

        backToCartBtn = findViewById(R.id.backToCartBtn);
        placeOrderBtn = findViewById(R.id.placeOrderBtn);

        orderTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioPickup) {
                deliverySection.setVisibility(View.GONE);
                pickupAddress.setVisibility(View.VISIBLE);
            } else {
                deliverySection.setVisibility(View.VISIBLE);
                pickupAddress.setVisibility(View.GONE);
            }
        });

        updateSummary();

        backToCartBtn.setOnClickListener(v -> finish());

        placeOrderBtn.setOnClickListener(v -> confirmOrder());
    }

    private void setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    private void updateSummary() {
        StringBuilder summary = new StringBuilder();
        double total = 0;

        for (CartItem item : CartManager.getCart()) {
            summary.append(item.getName())
                    .append(" x")
                    .append(item.getQuantity())
                    .append("\n");
            
            if (item.getExtras() != null && !item.getExtras().isEmpty()) {
                for (Extra extra : item.getExtras()) {
                    summary.append("  + ").append(extra.name).append("\n");
                }
            }

            total += item.getTotalPrice();
        }

        summaryText.setText(summary.toString());
        totalText.setText("Total: $" + String.format("%.2f", total));
    }

    private void confirmOrder() {
        String name = nameInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();

        if (name.isEmpty()) {
            nameInput.setError("Required");
            return;
        }

        if (radioDelivery.isChecked() && address.isEmpty()) {
            addressInput.setError("Required");
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Confirm Order")
                .setMessage("Are you sure you want to place this order?")
                .setPositiveButton("Place Order", (dialog, which) -> placeOrder(name))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void placeOrder(String name) {
        Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT).show();

        CartManager.getCart().clear();

        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra("customerName", name);
        startActivity(intent);

        finish();
    }
}