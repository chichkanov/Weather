package dvinc.yamblzhomeproject.ui.editCities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.db.entities.CityEntity;

public class EditCitiesFragment extends MvpAppCompatFragment implements EditCitiesView {

    @BindView(R.id.rv_edit_cities)
    RecyclerView rvEditCities;

    EditCitiesAdapter editCitiesAdapter;

    @InjectPresenter
    public EditCitiesPresenter presenter;

    private Unbinder unbinder;

    public static EditCitiesFragment newInstance() {
        return new EditCitiesFragment();
    }

    @ProvidePresenter
    EditCitiesPresenter provideEditCitiesPresenter() {
        return App.getComponent().getEditCitiesPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_cities,
                container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.edit_cities_head);
        initRecyclerView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initRecyclerView() {
        editCitiesAdapter = new EditCitiesAdapter(new ArrayList<>(), position -> presenter.onRemoveClick(editCitiesAdapter.getItem(position)));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());

        rvEditCities.setLayoutManager(layoutManager);
        rvEditCities.addItemDecoration(decoration);
        rvEditCities.setAdapter(editCitiesAdapter);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), R.string.load_weather_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCitiesList(List<CityEntity> cityEntities) {
        editCitiesAdapter.setDataset(cityEntities);
    }

    @Override
    public void onItemRemoved(CityEntity cityEntity) {
        editCitiesAdapter.removeItem(cityEntity);
    }
}
