package com.example.master_calculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button btnLogin;
    TextView textRegisterLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        textRegisterLink = findViewById(R.id.textRegisterLink);

        // 🔹 Кнопка входа
        btnLogin.setOnClickListener(v -> {
            String inputEmail = loginEmail.getText().toString().trim();
            String inputPassword = loginPassword.getText().toString();

            if (TextUtils.isEmpty(inputEmail) || TextUtils.isEmpty(inputPassword)) {
                Toast.makeText(this, "Введите email и пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            // Получаем сохранённые данные
            String savedEmail = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                    .getString("user_email", "");
            String savedPassword = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                    .getString("user_password", "");

            // Сравнение
            if (inputEmail.equals(savedEmail) && inputPassword.equals(savedPassword)) {
                Toast.makeText(this, "Добро пожаловать!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Неверные email или пароль", Toast.LENGTH_SHORT).show();
            }
        });
        textRegisterLink.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
    }
}