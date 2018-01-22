/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 1/22/18 7:28 AM
 *
 * Copyright (c) 2018 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 1/22/18 7:28 AM
 */

package vg.victoryglobal.victoryglobal.network.http.client;

import com.android.volley.*;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vg.victoryglobal.victoryglobal.model.MlmAccount;

public class Request extends Message {

    public String url;

    public JSONObject jsonRequest = new JSONObject();

    public String stringRequest;

    public interface JSONObjectRequest {
        void reset();
    }

    public interface StringRequest {
        void reset();
    }



    public void setAuthorization(String mlm_member_id, String session, String auth_token){
        try {
            jsonRequest.put("session", session);
            jsonRequest.put("auth_token", auth_token);
            jsonRequest.put("mlm_member_id", mlm_member_id);
        }catch(JSONException ex) {

        }
    }

    public void addParameter(String name, String value){
        try {
            jsonRequest.put(name, value);
        }catch(JSONException ex) {

        }
    }

    public void addParameters(HashMap<String , String> parameters){
        try {
            for (HashMap.Entry<String, String> entry : parameters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                jsonRequest.put(key, value);
            }

        }catch(JSONException ex) {

        }
    }

}
