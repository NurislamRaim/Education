package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView; // Импортируем TextView для имени
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // --- 1. Проверка состояния входа ---
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            // Если пользователь НЕ вошел, немедленно перенаправляем на LoginActivity
            redirectToLogin();
            return; // Прерываем дальнейшее выполнение onCreate
        }

        setContentView(R.layout.activity_profile);

        // --- 2. Отображение данных пользователя ---
        // Предполагаем, что у вас в XML есть TextView с ID tvUserName
        TextView tvUserName = findViewById(R.id.tvUserName);

        // Загружаем сохраненное имя пользователя. Если нет, ставим "Гость".
        String username = prefs.getString("username", "Гость");
        tvUserName.setText("Привет, " + username + "!");

        // --- 3. Обработка кнопки Выход ---
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            // Очищаем состояние входа
            prefs.edit().putBoolean("isLoggedIn", false).apply();

            // Перенаправляем на экран входа
            redirectToLogin();
        });
    }

    /**
     * Вспомогательный метод для перенаправления на LoginActivity
     */
    private void redirectToLogin() {
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        // Эти флаги гарантируют, что все предыдущие активности будут закрыты
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}