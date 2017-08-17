package dvinc.yamblzhomeproject.ui.weather.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.data.uiModel.DailyWeatherUi;
import dvinc.yamblzhomeproject.utils.WeatherUtils;

public class ForecastDailyAdapter extends RecyclerView.Adapter<ForecastDailyAdapter.Holder> {

    private List<DailyWeatherUi> dataset;

    private static DateFormat dateFormat = new SimpleDateFormat("E, d MMMM", Locale.getDefault());
    private static Calendar calendar = Calendar.getInstance(Locale.getDefault());

    public ForecastDailyAdapter(List<DailyWeatherUi> dataset) {
        this.dataset = dataset;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_forecast_daily, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        int tempMax = (int) dataset.get(position).getTempMax();
        int tempMin = (int) dataset.get(position).getTempMin();

        holder.tvTempMax.setText(holder.itemView.getResources().getString(R.string.weather_temp_forecast_cels, tempMax));
        holder.tvTempMin.setText(holder.itemView.getResources().getString(R.string.weather_temp_forecast_cels, tempMin));

        holder.weatherIcon.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(),
                WeatherUtils.getForecastIcon(dataset.get(position).getIcon())));

        setDate(holder, position);
    }

    private void setDate(Holder holder, int position) {
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, position);

        switch (position) {
            case 0: {
                holder.tvDate.setText(holder.itemView.getResources().getString(R.string.weather_forecast_date_today));
                break;
            }
            case 1: {
                holder.tvDate.setText(holder.itemView.getResources().getString(R.string.weather_forecast_date_tommorow));
                break;
            }
            default: {
                holder.tvDate.setText(dateFormat.format(calendar.getTime()));
                break;
            }
        }
    }

    public void setDataset(List<DailyWeatherUi> dataset) {
        this.dataset = dataset;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView tvTempMax;
        TextView tvTempMin;
        TextView tvDate;
        ImageView weatherIcon;

        public Holder(View itemView) {
            super(itemView);
            tvTempMax = itemView.findViewById(R.id.tv_item_forecast_daily_temp_max);
            tvTempMin = itemView.findViewById(R.id.tv_item_forecast_daily_temp_min);
            tvDate = itemView.findViewById(R.id.tv_item_forecast_daily_date);
            weatherIcon = itemView.findViewById(R.id.weather_icon_item_forecast_daily);
        }
    }
}

