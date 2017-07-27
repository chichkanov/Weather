package dvinc.yamblzhomeproject.ui.settings;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.evernote.android.job.JobManager;

import dvinc.yamblzhomeproject.net.background.BGSyncJob;

import static android.content.Context.MODE_PRIVATE;

@InjectViewState
public class SettingsPresenter extends MvpPresenter<SettingsView> {

    private static final String UPDATE_TIME = "UPDATE TIME";
    private static final String AUTOUPDATE = "AUTOUPDATE";

    void loadSettingsFromPrefs(Context context) {
        SharedPreferences str = context.getSharedPreferences("SETTINGS", MODE_PRIVATE);
        boolean autoUpdate = str.getBoolean(AUTOUPDATE, false);
        int minutes = str.getInt(UPDATE_TIME, 0);
        getViewState().loadSettings(autoUpdate, minutes);
    }

    void updateSettingsInPrefs(Context context, boolean autoUpdate, int minutes) {
        SharedPreferences.Editor editor = context.getSharedPreferences("SETTINGS", MODE_PRIVATE).edit();
        editor.putInt(UPDATE_TIME, minutes);
        if (autoUpdate) {
            BGSyncJob.schedulePeriodic(minutes);
        } else {
            JobManager.instance().cancelAllForTag(BGSyncJob.TAG);
        }
        editor.putBoolean(AUTOUPDATE, autoUpdate);
        editor.apply();
    }
}
