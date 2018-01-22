/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 1/22/18 7:24 AM
 *
 * Copyright (c) 2018 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 1/22/18 7:24 AM
 */

package vg.victoryglobal.victoryglobal.network.http.client;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import vg.victoryglobal.victoryglobal.R;

public class Response extends Message {

    public String message;

    /** Callback interface for responses. */
    public interface JsonListener {
        void onResponse(String responseData);
    }

    public interface ErrorListener{
        void onErrorResponse(String responseData, Integer statusCode);
    }

    /** Callback interface for responses. */
    public interface JSONObjectListener {
        void onResponse(JSONObject responseData);
    }

    public interface ActionListener {
        void onSuccess(String responseData);
    }
}
