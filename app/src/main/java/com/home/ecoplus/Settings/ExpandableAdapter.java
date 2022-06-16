package com.home.ecoplus.Settings;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.home.ecoplus.DayOfWeekSelector;
import com.home.ecoplus.R;
import com.home.ecoplus.RecycleViewAdapter.DayOfWeekItem;
import com.home.ecoplus.UserView;
import com.home.ecoplus.Utility;
import com.suke.widget.SwitchButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static com.home.ecoplus.Settings.ChildItem.TYPE_TEXT_CHILD;
import static com.home.ecoplus.Settings.ChildItem.TYPE_TIME_PICKER_CHILD;
import static com.home.ecoplus.Settings.ChildItem.TYPE_TWO_STATE_CHILD;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

public class ExpandableAdapter extends ExpandableRecyclerAdapter<SettingsItem, ChildItem, ParentViewHolder, ChildItemViewHolder> {
    private Context context;
    private List<SettingsItem> itemList;
    private ParentItemClickListener parentItemClickListener;
    private ChildItemClickListener childItemClickListener;

    public ExpandableAdapter(Context context, @NonNull List<SettingsItem> expandableItemList) {
        super(expandableItemList);
        this.context = context;
        itemList = expandableItemList;
    }

    public class HeaderViewHolder extends com.bignerdranch.expandablerecyclerview.ParentViewHolder {

        TextView headerText;

        HeaderViewHolder(View view) {
            super(view);
            headerText = (TextView) view.findViewById(R.id.header_text);
        }

    }

    public class TextViewHolder extends com.bignerdranch.expandablerecyclerview.ParentViewHolder implements View.OnClickListener{

        TextView text;
        ViewGroup rootView;
        ImageView arrow;

        TextViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            text = (TextView) view.findViewById(R.id.text);
            rootView =  view.findViewById(R.id.root);
            arrow =  view.findViewById(R.id.arrow);
        }

        @Override
        public void onClick(View view) {
            if (parentItemClickListener != null){
                parentItemClickListener.onParentItemClicked(view, this.getParentAdapterPosition());
            }
        }
    }

    public class EditTextViewHolder extends com.bignerdranch.expandablerecyclerview.ParentViewHolder implements View.OnClickListener{

        EditText editText;
        ViewGroup rootView;

        EditTextViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            editText = (EditText) view.findViewById(R.id.edit_text);
            rootView =  view.findViewById(R.id.root);
        }

        @Override
        public void onClick(View view) {
            if (parentItemClickListener != null){
                parentItemClickListener.onParentItemClicked(view, this.getParentAdapterPosition());
            }
        }
    }

    public class TwoStateViewHolder extends com.bignerdranch.expandablerecyclerview.ParentViewHolder implements View.OnClickListener {

        TextView name;
        TextView state;
        ViewGroup rootView;

        TwoStateViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            name = (TextView) view.findViewById(R.id.name);
            state = (TextView) view.findViewById(R.id.state);
            rootView =  view.findViewById(R.id.root);
        }

        @Override
        public void onClick(View view) {
            if (parentItemClickListener != null){
                parentItemClickListener.onParentItemClicked(view, this.getParentAdapterPosition());
            }
        }
    }

    public class SwitchViewHolder extends com.bignerdranch.expandablerecyclerview.ParentViewHolder implements View.OnClickListener{

        TextView name;
        SwitchButton switchButton;
        ViewGroup rootView;

        SwitchViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            name = (TextView) view.findViewById(R.id.name);
            switchButton = (SwitchButton) view.findViewById(R.id.switch_button);
            rootView =  view.findViewById(R.id.root);
        }

        @Override
        public void onClick(View view) {
            if (parentItemClickListener != null){
                switchButton.setChecked(!switchButton.isChecked());
                parentItemClickListener.onParentItemClicked(view, this.getParentAdapterPosition());
            }
        }
    }

    public class DayOfWeekSelectorViewHolder extends com.bignerdranch.expandablerecyclerview.ParentViewHolder {

        public ViewGroup rootView;
        public DayOfWeekSelector dayOfWeekSelector;
        private int adapterPosition;

        public DayOfWeekSelectorViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView =  itemView.findViewById(R.id.root);
            dayOfWeekSelector = (DayOfWeekSelector) itemView.findViewById(R.id.day_of_week_selector);
            dayOfWeekSelector.setOnDaySelectionChangedListener(new DayOfWeekSelector.DaySelectionChangeListener() {
                @Override
                public void onDaySelectionChanged(View v, List<DayOfWeekItem> list) {
                     adapterPosition = DayOfWeekSelectorViewHolder.this.getParentAdapterPosition();
                    ((DayOfWeekParentItem)itemList.get(adapterPosition)).setList(list);
                    if (parentItemClickListener != null){
                        parentItemClickListener.onParentItemClicked(itemView, DayOfWeekSelectorViewHolder.this.getParentAdapterPosition());
                    }
                }
            });
        }
    }

    public class UserViewViewHolder extends com.bignerdranch.expandablerecyclerview.ParentViewHolder {

        public ViewGroup rootView;
        public UserView userView;
        private int adapterPosition;

        public UserViewViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView =  itemView.findViewById(R.id.root);
            userView = (UserView) itemView.findViewById(R.id.user_view);
            userView.setOnUserItemClickListener(new UserView.UserItemClickListener() {
                @Override
                public void onUserItemClicked(View v, int position) {
                    adapterPosition = UserViewViewHolder.this.getParentAdapterPosition();
                    if (childItemClickListener != null){
                        childItemClickListener.onChildItemClicked(v, adapterPosition, position);
                    }
                }
            });
        }
    }

    public class TextChildViewHolder extends ChildItemViewHolder implements View.OnClickListener{

        public ViewGroup rootView;
        public TextView name;
        public ImageView checkMark;

        public TextChildViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView =  itemView.findViewById(R.id.root);
            name = (TextView) itemView.findViewById(R.id.name);
            checkMark = (ImageView) itemView.findViewById(R.id.check_mark);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int parentPosition = this.getParentAdapterPosition();
            int childPosition = this.getChildAdapterPosition();
            ExpandableItem expandableItem = (ExpandableItem) itemList.get(parentPosition);
            TextChildItem textChildItem = (TextChildItem) expandableItem.getChildItem(childPosition);
            if (!textChildItem.isSelected()){
                deselectTextChildItems(parentPosition);
                textChildItem.setSelected(true);
                notifyChildChanged(parentPosition, childPosition);
                notifyParentChanged(parentPosition);
            }

            switch (itemList.get(parentPosition).getType()){
                case SettingsItem.TYPE_EXPANDABLE_ITEM:{
                    if (expandableItem.shouldShowSelectedName()){
                            expandableItem.setSelectedName(getSelectedChildName(parentPosition));
                    }
                    break;
                }
            }
            if (childItemClickListener != null){
                childItemClickListener.onChildItemClicked(view, parentPosition, childPosition);
            }
        }
    }

    public class TimePickerChildViewHolder extends ChildItemViewHolder {

        public ViewGroup rootView;
        public TextView name;
        public SingleDateAndTimePicker timePicker;

        public TimePickerChildViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView =  itemView.findViewById(R.id.root);
            timePicker = (SingleDateAndTimePicker) itemView.findViewById(R.id.time_picker);
            timePicker.setIsAmPm(false);

            timePicker.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(String displayed, Date date) {
                    int parentPosition;
                    int childPosition;
                    parentPosition = getParentAdapterPosition();
                    childPosition = getChildAdapterPosition();
                    if (parentPosition >= 0 && childPosition>=0) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        TimePickerChildItem timePickerChildItem =
                                (TimePickerChildItem) itemList.get(parentPosition).getChildList().get(childPosition);
                        timePickerChildItem.setHour(hour);
                        timePickerChildItem.setMinute(minute);
                    }
                }
            });
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    public class ParentViewHolder extends com.bignerdranch.expandablerecyclerview.ParentViewHolder implements View.OnClickListener{

        public ViewGroup rootView;
        public TextView name;
        public TextView selectedName;

        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView =  itemView.findViewById(R.id.root);
            name = (TextView) itemView.findViewById(R.id.name);
            selectedName = (TextView) itemView.findViewById(R.id.selected_text);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            super.onClick(view);
            if (parentItemClickListener != null){
                parentItemClickListener.onParentItemClicked(view, this.getParentAdapterPosition());
            }
        }

        @Override
        public void setExpanded(boolean expanded) {
            super.setExpanded(expanded);
        }

        @Override
        public void onExpansionToggled(boolean expanded) {
            super.onExpansionToggled(expanded);
        }
    }

    public class SelectableParentViewHolder extends com.bignerdranch.expandablerecyclerview.ParentViewHolder implements View.OnClickListener{
        public ViewGroup rootView;
        public TextView name;
        public TextView selectedName;
        public ImageView checkMark;

        public SelectableParentViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView =  itemView.findViewById(R.id.root);
            name = (TextView) itemView.findViewById(R.id.name);
            selectedName = (TextView) itemView.findViewById(R.id.selected_text);
            checkMark = (ImageView) itemView.findViewById(R.id.check_mark);
           // itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            int adapterPosition = this.getParentAdapterPosition();
            ExpandableSelectableItem expandableItem = (ExpandableSelectableItem)itemList.get(adapterPosition);
            if (!expandableItem.isSelected()){
                deselectSelectableExpandableItems(getNearestNonExpandableItem(adapterPosition));
                expandableItem.setSelected(true);
            }
            super.onClick(view);
            if (parentItemClickListener != null){
                parentItemClickListener.onParentItemClicked(view, adapterPosition);
            }
        }

        @Override
        public void setExpanded(boolean expanded) {
            super.setExpanded(expanded);
        }

        @Override
        public void onExpansionToggled(boolean expanded) {
            super.onExpansionToggled(expanded);
            int parentPosition = this.getParentAdapterPosition();
            ExpandableSelectableItem expandableItem = (ExpandableSelectableItem) itemList.get(parentPosition);
           if(expanded) {
               if (expandableItem.getChildList() != null) {
                   if (expandableItem.getChildList().size() == 1) {
                       ChildItem childItem = expandableItem.getChildItem(0);
                       if (childItem instanceof TimePickerChildItem) {
                           TimePickerChildItem timePickerChildItem = (TimePickerChildItem) childItem;
                           if (timePickerChildItem.getHour() != null && timePickerChildItem.getMinute() != null) {
                               expandableItem.setSelectedName(Utility.getDisplayTime(
                                       timePickerChildItem.getHour(),
                                       timePickerChildItem.getMinute()));
                           } else {
                               Calendar calendar = Calendar.getInstance();
                               int hour = calendar.get(Calendar.HOUR_OF_DAY);
                               int minute = calendar.get(Calendar.MINUTE);
                               timePickerChildItem.setHour(hour);
                               timePickerChildItem.setMinute(minute);
                               expandableItem.setSelectedName(Utility.getDisplayTime(hour, minute));
                           }
                       }
                   }
               }
           }
           else {
               expandableItem.setSelectedName("");
           }
            notifyParentChanged(parentPosition);
        }
    }



    @UiThread
    @NonNull
    @Override
    public com.bignerdranch.expandablerecyclerview.ParentViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parentViewGroup.getContext());
        switch (viewType) {
            case SettingsItem.TYPE_HEADER: {
                View itemView = inflater.inflate(R.layout.settings_header, parentViewGroup, false);
                return new HeaderViewHolder(itemView);
            }
            case SettingsItem.TYPE_TEXT: {
                View itemView = inflater.inflate(R.layout.settings_text, parentViewGroup, false);
                return new TextViewHolder(itemView);
            }
            case SettingsItem.TYPE_EDIT_TEXT: {
                View itemView = inflater.inflate(R.layout.settings_edit_text, parentViewGroup, false);
                return new EditTextViewHolder(itemView);
            }
            case SettingsItem.TYPE_TWO_STATE_ITEM: {
                View itemView = inflater.inflate(R.layout.settings_two_state, parentViewGroup, false);
                return new TwoStateViewHolder(itemView);
            }
            case SettingsItem.TYPE_SWITCH_ITEM: {
                View itemView = inflater.inflate(R.layout.settings_switch, parentViewGroup, false);
                return new SwitchViewHolder(itemView);
            }
            case  SettingsItem.TYPE_DAY_OF_WEEK_ITEM: {
                View itemView = inflater.inflate(R.layout.settings_day_of_week_selector, parentViewGroup, false);
                return new DayOfWeekSelectorViewHolder(itemView);
            }
            case SettingsItem.TYPE_USER_VIEW_ITEM: {
                View itemView = inflater.inflate(R.layout.settings_user_view, parentViewGroup, false);
                return new UserViewViewHolder(itemView);
            }
            case SettingsItem.TYPE_EXPANDABLE_SELECTABLE_ITEM: {
                View itemView = inflater.inflate(R.layout.selectable_parent_item, parentViewGroup, false);
                return new SelectableParentViewHolder(itemView);
            }
            case SettingsItem.TYPE_EXPANDABLE_ITEM: {
                View itemView = inflater.inflate(R.layout.parent_item, parentViewGroup, false);
                return new ParentViewHolder(itemView);
            }
            case SettingsItem.TYPE_DESCRIPTION: {
                View itemView = inflater.inflate(R.layout.settings_description, parentViewGroup, false);
                return new HeaderViewHolder(itemView);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @UiThread
    @NonNull
    @Override
    public ChildItemViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(childViewGroup.getContext());
        switch (viewType) {
            case TYPE_TWO_STATE_CHILD: {
                View itemView = inflater.inflate(R.layout.header_item, childViewGroup, false);
                return new ChildItemViewHolder(itemView);
            }
            case  TYPE_TEXT_CHILD: {
                View itemView = inflater.inflate(R.layout.text_child_item, childViewGroup, false);
                return new TextChildViewHolder(itemView);
            }
            case  TYPE_TIME_PICKER_CHILD: {
                View itemView = inflater.inflate(R.layout.settings_time_picker, childViewGroup, false);
                return new TimePickerChildViewHolder(itemView);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @UiThread
    @Override
    public void onBindParentViewHolder(@NonNull com.bignerdranch.expandablerecyclerview.ParentViewHolder parentViewHolder, int parentPosition, @NonNull SettingsItem parent) {
        int viewType = getParentViewType(parentPosition);
        switch (viewType) {
            case SettingsItem.TYPE_HEADER: {
                HeaderItem headerItem = (HeaderItem) itemList.get(parentPosition);
                HeaderViewHolder holder = (HeaderViewHolder) parentViewHolder;
                holder.headerText.setText(headerItem.getName());
                break;
            }
            case SettingsItem.TYPE_TEXT: {
                TextItem textItem = (TextItem) itemList.get(parentPosition);
                TextViewHolder holder = (TextViewHolder) parentViewHolder;
                holder.text.setText(textItem.getText());
                if (textItem.getTextColor() != null){
                    holder.text.setTextColor(context.getResources().getColor(textItem.getTextColor()));
                }
                else {
                    holder.text.setTextColor(Color.BLACK);
                }
                if (textItem.shouldShowArrow()){
                    holder.arrow.setVisibility(View.VISIBLE);
                }
                else {
                    holder.arrow.setVisibility(View.INVISIBLE);
                }
                manageParentBorders(holder.rootView, parentPosition);
                break;
            }
            case SettingsItem.TYPE_EDIT_TEXT: {
                EditTextItem editTextItem = (EditTextItem) itemList.get(parentPosition);
                EditTextViewHolder holder = (EditTextViewHolder) parentViewHolder;
                holder.editText.setText(editTextItem.getText());
                if (editTextItem.getText() != null){
                    holder.editText.setText(editTextItem.getText());
                }
                holder.editText.setHint(editTextItem.getPlaceholder());
                manageParentBorders(holder.rootView, parentPosition);
                break;
            }
            case SettingsItem.TYPE_TWO_STATE_ITEM: {
                MoreStateItem moreStateItem = (MoreStateItem) itemList.get(parentPosition);
                TwoStateViewHolder holder = (TwoStateViewHolder) parentViewHolder;
                holder.name.setText(moreStateItem.getName());
                if (moreStateItem.getState()){
                    holder.state.setText(moreStateItem.getEnabledStateName());
                }else {
                    holder.state.setText(moreStateItem.getDisabledStateName());
                }
                manageParentBorders(holder.rootView, parentPosition);
                break;
            }
            case SettingsItem.TYPE_SWITCH_ITEM: {
                SwitchItem switchItem = (SwitchItem) itemList.get(parentPosition);
                SwitchViewHolder holder = (SwitchViewHolder) parentViewHolder;
                holder.name.setText(switchItem.getName());
                holder.switchButton.setChecked(switchItem.getState());
                manageParentBorders(holder.rootView, parentPosition);
                break;
            }
            case  SettingsItem.TYPE_DAY_OF_WEEK_ITEM: {
                DayOfWeekParentItem dayOfWeekParentItem = (DayOfWeekParentItem) itemList.get(parentPosition);
                DayOfWeekSelectorViewHolder holder = (DayOfWeekSelectorViewHolder) parentViewHolder;
                holder.dayOfWeekSelector.setList(dayOfWeekParentItem.getList());
                manageParentBorders(holder.rootView, parentPosition);
                break;
            }
            case  SettingsItem.TYPE_USER_VIEW_ITEM: {
                UserViewParentItem userViewParentItem = (UserViewParentItem) itemList.get(parentPosition);
                UserViewViewHolder holder = (UserViewViewHolder) parentViewHolder;
                holder.userView.setList(userViewParentItem.getList());
                manageParentBorders(holder.rootView, parentPosition);
                break;
            }
            case SettingsItem.TYPE_EXPANDABLE_ITEM: {
                ExpandableItem expandableItem = (ExpandableItem) itemList.get(parentPosition);
                ParentViewHolder holder = (ParentViewHolder) parentViewHolder;
                LinearLayout topBorder = (LinearLayout) holder.rootView.findViewById(R.id.top_border);
                LinearLayout bottomBorder = (LinearLayout) holder.rootView.findViewById(R.id.bottom_border);
                if (parentViewHolder.isExpanded()){
                    hide(topBorder);
                   setMargin(bottomBorder, 16);
                }else {
                    manageParentBorders(holder.rootView, parentPosition);
                }
                holder.name.setText(expandableItem.getName());
                if (expandableItem.shouldShowSelectedName()){
                    holder.selectedName.setVisibility(View.VISIBLE);
                    if (expandableItem.getSelectedName().isEmpty()){
                        holder.selectedName.setText(getSelectedChildName(parentPosition));
                    }
                    else {
                        holder.selectedName.setText(expandableItem.getSelectedName());
                    }
                }else {
                    holder.selectedName.setVisibility(View.INVISIBLE);
                }
                break;
            }
            case SettingsItem.TYPE_EXPANDABLE_SELECTABLE_ITEM: {
                ExpandableSelectableItem expandableItem = (ExpandableSelectableItem) itemList.get(parentPosition);
                SelectableParentViewHolder holder = (SelectableParentViewHolder) parentViewHolder;
                LinearLayout topBorder = (LinearLayout) holder.rootView.findViewById(R.id.top_border);
                LinearLayout bottomBorder = (LinearLayout) holder.rootView.findViewById(R.id.bottom_border);
                if (parentViewHolder.isExpanded()){
                    hide(topBorder);
                    setMargin(bottomBorder, 16);
                }else {
                    manageParentBorders(holder.rootView, parentPosition);
                }
                holder.name.setText(expandableItem.getName());
                if (expandableItem.isSelected()){
                    holder.checkMark.setVisibility(View.VISIBLE);
                }else {
                    holder.checkMark.setVisibility(View.INVISIBLE);
                }
                if (expandableItem.shouldShowSelected()){
                    holder.selectedName.setVisibility(View.VISIBLE);
                    holder.selectedName.setText(expandableItem.getSelectedName());
                }else {
                    holder.selectedName.setVisibility(View.INVISIBLE);
                }
                break;
            }
            case SettingsItem.TYPE_DESCRIPTION: {
                DescriptionItem headerItem = (DescriptionItem) itemList.get(parentPosition);
                HeaderViewHolder holder = (HeaderViewHolder) parentViewHolder;
                holder.headerText.setText(headerItem.getText());
                break;
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onBindChildViewHolder(@NonNull ChildItemViewHolder childItemViewHolder, int parentPosition, int childPosition, @NonNull ChildItem childItem) {
        int viewType = getChildViewType(parentPosition, childPosition);
        switch (viewType) {
            case ChildItem.TYPE_TEXT_CHILD: {
                TextChildItem textChildItem = (TextChildItem) getChildItem(parentPosition, childPosition);
                TextChildViewHolder holder = (TextChildViewHolder) childItemViewHolder;
                holder.name.setText(textChildItem.getText());
                if (textChildItem.isSelected()){
                    holder.checkMark.setVisibility(View.VISIBLE);
                }else {
                    holder.checkMark.setVisibility(View.INVISIBLE);
                }
                manageChildBorders(holder.rootView, parentPosition, childPosition);
                break;
            }
            case ChildItem.TYPE_TIME_PICKER_CHILD: {
                TimePickerChildItem timePickerChildItem = (TimePickerChildItem) getChildItem(parentPosition, childPosition);
                TimePickerChildViewHolder holder = (TimePickerChildViewHolder) childItemViewHolder;
                if (timePickerChildItem.getHour() != null && timePickerChildItem.getMinute() != null){
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, timePickerChildItem.getHour());
                    calendar.set(Calendar.MINUTE, timePickerChildItem.getMinute());
                    Date date = calendar.getTime();
                    holder.timePicker.setDefaultDate(date);
                }
                manageChildBorders(holder.rootView, parentPosition, childPosition);
                break;
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void setExpandCollapseListener(@Nullable ExpandCollapseListener expandCollapseListener) {
        super.setExpandCollapseListener(expandCollapseListener);
    }

    @Override
    protected void parentExpandedFromViewHolder(int flatParentPosition) {
        super.parentExpandedFromViewHolder(flatParentPosition);
      // notifyParentChanged(computeParentPosition(flatParentPosition));
      //  Log.e("p",""+computeParentPosition(flatParentPosition));
    }

    @Override
    protected void parentCollapsedFromViewHolder(int flatParentPosition) {
        super.parentCollapsedFromViewHolder(flatParentPosition);
       // notifyParentChanged(computeParentPosition(flatParentPosition));
      //  Log.e("p",""+computeParentPosition(flatParentPosition));
    }

    private ChildItem getChildItem(int parentPosition, int childPosition){
        return itemList.get(parentPosition).getChildList().get(childPosition);
    }
    private int computeParentPosition(int flatParentPosition){
        int i = 0;
        int parentPosition = 0;
        while (i<flatParentPosition-2){
            if (itemList.get(i).getType() == SettingsItem.TYPE_EXPANDABLE_SELECTABLE_ITEM){
                ExpandableSelectableItem item = (ExpandableSelectableItem) itemList.get(i);
                i += item.getChildList().size();
                parentPosition++;
            }else {
                i++;
            }
        }
        return parentPosition;
    }

    private int getNearestNonExpandableItem(int adapterPosition){
        if (adapterPosition == 0){
            return 0;
        }
        else {
            for (int i = adapterPosition; i >= 0; --i) {
                if (itemList.get(i).getType() != SettingsItem.TYPE_EXPANDABLE_SELECTABLE_ITEM) {
                    return i + 1;
                }
            }
        }
        return -1;
    }

    private void deselectSelectableExpandableItems(int startPosition){
            for (int i = startPosition; i < itemList.size(); ++i) {
                if (itemList.get(i).getType() == SettingsItem.TYPE_EXPANDABLE_SELECTABLE_ITEM) {
                    collapseParent(i);
                    ExpandableSelectableItem expandableItem = (ExpandableSelectableItem) itemList.get(i);
                    expandableItem.setSelectedName("");
                    expandableItem.setSelected(false);
                    notifyParentChanged(i);
                }else {
                    return;
                }
            }
    }

    private void deselectTextChildItems(int parentPosition){
        ExpandableItem expandableItem = (ExpandableItem) itemList.get(parentPosition);
        List<ChildItem> childItems = expandableItem.getChildList();
        if (childItems != null) {
            for (int i = 0; i < childItems.size(); i++) {
                ChildItem childItem = childItems.get(i);
                if (childItem instanceof TextChildItem){
                    TextChildItem textChildItem = (TextChildItem) childItem;
                    textChildItem.setSelected(false);
                    notifyChildChanged(parentPosition, i);
                }
            }
        }
    }

    private String getSelectedChildName(int parentPosition){
        List<ChildItem> list = itemList.get(parentPosition).getChildList();
        if (list != null){
            for (int i = 0; i < list.size(); i++){
                ChildItem childItem = list.get(i);
                if (childItem instanceof TextChildItem){
                    TextChildItem textChildItem = (TextChildItem)childItem;
                    if (textChildItem.isSelected()){
                        if (itemList.get(parentPosition) instanceof ExpandableItem){
                            ExpandableItem expandableItem = (ExpandableItem) itemList.get(parentPosition);
                            if (expandableItem.getSelectedNameList().get(i) != null){
                                return expandableItem.getSelectedNameList().get(i);
                            }else {
                                return textChildItem.getText();
                            }
                        }
                    }
                }else {
                    return null;
                }
            }
        }
        return null;
    }
    private void manageParentBorders(ViewGroup viewGroup, int position){
        LinearLayout topBorder = (LinearLayout) viewGroup.findViewById(R.id.top_border);
        LinearLayout bottomBorder = (LinearLayout) viewGroup.findViewById(R.id.bottom_border);
        if (position - 1 >= 0) {
            if (itemList.get(position - 1).getType() == SettingsItem.TYPE_HEADER) {
                show(topBorder);
                setMargin(topBorder, null);
                if (itemList.size()-1 >= position+1) {
                    if (itemList.get(position + 1).getType() == SettingsItem.TYPE_HEADER || itemList.get(position + 1).getType() == SettingsItem.TYPE_DESCRIPTION) {
                        setMargin(bottomBorder, null);
                    } else {
                        setMargin(bottomBorder, 16);
                    }
                }
                else {
                    setMargin(bottomBorder, null);
                }
            } else {
                hide(topBorder);
                if (itemList.size()-1 >= position+1) {
                    if (itemList.get(position + 1).getType() == SettingsItem.TYPE_HEADER) {
                        setMargin(bottomBorder, null);
                    } else {
                        setMargin(bottomBorder, 16);
                    }
                }else {
                    setMargin(bottomBorder, null);
                }
            }
        }else {
            if (itemList.size()-1 >= position+1) {
                if (itemList.get(position + 1).getType() == SettingsItem.TYPE_HEADER) {
                    setMargin(bottomBorder, null);
                } else {
                    setMargin(bottomBorder, 16);
                }
            }
        }
    }

    private String getSelectedContext(int parentPosition, int childPosition){
        ChildItem childItem = itemList.get(parentPosition).getChildList().get(childPosition);
        switch (childItem.getType()){
            case TYPE_TEXT_CHILD:{
                return ((TextChildItem)childItem).getText();
            }
            case TYPE_TIME_PICKER_CHILD:{
                TimePickerChildItem timePickerChildItem = (TimePickerChildItem)childItem;
                if (timePickerChildItem.getHour() != null && timePickerChildItem.getMinute() != null) {
                    return timePickerChildItem.getHour() + ":" + timePickerChildItem.getMinute();
                }else return "";
            }
            default:
                return "";
        }
    }

    private void manageChildBorders(ViewGroup viewGroup, int parentPosition, int childPosition){
        LinearLayout topBorder = (LinearLayout) viewGroup.findViewById(R.id.top_border);
        LinearLayout bottomBorder = (LinearLayout) viewGroup.findViewById(R.id.bottom_border);
        SettingsItem parentItem = itemList.get(parentPosition);
        List<ChildItem>childItems = parentItem.getChildList();
        if (childItems.size() == 1){
            hide(topBorder);
            setMargin(bottomBorder, null);
        }else {
            if (childPosition == 0){
                hide(topBorder);
                setMargin(bottomBorder, 53);
            }
            else if (childPosition == childItems.size()-1){
                hide(topBorder);
                if (parentPosition+1 <= itemList.size()-1) {
                    if (itemList.get(parentPosition+1).getType() == SettingsItem.TYPE_HEADER) {
                        setMargin(bottomBorder, null);
                    } else {
                        setMargin(bottomBorder, 16);
                    }
                }else {
                    hide(topBorder);
                    setMargin(bottomBorder, null);
                }
            }
            else {
                hide(topBorder);
                setMargin(bottomBorder, 53);
            }
        }
    }


    private void setMargin(ViewGroup viewGroup, @Nullable Integer marginDp){
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) viewGroup.getLayoutParams();
        if (marginDp != null) {
            ((ViewGroup.MarginLayoutParams) params).leftMargin =
                    (int) Utility.convertDpToPixel(marginDp, context);
        }else {
            ((ViewGroup.MarginLayoutParams) params).leftMargin = 0;
        }
        viewGroup.requestLayout();
    }

    public void show(ViewGroup viewGroup){
        viewGroup.setVisibility(View.VISIBLE);
    }

    public void hide(ViewGroup viewGroup){
        viewGroup.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
       return itemList.get(parentPosition).getChildList().get(childPosition).getType();
    }

    @Override
    public int getParentViewType(int parentPosition) {
        return itemList.get(parentPosition).getType();
    }

    @Override
    public boolean isParentViewType(int viewType) {
        return viewType != TYPE_TWO_STATE_CHILD && viewType != TYPE_TEXT_CHILD
                && viewType != TYPE_TIME_PICKER_CHILD;
    }

    public void setOnParentItemClickListener(ParentItemClickListener parentItemClickListener){
        this.parentItemClickListener = parentItemClickListener;
    }

    public void setOnChildItemClickListener(ChildItemClickListener childItemClickListener){
        this.childItemClickListener = childItemClickListener;
    }

    public interface ParentItemClickListener {
        void onParentItemClicked(View v, int parentPosition);
    }


    public interface ChildItemClickListener {
        void onChildItemClicked(View v, int parentPosition, int childPosition);
    }

}
