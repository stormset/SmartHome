package com.home.ecoplus;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.home.ecoplus.RecycleViewAdapter.DayOfWeekAdapter;
import com.home.ecoplus.RecycleViewAdapter.DayOfWeekItem;

import java.util.List;

public class DayOfWeekSelector extends RelativeLayout {
    Context context;
    View rootView;
    RecyclerView recyclerView;
    List<DayOfWeekItem> list;
    DaySelectionChangeListener listener;

    public DayOfWeekSelector(Context context, List<DayOfWeekItem> list) {
        super(context);
        this.context = context;
        this.list = list;
        init();
    }

    public DayOfWeekSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        rootView = inflate(context, R.layout.day_of_week_selector, this);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        if (list != null) {
            DayOfWeekAdapter adapter = new DayOfWeekAdapter(context, list);
            adapter.setOnItemClickListener(new DayOfWeekAdapter.ItemClickListener() {
                @Override
                public void onItemClicked(View v, List<DayOfWeekItem> list) {
                    if (listener != null){
                        listener.onDaySelectionChanged(v, list);
                    }
                }
            });
            GridLayoutManager mLayoutManager = new GridLayoutManager(context, 7);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(7,true));
            recyclerView.setAdapter(adapter);
        }
    }

    public void setList(List<DayOfWeekItem> list) {
        this.list = list;
        init();
    }

  public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

      private int spanCount;
      private int spacing;
      private int topSpacing;
      private boolean includeEdge;
      private int itemWidth;

      public GridSpacingItemDecoration(int spanCount, boolean includeEdge) {
          this.spanCount = spanCount;
          this.includeEdge = includeEdge;
          itemWidth = (int)Utility.convertDpToPixel(40, context);
      }

      @Override
      public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
          int position = parent.getChildAdapterPosition(view); // item position
          int column = position % spanCount; // item column
          spacing  =  (int) ((parent.getWidth()-7*itemWidth)/7.8f);
          topSpacing = (parent.getHeight()-itemWidth)/2;
          if (includeEdge) {
              outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
              outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

              if (position < spanCount) { // top edge
                  outRect.top =topSpacing;
              }
              outRect.bottom = topSpacing; // item bottom
          } else {
              outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
              outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
              if (position >= spanCount) {
                  outRect.top = spacing; // item top
              }
          }
      }
  }
    public void setOnDaySelectionChangedListener(DaySelectionChangeListener listener) {
        this.listener = listener;
    }

    public interface DaySelectionChangeListener {
        void onDaySelectionChanged(View v, List<DayOfWeekItem> list);
    }
}