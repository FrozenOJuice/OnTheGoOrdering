package com.example.onthegoordering;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class CustomizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customize);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView image = findViewById(R.id.customImage);
        TextView name = findViewById(R.id.customName);
        TextView price = findViewById(R.id.customPrice);

        TextView quantityText = findViewById(R.id.txtQuantity);
        TextView plus = findViewById(R.id.btnPlus);
        TextView minus = findViewById(R.id.btnMinus);

        Intent intent = getIntent();

        name.setText(intent.getStringExtra("name"));
        price.setText(intent.getStringExtra("price"));
        image.setImageResource(intent.getIntExtra("image", 0));

        String category = getIntent().getStringExtra("category");


        ArrayList<String> extras = getIntent().getStringArrayListExtra("extras");

        LinearLayout extrasContainer = findViewById(R.id.extrasContainer);

        for (String extra : extras) {
            CheckBox cb = new CheckBox(this);
            cb.setText(extra);
            cb.setButtonTintList(getColorStateList(R.color.primary));

            extrasContainer.addView(cb);
        }



        final int[] quantity = {1};

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        plus.setOnClickListener(v -> {
            quantity[0]++;
            quantityText.setText(String.valueOf(quantity[0]));
        });

        minus.setOnClickListener(v -> {
            if (quantity[0] > 1) {
                quantity[0]--;
                quantityText.setText(String.valueOf(quantity[0]));
            }
        });
    }
}