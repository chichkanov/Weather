package dvinc.yamblzhomeproject.ui.settings;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.evernote.android.job.JobManager;

import dvinc.yamblzhomeproject.net.background.BGSyncJob;

import static android.content.Context.MODE_PRIVATE;

public class PresenterSettingsImpl<T extends ViewSettings> implements PresenterSettings<T> {

    private static final String UPDATE_TIME = "UPDATE TIME";
    private static final String AUTOUPDATE = "AUTOUPDATE";

    private T view;

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void loadSettingsFromPrefs(Context context) {
        SharedPreferences str = context.getSharedPreferences("SETTINGS", MODE_PRIVATE);
        boolean autoUpdate = str.getBoolean(AUTOUPDATE, false);
        int minutes = str.getInt(UPDATE_TIME, 0);
        view.loadSettings(autoUpdate, minutes);
    }

    @Override
    public void updateSettingsInPrefs(Context context, boolean autoUpdate, int minutes) {
        SharedPreferences.Editor editor = context.getSharedPreferences("SETTINGS", MODE_PRIVATE).edit();
        editor.putInt(UPDATE_TIME, minutes);
        if (autoUpdate){
            BGSyncJob.schedulePeriodic(minutes);
            editor.putBoolean(AUTOUPDATE, autoUpdate);
        } else {
            JobManager.instance().cancelAllForTag(BGSyncJob.TAG);
            editor.putBoolean(AUTOUPDATE, autoUpdate);
        }
        editor.apply();
    }
}
