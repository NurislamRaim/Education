package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    // ------------------------------
    // Глобальная база пользователей
    // ------------------------------
    public static final Map<String, String> VALID_USERS = new HashMap<>();

    static {
        VALID_USERS.put("admin@gmail.com", "123456");
        VALID_USERS.put("test@example.com", "123456");
    }

    // Элементы UI
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private MaterialButton btnSignIn;
    private MaterialButton btnRegister;
    private View btnBack;
    private TextView tvForgotPassword;
    private TextView tvDontHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Находим элементы
        etEmail = findViewById(R.id.et_email_login);
        etPassword = findViewById(R.id.et_password_login);
        btnSignIn = findViewById(R.id.btn_sign_in_submit);
        btnBack = findViewById(R.id.btn_back_login);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        tvDontHaveAccount = findViewById(R.id.tv_dont_have_account);

        // Кнопка регистрации (добавлена!)
        btnRegister = findViewById(R.id.btn_register);

        // ---- Кнопки ----
        btnSignIn.setOnClickListener(v -> loginUser());

        btnBack.setOnClickListener(v -> finish());

        tvForgotPassword.setOnClickListener(v ->
                Toast.makeText(this, "Переход на экран восстановления пароля", Toast.LENGTH_SHORT).show()
        );

        tvDontHaveAccount.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class))
        );

        btnRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class))
        );
    }

    // ------------------------------
    // Логика входа
    // ------------------------------
    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!VALID_USERS.containsKey(email)) {
            Toast.makeText(this, "Пользователь не найден!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!VALID_USERS.get(email).equals(password)) {
            Toast.makeText(this, "Неверный пароль!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Сохраняем вход
        getSharedPreferences("LoginPrefs", MODE_PRIVATE)
                .edit()
                .putBoolean("isLoggedIn", true)
                .putString("userEmail", email)
                .apply();

        Toast.makeText(this, "Вход выполнен успешно!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}