package com.home.ecoplus.RecycleViewAdapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.home.ecoplus.R;
import com.home.ecoplus.Utility;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<UserInstanceItem> mList;
    private static ItemClickListener itemClickListener;

    public UserViewAdapter(Context mContext, List<UserInstanceItem> mList) {
        this.mList = mList;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public CircleImageView background;
        public TextView monogram, name;

        UserViewHolder(View view) {
            super(view);
            background = view.findViewById(R.id.bg);
            monogram = view.findViewById(R.id.text);
            name = view.findViewById(R.id.name);

            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ObjectAnimator.ofFloat(view, "scaleX", 1.08f).setDuration(40).start();
                            ObjectAnimator.ofFloat(view, "scaleY", 1.08f).setDuration(40).start();
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            ObjectAnimator.ofFloat(view, "scaleX", 1f).setDuration(30).start();
                            ObjectAnimator.ofFloat(view, "scaleY", 1f).setDuration(30).start();
                            break;
                    }
                    return false;
                }
            });
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(view, position);
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        UserInstanceItem userInstanceItem = mList.get(position);
        UserViewHolder holder = (UserViewHolder)viewHolder;
        holder.monogram.setText(Utility.getInitialChars(userInstanceItem.getUserName()));
        holder.name.setText(userInstanceItem.getCustomName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
       return super.getItemId(position);
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        itemClickListener = listener;
    }

    public interface ItemClickListener {
         void onItemClicked(View v, int position);
    }

}
