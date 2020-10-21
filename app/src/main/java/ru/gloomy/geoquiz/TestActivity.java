package ru.gloomy.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ru.gloomy.geoquiz.RecyclerView.Adapter;

public class TestActivity extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        recyclerView = findViewById(R.id.rvAnswer);
        recyclerView.setHasFixedSize(true); // в содержимом не меняем размер макета RecyclerView
        layoutManager = new LinearLayoutManager(this);// используем линейный менеджер компоновки
        recyclerView.setLayoutManager(layoutManager);

        // указываем адаптер
        mAdapter = new Adapter(mDataset);
        recyclerView.setAdapter(mAdapter);


        // создаем библиотеку
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
