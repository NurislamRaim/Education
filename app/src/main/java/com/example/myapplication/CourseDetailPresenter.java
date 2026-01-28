package com.example.myapplication;

public class CourseDetailPresenter {
    private final CourseDetailView view;

    public CourseDetailPresenter(CourseDetailView view) {
        this.view = view;
    }

    public void loadCourseDetails(String courseId) {
        // Тут могла быть загрузка данных из БД или API
        String title = "Курс по Android";
        String description = "Изучи основы Android-разработки на Java.";
        view.showCourseDetails(title, description);
    }
}
