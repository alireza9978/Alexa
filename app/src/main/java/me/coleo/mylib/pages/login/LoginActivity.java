package me.coleo.mylib.pages.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import me.coleo.mylib.R;
import me.coleo.mylib.constans.Constants;
import me.coleo.mylib.pages.MainActivity;
import me.coleo.mylib.server_class.ServerClass;

public class LoginActivity extends AppCompatActivity {

    private Button disabler;
    private ProgressBar pr;
    private TextView back;
    private EditText phone;
    private Button login;
    private Button resend;
    private TextView timer;
    private String lastNumber;
    private int state = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        lastNumber = "0";

        final LoginActivity context = this;
        ImageView sup = findViewById(R.id.supportBtn);
        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Constants.SUPPORT_PHONE_NUMBER));
                startActivity(intent);
            }
        });

        login = findViewById(R.id.signInBtn);
        phone = findViewById(R.id.phoneET);
        timer = findViewById(R.id.timerTextViewId);
        resend = findViewById(R.id.resendButtonId);
        disabler = findViewById(R.id.login_disabler);
        pr = findViewById(R.id.login_pr);
        back = findViewById(R.id.backCode);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = 0;
                makeLogin();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disablePage();
                switch (state) {
                    case 0: {
                        String temp = phone.getText().toString();
                        if (temp.startsWith("09") && temp.length() == 11) {
                            ServerClass.getConfirmCode(context, phone.getText().toString());
                            Toast.makeText(context, "اندکی صبر کنید ...", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "شماره وارد شده را برسی کنید", Toast.LENGTH_SHORT).show();
                            enablePage();
                        }
                        return;
                    }
                    case 1: {
                        String temp = phone.getText().toString();
                        if (temp.length() != 4) {
                            Toast.makeText(context, "کد را بررسی کنید", Toast.LENGTH_SHORT).show();
                            enablePage();
                        } else {
                            if (lastNumber != null) {
                                ServerClass.sendConfirmCode(context, lastNumber, temp);
                            } else {
                                Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return;
                    }
                    default: {
                        Log.i("LOGIN", "onClick: ridi");
                    }
                }
            }
        });


    }

    public void enablePage() {
        disabler.setVisibility(View.INVISIBLE);
        pr.setVisibility(View.INVISIBLE);
    }

    public void disablePage() {
        disabler.setVisibility(View.VISIBLE);
        pr.setVisibility(View.VISIBLE);
    }

    /**
     * تغییر وضعیت صفحه و نمایش قسمت گرفتن شماره
     */
    private void makeLogin() {
        state = 0;
        phone.setVisibility(View.VISIBLE);
        phone.setEnabled(true);
        phone.setHint("شماره خود را وارد کنید");
        login.setText("ورود");
        back.setVisibility(View.INVISIBLE);
        resend.setVisibility(View.INVISIBLE);
        resend.setEnabled(false);
        timer.setVisibility(View.INVISIBLE);
    }

    /**
     * تغییر وضعیت صفحه و نمایش قسمت گرفتن کد
     */
    public void goCodePage() {
        lastNumber = phone.getText().toString();
        makeGettingCode();
    }

    /**
     * تغییر وضعیت صفحه و نمایش قسمت گرفتن کد
     */
    private void makeGettingCode() {
        state = 1;
        phone.setText("");
        login.setText("تایید کد");
        phone.setHint("کد را وارد کنید");
        resend.setVisibility(View.VISIBLE);
        resend.setEnabled(true);
        timer.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        enablePage();
    }


    /**
     * تغییر صفحه
     */
    public void goApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
