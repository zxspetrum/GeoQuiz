package ru.gloomy.geoquiz.Lecture;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import ru.gloomy.geoquiz.DetailActivity;
import ru.gloomy.geoquiz.R;


public class LearnActivity extends AppCompatActivity implements LectureListFragment.Listener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

    }

    @Override
    public void itemClicked(long id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_LECTURE_ID, (int)id);
        startActivity(intent);
    }
    }