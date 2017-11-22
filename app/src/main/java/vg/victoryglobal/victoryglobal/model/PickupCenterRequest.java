/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 9:19 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 9:02 PM
 */

package vg.victoryglobal.victoryglobal.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;

import vg.victoryglobal.victoryglobal.R;

public class PickupCenterRequest {

    private ArrayList<PickupCenter> pickupCenters = new ArrayList<>();

    private ArrayList<String> pickupCentersStr = new ArrayList<>();

    //int id,
    HashMap<String , PickupCenter> pickupCentersHsh = new HashMap<>();

    private static final PickupCenterRequest ourInstance = new PickupCenterRequest();

    public static PickupCenterRequest getInstance() {
        return ourInstance;
    }

    private PickupCenterRequest() {
    }

    //setter and getter

    public void setPickupCenters(ArrayList<PickupCenter> pickupCenters) {
        this.pickupCenters = pickupCenters;
    }

    public ArrayList<PickupCenter> getPickupCenters() {
        return pickupCenters;
    }

    public ArrayList<String> getPickupCentersStr() {
        return pickupCentersStr;
    }

    public void setPickupCentersStr(ArrayList<String> pickupCentersStr) {
        this.pickupCentersStr = pickupCentersStr;
    }

    public HashMap<String, PickupCenter> getPickupCentersHsh() {
        return pickupCentersHsh;
    }

    public void setPickupCentersHsh(HashMap<String, PickupCenter> pickupCentersHsh) {
        this.pickupCentersHsh = pickupCentersHsh;
    }

    public synchronized void reset() {
        pickupCenters.clear();
        pickupCentersStr.clear();
        pickupCentersHsh.clear();
    }




    private void pickupCentersCallback(Context context, String response_data) {

        try {
            JSONObject object = (JSONObject) new JSONTokener(response_data).nextValue();
            int status = object.getInt("status");

            Log.e("PickupCenterRequest ", "Status: " + String.valueOf(status));

            if(status == 200 ){

                //TODO: use gson instead of JSONObject
                JSONArray pickup_centers = object.getJSONArray("pickup_centers");

                for (int i = 0; i < pickup_centers.length(); ++i) {
                    JSONObject pickup_center_json = pickup_centers.getJSONObject(i);

                    PickupCenter pickup_center =  new PickupCenter(
                            pickup_center_json.getString("id"),
                            pickup_center_json.getString("frontend_label"));

                    getPickupCentersStr().add(pickup_center_json.getString("frontend_label"));
                    getPickupCentersHsh().put(pickup_center_json.getString("frontend_label"),
                            pickup_center);
                    getPickupCenters().add(pickup_center);
                }
            }else if(status == 402 ){

                Toast.makeText(context, R.string.ui_exception, Toast.LENGTH_LONG).show();

            }else{

                Toast.makeText(context, R.string.ui_exception, Toast.LENGTH_LONG).show();

            }
        } catch (JSONException e) {
            //do nothing
            Log.e("PickupCenterRequest", "pickupCentersCallback: (4) " + e.getMessage());
            Toast.makeText(context, R.string.ui_exception, Toast.LENGTH_LONG).show();
        }


    }

    public void PickupCenters(final Context context) {

        if(!getPickupCenters().isEmpty()) {
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(context);

        String url = context.getString(R.string.api_url) + context.getString(R.string.api_pickupcenters);

        JSONObject post_data = new JSONObject();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST,
                        url,
                        post_data,
                        new com.android.volley.Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                pickupCentersCallback(context, response.toString());
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Do nothing
                                Log.e("PickupCenterRequest", "onErrorResponse: " + error.toString());
                                Toast.makeText(context, R.string.ui_unexpected_response, Toast.LENGTH_LONG).show();
                            }
                        }
                );

        queue.add(jsObjRequest);
    }
}
