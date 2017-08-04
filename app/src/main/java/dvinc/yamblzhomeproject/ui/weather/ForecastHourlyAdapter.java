package dvinc.yamblzhomeproject.ui.weather;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.pwittchen.weathericonview.WeatherIconView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.repository.model.weather.hourForecast.HourList;

class ForecastHourlyAdapter extends RecyclerView.Adapter<ForecastHourlyAdapter.Holder> {

    private List<HourList> dataset;

    private static DateFormat formatFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static DateFormat formatTo = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());


    ForecastHourlyAdapter(List<HourList> dataset) {
        this.dataset = dataset;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_forecast_hourly, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        int temp = (int) dataset.get(position).getMainHourly().getTemp() - 273;

        Date date = null;
        try {
            date = formatFrom.parse(dataset.get(position).getDtTxtHourly());
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("Adapter", "Parse Exception");
        }
        holder.tvTemp.setText(holder.itemView.getResources().getString(R.string.weather_temp_forecast_cels, temp));
        holder.tvHour.setText(formatTo.format(date));
    }

    void setDataset(List<HourList> dataset) {
        this.dataset = dataset;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView tvTemp;
        TextView tvHour;
        WeatherIconView weatherIcon;

        public Holder(View itemView) {
            super(itemView);
            tvHour = (TextView) itemView.findViewById(R.id.tv_item_forecast_hourly_hour);
            tvTemp = (TextView) itemView.findViewById(R.id.tv_item_forecast_hourly_temp);
            weatherIcon = (WeatherIconView) itemView.findViewById(R.id.weather_icon_item_forecast_hourly);
        }
    }
}
