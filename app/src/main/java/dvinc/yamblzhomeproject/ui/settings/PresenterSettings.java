package dvinc.yamblzhomeproject.ui.settings;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import android.content.Context;

public interface PresenterSettings<T extends ViewSettings> {

    void attachView(T weatherView);

    void detachView();

    void loadSettingsFromPrefs(Context context);

    void updateSettingsInPrefs(Context context, boolean autoUpdate, int minutes);
}
