/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 9:19 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 8:54 PM
 */

package vg.victoryglobal.victoryglobal.model;

public class MlmLocation {
    private String id;
    private String name;

    public MlmLocation(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //to display object as a string in spinner
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MlmLocation){
            MlmLocation c = (MlmLocation)obj;
            if(c.getName().equals(name) && c.getId()==id ) return true;
        }

        return false;
    }

}
