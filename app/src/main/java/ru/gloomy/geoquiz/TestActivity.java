package ru.gloomy.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    int currentQuestion = 0;
    int correct = 0, wrong = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //get all the questions
        loadAllQuestions();

        //shuffle the questions if you want
        Collections.shuffle(quizQuestions);

        //загрузка первого вопроса
        setQuestionScreen(currentQuestion);

        RecyclerView rvAnswers = findViewById(R.id.rvAnswers);
        rvAnswers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterRecyclerView(this, quizQuestions);
        rvAnswers.setAdapter(adapter);

        // создаем библиотеку
        private void loadAllQuestions () {
            quizQuestions = new ArrayList<>();
            Gson gson = new Gson();
            InputStream fileInputStream = getResources().openRawResource(R.raw.question_qeoquiz);
            String file = readTextFile(fileInputStream);
            QuestionList list = gson.fromJson(file, QuestionList.class);
            Log.e("TAG", "onCreate: " + list.getQuizQuestions().get(0).getQuestion());

            try {
                JSONObject jsonObj = new JSONObject(j);
                JSONArray questions = jsonObj.getJSONArray("questions");
                for (int i = 0; i < questions.length(); i++) {
                    JSONObject question = questions.getJSONObject(i);

                    String questionString = question.getString("question");
                    String answerString = question.getString("answers");
                    long true_answer = question.getLong("true_answer");
                    String variant = question.getString("variant");
                    quizQuestions.add(new QuizQuestion(
                            questionString,
                            answerString,
                            true_answer,
                            variant
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
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

    public void onItemClick(View view, int position) {
        if (quizQuestions.get(currentQuestion).getAnswer()
                        .equals(quizQuestions.get(currentQuestion).getTrue_answer())) {
                    //correct
                    correct++;
                    Toast.makeText(TestActivity.this, "Ответ правильный!", Toast.LENGTH_SHORT).show();
                } else {
                    //wrong
                    wrong++;
                    Toast.makeText(TestActivity.this, "Ответ неправильный! Правильный ответ: "
                            + quizQuestions.get(currentQuestion).getTrue_answer(), Toast.LENGTH_SHORT).show();
                }

                //загружаем следующий вопрос
                if (currentQuestion < quizQuestions.size() - 1) {
                    currentQuestion++;
                    setQuestionScreen(currentQuestion);
                } else {
                    //конец игры
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("correct: ", correct);
                    intent.putExtra("wrong: ", wrong);
                    startActivity(intent);
                    finish();
                }

        });
    private void setQuestionScreen(int number) {        //отображение вопросов на экране
        tvQuestion.setText(questionItems.get(number).getQuestion());
        rvAnswers.set(qquizQuestions.get(number).getAnswer());

    }

