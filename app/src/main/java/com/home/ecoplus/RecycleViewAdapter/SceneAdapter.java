package com.home.ecoplus.RecycleViewAdapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.home.ecoplus.MainActivity;
import com.home.ecoplus.R;

import java.util.List;

import eightbitlab.com.blurview.ClipView;


public class SceneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ListItem> mList;
    private static SceneItemClickListener sceneItemClickListener;

    public static class SceneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public ClipView clipView;
        LinearLayout sceneNameHolder;
        ImageView sceneIcon;
        TextView sceneName;

        SceneViewHolder(View view) {
            super(view);
            clipView = (ClipView) view.findViewById(R.id.blurLayout);
            sceneNameHolder = view.findViewById(R.id.scene_name_holde);
            sceneName = (TextView) view.findViewById(R.id.scene_name);
            sceneIcon = (ImageView) view.findViewById(R.id.scene_icon);
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
            if (sceneItemClickListener != null) {
                sceneItemClickListener.onSceneItemClicked(view, this.getAdapterPosition());
            }
        }
    }

    public SceneAdapter(Context mContext, List<ListItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
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
            case ListItem.TYPE_SCENE_ITEM: {
                View itemView = inflater.inflate(R.layout.scene_item, parent, false);
                return new SceneViewHolder(itemView);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ListItem.TYPE_SCENE_ITEM: {
                SceneItem sceneItem = (SceneItem) mList.get(position);
                SceneViewHolder holder = (SceneViewHolder) viewHolder;
                holder.sceneName.setText(sceneItem.getSceneName());
                boolean activated = sceneItem.getActivatedState();
                if (activated){
                    holder.clipView.setOverlayColor(Color.WHITE);
                    holder.sceneName.setTextColor(Color.BLACK);
                    holder.sceneIcon.setImageAlpha(255);
                }
                else{
                    holder.clipView.setOverlayColor(mContext.getResources().getColor(R.color.colorOverlay));
                    holder.sceneName.setTextColor(mContext.getResources().getColor(R.color.gray_off));
                    holder.sceneIcon.setImageAlpha(60);
                }

                FrameLayout blurredBackgroundImage = ((MainActivity)mContext)
                        .findViewById(R.id.blurred_background_image);

                if (blurredBackgroundImage != null) {
                    holder.clipView.setupWith(blurredBackgroundImage, 30);
                }

                switch (sceneItem.getIconID()){
                    case 0:
                        if (activated) holder.sceneIcon.setImageResource(R.drawable.ic_leaving_on);
                        else holder.sceneIcon.setImageResource(R.drawable.ic_leaving_off);
                        break;
                    case 1:
                        if (activated) holder.sceneIcon.setImageResource(R.drawable.ic_arriving_on);
                        else holder.sceneIcon.setImageResource(R.drawable.ic_arriving_off);
                        break;
                    case 2:
                        if (activated) holder.sceneIcon.setImageResource(R.drawable.ic_eco_on);
                        else holder.sceneIcon.setImageResource(R.drawable.ic_eco_off);
                        break;
                    case 3:
                        if (activated) holder.sceneIcon.setImageResource(R.drawable.ic_comfort_on);
                        else holder.sceneIcon.setImageResource(R.drawable.ic_comfort_off);
                        break;
                    case 4:
                        if (activated) holder.sceneIcon.setImageResource(R.drawable.ic_breakfast_on);
                        else holder.sceneIcon.setImageResource(R.drawable.ic_breakfast_on);
                        break;
                    case 5:
                        if (activated) holder.sceneIcon.setImageResource(R.drawable.ic_night_on);
                        else holder.sceneIcon.setImageResource(R.drawable.ic_night_off);
                        break;
                }
                break;
            }
            default:
                throw new IllegalStateException("unsupported item type");
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

    public void setOnSceneItemClickListener(SceneItemClickListener listener) {
        sceneItemClickListener = listener;
    }

    public interface SceneItemClickListener {
         void onSceneItemClicked(View v, int adapterPosition);
    }

}
