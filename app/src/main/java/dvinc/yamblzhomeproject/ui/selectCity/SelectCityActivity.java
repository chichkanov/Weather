package dvinc.yamblzhomeproject.ui.selectCity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.data.model.predictions.Prediction;

public class SelectCityActivity extends MvpAppCompatActivity implements SelectCityView {

    @BindView(R.id.et_select_city)
    EditText etSelectCity;
    @BindView(R.id.rv_select_city)
    RecyclerView rvCityList;

    private SelectCityAdapter adapter;

    @InjectPresenter
    SelectCityPresenter presenter;


    @ProvidePresenter
    SelectCityPresenter providePresenter(){
        return App.getComponent().getSelectCityPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);
        ButterKnife.bind(this);

        setCityNameObservable();
        initRecyclerView();

    }

    private void initRecyclerView() {
        adapter = new SelectCityAdapter(new ArrayList<>(), position -> presenter.citySelected(adapter.getItem(position)));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this, layoutManager.getOrientation());

        rvCityList.setLayoutManager(layoutManager);
        rvCityList.addItemDecoration(decoration);
        rvCityList.setAdapter(adapter);
    }

    @OnClick(R.id.ib_select_city_clear)
    void onClearClick() {
        presenter.clearButtonCLicked(etSelectCity.getText().toString());
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
        Toast.makeText(this, R.string.load_weather_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToWeather() {
        hideKeyboard();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void clearText() {
        etSelectCity.getText().clear();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSelectCity.getWindowToken(), 0);
    }
}
