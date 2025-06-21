package com.example.master_calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    EditText inputStart, inputEnd;
    TextView logView;
    Button btnTask1, btnTask2, btnTask3, btnTask4, btnClear, btnHistory, btnEnd;

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

        inputStart = findViewById(R.id.inputStart);
        inputEnd = findViewById(R.id.inputEnd);
        logView = findViewById(R.id.logTextView);
        btnTask1 = findViewById(R.id.btnTask1);
        btnTask2 = findViewById(R.id.btnTask2);
        btnTask3 = findViewById(R.id.btnTask3);
        btnTask4 = findViewById(R.id.btnTask4);
        btnClear = findViewById(R.id.btnClear);
        btnHistory = findViewById(R.id.btnHistory);
        btnEnd = findViewById(R.id.end);

        btnTask1.setOnClickListener(v -> runZuckermanTask());
        btnTask2.setOnClickListener(v -> runNivenTask());
        btnTask3.setOnClickListener(v -> runHappyNumberTask());
        btnTask4.setOnClickListener(v -> runLychrelTask());

        btnClear.setOnClickListener(v -> {
            logView.setText("Журнал действий\n");
        });

        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, com.example.master_calculator.HistoryActivity.class);
            String fullHistory = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                    .getString("history_full", "");
            intent.putExtra("history_data", fullHistory);
            startActivity(intent);
        });

        btnEnd.setOnClickListener(v -> {
            getSharedPreferences("UserPrefs", MODE_PRIVATE).edit().clear().apply();
            Intent intent = new Intent(MainActivity.this, com.example.master_calculator.LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    // Проверка диапазона
    private boolean validateRange() {
        int start, end;
        try {
            start = Integer.parseInt(inputStart.getText().toString());
            end = Integer.parseInt(inputEnd.getText().toString());
        } catch (Exception e) {
            logView.append("Ошибка: введите корректные числа\n\n");
            return false;
        }

        if (start > end) {
            logView.append("Ошибка: начало диапазона не может быть больше конца\n\n");
            return false;
        }

        return true;
    }

    private void runZuckermanTask() {
        if (!validateRange()) return;
        int start = Integer.parseInt(inputStart.getText().toString());
        int end = Integer.parseInt(inputEnd.getText().toString());
        List<Integer> result = findZuckermanNumbers(start, end);
        String time = new SimpleDateFormat("[MM.dd HH:mm:ss]", Locale.getDefault()).format(new Date());
        String output = time + " Задача 1 (Цукерман): " + result + "\n\n";
        logView.append(output);
        saveToHistory(output);
    }

    private List<Integer> findZuckermanNumbers(int start, int end) {
        List<Integer> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            int product = 1, temp = i;
            boolean hasZero = false;
            while (temp > 0) {
                int d = temp % 10;
                if (d == 0) {
                    hasZero = true;
                    break;
                }
                product *= d;
                temp /= 10;
            }
            if (!hasZero && i % product == 0) {
                list.add(i);
            }
        }
        return list;
    }

    private void runNivenTask() {
        if (!validateRange()) return;
        int start = Integer.parseInt(inputStart.getText().toString());
        int end = Integer.parseInt(inputEnd.getText().toString());
        List<Integer> result = findNivenNumbers(start, end);
        String time = new SimpleDateFormat("[MM.dd HH:mm:ss]", Locale.getDefault()).format(new Date());
        String output = time + " Задача 2 (Числа Нивена): " + result + "\n\n";
        logView.append(output);
        saveToHistory(output);
    }

    private List<Integer> findNivenNumbers(int start, int end) {
        List<Integer> list = new ArrayList<>();
        for (int num = start; num <= end; num++) {
            int sum = 0, temp = num;
            while (temp > 0) {
                sum += temp % 10;
                temp /= 10;
            }
            if (sum != 0 && num % sum == 0) {
                list.add(num);
            }
        }
        return list;
    }

    private void runHappyNumberTask() {
        if (!validateRange()) return;
        int start = Integer.parseInt(inputStart.getText().toString());
        int end = Integer.parseInt(inputEnd.getText().toString());
        List<Integer> result = new ArrayList<>();
        for (int num = start; num <= end; num++) {
            if (isHappy(num)) {
                result.add(num);
            }
        }
        String time = new SimpleDateFormat("[MM.dd HH:mm:ss]", Locale.getDefault()).format(new Date());
        String output = time + " Задача 3 (Счастливые числа): " + result + "\n\n";
        logView.append(output);
        saveToHistory(output);
    }

    private boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        while (n != 1 && !seen.contains(n)) {
            seen.add(n);
            int sum = 0;
            while (n > 0) {
                int digit = n % 10;
                sum += digit * digit;
                n /= 10;
            }
            n = sum;
        }
        return n == 1;
    }

    private void runLychrelTask() {
        if (!validateRange()) return;
        int start = Integer.parseInt(inputStart.getText().toString());
        int end = Integer.parseInt(inputEnd.getText().toString());
        StringBuilder result = new StringBuilder();
        String time = new SimpleDateFormat("[MM.dd HH:mm:ss]", Locale.getDefault()).format(new Date());
        result.append(time).append(" Задача 4 (Числа Лишрел):\n");

        for (int num = start; num <= end; num++) {
            String log = testLychrel(num);
            if (!log.isEmpty()) {
                result.append(log).append("\n");
            }
        }

        logView.append(result.append("\n").toString());
        saveToHistory(result.toString());
    }

    private String testLychrel(int n) {
        int maxIterations = 30;
        long current = n;
        StringBuilder steps = new StringBuilder();

        for (int i = 1; i <= maxIterations; i++) {
            long reversed = reverseNumber(current);
            long sum = current + reversed;
            steps.append(current).append(" + ").append(reversed).append(" = ").append(sum).append("\n");

            if (isPalindrome(sum)) {
                return "Число \"" + n + "\" (" + i + " итер.):\n" + steps.toString();
            }
            current = sum;
        }
        return "";
    }

    private long reverseNumber(long num) {
        long rev = 0;
        while (num > 0) {
            rev = rev * 10 + num % 10;
            num /= 10;
        }
        return rev;
    }

    private boolean isPalindrome(long num) {
        return num == reverseNumber(num);
    }

    private void saveToHistory(String text) {
        String oldHistory = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                .getString("history_full", "");
        String newHistory = oldHistory + text;
        getSharedPreferences("UserPrefs", MODE_PRIVATE)
                .edit()
                .putString("history_full", newHistory)
                .apply();
    }
}
