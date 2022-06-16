package com.home.ecoplus.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.home.ecoplus.ControllerInstance;
import com.home.ecoplus.Devices.DeviceItem;
import com.home.ecoplus.Devices.DeviceTypes;
import com.home.ecoplus.Devices.HeatingModes;
import com.home.ecoplus.Devices.HeatingSystemType;
import com.home.ecoplus.Devices.Light;
import com.home.ecoplus.Devices.Thermostat;
import com.home.ecoplus.MainActivity;
import com.home.ecoplus.MyDialog;
import com.home.ecoplus.NormalModeItemTouchHelperCallback;
import com.home.ecoplus.R;
import com.home.ecoplus.RecycleViewAdapter.Device;
import com.home.ecoplus.RecycleViewAdapter.DeviceAdapter;
import com.home.ecoplus.RecycleViewAdapter.ListItem;
import com.home.ecoplus.RecycleViewAdapter.RoomItem;
import com.home.ecoplus.RecycleViewAdapter.SceneAdapter;
import com.home.ecoplus.RecycleViewAdapter.SceneGroup;
import com.home.ecoplus.RecycleViewAdapter.SceneHeaderItem;
import com.home.ecoplus.RecycleViewAdapter.SceneItem;
import com.home.ecoplus.RecycleViewAdapter.SummaryItem;
import com.home.ecoplus.Settings.HomeSettings;
import com.home.ecoplus.Utility;
import com.home.ecoplus.VSeekBar;
import com.switchbutton.widget.SwitchButton;
import com.temperatureselector.TemperatureSelector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import eightbitlab.com.blurview.BlurAlgorithm;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.ClipView;
import eightbitlab.com.blurview.RenderScriptBlur;
import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollUpdateListener;
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator;
import me.everything.android.ui.overscroll.adapters.RecyclerViewOverScrollDecorAdapter;

public class HomeFragment extends Fragment {
    Context context;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    public RelativeLayout relativeLayout;
    public RecyclerView recyclerView;
    public DeviceAdapter deviceAdapter;
    public SceneAdapter sceneAdapter;
    public List<ListItem> deviceList;
    public List<ListItem> sceneList;
    public GridLayoutManager mLayoutManager;
    public ItemTouchHelper.Callback ithCallback;
    ControllerInstance controllerInstance = null;
    RelativeLayout blurDialogContent;
    RelativeLayout blurRoot;
    FrameLayout backgroundImage;
    BlurView blurView;
    ClipView homeSettings;
    ClipView edit;
    ClipView add;
    public FrameLayout blurredBackgroundImage;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeViewReferences(view);

        FrameLayout frameLayout = view.findViewById(R.id.blurred_background_image);
        if (context instanceof MainActivity) {
            View a =  ((MainActivity)context).findViewById(android.R.id.content);
            BlurView blurView = a.findViewById(R.id.bottom_navigation_background);
            if (frameLayout != null) {
                blurView.setupWith(blurRoot)
                        .setBlurAutoUpdate(true)
                        .blurRadius(18)
                        .blurAlgorithm(new RenderScriptBlur(context))
                        .setOverlayColor(getResources().getColor(R.color.navigation_blur_overlay));
            }
        }

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            if (((MainActivity) getActivity()).getSupportActionBar() != null) {
                Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationIcon(R.drawable.ic_empty);
            }
        }

        Drawable drawable = Objects.requireNonNull(frameLayout).getBackground();
        Bitmap original = ((BitmapDrawable)drawable).getBitmap();
        Point scaledSize = Utility.getScaledSize(original.getWidth(), original.getHeight(), 4f);
        Bitmap res = getResizedBitmap(original, scaledSize.x, scaledSize.y);
        BlurAlgorithm blurAlgorithm = new RenderScriptBlur(context);
        Bitmap bmd = blurAlgorithm.blur(res, 25);
        blurredBackgroundImage.setBackground(new BitmapDrawable(getResources(), bmd));


        // loadImageFromStorage("/data/user/0/com.home.ecoplus/app_imageDir/");
        homeSettings.setupWith(blurredBackgroundImage,4,28)
        .setBlurAutoUpdate(false)
        .setOverlayColor(context.getResources().getColor(R.color.colorOverlayLight));
        edit.setupWith(blurredBackgroundImage,4,12)
                .setBlurAutoUpdate(false)
                .setOverlayColor(context.getResources().getColor(R.color.colorOverlayLight));
        add.setupWith(blurredBackgroundImage,4,28)
                .setBlurAutoUpdate(false)
                .setOverlayColor(context.getResources().getColor(R.color.colorOverlayLight));
      //  topHeader.setupWith(backgroundImage, 1).setBlurAutoUpdate(false);
        homeSettings.setOnTouchListener(getOnTouchListener());
        edit.setOnTouchListener(getOnTouchListener());
        add.setOnTouchListener(getOnTouchListener());
        homeSettings.setOnClickListener(view1 -> {
                    // update the main content by replacing fragments
                    Fragment fragment = new HomeSettings();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down,R.anim.slide_up, R.anim.slide_down);
                    fragmentTransaction.replace(R.id.settings_frame, fragment);
                    fragmentTransaction.addToBackStack("sett");
                    fragmentTransaction.commit();

        });
        sceneAdapter = new SceneAdapter(context, createSceneList());
        sceneAdapter.notifyDataSetChanged();
        deviceAdapter = new DeviceAdapter(context, createDeviceList(), sceneAdapter);
        deviceAdapter.notifyDataSetChanged();
        mLayoutManager = new GridLayoutManager(context, getNumberOfColumns(114));
        mLayoutManager.setItemPrefetchEnabled(true);
        mLayoutManager.setInitialPrefetchItemCount(30);
        refreshSpanSize();
        recyclerView.setLayoutManager(mLayoutManager);
        ithCallback = new NormalModeItemTouchHelperCallback(context);

//        VerticalOverScrollBounceEffectDecorator decor = new VerticalOverScrollBounceEffectDecorator(new RecyclerViewOverScrollDecorAdapter(recyclerView, ithCallback));
//        decor.setOverScrollUpdateListener(new IOverScrollUpdateListener() {
//            @Override
//            public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {
//                if (offset > 0) {
//                    appBarLayout.setExpanded(true, true);
//                }
//            }
//        });

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(deviceAdapter);
        deviceAdapter.setOnDeviceItemClickListener((v, adapterPosition) -> {
            switch (deviceList.get(adapterPosition).getType()){
                case ListItem.TYPE_DEVICE:
                    Device clickedItem = (Device) deviceList.get(adapterPosition);
                    if (clickedItem.trySwitchState())deviceAdapter.notifyItemChanged(adapterPosition);
                    break;
                case ListItem.TYPE_DEVICE_GROUP:
                    break;
            }
        });
        deviceAdapter.setOnDeviceItemLongClickListener((v, adapterPosition) -> {
            boolean notResponding =
                    ((Device)deviceList.get(adapterPosition)).getDeviceItem() == null;

            ((MainActivity)getActivity()).hideBottomAppBar();

            if (controllerInstance == null && !notResponding) {
                openBlurDialog(v, adapterPosition);
            }
            else if (notResponding){
                recyclerView.setEnabled(false);
                MyDialog cdd = new MyDialog(context);
                cdd.show();
            }
        });
        sceneAdapter.setOnSceneItemClickListener((v, adapterPosition) -> {
            SceneItem clickedItem =  (SceneItem) sceneList.get(adapterPosition);
            clickedItem.setActivatedState(!clickedItem.getActivatedState());
            sceneAdapter.notifyItemChanged(adapterPosition);
        });
        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        refreshSpanSize();
    }

    @SuppressLint("ClickableViewAccessibility")
    public View.OnTouchListener getOnTouchListener(){
        return (view, motionEvent) -> {
            int downDuration = 80;
            float downAlpha = 0.8f;
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ObjectAnimator.ofFloat(view, "alpha", downAlpha).setDuration(downDuration).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (view.getAlpha() > downAlpha) {
                        int duration = (int) ((int) ((1 - downAlpha) / downDuration) * (view.getAlpha() - downAlpha));
                        ObjectAnimator.ofFloat(view, "alpha", downAlpha).setDuration(duration).start();

                        new Handler().postDelayed(() -> ObjectAnimator.ofFloat(view, "alpha", 1f).setDuration(100).start(), duration);
                    } else {
                        ObjectAnimator.ofFloat(view, "alpha", 1f).setDuration(100).start();
                    }
                    break;
            }
            return false;
        };
    }

    private void initializeViewReferences(View view){
        appBarLayout = view.findViewById(R.id.app_bar);
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar_layout);
        homeSettings = view.findViewById(R.id.home_settings);
        edit = view.findViewById(R.id.edit);
        add = view.findViewById(R.id.add);
        blurRoot = view.findViewById(R.id.blur_root);
        blurredBackgroundImage = view.findViewById(R.id.blurred_background_image);
        backgroundImage = view.findViewById(R.id.background_image);
        relativeLayout = view.findViewById(R.id.relative_layout);
        blurView = view.findViewById(R.id.BlurLayout2);
        blurDialogContent = view.findViewById(R.id.blur_dialog_content);
        recyclerView = view.findViewById(R.id.device_recycler_view);
    }

    private List<ListItem> createDeviceList() {
        deviceList = new ArrayList<>();
        //Summary
        // deviceList.add(new SummaryItem(745648464,"..."));
        //Scenes
        deviceList.add(new SceneHeaderItem("Scenarios"));
        deviceList.add(new SceneGroup());

        deviceList.add(new RoomItem(1,"Living Room"));

        deviceList.add(new Device("ST-1", 2, "Thermostat", null,
                new Thermostat(null, HeatingSystemType.RADIATORS, HeatingModes.HEATING, 19f, 21.5f)));
        deviceList.add(new Device("SL-1", 1, "Ceiling Light", R.id.ic_light_bulb,
                new Light(null, false)));
        deviceList.add(new Device("SL-1", 1, "In-Wall Lights", R.id.ic_light_bulb,
                new Light(null, true)));
        deviceList.add(new Device("SL-2", 1, "Lantern", R.id.ic_light_bulb,
                new Light(85, true)));
        deviceList.add(new Device("SL-2", 1, "Mood Lighting", R.id.ic_light_bulb,
                new Light(85, false)));

        deviceList.add(new RoomItem(2,"Hallway"));
        deviceList.add(new Device("ST-1", 1, "Thermostat", null,
                new Thermostat(null, HeatingSystemType.RADIATORS, HeatingModes.OFF, 17f, null)));
        deviceList.add(new Device("SL-1", 2, "Main Lights", R.id.ic_light_bulb,
                new Light(null, false)));
        deviceList.add(new Device("SL-1", 2, "Staircase Light", R.id.ic_light_bulb,
                new Light(null, true)));
        deviceList.add(new Device("SL-2", 2, "Secondary Light", R.id.ic_light_bulb,
                new Light(85, true)));
        for (int i = 0; i<60; i++){
            deviceList.add(new Device("SL-1", 2, "Light Nr. " + i + 1, R.id.ic_light_bulb,
                    new Light(0, false)));
        }
        return deviceList;
    }

    private List<ListItem> createSceneList() {
        sceneList = new ArrayList<>();
        sceneList.add(new SceneItem(4546, "Away", 0,false));
        sceneList.add(new SceneItem(4546, "At home", 1,true));
        sceneList.add(new SceneItem(4546, "Comfort",3, true));
        sceneList.add(new SceneItem(4546, "Eco", 2,false));
        sceneList.add(new SceneItem(4546, "Morning",4, true));
        sceneList.add(new SceneItem(4546, "Night",5,false));
        return sceneList;
    }

    private void refreshSpanSize(){
        int noOfColumns;
        noOfColumns = getNumberOfColumns(114);
        mLayoutManager.setSpanCount(noOfColumns);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return getSpanSizeMethod(position, noOfColumns);
            }
        });
    }

    private int getSpanSizeMethod(int position, int noOfColumns){
        switch(deviceAdapter.getItemViewType(position)){
            case ListItem.TYPE_SUMMARY:
            case ListItem.TYPE_ROOM:
            case ListItem.TYPE_SCENE_GROUP:
            case ListItem.TYPE_SCENE_HEADER:
                return noOfColumns;
            default:
                return 1;
        }
    }

    public int getNumberOfColumns(int widthInDp){
        return Utility.calculateNoOfColumns(context, widthInDp);
    }

    public void openBlurDialog(View view, int deviceAdapterID){
        view.bringToFront();
        view.invalidate();
        controllerInstance = addController(view, deviceAdapterID);
        ObjectAnimator.ofFloat(view, "alpha", 0f)
                .setDuration(150).start();
        View controller = controllerInstance.getController();
        relativeLayout.addView(controller);

        //translate the device card to the center of the screen
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int xDest = (dm.widthPixels/2) - (view.getMeasuredWidth()/2);
        int yDest = (dm.heightPixels/2) - (view.getMeasuredHeight()/2);

        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(view, "translationX",
                xDest - location[0]).setDuration(150);
        objectAnimatorX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //view.setAlpha(0f);
            }
        });
        objectAnimatorX.start();

        ObjectAnimator.ofFloat(view, "translationY",
                yDest - location[1]).setDuration(150).start();

        //add a black overlay to the blur
        blurDialogContent.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(blurDialogContent, "alpha", 0.16f)
                .setDuration(150).start();
        //align the controller to the top of the device card and match their sizes
        controller.setX(location[0] + (int)Utility.convertDpToPixel(2.5f, context));
        controller.setY(location[1] - (int)Utility.convertDpToPixel(15, context));

        int controllerWidth = view.getWidth();
        int controllerHeight = view.getHeight();
        controller.getLayoutParams().width = controllerWidth;
        controller.getLayoutParams().height = controllerHeight;
        controller.requestLayout();
        //translate the controller to screen center
        float parentCenterX = relativeLayout.getX() + relativeLayout.getWidth()/2;
        float parentCenterY = relativeLayout.getY() + relativeLayout.getHeight()/2;
        controller.animate()
                .translationX(parentCenterX - (int)Utility.convertDpToPixel(controllerInstance.getWidth(),
                        context)/2)
                .translationY(parentCenterY - (int)Utility.convertDpToPixel(controllerInstance.getHeight(),
                        context)/2).setDuration(150);
        new Handler().postDelayed(() -> {
            //animate width change
            ValueAnimator widthAnimator = ValueAnimator.ofInt(controllerWidth,
                    (int)Utility.convertDpToPixel(controllerInstance.getWidth(), context));
            widthAnimator.setDuration(150);
            widthAnimator.addUpdateListener(animation -> {
                controller.getLayoutParams().width = (int) animation.getAnimatedValue();
                controller.requestLayout();
            });
            widthAnimator.start();

            //animate height change
            ValueAnimator heightAnimator = ValueAnimator.ofInt(controllerHeight,
                    (int)Utility.convertDpToPixel(controllerInstance.getHeight(), context));
            heightAnimator.setDuration(150);
            heightAnimator.addUpdateListener(animation -> {
                controller.getLayoutParams().height = (int) animation.getAnimatedValue();
                controller.requestLayout();
            });
            heightAnimator.start();
        },0);

        //blur the background
        addBlur();
    }

    public void closeBlurDialog(){
       // slideUpBottomNavigation();
        deviceAdapter.notifyItemChanged(controllerInstance.getDeviceAdapterID());
        View parent = controllerInstance.getParent();
        ObjectAnimator.ofFloat(parent, "alpha", 1f)
                .setDuration(150).start();
        View controller = controllerInstance.getController();
        int parentX = controllerInstance.getParentCoordinates().x;
        int parentY = controllerInstance.getParentCoordinates().y;
        int width = controllerInstance.getWidth();
        int height = controllerInstance.getHeight();
        controllerInstance = null;

        //translate the controller back to its original position
        float parentCenterX = parentX + parent.getWidth()/2;
        float parentCenterY = parentY + parent.getHeight()/2;
        controller.animate().translationX(parentCenterX -
                (int)Utility.convertDpToPixel(110, context)/2)
                .translationY(parentCenterY - (int)Utility.convertDpToPixel(100,
                        context)/2).setDuration(150);

        //animate width change
        ValueAnimator widthAnimator = ValueAnimator.ofInt(
                (int)Utility.convertDpToPixel(width, context),
                (int)Utility.convertDpToPixel(100, context));
        widthAnimator.setDuration(150);
        widthAnimator.addUpdateListener(animation -> controller.getLayoutParams().width = (int)animation.getAnimatedValue());
        widthAnimator.start();

        //animate height change
        ValueAnimator heightAnimator = ValueAnimator.ofInt(
                (int)Utility.convertDpToPixel(height, context),
                (int)Utility.convertDpToPixel(110, context));
        heightAnimator.setDuration(150);
        heightAnimator.addUpdateListener(animation -> {
            controller.getLayoutParams().height = (int)animation.getAnimatedValue();
            controller.requestLayout();
        });
        heightAnimator.start();
        ObjectAnimator.ofFloat(controller, "alpha", 0f)
                .setDuration(150).start();
        //remove background blur
        removeBlur();
        new Handler().postDelayed(() -> relativeLayout.removeView(controller),150);
    }

    public void addBlur(){
        blurView.setVisibility(View.VISIBLE);
        blurView.setupWith(blurRoot)
                .blurAlgorithm(new RenderScriptBlur(context))
                .blurRadius(0.01f)
                .setOverlayColor(Color.TRANSPARENT)
                .setBlurAutoUpdate(false);

        ValueAnimator va = ValueAnimator.ofFloat(0.01f, 25f);
        va.setDuration(300);
        va.addUpdateListener(animation -> {
            blurView.blurRadius((float)animation.getAnimatedValue());
            blurView.updateBlur();
        });
        va.start();
    }

    public void removeBlur(){
        ObjectAnimator.ofFloat(blurDialogContent, "alpha", 0f).setDuration(240).start();
        ValueAnimator va = ValueAnimator.ofFloat(25f, 0f);
        va.setDuration(300);
        va.addUpdateListener(animation -> {
            blurView.blurRadius((float)animation.getAnimatedValue());
            blurView.updateBlur();
        });
        va.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                blurView.setVisibility(View.GONE);
                blurDialogContent.setVisibility(View.GONE);
            }
        });
        va.start();
    }

    public ControllerInstance addController(View parent, int deviceAdapterID){
        ControllerInstance controllerInstance = null;
        switch (((Device)deviceList.get(deviceAdapterID)).getDeviceType()){
            case DeviceTypes.DEVICE_LIGHT:
                DeviceItem deviceItem = ((Device)deviceList.get(deviceAdapterID)).getDeviceItem();
                Light light = (Light) deviceItem;
                if (light.getBrightness() != null) {
                    VSeekBar vSeekBar = new VSeekBar(context);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                    vSeekBar.setMax(100);
                    vSeekBar.setStep(1);
                    ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 150);
                    valueAnimator.setDuration(300);
                    valueAnimator.addUpdateListener(animation -> vSeekBar.setValue(light.getBrightness()));
                    valueAnimator.start();
                    vSeekBar.setOnValuesChangeListener(new VSeekBar.OnValuesChangeListener() {
                        @Override
                        public void onPointsChanged(VSeekBar vSeekBar, int value) {

                        }

                        @Override
                        public void onStartTrackingTouch(VSeekBar vSeekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(VSeekBar vSeekBar) {

                        }
                    });
                    vSeekBar.setId(R.id.controller);
                    controllerInstance = new ControllerInstance(parent, vSeekBar, deviceAdapterID, 125, 290);
                }
                else {
                    SwitchButton switchButton = new SwitchButton(context);
                    switchButton.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT));
                    switchButton.setId(R.id.controller);
                    controllerInstance = new ControllerInstance(parent, switchButton, deviceAdapterID, 125, 290);
                    switchButton.setChecked(light.isActivated());
                    switchButton.setOnCheckedChangeListener((view, isChecked) -> {

                    });
                }
                break;
            case DeviceTypes.DEVICE_THERMOSTAT:
                TemperatureSelector temperatureSelector = new TemperatureSelector(context, 17, 19);
                temperatureSelector.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                temperatureSelector.setId(R.id.controller);
                controllerInstance = new ControllerInstance(parent, temperatureSelector, deviceAdapterID, 270, 270);
                break;
            case DeviceTypes.DEVICE_SMART_PLUG:
                SwitchButton switchButton = new SwitchButton(context);
                switchButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                switchButton.setId(R.id.controller);
                controllerInstance = new ControllerInstance(parent, switchButton, deviceAdapterID, 125, 290);
                switchButton.setOnCheckedChangeListener((view, isChecked) -> {

                });
                break;
        }

        return controllerInstance;
    }


    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            blurredBackgroundImage.setBackground(new BitmapDrawable(getResources(), b));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_APPEND);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");
        Log.e("s", mypath.getAbsolutePath());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public boolean handleOnBackPress(){
        if (controllerInstance != null){
            closeBlurDialog();
            return true;
        }
        else {
            return false;
        }
    }
}
