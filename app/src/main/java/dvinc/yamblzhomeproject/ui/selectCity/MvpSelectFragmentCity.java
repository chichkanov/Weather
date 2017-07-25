package dvinc.yamblzhomeproject.ui.selectCity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dvinc.yamblzhomeproject.R;

public class MvpSelectFragmentCity extends Fragment implements SelectCityView {

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
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.select_city_head);
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
}
