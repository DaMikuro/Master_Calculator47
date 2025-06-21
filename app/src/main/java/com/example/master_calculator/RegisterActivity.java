package com.example.master_calculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    EditText editUsername, editEmail, editPassword, editRepeatPassword;
    Button btnRegister;
    TextView textLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.regis), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editUsername = findViewById(R.id.editUsername);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editRepeatPassword = findViewById(R.id.editRepeatPassword);
        btnRegister = findViewById(R.id.btnRegister);
        textLoginLink = findViewById(R.id.textLoginLink);

        btnRegister.setOnClickListener(v -> {
            String username = editUsername.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString();
            String repeat = editRepeatPassword.getText().toString();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(repeat)) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Некорректный email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(repeat)) {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                return;
            }


            getSharedPreferences("UserPrefs", MODE_PRIVATE)
                    .edit()
                    .putString("user_email", email)
                    .putString("user_password", password)
                    .apply();

            Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, com.example.master_calculator.LoginActivity.class));
            finish();
        });
        textLoginLink.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.master_calculator.LoginActivity.class));
            finish();
        });
    }
}