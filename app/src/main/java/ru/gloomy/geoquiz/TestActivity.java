package ru.gloomy.geoquiz;
//region import class
import android.annotation.SuppressLint;
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

    private String StringTrueAnswer;
    private int mCurrentQuestion;
    private int mCorrectAnswer;
    private int mWrongAnswer;
    private int mSizeArray;
    private int mIndexTrueAnswer;
    private int mClickAnswer;
    private int mSecondsTimerCountdown = 20;
    private int mPositionItemAnswer;
    private TextView mTvQuestionScore, mTvNowQuestion, mTvCountCorrect, mTvCountWrong, mTvQuestion;

 /* Дабы не запутаться :

    mCorrectAnswer - счёт количества правильных ответов,
    mWrongAnswer;
    mCurrentQuestion- счет количесва отвеченных вопросов,
    mSizeArray - размер массива(количество вопросов),
    mIndexTrueAnswer - номер правильного ответа,
    mSecondsTimerCountdown - время отведённое на тест (сек);
    mPositionItemAnswer/

    vQuestionScore - общее количество вопросов , tvNowQuestion - для отображения количество отвеченных вопросов, tvCountCorrect - отображает количество правильных вопросов,
     tvCountWrong - отображает количество неправильных вопросов, tvQuestion - обоюражени вопроса из json.

    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        RecyclerView rvAnswers = findViewById(R.id.rvAnswers);
        mTvQuestion = findViewById(R.id.tvQuestion);
        mTvCountCorrect = findViewById(R.id.tvCountCorrect);
        mTvCountWrong = findViewById(R.id.tvCountWrong);
        mTvQuestionScore = findViewById(R.id.tvQuestionScore);
        mTvNowQuestion = findViewById(R.id.tvNowQuestion);

        //создание random числа для варианта
        int a = (int) (Math.random() * 10);
        Toast toast = Toast.makeText(getApplicationContext(),
                "Вариант №: " + a, Toast.LENGTH_SHORT);
        toast.show();

        //region создаем библиотеку
        mQuestionList = new QuestionList();
        Gson gson = new Gson();
        InputStream fileInputStream = getResources().openRawResource(R.raw.question_qeoquiz);
        String file = readTextFile(fileInputStream);
        mQuestionList = gson.fromJson(file, QuestionList.class);
        mSizeArray = mQuestionList.getQuizQuestions().size();
        mIndexTrueAnswer = mQuestionList.getQuizQuestions().get(0).getTrueAnswer();

        //LOG.e
        Log.e("TAG", "onCreate: listQuestion: " + mQuestionList.getQuizQuestions().get(mCurrentQuestion).getQuestion());
        Log.e("TAG", "onCreate: listAnswers: " + mQuestionList.getQuizQuestions().get(mCurrentQuestion).getAnswers());
        Log.e("TAG", "onCreate: trueAnswers: " + mQuestionList.getQuizQuestions().get(mCurrentQuestion).getTrueAnswer());

        mAdapter = new AdapterRecyclerView(this, mQuestionList.getQuizQuestions().get(mCurrentQuestion).getAnswers());
        rvAnswers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvAnswers.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.row_divider)));
        runTimer();
        mTvQuestionScore.setText(String.format("%02d", mSizeArray));
        rvAnswers.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
            if (mCurrentQuestion > mSizeArray) {
                endGame();
            } else {
                mCurrentQuestion++;
        }
    }
    //ввод чтение GSON
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
    public List<String> onItemClick(View view, int mPositionItemAnswer) {
        mClickAnswer = mPositionItemAnswer;
        Toast.makeText(this, "Номер вопроса: " + mCurrentQuestion + " Ваш ответ: " + mAdapter.getItem(mPositionItemAnswer) + ", номер массива: " + mPositionItemAnswer + ", длина массива: " + mSizeArray + ". Правильный ответ: " + mIndexTrueAnswer,
                Toast.LENGTH_SHORT).show();
        updateQuestionsAndAnswers();
        countTruAndWrongAndAnswers();
        return null;
    }
    public void updateQuestionsAndAnswers() {
        mTvQuestion.setText(mQuestionList.getQuizQuestions().get(mCurrentQuestion).getQuestion());

        mAdapter.updateAdapterData();

    }

    @SuppressLint("DefaultLocale")
    public void countTruAndWrongAndAnswers() {
        if (mClickAnswer == mIndexTrueAnswer) {
            mCorrectAnswer++;
            mCurrentQuestion++;
            mTvNowQuestion.setText(String.format("%02d",mCurrentQuestion));
            mTvCountCorrect.setText(String.format("%02d",mCorrectAnswer));
        } else {
            mWrongAnswer++;
            mCurrentQuestion++;
            mTvNowQuestion.setText(String.format("%02d",mCurrentQuestion));
            mTvCountWrong.setText(String.format("%02d",mWrongAnswer));
        }
        updateQuestionsAndAnswers();
    }
    public final void endGame() {
            /*
             * реализовать Диалоговое окно. Активити Результат заменить на справочник с сылуками на статьи в Википедии
             */

            Intent result = new Intent(TestActivity.this, ResultActivity.class);
            startActivity(result);
        }
    //таймер обратного счета
    private void runTimer() {
        final TextView timeView = findViewById(R.id.text_view_countdown);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (mSecondsTimerCountdown % 3600) / 60;
                int secs = mSecondsTimerCountdown % 60;
                String time = String.format(Locale.getDefault(),
                        "%02d:%02d", minutes, secs);
                timeView.setText(time);
                mSecondsTimerCountdown--;
                if (mSecondsTimerCountdown < 15) {
                    timeView.setTextColor(Color.RED);

                }
                if (mSecondsTimerCountdown == 0) {
                    finish();
                    Intent result = new Intent(TestActivity.this, ResultActivity.class);
                    result.putExtra("correct: ", mCorrectAnswer);
                    result.putExtra("wrong: ", mWrongAnswer);
                    startActivity(result);
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}