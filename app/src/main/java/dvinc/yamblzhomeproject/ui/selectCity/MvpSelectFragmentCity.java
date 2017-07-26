package dvinc.yamblzhomeproject.ui.selectCity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.repository.model.predictions.Prediction;

public class MvpSelectFragmentCity extends Fragment implements SelectCityView {

    @BindView(R.id.et_select_city)
    EditText etSelectCity;
    @BindView(R.id.rv_select_city)
    RecyclerView rvCityList;

    private SelectCityAdapter adapter;
    private Unbinder unbinder;

    private SelectCityPresenterImpl<SelectCityView> presenter;

    public static MvpSelectFragmentCity newInstance() {
        return new MvpSelectFragmentCity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_city_select, container, false);
        unbinder = ButterKnife.bind(this, v);
        presenter = new SelectCityPresenterImpl<>();
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
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.detachView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initRecyclerView(){
        adapter = new SelectCityAdapter(new ArrayList<>());
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
}
