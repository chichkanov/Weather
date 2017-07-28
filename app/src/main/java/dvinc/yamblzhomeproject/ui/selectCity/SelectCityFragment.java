package dvinc.yamblzhomeproject.ui.selectCity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.repository.model.predictions.Prediction;
import dvinc.yamblzhomeproject.ui.weather.WeatherFragment;

public class SelectCityFragment extends MvpAppCompatFragment implements SelectCityView {

    @BindView(R.id.et_select_city)
    EditText etSelectCity;
    @BindView(R.id.rv_select_city)
    RecyclerView rvCityList;

    private SelectCityAdapter adapter;
    private Unbinder unbinder;

    @InjectPresenter
    SelectCityPresenter presenter;

    public static SelectCityFragment newInstance() {
        return new SelectCityFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_city_select, container, false);
        unbinder = ButterKnife.bind(this, v);
        setCityNameObservable();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.select_city_head);
        initRecyclerView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initRecyclerView() {
        adapter = new SelectCityAdapter(new ArrayList<>(), position -> presenter.citySelected(adapter.getItem(position)));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());

        rvCityList.setLayoutManager(layoutManager);
        rvCityList.addItemDecoration(decoration);
        rvCityList.setAdapter(adapter);
    }

    @Override
    public void setCityNameObservable() {
        presenter.setObservable(RxTextView.textChanges(etSelectCity));
    }

    @Override
    public void showList(List<Prediction> predictions) {
        adapter.setDataset(predictions);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), R.string.load_weather_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToWeather() {
        hideKeyboard();
        String backStateName = WeatherFragment.class.getName();
        getActivity().getSupportFragmentManager().popBackStackImmediate(backStateName, 0);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSelectCity.getWindowToken(), 0);
    }
}
