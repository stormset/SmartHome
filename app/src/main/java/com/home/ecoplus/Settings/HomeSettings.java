package com.home.ecoplus.Settings;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.home.ecoplus.MyGlideEngine;
import com.home.ecoplus.R;
import com.home.ecoplus.RecycleViewAdapter.DayOfWeekItem;
import com.home.ecoplus.RecycleViewAdapter.UserInstanceItem;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.ArrayList;
import java.util.List;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class HomeSettings extends Fragment {
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        context = getContext();
        View view = inflater.inflate(R.layout.home_settings, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        RelativeLayout relativeLayout = view.findViewById(R.id.blur_root);
        BlurView blurView = view.findViewById(R.id.toolbar_background);
        blurView.setupWith(relativeLayout)
                .setBlurAutoUpdate(true)
                .blurRadius(25)
                .blurAlgorithm(new RenderScriptBlur(context))
                .setOverlayColor(getResources().getColor(R.color.colorOverlay4));
        ExpandableAdapter settingsAdapter = new ExpandableAdapter(getContext(), createSettingsList());
        settingsAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(settingsAdapter);
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        settingsAdapter.setOnParentItemClickListener(new ExpandableAdapter.ParentItemClickListener() {
            @Override
            public void onParentItemClicked(View v, int parentPosition) {
                Toast.makeText(getContext(), "Parent: " + parentPosition, Toast.LENGTH_SHORT).show();
                if (parentPosition == 9){
                    openImageSelector();
                }
            }
        });
        settingsAdapter.setOnChildItemClickListener(new ExpandableAdapter.ChildItemClickListener() {

            @Override
            public void onChildItemClicked(View v, int parentPosition, int childPosition) {
                Toast.makeText(getContext(), "Parent: " + parentPosition + " && Child: " + childPosition, Toast.LENGTH_SHORT).show();
            }
        });
        /*
        EditTextItem editTextItem = (EditTextItem)settingsList.get(1);
        if (editTextItem.getText() == null){
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        */
        return view;
    }

    private List<SettingsItem> createSettingsList() {
        List<SettingsItem> settingsList = new ArrayList<>();

        settingsList.add(new HeaderItem("NAME"));
        settingsList.add(new EditTextItem("Home Name"));

        settingsList.add(new HeaderItem("HOME HUBS"));
        settingsList.add(new MoreStateItem(true,"Living Room", context));

        settingsList.add(new HeaderItem("PEOPLE"));
        settingsList.add(new UserViewParentItem(createUserList()));
        settingsList.add(new DescriptionItem(context.getResources().getString(R.string.invitation_description)));
        settingsList.add(new HeaderItem("HOME WALLPAPER"));
        settingsList.add(new TextItem("Take Photo...", R.color.colorPrimary));
        settingsList.add(new TextItem("Choose from Existing", true));

        return settingsList;
    }

   public List<DayOfWeekItem> createDoWList(){
       List<DayOfWeekItem>dow = new ArrayList<>();
       dow.add(new DayOfWeekItem(true));
       dow.add(new DayOfWeekItem());
       dow.add(new DayOfWeekItem());
       dow.add(new DayOfWeekItem());
       dow.add(new DayOfWeekItem());
       dow.add(new DayOfWeekItem());
       dow.add(new DayOfWeekItem());
       return dow;
   }

    public List<UserInstanceItem> createUserList(){
        List<UserInstanceItem>dow = new ArrayList<>();
        dow.add(new UserInstanceItem(0, "Admin", "Master User", true, true));
        dow.add(new UserInstanceItem(0, "Example User 1", true, true));
        dow.add(new UserInstanceItem(0, "Example User 2", true, true));
        dow.add(new UserInstanceItem(0, "Example User 3", true, true));
        dow.add(new UserInstanceItem(0, "Example User 4", true, true));
        dow.add(new UserInstanceItem(0, "Example Guest","Landlord", false, false));
        return dow;
    }

    @SuppressLint("CheckResult")
    public void openImageSelector(){
//        // no Android >= 10 support from matisse yet.
//        RxPermissions.getInstance(context).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .subscribe(granted -> {
//                    if (granted) {
//                        Matisse.from(HomeSettings.this)
//                                .choose(MimeType.ofImage())
//                                .countable(false)
//                                .capture(false)
//                                .maxSelectable(1)
//                                .showSingleMediaType(true)
//                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//                                .thumbnailScale(0.85f)
//                                .imageEngine(new MyGlideEngine())
//                                .forResult(4);
//                    } else {
//                        Log.e("s","DENEIED");
//                    }
//                });
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
