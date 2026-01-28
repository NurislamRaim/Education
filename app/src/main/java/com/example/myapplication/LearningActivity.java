package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LearningActivity extends AppCompatActivity {

    // Класс курса
    private static class Course {
        String title;
        String description;

        Course(String title, String description) {
            this.title = title;
            this.description = description;
        }
    }

    private final Course[] courses = {
            new Course("Основы программирования", "Изучение базовых концепций программирования: переменные, типы данных, условия, циклы, функции."),
            new Course("Android-разработка", "Создание мобильных приложений на Android, работа с Activity, Fragment, UI и базами данных."),
            new Course("Python для начинающих", "Основы Python: синтаксис, работа с переменными, списками, словарями и функциями."),
            new Course("Алгоритмы и структуры данных", "Изучение алгоритмов сортировки, поиска, стеков, очередей, списков и деревьев."),
            new Course("Веб-разработка на Django", "Создание веб-приложений с использованием Django, работа с моделями, шаблонами и URL-маршрутами.")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        LinearLayout container = findViewById(R.id.coursesContainer);
        Button btnBack = findViewById(R.id.btnBack);

        for (Course course : courses) {

            // Создаем LayoutParams с отступами
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 24); // ← вот так задаются отступы

            // Контейнер для каждого курса
            LinearLayout courseLayout = new LinearLayout(this);
            courseLayout.setOrientation(LinearLayout.VERTICAL);
            courseLayout.setPadding(24, 24, 24, 24);
            courseLayout.setBackgroundColor(0xFFEFEFEF);
            courseLayout.setLayoutParams(params);

            // Заголовок курса
            TextView title = new TextView(this);
            title.setText(course.title);
            title.setTextSize(18);
            title.setTextColor(0xFF333333);
            title.setPadding(0, 0, 0, 12);

            // Описание курса
            TextView description = new TextView(this);
            description.setText(course.description);
            description.setVisibility(View.GONE);
            description.setTextColor(0xFF666666);

            // Кнопка "Подробнее"
            Button btnToggle = new Button(this);
            btnToggle.setText("Подробнее");
            btnToggle.setOnClickListener(v -> {
                if (description.getVisibility() == View.GONE) {
                    description.setVisibility(View.VISIBLE);
                    btnToggle.setText("Скрыть");
                } else {
                    description.setVisibility(View.GONE);
                    btnToggle.setText("Подробнее");
                }
            });

            // Собираем блок
            courseLayout.addView(title);
            courseLayout.addView(btnToggle);
            courseLayout.addView(description);

            // Добавляем в основной контейнер
            container.addView(courseLayout);
        }

        // Кнопка назад
        btnBack.setOnClickListener(v -> finish());
    }
}
