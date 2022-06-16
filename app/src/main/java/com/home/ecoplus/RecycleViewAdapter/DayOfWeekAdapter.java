package com.home.ecoplus.RecycleViewAdapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.home.ecoplus.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class DayOfWeekAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private final List<DayOfWeekItem> mList;
    private static ItemClickListener itemClickListener;

    public DayOfWeekAdapter(Context mContext, List<DayOfWeekItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public class DayOfWeekViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public CircleImageView background;
        public TextView text;

        DayOfWeekViewHolder(View view) {
            super(view);
            background = view.findViewById(R.id.bg);
            text = view.findViewById(R.id.text);

            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ObjectAnimator.ofFloat(view, "scaleX", 1.08f).setDuration(40).start();
                            ObjectAnimator.ofFloat(view, "scaleY", 1.08f).setDuration(40).start();
                            break;
                        case MotionEvent.ACTION_UP:
                            ObjectAnimator.ofFloat(view, "scaleX", 1f).setDuration(30).start();
                            ObjectAnimator.ofFloat(view, "scaleY", 1f).setDuration(30).start();
                            break;
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
            mList.get(position).setClicked(! mList.get(position).getClicked());
            notifyItemChanged(position);
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(view, mList);
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.day_of_week_item, parent, false);
        return new DayOfWeekViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        DayOfWeekItem dayOfWeekItem = mList.get(position);
        DayOfWeekViewHolder holder = (DayOfWeekViewHolder)viewHolder;
        if (dayOfWeekItem.getClicked()){
            holder.background.setImageResource(R.color.colorPrimary);
        }else {
            holder.background.setImageResource(R.color.gray_off);
        }
        holder.text.setText(getDayShortName(position));
    }

    public String getDayShortName(int position){
        DateFormatSymbols symbols = new DateFormatSymbols();
        String[] dayNames = symbols.getShortWeekdays();
        switch (Calendar.getInstance().getFirstDayOfWeek()){
            case Calendar.SUNDAY:{
                return dayNames[position+1].substring(0,1);
            }
            case Calendar.MONDAY:{
                if(getCurrentLocale(mContext).getLanguage().equals("hu")) {
                    String name;
                    if (position <= 5) {
                        name = dayNames[position + 2];
                    } else {
                        name = dayNames[1];
                    }
                    if (name.length()>=3){
                        return name.substring(0,2);
                    }else {
                        return name;
                    }
                }else {
                    if (position <= 5) {
                        return dayNames[position + 2];
                    } else {
                        return dayNames[1];
                    }
                }
            }
            default:
                return dayNames[position+1].substring(0,1);
        }
    }

    Locale getCurrentLocale(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return context.getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
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
         void onItemClicked(View v, List<DayOfWeekItem> list);
    }

}
