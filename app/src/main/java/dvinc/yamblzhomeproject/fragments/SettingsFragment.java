package dvinc.yamblzhomeproject.fragments;
/*
 * Created by DV on Space 5 
 * 13.07.2017
 */

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.evernote.android.job.JobManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.net.background.BGSyncJob;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {

    @BindView(R.id.settingsUpdateTimeSpinner) Spinner frequencyTimeSpinner;
    @BindView(R.id.button_apply_new_settings) Button applyNewSettingsButton;
    @BindView(R.id.updateCheckbox) CheckBox updateCheckbox;
    private static String MINUTES = "15";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,
                container, false);
        ButterKnife.bind(this, view);
        setupTimeSpinner();

        SharedPreferences str = getActivity().getSharedPreferences("SETTINGS", MODE_PRIVATE);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.settings_time_array));
        int position = adapter.getPosition(""+str.getInt("UPDATE TIME", 0));

        frequencyTimeSpinner.setSelection(position);
        updateCheckbox.setChecked(str.getBoolean("AUTOUPDATE", false));

        return view;
    }

    /**
     * Method for set up spinner with time period chooser.
     */
    private void setupTimeSpinner(){
        String[] array = getResources().getStringArray(R.array.settings_time_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencyTimeSpinner.setAdapter(adapter);
    }

    @OnItemSelected(R.id.settingsUpdateTimeSpinner)
    void onClickItemSpinner(AdapterView<?> parent, View view, int position, long id){
        MINUTES = (String) parent.getItemAtPosition(position);
    }

    /**
     * Method for applying new settings.
     */
    @OnClick(R.id.button_apply_new_settings)
    void onClickApply(){
        SharedPreferences.Editor editor = getContext().getSharedPreferences("SETTINGS", MODE_PRIVATE).edit();
        editor.putInt("UPDATE TIME", Integer.parseInt(MINUTES));

        if (!updateCheckbox.isChecked()){
            //Cancel all background job with this tag
            JobManager.instance().cancelAllForTag(BGSyncJob.TAG);
            editor.putBoolean("AUTOUPDATE", !updateCheckbox.isChecked());
        } else {
            BGSyncJob.schedulePeriodic(Integer.parseInt(MINUTES));
            editor.putBoolean("AUTOUPDATE", updateCheckbox.isChecked());
        }
        editor.apply();

        Toast.makeText(getContext(), R.string.settings_new_settings_save, Toast.LENGTH_LONG).show();
    }
}