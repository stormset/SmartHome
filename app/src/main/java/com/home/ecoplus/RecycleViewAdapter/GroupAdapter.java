package com.home.ecoplus.RecycleViewAdapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.ecoplus.Devices.SmartRadiatorValve;
import com.home.ecoplus.R;
import com.temperatureselector.Utility;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class GroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ListItem> mList;
    private static GroupItemClickListener groupItemClickListener;

    public GroupAdapter(Context mContext, List<ListItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public CircleImageView circleImageView;
        public CircleImageView smallLabel;
        public TextView currentTemperatureText;
        public TextView currentTemperatureTextSup;
        public TextView targetTemperatureText;
        public TextView targetTemperatureTextSup;
        GroupViewHolder(View view) {
            super(view);
            circleImageView = view.findViewById(R.id.circleImageView);
            smallLabel = view.findViewById(R.id.smallLabel);
            currentTemperatureText = view.findViewById(R.id.current_temperature_text);
            currentTemperatureTextSup = view.findViewById(R.id.current_temperature_text_sup);
            targetTemperatureText = view.findViewById(R.id.target_temperature_text);
            targetTemperatureTextSup = view.findViewById(R.id.target_temperature_text_sup);
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
            if (groupItemClickListener != null) {
                groupItemClickListener.onGroupItemClicked(view, this.getAdapterPosition());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ListItem.GROUP_SMART_RADIATOR_VALVE: {
                View itemView = inflater.inflate(R.layout.group_item_smart_radiator_valve, parent, false);
                return new GroupViewHolder(itemView);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ListItem.GROUP_SMART_RADIATOR_VALVE: {
                /*SmartRadiatorValve smartRadiatorValve = (SmartRadiatorValve) ((Device) mList.get(position)).getDeviceItems();
                GroupViewHolder holder = (GroupViewHolder) viewHolder;
                setTemperatureText(holder.currentTemperatureText, holder.currentTemperatureTextSup, smartRadiatorValve.getCurrentTemperature());
                if (smartRadiatorValve.getTargetTemperature() != null) {
                    holder.smallLabel.setVisibility(VISIBLE);
                    setTemperatureText(holder.targetTemperatureText, holder.targetTemperatureTextSup, smartRadiatorValve.getTargetTemperature());
                }else {
                    holder.targetTemperatureTextSup.setText(" ");
                    holder.targetTemperatureText.setText(" ");
                    holder.smallLabel.setVisibility(INVISIBLE);
                }*/
                break;
            }
        }
    }

    public void setTemperatureText(TextView temperatureText, TextView temperatureTextSup, float temperature){
        String temp = String.valueOf(Utility.roundToHalf(temperature));
        int decimal = Integer.parseInt(temp.substring(temp.indexOf(".") + 1));
        temperatureText.setText(temp.substring(0, temp.indexOf(".")));
        if (decimal==5)temperatureTextSup.setText("5");
        else temperatureTextSup.setText("°");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
       return super.getItemId(position);
    }

    public void setOnGroupItemClickListener(GroupItemClickListener listener) {
        groupItemClickListener = listener;
    }

    public interface GroupItemClickListener {
         void onGroupItemClicked(View v, int adapterPosition);
    }

}
