package com.home.ecoplus.RecycleViewAdapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.home.ecoplus.Devices.DeviceItem;
import com.home.ecoplus.Devices.DeviceTypes;
import com.home.ecoplus.Devices.Light;
import com.home.ecoplus.Devices.Thermostat;
import com.home.ecoplus.MainActivity;
import com.home.ecoplus.R;
import com.home.ecoplus.Utility;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import eightbitlab.com.blurview.ClipView;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import static android.view.View.VISIBLE;
import static android.view.ViewConfiguration.getLongPressTimeout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class DeviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ListItem> deviceList;
    private SceneAdapter sceneAdapter;
    private static DeviceItemClickListener deviceItemClickListener;
    private static DeviceItemLongClickListener deviceItemLongClickListener;
    private RecyclerView sceneRecycleView;
   // private FrameLayout frameLayout;

    public DeviceAdapter(Context mContext, List<ListItem> deviceList, SceneAdapter sceneAdapter) {
        this.mContext = mContext;
        this.deviceList = deviceList;
        this.sceneAdapter = sceneAdapter;
       // frameLayout = ((MainActivity)mContext).findViewById(R.id.background_image);
    }

    public static class SummaryViewHolder extends RecyclerView.ViewHolder {
        TextView homeName;

        SummaryViewHolder(View view) {
            super(view);
            homeName = (TextView) view.findViewById(R.id.home_name);
        }
    }

    public static class SceneHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView sceneHeaderName;

        SceneHeaderViewHolder(View view) {
            super(view);
            sceneHeaderName = (TextView) view.findViewById(R.id.scene_header);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView header;
        public TextView noDevices;

        HeaderViewHolder(View view) {
            super(view);
            header = (TextView) view.findViewById(R.id.txt_header);
            noDevices = (TextView) view.findViewById(R.id.no_devices);
        }

    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public CircleImageView icon;
        public ImageView warningIcon;
        public LinearLayout iconTextHolder;
        public TextView iconText;
        public TextView iconTextSup;
        public TextView roomName;
        public TextView deviceName;
        public TextView additionalInformation;
        public ClipView clipView;

        public DeviceViewHolder (View view) {
            super(view);
            icon = (CircleImageView) view.findViewById(R.id.icon);
            warningIcon = (ImageView) view.findViewById(R.id.warning_icon);
            iconTextHolder = (LinearLayout) view.findViewById(R.id.icon_text_holder);
            iconText = (TextView) view.findViewById(R.id.icon_text);
            iconTextSup = (TextView) view.findViewById(R.id.icon_text_sup);
            roomName = (TextView) view.findViewById(R.id.room_name);
            deviceName = (TextView) view.findViewById(R.id.device_name);
            additionalInformation = (TextView) view.findViewById(R.id.additional_information);
            clipView = (ClipView) view.findViewById(R.id.blurLayout);

            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    float scaleUp = 1.08f;
                    int scaleUpDuration = 40;
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ObjectAnimator.ofFloat(view, "scaleX", scaleUp).setDuration(scaleUpDuration).start();
                            ObjectAnimator.ofFloat(view, "scaleY", scaleUp).setDuration(scaleUpDuration).start();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (view.getScaleX()>1){
                                        Log.e("t",""+getLongPressTimeout());
                                    }
                                }
                            },250);

                            break;
                        case MotionEvent.ACTION_UP:
                            int duration = 0;
                            float currentScale = view.getScaleX();
                            if (currentScale < scaleUp){
                                duration  = (int) ((scaleUp/scaleUpDuration)*(scaleUp-currentScale));
                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ObjectAnimator.ofFloat(view, "scaleX", 0.98f).setDuration(30).start();
                                    ObjectAnimator.ofFloat(view, "scaleY", 0.98f).setDuration(30).start();
                                }
                            }, duration);
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            int duration2 = 0;
                            float currentScale2 = view.getScaleX();
                            if (currentScale2 < scaleUp){
                                duration2  = (int) ((scaleUp/scaleUpDuration)*(scaleUp-currentScale2));
                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ObjectAnimator.ofFloat(view, "scaleX", 0.98f).setDuration(30).start();
                                    ObjectAnimator.ofFloat(view, "scaleY", 0.98f).setDuration(30).start();
                                }
                            }, duration2);
                            break;
                    }
                    return false;
                }
            });
            view.setOnLongClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (deviceItemClickListener != null) {
                deviceItemClickListener.onDeviceItemClicked(view, this.getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (deviceItemLongClickListener != null) {
                deviceItemLongClickListener.onDeviceItemLongClicked(view, this.getAdapterPosition());
            }
            return true;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return deviceList.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ListItem.TYPE_SUMMARY: {
                View itemView = inflater.inflate(R.layout.summary_card, parent, false);
                return new SummaryViewHolder(itemView);
            }
            case ListItem.TYPE_SCENE_HEADER: {
                View itemView = inflater.inflate(R.layout.scene_header_card, parent, false);
                return new SceneHeaderViewHolder(itemView);
            }
            case ListItem.TYPE_SCENE_GROUP: {
                View itemView = inflater.inflate(R.layout.scene_recycle_view, parent, false);
                sceneRecycleView = itemView.findViewById(R.id.scene_recycler_view);
                GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.HORIZONTAL, false);
                sceneRecycleView.setLayoutManager(mLayoutManager);
                OverScrollDecoratorHelper.setUpOverScroll(sceneRecycleView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
                sceneRecycleView.setItemAnimator(new DefaultItemAnimator());
                sceneRecycleView.setAdapter(sceneAdapter);
                return new SceneHeaderViewHolder(itemView);
            }
            case ListItem.TYPE_ROOM: {
                View itemView = inflater.inflate(R.layout.room_header_card, parent, false);
                return new HeaderViewHolder(itemView);
            }
            case ListItem.TYPE_DEVICE: {
                View itemView = inflater.inflate(R.layout.device_card, parent, false);
                return new DeviceViewHolder(itemView);
            }
            case ListItem.TYPE_DEVICE_GROUP: {
                View itemView = inflater.inflate(R.layout.device_card, parent, false);
                return new DeviceViewHolder(itemView);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Resources resources = mContext.getResources();
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ListItem.TYPE_SUMMARY: {
                SummaryItem summary = (SummaryItem) deviceList.get(position);
                SummaryViewHolder holder = (SummaryViewHolder) viewHolder;
                holder.homeName.setText(summary.getHomeName());
                break;
            }
            case ListItem.TYPE_SCENE_GROUP:
                break;
            case ListItem.TYPE_SCENE_HEADER: {
                SceneHeaderItem sceneHeaderItem = (SceneHeaderItem) deviceList.get(position);
                SceneHeaderViewHolder holder = (SceneHeaderViewHolder) viewHolder;
                holder.sceneHeaderName.setText(sceneHeaderItem.getsceneHeaderName());
                break;
            }
            case ListItem.TYPE_ROOM: {
                RoomItem header = (RoomItem) deviceList.get(position);
                HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
                holder.header.setText(header.getRoomName());
                break;
            }
            case ListItem.TYPE_DEVICE: {
                DeviceViewHolder holder = (DeviceViewHolder) viewHolder;
                Device device = (Device) deviceList.get(position);

                holder.warningIcon.setVisibility(View.GONE);
                holder.iconTextHolder.setVisibility(View.GONE);
                holder.icon.setAlpha(1f);
                holder.roomName.setText(Utility.getRoomById(deviceList, device.getRoomID()).getRoomName());
                holder.deviceName.setText(device.getDeviceName());

                if (!device.isResponding()){
                    disableCard(holder, resources);
                    if (device.getIconID() != null){
                        setIcon(holder.icon, device.getIconID(), false);
                    }
                    //It's a thermostat
                    else {
                        setThermostatIcon(holder, null, false);
                    }
                    holder.warningIcon.setVisibility(VISIBLE);
                    holder.warningIcon.setImageResource(R.drawable.ic_warning);
                    holder.additionalInformation.setTextColor(resources.getColor(R.color.claret));
                    holder.additionalInformation.setText(resources.getString(R.string.not_responding));
                }else {
                    Boolean activated = device.isActivated();
                    if (activated) {
                        activateCard(holder, resources);
                    } else {
                        disableCard(holder, resources);
                    }

                    if (device.getIconID() != null) {
                        setIcon(holder.icon, device.getIconID(), activated);
                    }

                    DeviceItem deviceItem = device.getDeviceItem();
                    switch (device.getDeviceType()){
                        case DeviceTypes.DEVICE_LIGHT:
                            Light light = (Light) deviceItem;
                            if (light != null) {
                                if (activated) {
                                    if (light.getBrightness() != null) {
                                        holder.additionalInformation.setText(resources.getString(
                                                R.string.brightness_level, light.getBrightness()));
                                    } else holder.additionalInformation.setText(
                                            resources.getString(R.string.state_on));
                                } else {
                                    holder.additionalInformation.setText(
                                            resources.getString(R.string.state_off));
                                }
                            }
                            break;

                        case DeviceTypes.DEVICE_THERMOSTAT:
                            Thermostat thermostat = (Thermostat) deviceItem;
                            if (thermostat != null) {

                                setThermostatIcon(holder, thermostat.getCurrentTemperature(), activated);

                                switch (thermostat.getHeatingMode()) {
                                    case HEATING:
                                        holder.additionalInformation.setText(
                                                resources.getString(R.string.heat_set_to,
                                                        com.temperatureselector.Utility.roundToHalf(
                                                                thermostat.getTargetTemperature())));
                                        break;

                                    case COOLING:
                                        holder.additionalInformation.setText(
                                                resources.getString(R.string.cool_set_to,
                                                        com.temperatureselector.Utility.roundToHalf(
                                                                thermostat.getTargetTemperature())));
                                        break;

                                    case ECO_HEATING:
                                        break;

                                    case ECO_COOLING:
                                        break;

                                    case HEATING_HOLD:
                                        holder.additionalInformation.setText(
                                                resources.getString(R.string.temperature_hold,
                                                        com.temperatureselector.Utility.roundToHalf(
                                                                thermostat.getTargetTemperature())));
                                    case COOLING_HOLD:
                                        holder.additionalInformation.setText(
                                                resources.getString(R.string.temperature_hold,
                                                        com.temperatureselector.Utility.roundToHalf(
                                                                thermostat.getTargetTemperature())));
                                        break;

                                    case FAN:
                                        holder.additionalInformation.setText(
                                                resources.getString(R.string.fan));
                                        break;

                                    case OFF:
                                        holder.additionalInformation.setText(
                                                resources.getString(R.string.state_off));
                                        break;
                                }
                            }
                            break;
                    }
                }
                FrameLayout blurredBackgroundImage = ((MainActivity)mContext)
                        .findViewById(R.id.blurred_background_image);

                if (blurredBackgroundImage != null) {
                    //TODO
                    holder.clipView.setupWith(blurredBackgroundImage, 600);
                }
                break;
            }
            case ListItem.TYPE_DEVICE_GROUP: {
                DeviceViewHolder holder = (DeviceViewHolder) viewHolder;
                DeviceGroup deviceGroup = (DeviceGroup) deviceList.get(position);

                holder.warningIcon.setVisibility(View.GONE);
                holder.iconTextHolder.setVisibility(View.GONE);
                holder.icon.setAlpha(1f);
                holder.roomName.setText(Utility.getRoomById(deviceList, deviceGroup.getRoomID()).getRoomName());
                holder.deviceName.setText(deviceGroup.getGroupName());

                if (deviceGroup.getRespondingDeviceCount() == 0){
                    disableCard(holder, resources);
                    if (deviceGroup.getGroupIconID() != null){
                        setIcon(holder.icon, deviceGroup.getGroupIconID(), false);
                    }
                    //It's a thermostat
                    else {
                        setThermostatIcon(holder, null, false);
                    }
                    holder.warningIcon.setVisibility(VISIBLE);
                    holder.warningIcon.setImageResource(R.drawable.ic_warning);
                    holder.additionalInformation.setTextColor(resources.getColor(R.color.claret));
                    holder.additionalInformation.setText(resources.getString(R.string.not_responding));
                }else {
                    List<DeviceItem> deviceItems = deviceGroup.getDeviceItems();
                    if (deviceGroup.getRespondingDeviceCount() != deviceItems.size()){
                        holder.warningIcon.setVisibility(VISIBLE);
                        holder.warningIcon.setImageResource(R.drawable.ic_warning);
                    }

                    Boolean activated = deviceGroup.isActivated();
                    if (activated) {
                        activateCard(holder, resources);
                    } else {
                        disableCard(holder, resources);
                    }

                    if (deviceGroup.getGroupIconID() != null) {
                        setIcon(holder.icon, deviceGroup.getGroupIconID(), activated);
                    }
                    DeviceItem deviceItem = null;

                    //There should be a @NonNull deviceItem because getRespondingDeviceCount > 0
                    for (DeviceItem mDeviceItem : deviceItems){
                        if (mDeviceItem != null){
                            deviceItem = mDeviceItem;
                            break;
                        }
                    }

                    switch (deviceGroup.getDeviceType()){
                        case DeviceTypes.DEVICE_LIGHT:
                            Light light = (Light) deviceItem;
                            if (light != null) {
                                if (activated) {
                                    if (light.getBrightness() != null) {
                                        holder.additionalInformation.setText(resources.getString(
                                                R.string.brightness_level, light.getBrightness()));
                                    } else holder.additionalInformation.setText(
                                            resources.getString(R.string.state_on));
                                } else {
                                    holder.additionalInformation.setText(
                                            resources.getString(R.string.state_off));
                                }
                            }
                            break;

                        case DeviceTypes.DEVICE_THERMOSTAT:
                            Thermostat thermostat = (Thermostat) deviceItem;
                            if (thermostat != null) {
                                setThermostatIcon(holder, thermostat.getCurrentTemperature(), activated);

                                switch (thermostat.getHeatingMode()) {
                                    case HEATING:
                                        holder.additionalInformation.setText(
                                                resources.getString(R.string.heat_set_to,
                                                        com.temperatureselector.Utility.roundToHalf(
                                                                thermostat.getTargetTemperature())));
                                        break;

                                    case COOLING:
                                        holder.additionalInformation.setText(
                                                resources.getString(R.string.cool_set_to,
                                                        com.temperatureselector.Utility.roundToHalf(
                                                                thermostat.getTargetTemperature())));
                                        break;

                                    case ECO_HEATING:
                                        break;

                                    case ECO_COOLING:
                                        break;

                                    case HEATING_HOLD:
                                        holder.additionalInformation.setText(
                                                resources.getString(R.string.temperature_hold,
                                                        com.temperatureselector.Utility.roundToHalf(
                                                                thermostat.getTargetTemperature())));
                                    case COOLING_HOLD:
                                        holder.additionalInformation.setText(
                                                resources.getString(R.string.temperature_hold,
                                                        com.temperatureselector.Utility.roundToHalf(
                                                                thermostat.getTargetTemperature())));
                                        break;

                                    case FAN:
                                        holder.additionalInformation.setText(
                                                resources.getString(R.string.fan));
                                        break;

                                    case OFF:
                                        holder.additionalInformation.setText(
                                                resources.getString(R.string.state_off));
                                        break;
                                }
                            }
                            break;
                    }
                }

                FrameLayout blurredBackgroundImage = ((MainActivity)mContext)
                        .findViewById(R.id.blurred_background_image);

                if (blurredBackgroundImage != null) {
                    holder.clipView.setupWith(blurredBackgroundImage, 30);
                }
                break;
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    @Override
    public long getItemId(int position) {
       return super.getItemId(position);
    }

    public RecyclerView getSceneRecycleView() {
        return sceneRecycleView;
    }

    public void setOnDeviceItemClickListener(DeviceItemClickListener listener) {
        deviceItemClickListener = listener;
    }

    public void setOnDeviceItemLongClickListener(DeviceItemLongClickListener listener) {
        deviceItemLongClickListener = listener;
    }

    public interface DeviceItemClickListener {
         void onDeviceItemClicked(View v, int adapterPosition);
    }

    public interface DeviceItemLongClickListener {
        void onDeviceItemLongClicked(View v, int adapterPosition);
    }

    private void setIcon(CircleImageView circleImageView, @NonNull Integer iconId, boolean activated){
        switch (iconId){
            case R.id.ic_light_bulb:
                if (activated) {
                    circleImageView.setImageResource(R.drawable.ic_light_bulb_on);
                } else {
                    circleImageView.setImageResource(R.drawable.ic_light_bulb_off);
                }
                break;
        }
    }

    private void setThermostatIcon(DeviceViewHolder holder, @Nullable Float currentTemperature, boolean activated){
        if (currentTemperature != null) {
                //TODO: interpolate color changing according current temperature
            if (activated) {
                holder.icon.setImageResource(R.color.eco_heating_color);
            }
            else {
                holder.icon.setImageResource(R.color.gray_off);
            }
            holder.iconTextHolder.setVisibility(VISIBLE);
            holder.iconText.setText(Utility.getStringTemperature(currentTemperature));
            if (Utility.getDecimal(currentTemperature) == 5) {
                holder.iconTextSup.setText("5");
            } else {
                holder.iconTextSup.setText("°");
            }
        }
        else {
            holder.icon.setImageResource(R.color.gray_off);
            holder.iconTextHolder.setVisibility(VISIBLE);
            holder.iconText.setText("--");
            holder.iconTextSup.setText("°");
        }
    }

    public void activateCard(DeviceViewHolder holder, Resources resources){
        holder.roomName.setTextColor(Color.BLACK);
        holder.deviceName.setTextColor(Color.BLACK);
        holder.clipView.setOverlayColor(Color.WHITE);
        holder.additionalInformation.setTextColor(resources.getColor(R.color.dark_gray_on));
    }

    public void disableCard(DeviceViewHolder holder, Resources resources){
        holder.roomName.setTextColor(resources.getColor(R.color.gray_off));
        holder.deviceName.setTextColor(resources.getColor(R.color.gray_off));
        holder.clipView.setOverlayColor(resources.getColor(R.color.colorOverlay));
        holder.additionalInformation.setTextColor(resources.getColor(R.color.dark_gray_off));
    }
}
