package dvinc.yamblzhomeproject.ui.settings;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import dvinc.yamblzhomeproject.R;

public class SettingsFragment extends MvpAppCompatFragment implements SettingsView {
    @BindView(R.id.settingsUpdateTimeSpinner)
    Spinner frequencyTimeSpinner;
    @BindView(R.id.button_apply_new_settings)
    Button applyNewSettingsButton;
    @BindView(R.id.updateCheckbox)
    CheckBox updateCheckbox;
    private static String MINUTES = "15";

    @InjectPresenter
    SettingsPresenter settingsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,
                container, false);
        ButterKnife.bind(this, view);
        setupTimeSpinner();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        settingsPresenter.loadSettingsFromPrefs(getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.nav_head_settings);
    }

    /**
     * Method for set up spinner with time period chooser.
     */
    private void setupTimeSpinner() {
        String[] array = getResources().getStringArray(R.array.settings_time_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencyTimeSpinner.setAdapter(adapter);
    }

    @OnItemSelected(R.id.settingsUpdateTimeSpinner)
    void onClickItemSpinner(AdapterView<?> parent, View view, int position, long id) {
        MINUTES = (String) parent.getItemAtPosition(position);
    }

    @OnClick(R.id.button_apply_new_settings)
    void onClickApply() {
        setNewSettings();
    }

    @Override
    public void loadSettings(boolean autoUpdate, int minutes) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.settings_time_array));
        int position = adapter.getPosition(minutes + "");
        frequencyTimeSpinner.setSelection(position);
        updateCheckbox.setChecked(autoUpdate);
    }

    @Override
    public void setNewSettings() {
        settingsPresenter.updateSettingsInPrefs(getContext(), updateCheckbox.isChecked(), Integer.parseInt(MINUTES));
        Toast.makeText(getContext(), R.string.settings_new_settings_save, Toast.LENGTH_LONG).show();
    }
}
