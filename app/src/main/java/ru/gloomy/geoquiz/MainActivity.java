package ru.gloomy.geoquiz;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;



public class MainActivity extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button but_menu_test, but_menu_resul;
        final ImageView iv_menu;

        but_menu_resul = findViewById(R.id.but_menu_result);
        but_menu_test = findViewById(R.id.but_menu_test);
        iv_menu = findViewById(R.id.iv_menu);

        View.OnClickListener  allBut = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (
                        v.getId()) {
                        case R.id.but_menu_test:
                        but_menu_test.setBackgroundResource(R.drawable.but_menu_press);
                        Intent test = new Intent(MainActivity.this, TestActivity.class);
                        startActivity(test);
                        break;


                    case R.id.but_menu_result:
                        but_menu_resul.setBackgroundResource(R.drawable.but_menu_press);
                        Intent result = new Intent(MainActivity.this, ResultActivity.class);
                        startActivity(result);
                        break;


                    //место для пасхалки
                    case R.id.iv_menu:
                        iv_menu.animate().rotation(-360);
                        break;


                }
            }
        };

        but_menu_test.setOnClickListener(allBut);
        but_menu_resul.setOnClickListener(allBut);
        iv_menu.setOnClickListener(allBut);
    }

}