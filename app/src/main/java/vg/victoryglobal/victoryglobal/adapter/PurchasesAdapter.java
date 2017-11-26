/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/23/17 1:35 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/23/17 1:35 PM
 */

package vg.victoryglobal.victoryglobal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.Purchase;
import vg.victoryglobal.victoryglobal.utils.DateTimeFormat;

public class PurchasesAdapter extends RecyclerView.Adapter<PurchasesAdapter.PurchasesViewHolder> {

    private final Context mContext;
    private ArrayList<Purchase> purchases = new ArrayList<>();

    private final PurchasesAdapterListener listener;

    public PurchasesAdapter(Context context, ArrayList<Purchase> purchases, PurchasesAdapterListener listener) {
        this.mContext = context;
        this.purchases = purchases;
        this.listener = listener;
    }

    @Override
    public PurchasesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PurchasesViewHolder holder, int position) {

        DateTimeFormat dateTimeFormat = new DateTimeFormat();
        Purchase purchase = purchases.get(position);

        holder.grandTotal.setText(String.valueOf(purchase.getGrand_total()));
        holder.datePosted.setText(dateTimeFormat.createdTimeFormatted(purchase.getDate_posted()));
        holder.id.setText(String.valueOf(purchase.getId()));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PurchasesViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public final TextView grandTotal;
        public final TextView datePosted;
        public final TextView id;

        public PurchasesViewHolder(View itemView) {
            super(itemView);

            datePosted = itemView.findViewById(R.id.date_posted);
            id = itemView.findViewById(R.id.id);
            grandTotal = itemView.findViewById(R.id.grand_total);

            itemView.setOnLongClickListener(this);

        }

        @Override
        public boolean onLongClick(View view) {
            listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }

    public interface PurchasesAdapterListener {

        void onMessageRowClicked(int position);

        void onRowLongClicked(int position);
    }
}