package dvinc.yamblzhomeproject.ui.weather.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.data.uiModel.HourlyWeatherUi;
import dvinc.yamblzhomeproject.utils.WeatherUtils;

public class ForecastHourlyAdapter extends RecyclerView.Adapter<ForecastHourlyAdapter.Holder> {

    private List<HourlyWeatherUi> dataset;

    public ForecastHourlyAdapter(List<HourlyWeatherUi> dataset) {
        this.dataset = dataset;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_forecast_hourly, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        int temp = (int) dataset.get(position).getTemp();

        DateFormat formatTo = android.text.format.DateFormat.getTimeFormat(holder.itemView.getContext());

        Date date = new Date(dataset.get(position).getDate() * 1000L);

        holder.tvTemp.setText(holder.itemView.getResources().getString(R.string.weather_temp_forecast_cels, temp));
        holder.tvHour.setText(formatTo.format(date));
        holder.weatherIcon.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(),
                WeatherUtils.getForecastIcon(dataset.get(position).getIcon())));
    }

    public void setDataset(List<HourlyWeatherUi> dataset) {
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
        ImageView weatherIcon;

        public Holder(View itemView) {
            super(itemView);
            tvHour = itemView.findViewById(R.id.tv_item_forecast_hourly_hour);
            tvTemp = itemView.findViewById(R.id.tv_item_forecast_hourly_temp);
            weatherIcon = itemView.findViewById(R.id.weather_icon_item_forecast_hourly);
        }
    }
}
