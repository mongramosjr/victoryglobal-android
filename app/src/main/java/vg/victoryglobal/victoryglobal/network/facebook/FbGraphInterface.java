/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/12/17 2:08 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/10/17 4:26 PM
 */

package vg.victoryglobal.victoryglobal.network.facebook;

import retrofit2.Call;
import retrofit2.http.GET;
import vg.victoryglobal.victoryglobal.model.facebook.FbGraph;

/*

 String pageId = "188501987866116";

String fbVictoryGlobalFeed =
            "https://graph.facebook.com/v2.1/188501987866116?fields=feed{id,story,from,with_tags,icon,created_time,message,full_picture}&access_token=344323752657014|ZPK9j2D6HQkksmJyUrCL1v6IrfY&limit=20";



 */


public interface FbGraphInterface {

    @GET("188501987866116?fields=feed{id,story,created_time,message,full_picture,icon,with_tags,from}&access_token=344323752657014|536afccd5b2195e51fb935d22629d306&limit=20")
    Call<FbGraph> getFeed();
}
