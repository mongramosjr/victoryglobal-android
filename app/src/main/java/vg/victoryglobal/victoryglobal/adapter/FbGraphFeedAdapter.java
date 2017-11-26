/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/10/17 1:54 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/10/17 1:54 PM
 */

package vg.victoryglobal.victoryglobal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.facebook.FbGraphFeed;
import vg.victoryglobal.victoryglobal.utils.DateTimeFormat;


import com.bumptech.glide.Glide;


public class FbGraphFeedAdapter extends RecyclerView.Adapter<FbGraphFeedAdapter.VictoryGlobalFeedViewHolder> {

    private final Context mContext;
    private ArrayList<FbGraphFeed> feeds = new ArrayList<>();
    private final FacebookGraphFeedAdapterListener listener;

    public class VictoryGlobalFeedViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public final TextView createdTime;
        public final TextView message;
        public final TextView from_or_story;
        public final TextView description;
        public final ImageView fullPicture;
        public final ImageView videoSource;

        public VictoryGlobalFeedViewHolder(View view) {
            super(view);

            createdTime = view.findViewById(R.id.created_time);
            message = view.findViewById(R.id.message);
            fullPicture = view.findViewById(R.id.full_picture);
            videoSource = view.findViewById(R.id.video_source);
            from_or_story = view.findViewById(R.id.from_or_story);
            description =  view.findViewById(R.id.description);

            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }

    public FbGraphFeedAdapter(Context mContext, ArrayList<FbGraphFeed> feeds,
                              FacebookGraphFeedAdapterListener listener) {
        this.mContext = mContext;
        this.feeds = feeds;
        this.listener = listener;
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

        DateTimeFormat dateTimeFormat = new DateTimeFormat();

        // displaying text view data
        applyFromOrStory(holder, feed);

        applyMessage(holder, feed);


        holder.createdTime.setText(dateTimeFormat.createdTimeFormatted(feed.getCreatedTime(), null, null));

        applyVideo(holder, feed);
        applyDescription(holder, feed);
        applyFullPicture(holder, feed);

    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }





    public interface FacebookGraphFeedAdapterListener {
        void onIconClicked(int position);

        void onIconImportantClicked(int position);

        void onMessageRowClicked(int position);

        void onRowLongClicked(int position);
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
            holder.fullPicture.setVisibility(View.INVISIBLE);
        }
    }

    private void applyFromOrStory(VictoryGlobalFeedViewHolder holder, FbGraphFeed feed) {
        if (!TextUtils.isEmpty(feed.getStory())) {
            holder.from_or_story.setText(feed.getStory());
        } else {
            holder.from_or_story.setText(feed.getFrom().getName());
        }
    }

    private void applyMessage(VictoryGlobalFeedViewHolder holder, FbGraphFeed feed) {
        if (!TextUtils.isEmpty(feed.getMessage())) {
            holder.message.setText(feed.getMessage());
            holder.message.setVisibility(View.VISIBLE);
        } else {
            holder.message.setVisibility(View.GONE);
        }
    }

    private void applyVideo(VictoryGlobalFeedViewHolder holder, FbGraphFeed feed) {
        if (feed.getType().equals("video")) {
            holder.videoSource.setVisibility(View.VISIBLE);
        } else {
            holder.videoSource.setVisibility(View.GONE);
        }
    }

    private void applyDescription(VictoryGlobalFeedViewHolder holder, FbGraphFeed feed) {
        if (!TextUtils.isEmpty(feed.getDescription())) {
            holder.description.setText(feed.getDescription());
            holder.description.setVisibility(View.VISIBLE);
        } else {
            holder.description.setVisibility(View.GONE);
        }
    }






}
