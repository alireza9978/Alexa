package me.coleo.mylib.temp_models;

import com.squareup.moshi.Json;

public class History {

    @Json(name = "id")
    int id;
    @Json(name = "type")
    String type;
    @Json(name = "coin")
    int coin;

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", coin=" + coin +
                "}\n";
    }
}
