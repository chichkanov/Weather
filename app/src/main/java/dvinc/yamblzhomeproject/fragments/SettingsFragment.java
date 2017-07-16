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
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import dvinc.yamblzhomeproject.R;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {

    @BindView(R.id.settingsUpdateTimeSpinner) Spinner frequencyTimeSpinner;
    @BindView(R.id.button_apply_new_settings) Button applyNewSettingsButton;
    private static String MINUTES;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,
                container, false);
        ButterKnife.bind(this, view);
        setupTimeSpinner();
        return view;
    }

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

    @OnClick(R.id.button_apply_new_settings)
    void onClickApply(){
        SharedPreferences.Editor editor = getContext().getSharedPreferences("UPDATE TIME MINUTES", MODE_PRIVATE).edit();
        editor.putInt("UPDATE TIME", Integer.parseInt(MINUTES));
        editor.apply();
    }
}
