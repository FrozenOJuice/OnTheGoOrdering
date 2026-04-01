package com.example.onthegoordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

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
        setContentView(R.layout.activity_checkout);

        // --- Bind UI ---
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

        // --- Toggle Pickup / Delivery ---
        orderTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioPickup) {
                deliverySection.setVisibility(View.GONE);
                pickupAddress.setVisibility(View.VISIBLE);
            } else {
                deliverySection.setVisibility(View.VISIBLE);
                pickupAddress.setVisibility(View.GONE);
            }
        });

        // --- Load Order Summary ---
        updateSummary();

        // --- Back Button ---
        backToCartBtn.setOnClickListener(v -> finish());

        // --- Place Order ---
        placeOrderBtn.setOnClickListener(v -> placeOrder());
    }

    private void updateSummary() {
        StringBuilder summary = new StringBuilder();
        double total = 0;

        for (CartItem item : CartManager.getCart()) {
            summary.append(item.getName())
                    .append(" x")
                    .append(item.getQuantity())
                    .append("\n");

            total += item.getTotalPrice();
        }

        summaryText.setText(summary.toString());
        totalText.setText("Total: $" + String.format("%.2f", total));
    }

    private void placeOrder() {
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

        Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT).show();

        // Clear cart
        CartManager.getCart().clear();

        // Go to confirmation screen
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra("customerName", name);
        startActivity(intent);

        finish();
    }
}