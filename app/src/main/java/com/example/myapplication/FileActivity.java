package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FileActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1;

    private ArrayList<Uri> fileUris = new ArrayList<>();
    private ArrayList<String> fileNames = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private ListView fileListView;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        Button openFileButton = findViewById(R.id.openFileButton);
        fileListView = findViewById(R.id.fileListView);
        emptyText = findViewById(R.id.emptyText);

        loadSavedFiles();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                fileNames
        );

        fileListView.setAdapter(adapter);
        fileListView.setEmptyView(emptyText);

        openFileButton.setOnClickListener(v -> openFilePicker());

        fileListView.setOnItemClickListener(
                (parent, view, position, id) ->
                        openPdf(fileUris.get(position))
        );
    }

    // ---------- FILE PICKER ----------
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION |
                        Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
        );
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    // ---------- RESULT ----------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST &&
                resultCode == Activity.RESULT_OK &&
                data != null) {

            Uri uri = data.getData();
            if (uri == null) return;

            getContentResolver().takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            );

            String fileName = getFileName(uri);

            fileUris.add(uri);
            fileNames.add(fileName);
            adapter.notifyDataSetChanged();

            saveFiles();

            Toast.makeText(
                    this,
                    "Файл добавлен: " + fileName,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // ---------- SAVE ----------
    private void saveFiles() {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < fileNames.size(); i++) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("name", fileNames.get(i));
                obj.put("uri", fileUris.get(i).toString());
                jsonArray.put(obj);
            } catch (JSONException ignored) {}
        }

        SharedPreferences prefs =
                getSharedPreferences("MyFiles", MODE_PRIVATE);
        prefs.edit()
                .putString("files_json", jsonArray.toString())
                .apply();
    }

    // ---------- LOAD ----------
    private void loadSavedFiles() {
        SharedPreferences prefs =
                getSharedPreferences("MyFiles", MODE_PRIVATE);

        String jsonString = prefs.getString("files_json", null);

        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    fileNames.add(obj.getString("name"));
                    fileUris.add(Uri.parse(obj.getString("uri")));
                }
            } catch (JSONException ignored) {}
        }
    }

    // ---------- FILE NAME (CORRECT WAY) ----------
    private String getFileName(Uri uri) {
        String result = "Без названия";

        try (Cursor cursor = getContentResolver()
                .query(uri, null, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                int index =
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (index != -1) {
                    result = cursor.getString(index);
                }
            }
        }
        return result;
    }

    // ---------- OPEN PDF ----------
    private void openPdf(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(
                    this,
                    "Нет приложения для открытия PDF",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
