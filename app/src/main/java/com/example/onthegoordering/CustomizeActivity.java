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

        TextView totalText = findViewById(R.id.txtTotalPrice);

        double basePrice = Double.parseDouble(
                getIntent().getStringExtra("price").replace("$", "")
        );

        Intent intent = getIntent();

        name.setText(intent.getStringExtra("name"));
        price.setText(intent.getStringExtra("price"));
        image.setImageResource(intent.getIntExtra("image", 0));

        String category = getIntent().getStringExtra("category");


        ArrayList<Extra> extras = (ArrayList<Extra>) getIntent().getSerializableExtra("extras");

        LinearLayout extrasContainer = findViewById(R.id.extrasContainer);

        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        final int[] quantity = {1};

        Runnable updateTotal = () -> {
            double total = basePrice;

            for (CheckBox cb : checkBoxes) {
                if (cb.isChecked()) {
                    Extra extra = (Extra) cb.getTag();
                    total += extra.price;
                }
            }

            total *= quantity[0];

            totalText.setText("Total: $" + String.format("%.2f", total));
        };

        for (Extra extra : extras) {
            CheckBox cb = new CheckBox(this);
            cb.setText(extra.name + " (+$" + extra.price + ")");
            cb.setTag(extra);
            cb.setButtonTintList(getColorStateList(R.color.primary));
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> updateTotal.run());

            extrasContainer.addView(cb);
            checkBoxes.add(cb);
        }

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        plus.setOnClickListener(v -> {
            quantity[0]++;
            quantityText.setText(String.valueOf(quantity[0]));
            updateTotal.run();
        });

        minus.setOnClickListener(v -> {
            if (quantity[0] > 1) {
                quantity[0]--;
                quantityText.setText(String.valueOf(quantity[0]));
            }
            updateTotal.run();
        });

        updateTotal.run();
    }


}