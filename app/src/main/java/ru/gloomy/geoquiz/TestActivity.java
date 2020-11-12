package ru.gloomy.geoquiz;
//region import class
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
//endregion

public class TestActivity extends AppCompatActivity implements AdapterRecyclerView.ItemClickListener {

   public List<String> quizQuestions;
   private AdapterRecyclerView adapter;
   private int length , correct, wrong,  seconds = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//region создание random числа для варианта
        int a = (int) (Math.random() * 10);
        Toast toast = Toast.makeText(getApplicationContext(),
                "Вариант №: " + a, Toast.LENGTH_SHORT);
        toast.show();
        //endregion

        // создаем библиотеку
        quizQuestions = new ArrayList<>();
        Gson gson = new Gson();
        InputStream fileInputStream = getResources().openRawResource(R.raw.question_qeoquiz);
        String file = readTextFile(fileInputStream);
        QuestionList list = gson.fromJson(file, QuestionList.class);

//region LOG.e
        Log.e("TAG", "onCreate: listQuestion: " + list.getQuizQuestions().get(length).getQuestion());
        Log.e("TAG", "onCreate: listAnswers: " + list.getQuizQuestions().get(length).getAnswers());
        Log.e("TAG", "onCreate: listTrueAnswer: "+ list.getQuizQuestions().get(length).getTrueAnswer());

//endregion
        RecyclerView rvAnswers;
        rvAnswers = findViewById(R.id.rvAnswers);
        TextView tvQuestion = findViewById(R.id.tvQuestion);
        runTimer();

//region инициализируем костомный список
        rvAnswers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvAnswers.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.row_divider)));
        //endregion

       //заполняем адаптер
            adapter = new AdapterRecyclerView(this, list.getQuizQuestions().get(length).getAnswers());
            adapter.setClickListener(this);
            rvAnswers.setAdapter(adapter);
            tvQuestion.setText(list.getQuizQuestions().get(length).getQuestion());

    }

    public List<String> onItemClick(View view, int position) {
        //= quizQuestions.size();
        for (int length =0; length<quizQuestions.size();) {
            Toast.makeText(this, "Ваш ответ " + adapter.getItem(position) + " номер массива " + position +" длина массива: " +length, Toast.LENGTH_SHORT).show();
            adapter.dataSetChanged(quizQuestions);
            quizQuestions.get(length++);

        }
        return null;
    }

//region ввод чтение gson
    public String readTextFile(InputStream inputStream) {
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
//endregion

//region таймер обратного счета
    private void runTimer() {
        final TextView timeView = findViewById(R.id.text_view_countdown);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(),
                        "%02d:%02d", minutes, secs);
                timeView.setText(time);
                seconds--;
                if (seconds < 15) {
                    timeView.setTextColor(Color.RED);

                } if  ( seconds == 0){
                    finish();

                    Intent result = new Intent(TestActivity.this, ResultActivity.class);
                    result.putExtra("correct: ", correct);
                    result.putExtra("wrong: ", wrong);
                    startActivity(result);
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
//endregion

//region жизненный цикл приложения
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TAG", "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG","onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG","onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG", "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy() called");
    }
//endregion
}


