/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/22/17 7:19 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/22/17 7:19 PM
 */

package vg.victoryglobal.victoryglobal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.PayoutReport;

public class PayoutReportsAdapter extends RecyclerView.Adapter<PayoutReportsAdapter.PayoutReportsViewHolder> {

    private final Context mContext;
    private ArrayList<PayoutReport> payoutReports = new ArrayList<>();

    private final PayoutReportsAdapterListener listener;

    public PayoutReportsAdapter(Context context, ArrayList<PayoutReport> payout_reports,
                                PayoutReportsAdapterListener listener) {
        this.mContext = context;
        this.payoutReports = payout_reports;
        this.listener = listener;
    }

    @Override
    public PayoutReportsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payout_reports_row, parent, false);

        return new PayoutReportsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PayoutReportsViewHolder holder, int position) {
        PayoutReport payout_report = payoutReports.get(position);

        holder.created.setText(payout_report.createdTimeFormatted(payout_report.getCreated()));
        holder.dateEnd.setText(payout_report.createdTimeFormatted(payout_report.getDate_end()));
        holder.dateStart.setText(payout_report.createdTimeFormatted(payout_report.getDate_start()));
        holder.totalAmount.setText(String.valueOf(payout_report.getTotal_amount()));

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PayoutReportsViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public final TextView totalAmount;
        public final TextView dateStart;
        public final TextView dateEnd;
        public final TextView created;

        public PayoutReportsViewHolder(View itemView) {
            super(itemView);

            created = itemView.findViewById(R.id.created);
            dateStart = itemView.findViewById(R.id.date_start);
            dateEnd = itemView.findViewById(R.id.date_end);
            totalAmount = itemView.findViewById(R.id.total_amount);



            itemView.setOnLongClickListener(this);

        }

        @Override
        public boolean onLongClick(View view) {
            listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }





    public interface PayoutReportsAdapterListener {

        void onMessageRowClicked(int position);

        void onRowLongClicked(int position);
    }

}
