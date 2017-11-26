/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/26/17 2:05 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/26/17 2:05 PM
 */

package vg.victoryglobal.victoryglobal.utils;

import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mong on 11/26/17.
 */

public class DateTimeFormat {

    public DateTimeFormat(){

    }

    public String createdTimeFormatted(String date_time, @Nullable String fromFormat, @Nullable String toFormat)
    {
        //2017-09-18T08:02:39+0000
        //yyyy-MM-dd'T'HH:mm:ssZ
        String myFormat = "yyyy-MM-dd'T'HH:mm:ssZ"; //In which you need put here
        SimpleDateFormat sdformat;
        if(fromFormat != null){
            sdformat = new SimpleDateFormat(fromFormat, Locale.US);
        }else {
            sdformat = new SimpleDateFormat(myFormat, Locale.US);
        }

        Calendar calendar = Calendar.getInstance();

        Date date = new Date();
        try {
            date = sdformat.parse(date_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return createdTimeFormatted(date, toFormat);
    }

    public String createdTimeFormatted(Date date_time){
        return createdTimeFormatted(date_time, null);
    }

    public String createdTimeFormatted(Date date_time, @Nullable String showFormat)
    {
        String display_date;

        //2017-09-18T08:02:39+0000
        //yyyy-MM-dd'T'HH:mm:ssZ
        SimpleDateFormat sdformat;

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date_time);

        String myFormat = "MMM dd, yyyy 'at' hh:mm a"; //In which you need put here
        if(showFormat != null) {
            sdformat = new SimpleDateFormat(showFormat, Locale.US);
        }else{
            sdformat = new SimpleDateFormat(myFormat, Locale.US);
        }


        display_date = sdformat.format(calendar.getTime());


        return display_date;


    }
}
