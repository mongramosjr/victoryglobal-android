/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/23/17 3:30 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/23/17 3:30 AM
 */

package vg.victoryglobal.victoryglobal.model;

import com.google.gson.Gson;

import java.util.ArrayList;

public class PayoutReportsRequest {
    private static final PayoutReportsRequest onlyInstance = new PayoutReportsRequest();

    //Response from API request
    private PayoutReportsResponse payoutReportsResponse = null;

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

    public void setPayoutReportsResponse(PayoutReportsResponse payoutReportsResponse) {
        this.payoutReportsResponse = payoutReportsResponse;
    }

    public PayoutReportsResponse getPayoutReportsResponse() {
        if(payoutReportsResponse == null){
            this.payoutReportsResponse = new PayoutReportsResponse();
        }
        return payoutReportsResponse;
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
        payoutReportsResponse = gson.fromJson(response_data, PayoutReportsResponse.class);
        if(payoutReportsResponse == null) {
            setSuccess(false);
            return false;
        }else{

            if(pagingPrev == null){
                pagingPrev = payoutReportsResponse.paging;
                itemInsertedPosition = 0;
                itemInsertedCount = payoutReportsResponse.payout_reports.size();
                payoutReportsList.addAll(payoutReportsResponse.payout_reports);
            }else if(!pagingPrev.equals(payoutReportsResponse.paging)){
                if(pagingPrev.count == payoutReportsResponse.paging.count
                        && pagingPrev.perPage ==  payoutReportsResponse.paging.perPage
                        && pagingPrev.direction == payoutReportsResponse.paging.direction
                        && pagingPrev.page !=  payoutReportsResponse.paging.page){

                    if(pagingPrev.page < payoutReportsResponse.paging.page) {
                        //append
                        itemInsertedPosition = payoutReportsList.size() - 1;
                        itemInsertedCount = payoutReportsResponse.payout_reports.size();
                        payoutReportsList.addAll(payoutReportsResponse.payout_reports);
                    }else{
                        //prepend
                        itemInsertedPosition = 0;
                        itemInsertedCount = payoutReportsResponse.payout_reports.size();
                        payoutReportsList.addAll(0, payoutReportsResponse.payout_reports);
                    }

                }else{
                    // change on direction, perpage, or has additional entry
                    itemInsertedPosition = 0;
                    itemInsertedCount = payoutReportsResponse.payout_reports.size();
                    payoutReportsList.clear();
                    payoutReportsList.addAll(payoutReportsResponse.payout_reports);
                }
            }else{
                //do nothing, exception
            }
        }
        setSuccess(true);
        return true;
    }

    public void reset()
    {
        payoutReportsResponse = null;
        payoutReportsList.clear();
    }
}
