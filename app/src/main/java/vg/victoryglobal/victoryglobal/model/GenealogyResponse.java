/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/30/17 11:19 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/30/17 11:19 AM
 */

package vg.victoryglobal.victoryglobal.model;

import java.util.ArrayList;
import java.util.HashMap;

public class GenealogyResponse {

    public Integer status;
    public ArrayList<GenealogyStructurePerLevel> genealogy;
    public TotalDownlines total_downlines;

    public Sponsor sponsor;
    public Upline upline;
    public Account account;
    public Profile profile;

    public HashMap<String, String> mlm_ranks = new HashMap<>();

    public HashMap<String, String>pickup_centers = new HashMap<>();

}
