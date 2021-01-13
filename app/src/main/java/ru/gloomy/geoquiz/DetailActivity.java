package ru.gloomy.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.gloomy.geoquiz.Lecture.LectureDetailFragment;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_LECTURE_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        LectureDetailFragment frag = (LectureDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.detail_frag);
        int lectureId = (int) getIntent().getExtras().get(EXTRA_LECTURE_ID);
        frag.setLecture(lectureId);
    }
}