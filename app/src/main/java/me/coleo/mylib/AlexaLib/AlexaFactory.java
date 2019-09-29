package me.coleo.mylib.AlexaLib;


import com.squareup.moshi.Json;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

import me.coleo.mylib.AlexaLib.exceptions.InvalidInput;
import timber.log.Timber;

public class AlexaFactory {

    public JSONObject get(Object input) {
        if (input instanceof Class<?>) {
            throw new InvalidInput("invalid input object expected");
        }
        if (input == null) {
            throw new InvalidInput("null input");
        }

        JSONObject output = new JSONObject();
        for (Field field : input.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(input);
                if (value == null){
                    Timber.e("null value");
                }
                output.put(field.getName(),value);
            } catch (IllegalAccessException | JSONException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

}
