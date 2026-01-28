package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButtons();
    }

    private void initButtons() {

        findViewById(R.id.btnLearning).setOnClickListener(v ->
                open(LearningActivity.class));

        findViewById(R.id.btnCourseDetail).setOnClickListener(v ->
                open(CourseDetailActivity.class));

        findViewById(R.id.btnStartTest).setOnClickListener(v ->
                open(TestActivity.class));

        findViewById(R.id.btnFiles).setOnClickListener(v ->
                open(FileActivity.class));

        findViewById(R.id.btnProfile).setOnClickListener(v ->
                open(ProfileActivity.class));
    }

    private void open(Class<?> activity) {
        startActivity(new Intent(MainActivity.this, activity));
    }
}
