package me.coleo.mylib.pages;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.coleo.mylib.AlexaLib.AlexaFactory;
import me.coleo.mylib.AlexaLib.Mode;
import me.coleo.mylib.R;
import me.coleo.mylib.temp_models.Address;
import me.coleo.mylib.temp_models.Profile;
import me.coleo.mylib.temp_models.User;

public class MainActivity extends AppCompatActivity {

    String TAG = "MAIN_ACTIVITY";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.temp_text_view);
        AlexaFactory alexa = new AlexaFactory();
//        textView.setText(alexa.get(new Profile(12, 50, "COdeOFMe")).toString());
        Profile profile = new Profile(12, "COdeOFMe",120,"InviteMe");
        User a = new User(12, "Alireza", "Rahimi", new Address(123.412, 123.321), profile);
        User b = new User(12, "Alireza", "Rahimi", new Address(123.412, 123.321), profile);
        User c = new User(12, "Alireza", "Rahimi", new Address(123.412, 123.321), profile);
        a.addFriend(b);
        b.addFriend(a);
        a.addFriend(c);
        textView.setText(alexa.invoke(a, Mode.Create).toString());

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
