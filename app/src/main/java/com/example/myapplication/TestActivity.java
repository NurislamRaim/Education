package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    private static class Question {
        String question;
        String[] options;
        int correctIndex;

        Question(String question, String[] options, int correctIndex) {
            this.question = question;
            this.options = options;
            this.correctIndex = correctIndex;
        }
    }

    private Question[] questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private TextView textQuestion;
    private RadioGroup optionsGroup;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textQuestion = findViewById(R.id.textViewTests);
        optionsGroup = findViewById(R.id.optionsGroup);
        btnNext = findViewById(R.id.btnStartTest);

        initQuestions();
        showQuestion(currentQuestionIndex);

        btnNext.setOnClickListener(v -> handleNextQuestion());
    }

    private void handleNextQuestion() {
        int selectedId = optionsGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            Toast.makeText(this, "Выберите вариант ответа!", Toast.LENGTH_SHORT).show();
            return;
        }

        int answerIndex = optionsGroup.indexOfChild(findViewById(selectedId));
        if (answerIndex == questions[currentQuestionIndex].correctIndex) {
            score++;
        }

        currentQuestionIndex++;
        if (currentQuestionIndex < questions.length) {
            showQuestion(currentQuestionIndex);
        } else {
            Toast.makeText(this, "Тест завершён! Правильных ответов: " + score + "/" + questions.length, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void initQuestions() {
        questions = new Question[]{
                // Основы программирования
                new Question("Что такое переменная в программировании?", new String[]{"Объект, который хранит данные", "Функция для вывода текста", "Тип данных int", "Массив"}, 0),
                new Question("Что означает оператор '=' в Java?", new String[]{"Сравнение", "Присваивание значения", "Создание метода", "Условный оператор"}, 1),

                // Android-разработка
                new Question("Какой файл содержит разметку интерфейса в Android?", new String[]{"MainActivity.java", "AndroidManifest.xml", "activity_main.xml", "build.gradle"}, 2),
                new Question("Что такое Intent в Android?", new String[]{"Компонент для хранения данных", "Объект для перехода между Activity", "Виджет для кнопки", "Библиотека Android"}, 1),

                // Python для начинающих
                new Question("Как вывести текст в Python?", new String[]{"print()", "echo()", "console.log()", "System.out.println()"}, 0),
                new Question("Как объявить список в Python?", new String[]{"{}", "[]", "()", "<>"}, 1),

                // Алгоритмы и структуры данных
                new Question("Что такое стек (stack)?", new String[]{"Очередь с FIFO", "Последовательность с LIFO", "Дерево", "Граф"}, 1),
                new Question("Какой алгоритм сортировки проще всего реализовать?", new String[]{"Сортировка пузырьком", "Быстрая сортировка", "Сортировка слиянием", "HeapSort"}, 0),

                // Веб-разработка на Django
                new Question("Как создать новое приложение в Django?", new String[]{"python manage.py startapp имя_приложения", "django start project", "python create app", "django new app"}, 0),
                new Question("Что делает файл urls.py в Django?", new String[]{"Содержит настройки базы данных", "Определяет маршруты URL для приложения", "Создаёт модели", "Запускает сервер"}, 1)
        };
    }

    private void showQuestion(int index) {
        optionsGroup.removeAllViews();
        Question q = questions[index];
        textQuestion.setText(q.question);

        for (String option : q.options) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            optionsGroup.addView(radioButton);
        }

        optionsGroup.clearCheck();
    }
}
