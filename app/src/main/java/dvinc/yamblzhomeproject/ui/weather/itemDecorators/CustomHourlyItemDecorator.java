package dvinc.yamblzhomeproject.ui.weather.itemDecorators;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

public class CustomHourlyItemDecorator extends RecyclerView.ItemDecoration {

    private static final int ADDITIONAL_SPACING = 16;

    private Context context;

    public CustomHourlyItemDecorator(Context context) {
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int left = parent.getChildViewHolder(view).getLayoutPosition() != 0 ? dpToPx(ADDITIONAL_SPACING) : 0;
        int right = parent.getChildViewHolder(view).getLayoutPosition() != state.getItemCount() - 1 ? dpToPx(ADDITIONAL_SPACING) : 0;
        outRect.set(left, 0, right, 0);
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
