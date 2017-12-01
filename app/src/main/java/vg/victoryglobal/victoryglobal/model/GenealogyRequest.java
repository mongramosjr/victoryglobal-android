/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/30/17 5:49 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/30/17 5:49 PM
 */

package vg.victoryglobal.victoryglobal.model;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class GenealogyRequest {
    private static final GenealogyRequest onlyInstance = new GenealogyRequest();

    public static GenealogyRequest getInstance() {
        return onlyInstance;
    }

    private GenealogyResponse genealogyResponse;

    private HashMap<Integer, GenealogyResponse> genealogyList = new HashMap<>();

    private ArrayList<MlmResponseError> mlmResponseErrors = new ArrayList<>();

    private Boolean success = false;

    private GenealogyRequest() {
    }

    //setter and getter

    public void setMlmResponseErrors(ArrayList<MlmResponseError> mlmResponseErrors) {
        this.mlmResponseErrors = mlmResponseErrors;
    }
    public ArrayList<MlmResponseError> getMlmResponseErrors() {
        return mlmResponseErrors;
    }

    public void setGenealogyResponse(GenealogyResponse genealogyResponse) {
        this.genealogyResponse = genealogyResponse;
    }

    public GenealogyResponse getGenealogyResponse() {
        return genealogyResponse;
    }

    public void setGenealogyList(HashMap<Integer, GenealogyResponse> genealogyList) {
        this.genealogyList = genealogyList;
    }

    public HashMap<Integer, GenealogyResponse> getGenealogyList() {
        return genealogyList;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    //other method
    public synchronized Boolean saveGenealogy(String response_data) {
        Gson gson = new Gson();
        genealogyResponse = gson.fromJson(response_data, GenealogyResponse.class);
        if(genealogyResponse == null) {
            setSuccess(false);
            return false;
        }

        Integer distributor_id = null;
        if(genealogyResponse.account != null) {
            if(genealogyResponse.account.id != null) {
                distributor_id = genealogyResponse.account.id;
            }
        }

        if(distributor_id != null) {
            if(!genealogyList.containsKey(distributor_id)){
                genealogyList.put(distributor_id, genealogyResponse);
            }

        }
        setSuccess(true);
        return true;
    }
}
