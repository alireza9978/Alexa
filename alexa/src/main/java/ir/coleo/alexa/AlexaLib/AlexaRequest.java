package ir.coleo.alexa.AlexaLib;

import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;

import ir.coleo.alexa.AlexaLib.exceptions.PackageException;
import ir.coleo.alexa.AlexaLib.validators.Validator;

public class AlexaRequest {

    private final String baseUrl;
    private String TAG = "ALEXA_REQUEST";

    public AlexaRequest(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void sendRequest(Mode mode, Object object) {
        Validator.isValid(mode, object);
        String url = getUrl(mode, object);
        switch (mode) {
            case Create:
            case Update:
            case Get:
                get(object, url);
                break;
        }
    }

    public String get(Object object, String url) {
        Package aPackage = object.getClass().getPackage();
        return aPackage.toString();
    }

    private String getUrl(Mode mode, Object object) {
        String url = baseUrl;
        ArrayList<String> packageParts = splitPackage(Objects.requireNonNull(object.getClass().getPackage()).getName());
        int startUrlIndex = -1;
        for (int i = 0; i < packageParts.size(); i++) {
            String s = packageParts.get(i);
            if (s.equals("models")) {
                startUrlIndex = i + 1;
                break;
            }
        }
        if (startUrlIndex == -1) {
            throw new PackageException("no models package found");
        }
        for (int i = startUrlIndex; i < packageParts.size(); i++) {
            url += ("/" + packageParts.get(i));
        }
        url += ("/" + object.getClass().getSimpleName().toLowerCase());
        Log.i(TAG, "getUrl: " + url);
        return url;
    }

    private ArrayList<String> splitPackage(String packageName) {
        ArrayList<String> packageParts = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < packageName.length(); i++) {
            if (packageName.charAt(i) == '.') {
                packageParts.add(packageName.substring(j, i).toLowerCase());
                j = i + 1;
            }
        }
        packageParts.add(packageName.substring(j));
        return packageParts;
    }

}
