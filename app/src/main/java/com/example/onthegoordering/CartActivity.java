package com.example.onthegoordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CartActivity extends AppCompatActivity {

    RecyclerView recycler;
    TextView totalText;
    TextView emptyText;
    CartAdapter adapter;
    View btnClearAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recycler = findViewById(R.id.recyclerCart);
        totalText = findViewById(R.id.txtTotal);
        emptyText = findViewById(R.id.txtEmpty);
        btnClearAll = findViewById(R.id.btnClearAll);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CartAdapter(CartManager.getCart(), new CartAdapter.CartListener() {
            @Override
            public void onRemove(int position) {
                CartManager.removeItem(position);
                adapter.notifyDataSetChanged();
                updateTotal();
            }

            @Override
            public void onEdit(CartItem item, int position) {
                Intent intent = new Intent(CartActivity.this, CustomizeActivity.class);

                intent.putExtra("name", item.name);
                intent.putExtra("price", item.basePrice);
                intent.putExtra("quantity", item.quantity);
                intent.putExtra("selectedExtras", item.extras);
                intent.putExtra("extras", item.allExtras);
                intent.putExtra("image", item.image);
                intent.putExtra("editIndex", position);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }

            @Override
            public void onIncrement(int position) {
                CartManager.getCart().get(position).quantity++;
                adapter.notifyItemChanged(position);
                updateTotal();
            }

            @Override
            public void onDecrement(int position) {
                CartItem item = CartManager.getCart().get(position);
                if (item.quantity > 1) {
                    item.quantity--;
                    adapter.notifyItemChanged(position);
                    updateTotal();
                }
            }
        });

        recycler.setAdapter(adapter);

        updateTotal();

        findViewById(R.id.btnBackToMenu).setOnClickListener(v -> finish());

        btnClearAll.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Clear Cart?")
                    .setMessage("Are you sure you want to remove all items from your cart?")
                    .setPositiveButton("Clear All", (dialog, which) -> {
                        CartManager.getCart().clear();
                        adapter.notifyDataSetChanged();
                        updateTotal();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        Button btnCheckout = findViewById(R.id.btnCheckout);

        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(this, CheckoutActivity.class);
            startActivity(intent);
        });
    }

    public void updateTotal() {
        double total = CartManager.getTotal();
        totalText.setText("Total: $" + String.format("%.2f", total));
        updateEmptyState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        updateTotal();
    }

    private void updateEmptyState() {
        if (CartManager.getCart().isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            btnClearAll.setVisibility(View.GONE);
        } else {
            emptyText.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            btnClearAll.setVisibility(View.VISIBLE);
        }
    }
}
