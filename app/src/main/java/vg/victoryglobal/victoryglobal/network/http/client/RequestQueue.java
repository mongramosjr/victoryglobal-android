/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 1/22/18 7:47 AM
 *
 * Copyright (c) 2018 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 1/22/18 7:47 AM
 */

package vg.victoryglobal.victoryglobal.network.http.client;

import android.content.Context;


import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RequestQueue{

    public static void NewJsonRequestQueue(Context context, Request request,
                                           final Response.JsonListener jsonListener,
                                           final Response.ErrorListener errorListener) {


        com.android.volley.RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST,
                        request.url,
                        request.jsonRequest,
                        new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                jsonListener.onResponse(response.toString());
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if(error.networkResponse!=null){
                                    errorListener.onErrorResponse(error.getMessage(), error.networkResponse.statusCode);
                                }else{
                                    errorListener.onErrorResponse(error.getMessage(), 404);
                                }

                            }
                        }
                );

        // 6 minutes
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 144,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        queue.add(jsObjRequest);
    }
}
