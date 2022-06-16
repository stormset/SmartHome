package com.home.ecoplus;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.home.ecoplus.RecycleViewAdapter.DeviceAdapter;
import com.home.ecoplus.RecycleViewAdapter.ListItem;

import java.util.ArrayList;
import java.util.List;

public class NormalModeItemTouchHelperCallback extends ItemTouchHelper.Callback
{
    private Context mContext;
    private RecyclerView recyclerView;
    private DeviceAdapter adapter;
    private List<ListItem> itemList = new ArrayList<>();
    private RecyclerView.ViewHolder selected = null;

    public NormalModeItemTouchHelperCallback(Context context){
        if (context instanceof MainActivity){
            mContext = context;
           // recyclerView = ((MainActivity) context).recyclerView;
           // adapter = ((MainActivity) context).deviceAdapter;
           // itemList = ((MainActivity) context).deviceList;
/*            recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(View view) {
                    //refreshEmptyHeaders();
                }

                @Override
                public void onChildViewDetachedFromWindow(View view) {
                }
            });*/
        }
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return 0;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }



    public void refreshEmptyHeaders(){
        for (int i = 0; i < recyclerView.getChildCount(); i++){
            View child = recyclerView.getChildAt(i);
            int childAdapterPosition = recyclerView.getChildAdapterPosition(child);
            if (itemList.get(childAdapterPosition).getType() == ListItem.TYPE_ROOM) {
                if (getChildCount(childAdapterPosition) == 0) {
                    DeviceAdapter.HeaderViewHolder holder = (DeviceAdapter.HeaderViewHolder) recyclerView.getChildViewHolder(child);
                    holder.noDevices.setVisibility(View.VISIBLE);
                }else {
                    DeviceAdapter.HeaderViewHolder holder = (DeviceAdapter.HeaderViewHolder) recyclerView.getChildViewHolder(child);
                    holder.noDevices.setVisibility(View.GONE);
                }
            }
        }
    }


    public int getChildCount(int position){
        int i = 1;
        if ((itemList.size()-1 == position))return 0;
        else {
            while (((itemList.get(position + i)).getType() != ListItem.TYPE_ROOM)
                    && ((position + i) < itemList.size()-1)) {
                i++;
            }
            if ((itemList.size() - 1 == position + i) && itemList.get(position+i).getType() != ListItem.TYPE_ROOM)return i;
            return i - 1;
        }
    }

}