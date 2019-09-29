package me.coleo.mylib.temp_models;

import com.squareup.moshi.Json;

import java.util.List;

public class UserRecords {

    @Json(name = "user_records") List<History> histories;


    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (History history : histories){
            temp.append(history.toString());
        }
        return temp.toString();
    }
}
