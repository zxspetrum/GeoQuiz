package ru.gloomy.geoquiz;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

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
        final Animation animationRotateMenu = AnimationUtils.loadAnimation(
                this, R.anim.rotate_menu);

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

                    case R.id.iv_menu:
                        iv_menu.startAnimation(animationRotateMenu);
                        break;
                    //место для пасхалки
/*
Нажав на картинку определенное количество раз(азбука морзе),
картинка меняется на грам.пластинку.Удерживая нажатием на изображении, пластинка вращяется
играет музыка, по завершению проигрывания открывается  MOD режим
 */
                }
            }
        };

        but_menu_test.setOnClickListener(allBut);
        but_menu_resul.setOnClickListener(allBut);
        iv_menu.setOnClickListener(allBut);
    }

}