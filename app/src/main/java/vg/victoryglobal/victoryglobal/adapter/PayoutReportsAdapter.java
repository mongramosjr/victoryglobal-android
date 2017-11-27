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
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.PayoutReport;
import vg.victoryglobal.victoryglobal.utils.DateTimeFormat;

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
        DateTimeFormat dateTimeFormat = new DateTimeFormat();

        DecimalFormat nf = new DecimalFormat("###,##0.00");
        String total_amount = nf.format(payout_report.getTotal_amount());

        holder.createdDay.setText(dateTimeFormat.createdTimeFormatted(payout_report.getCreated(),"dd"));
        holder.createdMonth.setText(dateTimeFormat.createdTimeFormatted(payout_report.getDate_end(), "MMM"));
        holder.dateEnd.setText(dateTimeFormat.createdTimeFormatted(payout_report.getDate_end(), "dd MMM YYYY"));
        holder.dateStart.setText(dateTimeFormat.createdTimeFormatted(payout_report.getDate_start(), "dd MMM YYYY"));
        holder.totalAmount.setText(total_amount);
        holder.payoutTerm.setText(String.valueOf(payout_report.getPayout_term()));

    }

    @Override
    public int getItemCount() {
        return payoutReports.size();
    }

    public class PayoutReportsViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public final TextView totalAmount;
        public final TextView dateStart;
        public final TextView dateEnd;
        public final TextView createdDay;
        public final TextView createdMonth;
        public final TextView payoutTerm;

        public PayoutReportsViewHolder(View itemView) {
            super(itemView);

            createdDay = itemView.findViewById(R.id.created_day);
            createdMonth = itemView.findViewById(R.id.created_month);
            dateStart = itemView.findViewById(R.id.date_start);
            dateEnd = itemView.findViewById(R.id.date_end);
            totalAmount = itemView.findViewById(R.id.total_amount);
            payoutTerm = itemView.findViewById(R.id.payout_term);

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
