/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/23/17 10:42 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/22/17 10:09 AM
 */

package vg.victoryglobal.victoryglobal.listener;

import android.app.Activity;
import android.app.DatePickerDialog;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class TextInputDatePicker implements OnClickListener, DatePickerDialog.OnDateSetListener {

    private final TextInputEditText editText;
    private final Calendar myCalendar;
    private final Context ctx;
    private final Activity mActivity;

    public TextInputDatePicker(TextInputEditText editText, Context context, @Nullable Activity activity){
        this.editText = editText;
        this.editText.setOnClickListener(this);
        this.ctx = context;
        this.mActivity = activity;
        myCalendar = Calendar.getInstance();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)     {
        // this.editText.setText();

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.US);
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        editText.setText(sdformat.format(myCalendar.getTime()));

    }

    @Override
    public void onClick(View view) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        DatePickerDialog dialog;
        if(mActivity != null) {
            dialog = new DatePickerDialog(mActivity, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }else{
            dialog = new DatePickerDialog(ctx, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        dialog.show();
    }
}