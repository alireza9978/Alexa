package me.coleo.mylib.constans;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * کلاس مقادیر ثابت
 */
public class Constants {

    public static Context context;

    /**
     *  چند مقدار بی ارزش برای جابجایی داده در بین صفحه ها
     *  و ذخیره کردن مقادیر در حافظه گوشی
     */
    public static int ARRIVED_PAGE = 175;
    public static int MENU = 35;
    public static int EXIT = 342;
    public static int SAY_REPORT_CODE = 125;
    public static int NEW_WASTE_CODE = 105;
    public static int SIGN_PAGE_CODE = 105;
    public static int FINAL_CHECK_PAGE_CODE = 126;
    public static int CODE_RESULT_OK = 200;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 10810;
    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 10809;

    private static String TOKEN_STORAGE = "someWhereInDarkness";
    private static String TOKEN_DATA = "someWhereInDarkness12";
    public static final int MY_PERMISSIONS_REQUEST_CALL = 10900;
    public static String SUPPORT_PHONE_NUMBER = "+985136043479";
    public static String SIGN_STRING_DATA = "fuckCoustomer";

    /**
     *  چند مقدار بی ارزش برای جابجایی داده در بین صفحه ها
     *  و ذخیره کردن مقادیر در حافظه گوشی
     */
    public static String NO_TOKEN = "where is token";
    public static String REQUEST_DATA = "sendingData";
    public static String HELP_NULL = "asdasdvdqqwzz";
    public static String TRASH_DATA = "sendingsadaData";
    public static String NOT_MENTIONED= "ssadgsadavvmda";
    public static String MAIN_TRASH_DATA = "sendsdfgr";
    public static String MAIN_TRASH_NAME = "sendsdddfgr";
    public static String MAIN_TRASH_PHONE = "seasdvvnsddadfgr";
    public static String MAIN_TRASH_ADDRESS = "sendjhkmusdfgr";
    public static String MAIN_REQ_ID = "sedsacxddacxzgr";

    private static String NOTIF_STOREGE = "someWhereInLight";
    private static String NOTIF_DATA = "someWhereInDarLight12";
    public static final String NO_NOTIF = "noBag";

    /**
     * ادرس های انتقال داده به سرور
     */
    public static String BASE_URL = "http://abjo.coleo.ir/api/v1/";
    public static String DRIVER_GET_CONFIRM_CODE = BASE_URL + "auth/check_phone/user/";
    public static final String URL_SEND_CODE = BASE_URL + "auth/check_code/user/";
    public static  String URL_GET_HISTORY = BASE_URL + "user_records_store/";


    /**
     * ذخیره کلید ارطباط با سرور در حافظه
     */
    public static void setToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TOKEN_STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_DATA, token);
        editor.apply();
        editor.commit();
    }

    /**
     *گرفتن کلید ارتباط با سرور
     */
    public static String getToken(Context context) {
        if (context != null){
            SharedPreferences sharedPreferences = context.getSharedPreferences(TOKEN_STORAGE, Context.MODE_PRIVATE);
            return sharedPreferences.getString(TOKEN_DATA, Constants.NO_TOKEN);
        }else{
            SharedPreferences sharedPreferences = Constants.context.getSharedPreferences(TOKEN_STORAGE, Context.MODE_PRIVATE);
            return sharedPreferences.getString(TOKEN_DATA, Constants.NO_TOKEN);
        }
    }

    /**
     * تحلیل زمان بازگشتی از سرور
     */
    public static String parseTime(String time) {
        String year = time.substring(0, 4);
        String month = time.substring(4, 6);
        String day = time.substring(6, 8);
        String hour = time.substring(8, 10);
        String minute = time.substring(10, 12);
        String second = time.substring(12, 14);
        return hour + ":" + minute + ":" + second + " - " + year + "/" + month + "/" + day;
    }


}
