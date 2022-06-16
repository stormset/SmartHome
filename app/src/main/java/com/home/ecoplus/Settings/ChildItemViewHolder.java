package com.home.ecoplus.Settings;

import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.home.ecoplus.R;

public class ChildItemViewHolder extends ChildViewHolder {

    public ViewGroup rootView;
    public TextView name;

    public ChildItemViewHolder(@NonNull View itemView) {
        super(itemView);
        rootView =  itemView.findViewById(R.id.root);
        name = (TextView) itemView.findViewById(R.id.name);
    }
}
