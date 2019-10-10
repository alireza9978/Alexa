package me.coleo.mylib.models;

import com.squareup.moshi.Json;

import java.util.List;

import me.coleo.mylib.models.my_pack.History;

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
