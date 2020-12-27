package ru.gloomy.geoquiz;

//region import class
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//endregion


public class TestActivity extends AppCompatActivity implements AdapterRecyclerView.ItemClickListener {

    private QuestionList mQuestionList;
    private AdapterRecyclerView mAdapter;
    private RecyclerView rvAnswers;
    private int mCurrentQuestion;
    private int mCorrectAnswer;
    private int mWrongAnswer;
    private int mSizeArray;
    private String mIndexTrueAnswer;
    private int mTotalResult;
    private TextView mTvNowQuestion;
    private TextView mTvCountCorrect;
    private TextView mTvCountWrong;
    private TextView mTvQuestion;
    private  Dialog gameEnd;
    private  Button btnAccept;
    private TextView titleTvPopupNegativeImg, messageTvPopupNegativeImg, resultTvPopupNegativeImg;
    private  TextView titleTvPopupPositiveImg, messageTvPopupPositiveImg, resultTvPopupPositiveImg;
    private   TextView titleTvPopupNeutralImg, messageTvPopupNeutralImg, resultTvPopupNeutralImg;
    private String SelectPosition;
    private static final String RECYCLERVIEW = "recyclerView";
    private Random rnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        RecyclerView rvAnswers = findViewById(R.id.rvAnswers);
        mTvQuestion = findViewById(R.id.tvQuestion);
        mTvCountCorrect = findViewById(R.id.tvCountCorrect);
        mTvCountWrong = findViewById(R.id.tvCountWrong);
        TextView tvQuestionScore = findViewById(R.id.tvQuestionScore);
        mTvNowQuestion = findViewById(R.id.tvNowQuestion);
        gameEnd = new Dialog(this);
        mQuestionList = new QuestionList();

        Gson gson = new Gson();
        InputStream fileInputStream = getResources().openRawResource(R.raw.question_qeoquiz);
        String file = readTextFile(fileInputStream);
        mQuestionList = gson.fromJson(file, QuestionList.class);

        rvAnswers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvAnswers.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.row_divider)));

       /*
        rnd = new Random();
        rnd.nextInt(mSizeArray);
       */

        mAdapter = new AdapterRecyclerView(this, mQuestionList.getQuizQuestions().get(mCurrentQuestion).getAnswers());
        rvAnswers.setAdapter(mAdapter);
        mSizeArray = mQuestionList.getQuizQuestions().size();
        tvQuestionScore.setText(String.format("%02d", mSizeArray));
        mTvQuestion.setText(mQuestionList.getQuizQuestions().get(mCurrentQuestion).getQuestion());
        mAdapter.setClickListener(this);
        mIndexTrueAnswer = mQuestionList.getQuizQuestions().get(mCurrentQuestion).getTrueAnswer();
        runTimer();
        if (savedInstanceState != null) {
            mSecondsTimerCountdown  = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
           // mCurrentQuestion = savedInstanceState.getInt("index");
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(RECYCLERVIEW);
            rvAnswers.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }
    //ввод чтение GOON
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
    @Override
   public List<String> onItemClick(View view, int position) {
       SelectPosition = toString(mAdapter.getItem(position));
       if (countTruAndWrongAndAnswers()) {
           onStopTimer();
           currentTotal();
       } else {
           updateQuestionsAndAnswers();
       }
       return null;
   }
  private String toString(String item) {
        return item;
    }
   public void updateQuestionsAndAnswers() {
        QuizQuestion quizQuestion = mQuestionList.getQuizQuestions().get(mCurrentQuestion);
        mTvQuestion.setText(mQuestionList.getQuizQuestions().get(mCurrentQuestion).getQuestion());
        mAdapter.updateAdapterData(quizQuestion.getAnswers());
        mIndexTrueAnswer = mQuestionList.getQuizQuestions().get(mCurrentQuestion).getTrueAnswer();
   }
    @SuppressLint("DefaultLocale")
    public boolean  countTruAndWrongAndAnswers() {

            if (SelectPosition.equals(mIndexTrueAnswer) ) {

            mCorrectAnswer++;
            mCurrentQuestion++;
            mTvNowQuestion.setText(String.format("%02d", mCurrentQuestion));
            mTvCountCorrect.setText(String.format("%02d", mCorrectAnswer));

        } else {
            mWrongAnswer++;
            mCurrentQuestion++;
            mTvNowQuestion.setText(String.format("%02d", mCurrentQuestion));
            mTvCountWrong.setText(String.format("%02d", mWrongAnswer));
        }
        return mCurrentQuestion == mSizeArray;
    }
    public final void currentTotal() {
        mTotalResult = mCorrectAnswer * 100 / mSizeArray;
        if (mTotalResult > 85) {
            onStopTimer();
            ShowPositivePopup();
        } if (mTotalResult < 85) {
            onStopTimer();
            ShowNeutralPopup();
        }if (mTotalResult < 70) {
            onStopTimer();
            ShowNegativePopup();
        }
    }
    public void ShowNegativePopup () {

        gameEnd.setContentView(R.layout.custom_negative_popup);
        resultTvPopupNegativeImg = gameEnd.findViewById(R.id.resultTvPopupNegativeImg);
        btnAccept = gameEnd.findViewById(R.id.btnAcceptPopupNegativeImg);
        titleTvPopupNegativeImg = gameEnd.findViewById(R.id.titleTvPopupNegativeImg);
        messageTvPopupNegativeImg = gameEnd.findViewById(R.id.messageTvPopupNegativeImg);
        resultTvPopupNegativeImg.setText(mTotalResult+" %");
        gameEnd.setCanceledOnTouchOutside(false);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gameEnd.cancel();
                finish();

            }
        });
        gameEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        gameEnd.show();
    }
    public void ShowPositivePopup () {

        gameEnd.setContentView(R.layout.custom_positive_popup);
        btnAccept = gameEnd.findViewById(R.id.btnAcceptPopupPositiveImg);
        titleTvPopupPositiveImg = gameEnd.findViewById(R.id.titleTvPopupPositiveImg);
        messageTvPopupPositiveImg = gameEnd.findViewById(R.id.messageTvPopupPositiveImg);
        resultTvPopupPositiveImg = gameEnd.findViewById(R.id.resultTvPopupPositiveImg);
        resultTvPopupPositiveImg.setText(mTotalResult+" %");
        gameEnd.setCanceledOnTouchOutside(false);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
        gameEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        gameEnd.show();
    }
    public void ShowNeutralPopup () {

        gameEnd.setContentView(R.layout.custom_neutral_popup);
        resultTvPopupNeutralImg = gameEnd.findViewById(R.id.resultTvPopupNeutralImg);
        resultTvPopupNeutralImg.setText(mTotalResult+" %");
        btnAccept = gameEnd.findViewById(R.id.btnAcceptPopupNeutralImg);
        titleTvPopupNeutralImg = gameEnd.findViewById(R.id.titleTvPopupNeutralImg);
        messageTvPopupNeutralImg = gameEnd.findViewById(R.id.messageTvPopupNeutralImg);
        gameEnd.setCanceledOnTouchOutside(false);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            finish();
            }
        });
     gameEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        gameEnd.show();
    }
    //таймер обратного счета
    int  mSecondsTimerCountdown = 30;
    private boolean running;
    private void runTimer() {
        final TextView timeView = findViewById(R.id.text_view_countdown);
        final Handler handler = new Handler();
        final Animation flashCombo = AnimationUtils.loadAnimation(
                this, R.anim.flash_taimer);
        final Animation watchRotate = AnimationUtils.loadAnimation(
                this, R.anim.flash_taimer);

        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (mSecondsTimerCountdown % 3600) / 60;
                int secs = mSecondsTimerCountdown % 60;
                String time = String.format(Locale.getDefault(),
                        "%02d:%02d", minutes, secs);
                timeView .setText(time);

                running = true;
                mSecondsTimerCountdown--;
                /* if (mSecondsTimerCountdown ==90) {
                    ivWatch.startAnimation(watchRotate);
                }
                if (mSecondsTimerCountdown ==60) {
                    ivWatch.startAnimation(watchRotate);
                }
              */
                if (mSecondsTimerCountdown < 20) {
                    timeView.setTextColor(Color.RED);
                    timeView.startAnimation(flashCombo);
                }
                if (mSecondsTimerCountdown == 0) {
                    running = false;
                    onStop();
                    currentTotal();
                    handler.postDelayed(this, 1000);

                }
               handler.postDelayed(this, 1000);
            }
        });
    }
    protected void onStopTimer(){
        running = false;
        onStop();
    }

    public void onSaveInstanceState (@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", mSecondsTimerCountdown);
        savedInstanceState.putBoolean("running", running);
       // savedInstanceState.putInt("index", mCurrentQuestion);
        savedInstanceState.putParcelable(RECYCLERVIEW, rvAnswers.getLayoutManager().onSaveInstanceState());
    }
}