package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Активность для регистрации нового пользователя.
 * Добавляет учетные данные в статическую карту VALID_USERS в LoginActivity.
 * Requires: R.layout.activity_registration (for UI elements).
 */
public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText etFullName;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private CheckBox cbAgreeDataProcessing;
    private MaterialButton btnSignUp;
    private View btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Предполагается, что XML-файл activity_registration.xml существует
        setContentView(R.layout.activity_registration);

        // 1. Находим элементы по ID из activity_registration.xml
        etFullName = findViewById(R.id.et_full_name_registration);
        etEmail = findViewById(R.id.et_email_registration);
        etPassword = findViewById(R.id.et_password_registration);
        cbAgreeDataProcessing = findViewById(R.id.cb_agree_data_processing);
        btnSignUp = findViewById(R.id.btn_sign_up_submit);
        btnBack = findViewById(R.id.btn_back_registration);
        View tvAlreadyHaveAccount = findViewById(R.id.tv_already_have_account);

        // 2. Устанавливаем слушатели
        btnSignUp.setOnClickListener(v -> handleRegistration());

        // Кнопка "Назад" и ссылка "Уже есть аккаунт? Войти"
        btnBack.setOnClickListener(v -> finish());
        tvAlreadyHaveAccount.setOnClickListener(v -> finish());
    }

    /**
     * Обрабатывает логику регистрации: валидация, проверка уникальности Email,
     * добавление пользователя и переход на экран входа.
     */
    private void handleRegistration() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // 1. Проверка на пустые поля
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Проверка на согласие с обработкой данных
        if (!cbAgreeDataProcessing.isChecked()) {
            Toast.makeText(this, "Необходимо согласиться с обработкой данных.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. Проверка на минимальную длину пароля
        if (password.length() < 6) {
            Toast.makeText(this, "Пароль должен содержать не менее 6 символов.", Toast.LENGTH_LONG).show();
            return;
        }

        // 4. Проверка, что Email еще не зарегистрирован
        // Мы полагаемся на статическую карту VALID_USERS из LoginActivity.
        // Это необходимо, чтобы новый пользователь не перезаписал существующего.
        if (LoginActivity.VALID_USERS.containsKey(email)) {
            Toast.makeText(this, "Этот Email уже зарегистрирован.", Toast.LENGTH_LONG).show();
            return;
        }

        // --- ЛОГИКА РЕГИСТРАЦИИ ---
        // Добавляем нового пользователя в статическую карту VALID_USERS
        LoginActivity.VALID_USERS.put(email, password);

        Toast.makeText(this, "Регистрация успешна! Теперь выполните вход.", Toast.LENGTH_LONG).show();

        // Переход на экран входа (LoginActivity)
        // Флаги CLEAR_TOP и SINGLE_TOP гарантируют, что мы возвращаемся к существующему экземпляру LoginActivity
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

        // Закрываем текущую активность, чтобы пользователь не мог вернуться к ней кнопкой "Назад"
        finish();
    }
}