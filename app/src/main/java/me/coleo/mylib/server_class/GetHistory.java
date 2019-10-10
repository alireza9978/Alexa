package me.coleo.mylib.server_class;

import me.coleo.mylib.models.UserRecords;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public interface GetHistory {


    @Headers({"Content-Type: application/json", "role: user"})
    @GET("user_records_store/{page}/")
    Call<UserRecords> listHistory(@Path("page") String page, @Header("token") String token);

}
//

//headers.put("Content-Type", "application/json");
//        headers.put("token", Constants.getToken(context));
//        headers.put("role", "user");
//
//    public static void getHistory(final Context context, int page) {
//        String url = Constants.URL_GET_HISTORY;
//        url += page;
//        url += "/";
//        ((MainActivity) context).hideHistory();
//
//        ObjectRequest jsonObjectRequest = new ObjectRequest
//                (context, Request.Method.GET, url, null,
//                        response -> {
////                                Log.i(TAG, "get history: " + response.toString());
//                            saveToken(context, response);
//                            ArrayList<DateAction> actions = new ArrayList<>();
//                            try {
//                                JSONArray records = response.getJSONArray("user_records");
//                                for (int i = 0; i < records.length(); i++) {
//                                    DateAction temp = historyParser(records.getJSONObject(i));
//                                    if (temp != null)
//                                        actions.add(temp);
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            ((MainActivity) context).updateHistory(actions);
//                        }
//                        , error -> {
//                    ServerClass.handleError(context, error);
//                    ((MainActivity) context).showHistory();
//                }
//                );
//
//        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
//    }
//
//    private static DateAction historyParser(JSONObject object) {
//        DateAction date = DateParser(object);
//        try {
//            assert date != null;
//            date.setId(object.getInt("id"));
//            String kind = object.getString("type");
//            int coin = object.getInt("coin");
//            String imageUrl = object.getJSONObject("icon").getString("url");
//            switch (kind) {
//                case "spend": {
//                    String title = object.getString("text");
//                    return new Transition(date, title, coin, "");
//                }
//                case "introduce": {
//                    String title = object.getString("text");
//                    return new Introduce(date, coin, title, imageUrl);
//                }
//                case "activity": {
//                    int point = object.getInt("point");
//                    int distance = object.getInt("distance");
//                    return new History(date, coin, point, distance, imageUrl);
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }