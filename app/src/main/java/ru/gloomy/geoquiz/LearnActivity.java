package ru.gloomy.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LearnActivity extends AppCompatActivity implements AdapterLearn.ItemClickListener {

    AdapterLearn mAdapterLearn;
    RecyclerView rvTitleLear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        rvTitleLear = findViewById(R.id.rvTitle);
        rvTitleLear.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvTitleLear.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.row_divider)));

        mAdapterLearn = new AdapterLearn(this, getResources().getStringArray(R.array.title_learn));
        rvTitleLear.setAdapter(mAdapterLearn);
        mAdapterLearn.setClickListener(this);

    }

    @Override
    public void onItemClick(View view, int position) {
        switch (mAdapterLearn.getItem(position)) {
        }
        if (position == 0) {
        }

            if (position == 1) {

            }
            if (position == 2) {

            }
            if (position == 3) {

            }
            if (position == 4) {

            }
            if (position == 5) {

            }
            if (position == 6) {
                Intent restartMenu = new Intent(LearnActivity.this, MenuActivity.class);
                finish();
                startActivity(restartMenu);
            }


            Toast toast = Toast.makeText(this, "Выбрана позиция: " + mAdapterLearn.getItem(position) + " номер строки: " + position, Toast.LENGTH_LONG);
            toast.show();
        }
    }