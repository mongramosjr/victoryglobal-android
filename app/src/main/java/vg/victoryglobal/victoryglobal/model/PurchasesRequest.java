/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/23/17 1:56 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/23/17 1:56 PM
 */

package vg.victoryglobal.victoryglobal.model;

import com.google.gson.Gson;

import java.util.ArrayList;

public class PurchasesRequest {
    private static final PurchasesRequest onlyInstance = new PurchasesRequest();

    //Response from API request
    private PurchasesResponse purchasesResponse = null;

    //save to array list once fetched a successful reponse
    private ArrayList<Purchase> purchaseList = new ArrayList<>();

    private ArrayList<MlmResponseError> mlmResponseErrors = new ArrayList<>();

    private Paging pagingPrev = null;

    private Boolean success = false;

    private Integer itemInsertedPosition;
    private Integer itemInsertedCount;

    public static PurchasesRequest getInstance() {
        return onlyInstance;
    }

    private PurchasesRequest() {
    }

    //setter and getter
    public ArrayList<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public ArrayList<MlmResponseError> getMlmResponseErrors() {
        return mlmResponseErrors;
    }

    public void setMlmResponseErrors(ArrayList<MlmResponseError> mlmResponseErrors) {
        this.mlmResponseErrors = mlmResponseErrors;
    }

    public void setPurchasesResponse(PurchasesResponse purchasesResponse) {
        this.purchasesResponse = purchasesResponse;
    }

    public PurchasesResponse getPurchasesResponse() {

        if(purchasesResponse == null){
            this.purchasesResponse = new PurchasesResponse();
        }
        return purchasesResponse;
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

    //other methods
    public synchronized Boolean savePurchases(String response_data) {
        //NOTE: When adding to the list, use add or addAll instead of deep copy
        Gson gson = new Gson();
        purchasesResponse = gson.fromJson(response_data, PurchasesResponse.class);

        if(purchaseList == null) {
            setSuccess(false);
            return false;
        }else{
            if(pagingPrev == null){
                pagingPrev = purchasesResponse.paging;
                itemInsertedPosition = 0;
                itemInsertedCount = purchasesResponse.getPurchases().size();
                purchaseList.addAll(purchasesResponse.getPurchases());
            }else if(!pagingPrev.equals(purchasesResponse.paging)){
                if(pagingPrev.count == purchasesResponse.paging.count
                        && pagingPrev.perPage ==  purchasesResponse.paging.perPage
                        && pagingPrev.direction == purchasesResponse.paging.direction
                        && pagingPrev.page !=  purchasesResponse.paging.page){

                    if(pagingPrev.page < purchasesResponse.paging.page) {
                        //append
                        itemInsertedPosition = purchaseList.size() - 1;
                        itemInsertedCount = purchasesResponse.getPurchases().size();
                        purchaseList.addAll(purchasesResponse.getPurchases());
                    }else{
                        //prepend
                        itemInsertedPosition = 0;
                        itemInsertedCount = purchasesResponse.getPurchases().size();
                        purchaseList.addAll(0, purchasesResponse.getPurchases());
                    }

                }else{
                    // change on direction, perpage, or has additional entry
                    itemInsertedPosition = 0;
                    itemInsertedCount = purchasesResponse.getPurchases().size();
                    purchaseList.clear();
                    purchaseList.addAll(purchasesResponse.getPurchases());
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
        purchasesResponse = null;
        purchaseList.clear();
    }
}
