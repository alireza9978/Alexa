package me.coleo.mylib.pages;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.coleo.mylib.AlexaLib.AlexaFactory;
import me.coleo.mylib.R;
import me.coleo.mylib.temp_models.Profile;

public class MainActivity extends AppCompatActivity {

    String TAG = "MAIN_ACTIVITY";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.temp_text_view);
        AlexaFactory alexa = new AlexaFactory();
        textView.setText(alexa.get(new Profile(12, 50, "COdeOFMe")).toString());

//        Moshi moshi = new Moshi.Builder().build();
//        JsonAdapter<History> jsonAdapter = moshi.adapter(History.class);
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(MoshiConverterFactory.create(moshi))
//                .baseUrl(Constants.BASE_URL)
//                .build();
//
//        GetHistory service = retrofit.create(GetHistory.class);
//        Call<UserRecords> tempList = service.listHistory("0", Constants.getToken(this));
//        tempList.enqueue(new Callback<UserRecords>() {
//            @Override
//            public void onResponse(Call<UserRecords> call, Response<UserRecords> response) {
//                textView.setText(response.body().toString());
//
//            }
//
//            @Override
//            public void onFailure(Call<UserRecords> call, Throwable t) {
//                textView.setText(t.toString());
//            }
//        });

    }
}
