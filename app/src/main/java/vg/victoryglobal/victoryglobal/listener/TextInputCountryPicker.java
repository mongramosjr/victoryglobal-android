/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 12/18/17 10:28 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 12/18/17 8:46 AM
 */

package vg.victoryglobal.victoryglobal.listener;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.View.OnClickListener;

import com.hendraanggrian.countrydialog.Country;
import com.hendraanggrian.countrydialog.CountryDialog;


import vg.victoryglobal.victoryglobal.model.CountryCode;

public class TextInputCountryPicker implements OnClickListener, CountryDialog.OnSelectedListener {

    private final TextInputEditText editText;
    private final Context ctx;
    private final Activity mActivity;
    private final CountryCode countryCode;

    public TextInputCountryPicker(TextInputEditText editText, Context context, @Nullable Activity activity, @Nullable CountryCode country_code) {
        this.editText = editText;
        this.editText.setOnClickListener(this);
        this.ctx = context;
        this.mActivity = activity;
        this.countryCode = country_code;
    }


    @Override
    public void onClick(View view) {
        new CountryDialog.Builder(mActivity).title("Select country").onSelected(this).show();
    }


    @Override
    public void onSelected(@NonNull Country country) {
        editText.setText(country.getName(mActivity.getApplicationContext()));
        countryCode.setCode(country.isoCode);
        countryCode.setName(country.getName(mActivity.getApplicationContext()));
    }

}