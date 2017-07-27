package dvinc.yamblzhomeproject.ui.settings;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import com.arellomobile.mvp.MvpView;

interface SettingsView extends MvpView {

    void loadSettings(boolean autoUpdate, int minutes);

    void setNewSettings();
}
