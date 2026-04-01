package com.example.onthegoordering;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        TextView txtThanks = findViewById(R.id.txtThanks);
        Button backToMenuBtn = findViewById(R.id.backToMenuBtn);

        String name = getIntent().getStringExtra("customerName");
        if (name != null && !name.isEmpty()) {
            txtThanks.setText("Thanks, " + name + "!");
        }

        backToMenuBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
