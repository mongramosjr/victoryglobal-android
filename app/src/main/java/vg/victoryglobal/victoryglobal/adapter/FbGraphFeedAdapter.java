/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/10/17 1:54 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/10/17 1:54 PM
 */

package vg.victoryglobal.victoryglobal.adapter;

import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.facebook.FbGraphFeed;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import static android.R.id.message;

/**
 * Created by mong on 9/10/17.
 */

public class FbGraphFeedAdapter extends RecyclerView.Adapter<FbGraphFeedAdapter.VictoryGlobalFeedViewHolder> {

    private Context mContext;
    private List<FbGraphFeed> feeds;
    private FacebookGraphFeedAdapterListener listener;

    public FbGraphFeedAdapter(Context mContext, List<FbGraphFeed> feeds,
                              FacebookGraphFeedAdapterListener listener) {
        this.mContext = mContext;
        this.feeds = feeds;
        this.listener = listener;
    }

    public class VictoryGlobalFeedViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView story, createdTime, message, from;
        public ImageView fullPicture, icon;
        public LinearLayout messageContainer;
        public RelativeLayout iconContainer, iconBack, iconFront;

        public VictoryGlobalFeedViewHolder(View view) {
            super(view);
            story = view.findViewById(R.id.story);
            createdTime = view.findViewById(R.id.created_time);
            message = view.findViewById(R.id.message);
            fullPicture = view.findViewById(R.id.full_picture);
            icon = view.findViewById(R.id.icon);
            from = view.findViewById(R.id.from);

            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onRowLongClicked(getAdapterPosition());
            //view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }




    public interface FacebookGraphFeedAdapterListener {
        void onIconClicked(int position);

        void onIconImportantClicked(int position);

        void onMessageRowClicked(int position);

        void onRowLongClicked(int position);
    }

    /* --------------- */
    @Override
    public VictoryGlobalFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facebook_graph_feed, parent, false);

        return new VictoryGlobalFeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VictoryGlobalFeedViewHolder holder, int position) {

        FbGraphFeed feed = feeds.get(position);

        // displaying text view data
        applyStory(holder, feed);

        holder.message.setText(feed.getMessage());
        holder.createdTime.setText(feed.getCreatedTime());

        // display profile image
        applyIcon(holder, feed);
        applyFullPicture(holder, feed);


    }

    @Override
    public int getItemCount() {
        return 0;
    }


    private void applyIcon(VictoryGlobalFeedViewHolder holder, FbGraphFeed feed) {
        if (!TextUtils.isEmpty(feed.getIcon())) {
            Glide.with(mContext).load(feed.getIcon())
                    .thumbnail(1f)
                    //.crossFade()
                    //.transform(new CircleTransform(mContext))
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.icon);
            holder.icon.setVisibility(View.VISIBLE);
        }else{
            holder.icon.setImageResource(R.drawable.bg_circle);
            //holder.icon.setColorFilter();
            holder.icon.setVisibility(View.VISIBLE);
        }
    }

    private void applyFullPicture(VictoryGlobalFeedViewHolder holder, FbGraphFeed feed) {
        if (!TextUtils.isEmpty(feed.getFullPicture())) {
            Glide.with(mContext).load(feed.getFullPicture())
                    .thumbnail(1f)
                 //   .crossFade()
                 //   .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.fullPicture);
            holder.fullPicture.setVisibility(View.VISIBLE);
        }else{
            holder.fullPicture.setVisibility(View.GONE);
        }
    }

    private void applyStory(VictoryGlobalFeedViewHolder holder, FbGraphFeed feed) {
        if (!TextUtils.isEmpty(feed.getStory())) {
            holder.from.setText(feed.getStory());
            holder.from.setVisibility(View.GONE);
            holder.story.setVisibility(View.VISIBLE);
        } else {
            holder.from.setText(feed.getFrom().getName());
            holder.from.setVisibility(View.VISIBLE);
            holder.story.setVisibility(View.GONE);
        }
    }






}
