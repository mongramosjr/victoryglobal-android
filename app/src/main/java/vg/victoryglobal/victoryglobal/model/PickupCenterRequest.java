/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 9:19 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 9:02 PM
 */

package vg.victoryglobal.victoryglobal.model;

import java.util.ArrayList;

public class PickupCenterRequest {

    private ArrayList<PickupCenter> pickupCenters = new ArrayList<>();

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

    public synchronized void reset() {
        pickupCenters.clear();
    }
}
