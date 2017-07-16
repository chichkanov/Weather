package dvinc.yamblzhomeproject;
/*
 * Created by DV on Space 5 
 * 14.07.2017
 */

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.evernote.android.job.JobManager;

import dvinc.yamblzhomeproject.net.RetrofitApi;
import dvinc.yamblzhomeproject.net.background.BGJobCreator;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private Retrofit retrofit;
    private RetrofitApi api;
    private static final String BASE_URL = "http://api.openweathermap.org/";

    public static App get(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    public RetrofitApi getApi() {
        return api;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(RetrofitApi.class);
        JobManager.create(this).addJobCreator(new BGJobCreator());
    }
}
