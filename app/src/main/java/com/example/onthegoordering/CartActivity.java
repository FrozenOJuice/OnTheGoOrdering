package com.example.onthegoordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        recycler.setLayoutManager(new LinearLayoutManager(this));

        // ✅ NEW: pass data + listener
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
        });

        recycler.setAdapter(adapter);

        updateTotal();

        findViewById(R.id.btnBackToMenu).setOnClickListener(v -> finish());
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
        } else {
            emptyText.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
        }
    }
}