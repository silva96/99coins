package com.silva.benjamin.ninetyninecoins.util;



import android.content.Context;
import android.widget.Toast;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.silva.benjamin.ninetyninecoins.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by benjamin on 1/6/16.
 */
public class Helper {

    private static String BASE_URL = "https://nncoins-api.herokuapp.com/";
    public static String COVER_URL = "http://99coins.cl/covers/";
    public static boolean FETCHED = false;
    public static boolean FIRST_FRAGMENT_ADDED = false;


    public static APIService service(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();
        //to use realm with retrofit.
        Gson gson = new GsonBuilder().setExclusionStrategies(new  ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        APIService service = retrofit.create(APIService.class);
        return service;
    }

    public static String formatPrice(String lowerPrice) {

        NumberFormat nf = DecimalFormat.getInstance();
        DecimalFormatSymbols customSymbol = new DecimalFormatSymbols();
        customSymbol.setDecimalSeparator(',');
        customSymbol.setGroupingSeparator('.');
        ((DecimalFormat)nf).setDecimalFormatSymbols(customSymbol);
        nf.setGroupingUsed(true);
        return nf.format(Integer.parseInt(lowerPrice));
    }

    public static void showAToast (Toast mToast, Context context, String message, int gravity, int xoffset, int yoffset){
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        int x_offset = Math.round(xoffset * context.getResources().getDisplayMetrics().density);
        int y_offset = Math.round(yoffset * context.getResources().getDisplayMetrics().density);
        mToast.setGravity(gravity, x_offset, y_offset);
        mToast.show();
    }
}
