package com.example.master_calculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    TextView historyTextView;
    Button btnClearHistory, btnBackHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyTextView = findViewById(R.id.historyTextView);
        btnClearHistory = findViewById(R.id.btnClearHistory);
        btnBackHistory = findViewById(R.id.btnBackHistory);

        // Получаем историю
        Intent intent = getIntent();
        String history = intent.getStringExtra("history_data");

        if (history != null && !history.isEmpty()) {
            historyTextView.setText(history);
        } else {
            historyTextView.setText("История пуста");
        }

        // Очистить историю (только в памяти)
        btnClearHistory.setOnClickListener(v -> {
            historyTextView.setText("История очищена");
            getSharedPreferences("UserPrefs", MODE_PRIVATE)
                    .edit()
                    .putString("history_full", "")
                    .apply();
        });

        // Назад
        btnBackHistory.setOnClickListener(v -> {
            finish();
        });
    }
}