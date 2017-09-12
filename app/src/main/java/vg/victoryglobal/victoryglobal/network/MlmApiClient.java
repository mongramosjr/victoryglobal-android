/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/12/17 2:08 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/10/17 4:35 PM
 */

package vg.victoryglobal.victoryglobal.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MlmApiClient {

    public static final String BASE_URL = "https://api.victoryglobal.vg/";
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
