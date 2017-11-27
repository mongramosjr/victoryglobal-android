/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/23/17 3:30 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/23/17 3:30 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class PayoutReportsRequest {
    private static final PayoutReportsRequest onlyInstance = new PayoutReportsRequest();

    //Response from API request
    private PayoutReports payoutReports = null;

    //save to array list once fetched a successful reponse
    private ArrayList<PayoutReport> payoutReportsList = new ArrayList<>();

    private ArrayList<MlmResponseError> mlmResponseErrors = new ArrayList<>();

    private Paging pagingPrev = null;

    private Boolean success = false;

    private Integer itemInsertedPosition;
    private Integer itemInsertedCount;


    public static PayoutReportsRequest getInstance() {
        return onlyInstance;
    }

    private PayoutReportsRequest() {
    }

    //setter and getter


    public ArrayList<PayoutReport> getPayoutReportsList() {
        return payoutReportsList;
    }

    public ArrayList<MlmResponseError> getMlmResponseErrors() {
        return mlmResponseErrors;
    }

    public void setMlmResponseErrors(ArrayList<MlmResponseError> mlmResponseErrors) {
        this.mlmResponseErrors = mlmResponseErrors;
    }

    public void setPayoutReports(PayoutReports payoutReports) {
        this.payoutReports = payoutReports;
    }

    public PayoutReports getPayoutReports() {
        if(payoutReports == null){
            this.payoutReports = new PayoutReports();
        }
        return payoutReports;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setPagingPrev(Paging pagingPrev) {
        this.pagingPrev = pagingPrev;
    }

    public Paging getPagingPrev() {
        return pagingPrev;
    }

    public Integer getItemInsertedCount() {
        return itemInsertedCount;
    }

    public Integer getItemInsertedPosition() {
        return itemInsertedPosition;
    }

    //other method
    public synchronized Boolean savePayoutReports(String response_data) {
        //NOTE: When adding to the list, use add or addAll instead of deep copy
        Gson gson = new Gson();
        payoutReports = gson.fromJson(response_data, PayoutReports.class);
        if(payoutReports == null) {
            setSuccess(false);
            return false;
        }else{

            if(pagingPrev == null){
                pagingPrev = payoutReports.paging;
                itemInsertedPosition = 0;
                itemInsertedCount = payoutReports.payout_reports.size();
                payoutReportsList.addAll(payoutReports.payout_reports);
            }else if(!pagingPrev.equals(payoutReports.paging)){
                if(pagingPrev.count == payoutReports.paging.count
                        && pagingPrev.perPage ==  payoutReports.paging.perPage
                        && pagingPrev.direction == payoutReports.paging.direction
                        && pagingPrev.page !=  payoutReports.paging.page){

                    if(pagingPrev.page < payoutReports.paging.page) {
                        //append
                        itemInsertedPosition = payoutReportsList.size() - 1;
                        itemInsertedCount = payoutReports.payout_reports.size();
                        payoutReportsList.addAll(payoutReports.payout_reports);
                    }else{
                        //prepend
                        itemInsertedPosition = 0;
                        itemInsertedCount = payoutReports.payout_reports.size();
                        payoutReportsList.addAll(0, payoutReports.payout_reports);
                    }

                }else{
                    // change on direction, perpage, or has additional entry
                    itemInsertedPosition = 0;
                    itemInsertedCount = payoutReports.payout_reports.size();
                    payoutReportsList.clear();
                    payoutReportsList.addAll(payoutReports.payout_reports);
                }
            }else{
                //do nothing, exception
            }
        }
        setSuccess(true);
        return true;
    }
}
