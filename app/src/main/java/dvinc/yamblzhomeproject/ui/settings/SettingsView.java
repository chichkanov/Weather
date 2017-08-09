package dvinc.yamblzhomeproject.ui.settings;


import com.arellomobile.mvp.MvpView;

interface SettingsView extends MvpView {

    void loadSettings(boolean autoUpdate, int minutes);

    void setNewSettings();
}
