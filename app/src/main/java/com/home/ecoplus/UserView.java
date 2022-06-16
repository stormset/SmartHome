package com.home.ecoplus;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.home.ecoplus.RecycleViewAdapter.UserInstanceItem;
import com.home.ecoplus.RecycleViewAdapter.UserViewAdapter;

import java.util.List;

public class UserView extends RelativeLayout {
    Context context;
    View rootView;
    RecyclerView recyclerView;
    List<UserInstanceItem> list;
    UserItemClickListener listener;

    public UserView(Context context, List<UserInstanceItem> list) {
        super(context);
        this.context = context;
        this.list = list;
        init();
    }

    public UserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        rootView = inflate(context, R.layout.day_of_week_selector, this);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        if (list != null) {
            UserViewAdapter adapter = new UserViewAdapter(context, list);
            adapter.setOnItemClickListener(new UserViewAdapter.ItemClickListener() {
                @Override
                public void onItemClicked(View v, int position) {
                    if (listener != null){
                        listener.onUserItemClicked(v, position);
                    }
                }
            });
            GridLayoutManager mLayoutManager = new GridLayoutManager(context, 3);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    mLayoutManager.getOrientation());
            recyclerView.addItemDecoration(new VerticalSpaceItemDecoration((int) Utility.convertDpToPixel(12, getContext())));
            recyclerView.setAdapter(adapter);
        }
    }

    public void setList(List<UserInstanceItem> list) {
        this.list = list;
        init();
    }

    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
            outRect.top = verticalSpaceHeight;
        }
    }
    public void setOnUserItemClickListener(UserItemClickListener listener) {
        this.listener = listener;
    }

    public interface UserItemClickListener {
        void onUserItemClicked(View v, int position);
    }
}