package dvinc.yamblzhomeproject.ui.selectCity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.repository.model.predictions.Prediction;

class SelectCityAdapter extends RecyclerView.Adapter<SelectCityAdapter.Holder>{

    private List<Prediction> dataset;

    SelectCityAdapter(List<Prediction> dataset){
        this.dataset = dataset;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_prediction, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tvMain.setText(dataset.get(position).getStructuredFormatting().getMainText());
        holder.tvDesc.setText(dataset.get(position).getStructuredFormatting().getSecondaryText());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    void setDataset(List<Prediction> dataset){
        this.dataset = dataset;
        notifyDataSetChanged();
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView tvMain;
        TextView tvDesc;

        Holder(View itemView) {
            super(itemView);
            tvMain = (TextView) itemView.findViewById(R.id.tv_item_select_city_main);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_item_select_city_desc);
        }

    }
}
