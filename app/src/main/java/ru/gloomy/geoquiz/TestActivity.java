package ru.gloomy.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;




import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends Activity {
    private List<Void> answers;
    private int IndexQuestion = 0;
    private ArrayAdapter<QuizQuestion> adapter;
    private ListView AnswerListView;
    private Object String;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        answers = new ArrayList<>();
        AnswerListView =  findViewById(R.id.AnswerListView);
        AnswerListView.setAdapter(adapter);

        /*public Void addQuestion(ListView){
           String = answers.toString();
            QuizQuestion answers = new QuizQuestion(answers);
            answers.add(answers);
            adapter.notifyDataSetChanged();
        }*/


        // создаем библиотеку
        Gson gson = new Gson();
        InputStream fileInputStream = getResources().openRawResource(R.raw.question_qeoquiz);
        String file = readTextFile(fileInputStream);
        QuestionList list = gson.fromJson(file, QuestionList.class);
        Log.e("TAG", "onCreate: " + list.getQuizQuestions().get(0).getQuestion());


    }

    public String readTextFile(InputStream inputStream) { // ввод чтение gson


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



