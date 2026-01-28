package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CourseDetailActivity extends AppCompatActivity {

    private String[] courses = {
            "Основы программирования",
            "Android-разработка",
            "Python для начинающих",
            "Алгоритмы и структуры данных",
            "Веб-разработка на Django"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        LinearLayout container = findViewById(R.id.courseContainer);

        for (String course : courses) {
            View courseCard = createCourseCard(course);
            container.addView(courseCard);
        }
    }

    private View createCourseCard(String title) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundResource(R.drawable.course_card_bg);
        card.setPadding(32, 32, 32, 32);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 24);
        card.setLayoutParams(params);

        TextView titleView = new TextView(this);
        titleView.setText(title);
        titleView.setTextSize(18);
        titleView.setTextColor(0xFFFFFFFF);
        titleView.setPadding(0, 0, 0, 12);
        card.addView(titleView);

        Button openBtn = new Button(this);
        openBtn.setText("Открыть");
        openBtn.setBackgroundColor(0xFF1E293B);
        openBtn.setTextColor(0xFFFFFFFF);
        openBtn.setPadding(0, 8, 0, 8);
        openBtn.setOnClickListener(v -> openCourse(title));

        card.addView(openBtn);

        return card;
    }

    private void openCourse(String courseName) {
        Intent intent = new Intent(this, FileActivity.class);
        intent.putExtra("course_name", courseName);
        startActivity(intent);
    }
}
