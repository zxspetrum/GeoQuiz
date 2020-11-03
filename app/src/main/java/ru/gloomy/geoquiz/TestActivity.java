package ru.gloomy.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TestActivity extends AppCompatActivity {


    List<String> quizQuestions;
    AdapterRecyclerView adapter;
    TextView tvQuestion;
    int currentQuestion = 0;
    int correct = 0, wrong = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // создаем библиотеку

            quizQuestions = new ArrayList<>();
            Gson gson = new Gson();
            InputStream fileInputStream = getResources().openRawResource(R.raw.question_qeoquiz);
            String file = readTextFile(fileInputStream);
            QuestionList list = gson.fromJson(file, QuestionList.class);
            Log.e("TAG", "onCreate: " + list.getQuizQuestions().get(0).getQuestion());


        RecyclerView rvAnswers = findViewById(R.id.rvAnswers);
        TextView tvQuestion = findViewById(R.id.tvQuestion);
        rvAnswers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterRecyclerView(this, quizQuestions);
        rvAnswers.setAdapter(adapter);
        tvQuestion.setText("Добавить вопрос из QuizQuestion");
    }

        public String readTextFile (InputStream inputStream){ // ввод чтение gson
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte buf[] = new byte[1024];
            int len;
            try {
                while ((len = inputStream.read(buf)) != -1) {
                    outputStream.write(buf, 0, len);
                }
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {

            }
            return outputStream.toString();

        }



}

