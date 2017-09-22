/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/10/17 1:54 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/10/17 1:32 PM
 */

package vg.victoryglobal.victoryglobal.network.facebook;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class FbGraphClient {

    public static final String BASE_URL = "https://graph.facebook.com/v2.10/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}