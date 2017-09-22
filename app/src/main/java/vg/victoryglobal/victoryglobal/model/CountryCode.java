/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 9:19 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 8:50 PM
 */

package vg.victoryglobal.victoryglobal.model;

public class CountryCode {
    private String code;
    private String name;


    public CountryCode(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        if(obj instanceof CountryCode){
            CountryCode c = (CountryCode)obj;
            if(c.getName().equals(name) && c.getCode().equals(code)) return true;
        }

        return false;
    }

}
