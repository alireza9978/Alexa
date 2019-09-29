package me.coleo.mylib.server_class;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import me.coleo.mylib.constans.Constants;
import me.coleo.mylib.pages.login.LoginActivity;
import timber.log.Timber;

public class ServerClass {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm != null && cm.getActiveNetworkInfo() != null;
    }


    private static void saveToken(Context context, JSONObject response) {
        String token = null;
        try {
            token = response.getString("token");
        } catch (Exception e) {
            Log.i("SERVER_CONNECTION", "Oh_RIDIM_TOKEN");
        }
        Constants.setToken(context, token);
    }


    public static void getConfirmCode(final Context context, String phone) {

        String url = Constants.DRIVER_GET_CONFIRM_CODE;
        url += phone;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest

                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ((LoginActivity) context).goCodePage();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ((LoginActivity) context).enablePage();
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    public static void sendConfirmCode(final Context context, String phone, String code) {

        String url = Constants.URL_SEND_CODE;
        url += phone;
        url += "/";
        url += code;
        url += "/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        saveToken(context, response);
                        ((LoginActivity) context).goApp();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ((LoginActivity) context).enablePage();
                        Timber.log(1,"status code is = " + error.networkResponse.statusCode);
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }



}
