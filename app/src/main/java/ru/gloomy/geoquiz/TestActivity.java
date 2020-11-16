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
import java.util.List;
import java.util.Locale;

//endregion

public class TestActivity extends AppCompatActivity implements AdapterRecyclerView.ItemClickListener {


    private QuestionList mQuestionList;
    private AdapterRecyclerView mAdapter;
    private int mCurrentQuestion;
    private int mCorrect;
    private int mWrong;
    private int mSize;
    private String mIndexTrueAnswer;
    private int mSeconds = 20;
    private  TextView mTvQuestionScore, mTvNowQuestion, mTvCountCorrect, mTvCountWrong, mTvQuestion;



    /* Дабы не запутаться :

    currentQuestion общее количество отвеченых вопросов,correct - счёт количества правильных ответов, wrong - счет количесва неправильных ответов, size - размер массива(количество вопросов),
    indexTrueAnswer - номер ответа, seconds - время отведённое на тест (сек);

    vQuestionScore - общее количество вопросов , tvNowQuestion - для отображения количество отвеченных вопросов, tvCountCorrect - отображает количество правильных вопросов,
     tvCountWrong - отображает количество неправильных вопросов, tvQuestion - обоюражени вопроса из json.

    */

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
        mQuestionList = new QuestionList();
        Gson gson = new Gson();
        InputStream fileInputStream = getResources().openRawResource(R.raw.question_qeoquiz);
        String file = readTextFile(fileInputStream);
        mQuestionList = gson.fromJson(file, QuestionList.class);



//region LOG.e
        Log.e("TAG", "onCreate: listQuestion: " + mQuestionList.getQuizQuestions().get(0).getQuestion());
        Log.e("TAG", "onCreate: listAnswers: " + mQuestionList.getQuizQuestions().get(0).getAnswers());



//endregion
        RecyclerView rvAnswers;
        rvAnswers = findViewById(R.id.rvAnswers);
        mSize = mQuestionList.getQuizQuestions().size();
        mTvQuestion = findViewById(R.id.tvQuestion);
        mTvQuestionScore = findViewById(R.id.tvQuestionScore);
        mTvQuestionScore.setText(String.valueOf(mSize));
        mTvNowQuestion = findViewById(R.id.tvNowQuestion);
        mTvNowQuestion.setText(String.valueOf(mCurrentQuestion));
        mIndexTrueAnswer = String.valueOf(mQuestionList.getQuizQuestions().get(3).getTrueAnswer());


//region инициализируем костомный список
        rvAnswers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvAnswers.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.row_divider)));
//endregion

//заполняем адаптер

                mAdapter = new AdapterRecyclerView(this, mQuestionList.getQuizQuestions().get(mCurrentQuestion).getAnswers());
                mAdapter.setClickListener(this);
                rvAnswers.setAdapter(mAdapter);
                mTvQuestion.setText(mQuestionList.getQuizQuestions().get(mCurrentQuestion).getQuestion());
        runTimer(); //запуск таймера
    }

//на "наподумать"
   /*
    public Dialog onCreateDialog(Bundle savedInstanceState) {
            String title = "Тест завершен ";
            String message = "Вы набрали ___ %";
            String button1String = "Вернуться в меню";
            String button2String = "Пройти еще раз";

            AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
            builder.setTitle(title);  // заголовок
            builder.setMessage(message); // сообщение
            builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getBaseContext() , "выход в главное меню",
                            Toast.LENGTH_LONG).show();
                }
            });
            builder.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getBaseContext(), "рестарт теста", Toast.LENGTH_LONG)
                            .show();
                }
            });
            builder.setCancelable(true);

            return builder.create();
        }

    */

    public List<String> onItemClick(View view, int position) {
        mCurrentQuestion++;
        Toast.makeText(this, "Ваш ответ: " + mAdapter.getItem(position) + ", номер массива: " + position + ", длина массива: " + mSize + ". Правильный ответ: " + mIndexTrueAnswer,
                    Toast.LENGTH_SHORT).show();

        return null;
    }

    //region ввод чтение Gson
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
                int minutes = (mSeconds % 3600) / 60;
                int secs = mSeconds % 60;
                String time = String.format(Locale.getDefault(),
                        "%02d:%02d", minutes, secs);
                timeView.setText(time);
                mSeconds--;
                if (mSeconds < 15) {
                    timeView.setTextColor(Color.RED);

                }
                if (mSeconds == 0) {
                    finish();

                    Intent result = new Intent(TestActivity.this, ResultActivity.class);
                    result.putExtra("correct: ", mCorrect);
                    result.putExtra("wrong: ", mWrong);
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
        Log.e("TAG", "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "onResume() called");
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



