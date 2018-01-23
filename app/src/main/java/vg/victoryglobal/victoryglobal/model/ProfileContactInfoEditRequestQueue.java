/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 1/23/18 10:41 AM
 *
 * Copyright (c) 2018 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 1/23/18 10:41 AM
 */

package vg.victoryglobal.victoryglobal.model;

import com.google.gson.Gson;

import vg.victoryglobal.victoryglobal.network.http.client.Request;
import vg.victoryglobal.victoryglobal.network.http.client.Response;

public class ProfileContactInfoEditRequestQueue implements Response.ActionListener {
    private static final ProfileContactInfoEditRequestQueue ourInstance = new ProfileContactInfoEditRequestQueue();

    public static ProfileContactInfoEditRequestQueue getInstance() {
        return ourInstance;
    }

    private Response response;
    private Request request;

    private ProfileContactInfoEditRequestQueue() {
    }

    @Override
    public void onSuccess(String responseData) {
        Gson gson = new Gson();
        response = gson.fromJson(responseData, Response.class);
        if(response == null) {

        }else{

        }
    }

    public Request getRequest() {
        if(request == null){
            this.request = new Request();
        }
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        if(response == null){
            this.response = new Response();
        }
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
