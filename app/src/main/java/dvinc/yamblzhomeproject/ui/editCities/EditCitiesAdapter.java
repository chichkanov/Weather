package dvinc.yamblzhomeproject.ui.editCities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.db.entities.CityEntity;

class EditCitiesAdapter extends RecyclerView.Adapter<EditCitiesAdapter.Holder> {

    private List<CityEntity> dataset;
    private OnItemRemoveClick clickListener;

    EditCitiesAdapter(List<CityEntity> dataset, OnItemRemoveClick onItemRemoveClick) {
        this.dataset = dataset;
        this.clickListener = onItemRemoveClick;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_cities, parent, false);
        return new Holder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tvCityName.setText(dataset.get(position).getCityTitle());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    CityEntity getItem(int position) {
        return dataset.get(position);
    }

    void removeItem(CityEntity cityEntity) {
        int position = dataset.indexOf(cityEntity);
        dataset.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataset.size());
    }

    void setDataset(List<CityEntity> items) {
        this.dataset = items;
        notifyDataSetChanged();
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView tvCityName;
        ImageButton ibClear;

        public Holder(View itemView, OnItemRemoveClick clickListener) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tv_item_edit_cities_name);
            ibClear = itemView.findViewById(R.id.ib_edit_cities_delete);
            setListener(clickListener);
        }

        private void setListener(OnItemRemoveClick listener) {
            ibClear.setOnClickListener(l -> {
                if (listener != null) {
                    listener.onItemRemoveClick(getAdapterPosition());
                    ibClear.setClickable(false);
                }
            });
        }
    }
}
