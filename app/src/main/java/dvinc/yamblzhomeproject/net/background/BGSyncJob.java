package dvinc.yamblzhomeproject.net.background;
/*
 * Created by DV on Space 5 
 * 15.07.2017
 */

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import dvinc.yamblzhomeproject.model.WeatherResponse;
import dvinc.yamblzhomeproject.net.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static dvinc.yamblzhomeproject.fragments.WeatherFragment.SHARED_PREFERENCES_NAME;

public class BGSyncJob extends Job {

    public static final String TAG = "job_demo_tag";
    private static final String BASE_URL = "http://api.openweathermap.org/";
    private static final String API_KEY = "21cd7fe880c848c7d533498d2413f293";
    private static final String CITY = "Moscow";

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        // Background job start run here
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi api = retrofit.create(RetrofitApi.class);
        Call<WeatherResponse> weatherResponseCall = api.getTranslate(CITY, API_KEY);

        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                WeatherResponse weatherResponse = response.body();

                Gson jsonObject = new Gson();
                String callbackStringFromJSON = jsonObject.toJson(weatherResponse);

                SharedPreferences.Editor editor = getContext().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE).edit();
                editor.putString("JSON", callbackStringFromJSON);
                long currentTimeMillis = System.currentTimeMillis();
                editor.putLong("LAST UPDATE TIME", currentTimeMillis);
                editor.apply();
                Log.v("BACKGROUND", "BACKGROUND JOB EXECUTE, SHARED PREF WAS UPDATE");
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.v("BACKGROUND", "BACKGROUND JOB FAILURE");
            }
        });
        return Result.SUCCESS;
    }

    public static void schedulePeriodic() {
        Log.v("BACKGROUND", "schedulePeriodic is start");
        new JobRequest.Builder(BGSyncJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                .setPersisted(true)
                .build()
                .schedule();
    }
}
