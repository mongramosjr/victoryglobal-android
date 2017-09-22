/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/12/17 2:08 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/10/17 4:38 PM
 */

package vg.victoryglobal.victoryglobal.network;

import retrofit2.Call;
import retrofit2.http.POST;
import vg.victoryglobal.victoryglobal.model.facebook.FbGraph;

public interface MlmApiInterface {


    @POST("activation-code/registration/checkfirst/.json")
    Call<FbGraph> activationCodeCheckFirst();

    @POST("activation-code/registration/.json")
    Call<FbGraph> activationCode();
}
