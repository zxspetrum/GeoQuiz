package ru.gloomy.geoquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class TestActivity extends AppCompatActivity {


    List<String> quizQuestions;
    AdapterRecyclerView adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        RecyclerView rvAnswers = findViewById(R.id.rvAnswers);
        rvAnswers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterRecyclerView(this, quizQuestions);
        rvAnswers.setAdapter(adapter);



        // создаем библиотеку

            quizQuestions = new ArrayList<>();
            Gson gson = new Gson();
            InputStream fileInputStream = getResources().openRawResource(R.raw.question_qeoquiz);
            String file = readTextFile(fileInputStream);
            QuestionList list = gson.fromJson(file, QuestionList.class);
            Log.e("TAG", "onCreate: " + list.getQuizQuestions().get(0).getQuestion());

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

