/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/19/17 9:57 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 10/29/17 7:03 PM
 */

package vg.victoryglobal.victoryglobal.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.utils.PersistentCookieStore;

public class AccountLoginRequest {

    private AccountLogin accountLogin  = new AccountLogin();

    private AuthLogin authLogin;

    private CookieStore cookieStore;

    private ArrayList<MlmResponseError> mlmResponseErrors = new ArrayList<>();

    private boolean success = false;

    private Context accountContext;

    private static final AccountLoginRequest ourInstance = new AccountLoginRequest();

    public static AccountLoginRequest getInstance() {
        return ourInstance;
    }

    private AccountLoginRequest() {
    }

    public Context getAccountContext() {
        return accountContext;
    }

    public void setAccountContext(Context accountContext) {
        accountContext = accountContext;
    }

    public boolean hasAuthLogin(){

        AuthLogin auth_login = getAuthLogin();
        return auth_login != null;

    }


    //setter and getter
    public AccountLogin getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(AccountLogin accountLogin) {
        this.accountLogin = accountLogin;
    }

    public AuthLogin getAuthLogin() {
        return authLogin;
    }

    public void setAuthLogin(AuthLogin authLogin) {
        this.authLogin = authLogin;
    }

    public void setMlmResponseErrors(ArrayList<MlmResponseError> mlmResponseErrors) {
        this.mlmResponseErrors = mlmResponseErrors;
    }

    public ArrayList<MlmResponseError> getMlmResponseErrors() {
        return mlmResponseErrors;
    }

    public synchronized void reset() {
        setAccountLogin(new AccountLogin());
    }
    public synchronized void resetErrorCodes() {
        mlmResponseErrors.clear();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void getAccountSession() {
        accountLogin.setSession(getPrefs().getString("session", ""));
        accountLogin.setAuthToken(getPrefs().getString("auth_token", ""));
        accountLogin.setStatus(getPrefs().getBoolean("status", false));
        accountLogin.setMlmMemberId(getPrefs().getInt("mlm_member_id", 0));
    }

    public void saveAccountSession(String response_data) {
        Gson gson = new Gson();

        setAuthLogin(gson.fromJson(response_data, AuthLogin.class));

        getAccountLogin().setMlmMemberId(authLogin.getAccount().id);
        getAccountLogin().setSession(authLogin.getSession());
        getAccountLogin().setAuthToken(authLogin.getAuthToken());

        if(authLogin.getStatus() == 200){
            getAccountLogin().setStatus(true);
        }else{
            getAccountLogin().setStatus(false);
        }

        // save to SharedPreferences
        SharedPreferences.Editor editor = getPrefs().edit();
        if(authLogin.getStatus() == 200) {
            editor.putBoolean("status", true);
        }else{
            editor.putBoolean("status", false);
        }
        editor.putString("auth_token", authLogin.getAuthToken());
        editor.putString("session", authLogin.getSession());
        editor.putInt("mlm_member_id", authLogin.getAccount().id);
        editor.apply();
    }

    private SharedPreferences getPrefs() {
        return accountContext.getSharedPreferences(accountContext.getString(R.string.user_authentication), Context.MODE_PRIVATE);
    }
}
