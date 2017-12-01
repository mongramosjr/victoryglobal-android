/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/30/17 11:19 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/30/17 11:19 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class GenealogyResponse {

    public Integer status;
    public ArrayList<GenealogyStructurePerLevel> genealogy;
    public TotalDownlines total_downlines;

    public Sponsor sponsor;
    public Upline upline;
    public Account account;
    public Profile profile;

}
