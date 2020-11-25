package ru.gloomy.geoquiz;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import www.sanju.motiontoast.MotionToast;

public class MenuActivity extends Activity {
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        final Button but_menu_test, but_menu_result;
        final ImageView iv_menu;

        but_menu_result = findViewById(R.id.but_menu_result);
        but_menu_test = findViewById(R.id.but_menu_test);
        iv_menu = findViewById(R.id.iv_menu);
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale_butt);
        final Animation animationRotateMenu = AnimationUtils.loadAnimation(
                this, R.anim.rotate_menu);

        View.OnClickListener allBut = new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.but_menu_test:
                        but_menu_test.startAnimation(animScale);
                        Intent test = new Intent(MenuActivity.this, TestActivity.class);
                        startActivity(test);
                        break;

                    case R.id.but_menu_result:
                        but_menu_result.startAnimation(animScale);
                        Intent result = new Intent(MenuActivity.this, ResultActivity.class);
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
        but_menu_result.setOnClickListener(allBut);
        iv_menu.setOnClickListener(allBut);
    }

    //Обработка системной кнопки Назад
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            CostomToast();
        }
        backPressedTime = System.currentTimeMillis();
    }

    public void CostomToast() {
        MotionToast.Companion.darkToast(MenuActivity.this, "Внимание,", "Нажмите еще раз для выхода из приложения",
                MotionToast.TOAST_ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.SHORT_DURATION,
                ResourcesCompat.getFont(this, R.font.ubuntu_light));
    }
}