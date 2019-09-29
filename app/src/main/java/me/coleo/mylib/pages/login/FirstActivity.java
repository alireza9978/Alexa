package me.coleo.mylib.pages.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import me.coleo.mylib.R;
import me.coleo.mylib.constans.Constants;
import me.coleo.mylib.pages.MainActivity;
import me.coleo.mylib.server_class.ServerClass;
import timber.log.Timber;

public class FirstActivity extends AppCompatActivity {

    private final Context context = this;
    private Button retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retry = findViewById(R.id.retryBtn);

        retry.setVisibility(View.INVISIBLE);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ServerClass.isNetworkConnected(context)) {
                    if (Constants.getToken(context).equals(Constants.NO_TOKEN) || Constants.getToken(context).equals("")) {
                        goLogin();
                    } else {
                        goApp();
                    }
                } else {
                    Toast.makeText(context, "اینترنت خود را برسی کنید", Toast.LENGTH_SHORT).show();
                    enablePage();
                }
            }
        });

        Timber.plant(new Timber.DebugTree());
    }

    /**
     * بررسی کلید و باز کردن صفحه ی ورود کاربر
     */
    @Override
    protected void onResume() {
        super.onResume();
        final Context context = this;

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (ServerClass.isNetworkConnected(context)) {
                    if (Constants.getToken(context).equals(Constants.NO_TOKEN) || Constants.getToken(context).equals("")) {
                        goLogin();
                    } else {
                        goApp();
                    }
                } else {
                    Toast.makeText(context, "اینترنت خود را برسی کنید", Toast.LENGTH_SHORT).show();
                    enablePage();
                }
            }
        };
        handler.postDelayed(runnable, 1000);

    }

    /**
     * تغییر صفحه برنامه
     */
    public void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * تغییر صفحه برنامه
     */
    public void goApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * نمایش دکمه تلاش مجدد
     */
    public void enablePage() {
        retry.setVisibility(View.VISIBLE);
        retry.setEnabled(true);
    }


}
