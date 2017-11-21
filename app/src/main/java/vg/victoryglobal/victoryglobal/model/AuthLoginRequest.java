/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/21/17 6:47 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/21/17 6:46 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.net.CookieStore;
import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;

public class AuthLoginRequest {


    /**
     * Singleton instance of authentication request
     */
    static final AuthLoginRequest rootInstance = new AuthLoginRequest(null);

    /**
     * The Context of the AuthLoginRequest
     */
    private final Context applicationContext;


    // request
    private AccountLogin accountLogin  = new AccountLogin();

    // response
    private AuthLogin authLogin;

    // cookie
    private CookieStore cookieStore;

    // response error
    private ArrayList<MlmResponseError> mlmResponseErrors = new ArrayList<>();

    // response status
    private boolean success = false;

    /**
     * Constructs a AuthLoginRequest for a subsystem.  Most applications do not
     * need to create new AuthLoginRequest explicitly; instead, they should call
     * the static factory methods
     * {@link #getAuthLoginRequest(android.content.Context) getAuthLoginRequest}.
     *
     * @param context the context for the AuthLoginRequest
     */
    protected AuthLoginRequest(@Nullable Context context) {
        this.applicationContext = context;
    }

    /**
     * Finds a registered authenticated login for a subsystem, or creates one in case
     * no authenticated login has been registered yet.
     *
     * @param context the Context for the logger
     *
     *
     * @throws NullPointerException if <code>context</code> is <code>null</code>.
     */
    public static AuthLoginRequest getAuthLoginRequest(Context context){

        if (context == null) {
            throw new NullPointerException();
        }

        AuthLoginRequest result;
        result = new AuthLoginRequest(context);
        return result;
    }


    //getter and setter

    /**
     * Returns the context of this authlogin.
     *
     * @return the context of this authlogin
     */
    public Context getApplicationContext() {
        /* Note that the name of a logger cannot be changed during
        * its lifetime, so no synchronization is needed.
        */
        return applicationContext;
    }

    public synchronized AccountLogin getAccountLogin() {
        return accountLogin;
    }

    public synchronized void setAccountLogin(AccountLogin accountLogin) {
        this.accountLogin = accountLogin;
    }


    public synchronized AuthLogin getAuthLogin() {
        return authLogin;
    }

    public synchronized void setAuthLogin(AuthLogin authLogin) {
        this.authLogin = authLogin;
    }


    public synchronized void setMlmResponseErrors(ArrayList<MlmResponseError> mlmResponseErrors) {
        this.mlmResponseErrors = mlmResponseErrors;
    }

    public synchronized ArrayList<MlmResponseError> getMlmResponseErrors() {
        return mlmResponseErrors;
    }


    public synchronized boolean isSuccess() {
        return success;
    }

    public synchronized void setSuccess(boolean success) {
        this.success = success;
    }


    // other methods

    public synchronized void reset() {
        setAccountLogin(new AccountLogin());
        setAuthLogin(new AuthLogin());
    }
    public synchronized void resetErrorCodes() {
        mlmResponseErrors.clear();
    }


    public synchronized boolean hasAuthLogin() {
        return authLogin != null;
    }




    public synchronized void getAuthSession() {

        // get from SharedPreferences
        accountLogin.setSession(getPrefs().getString("session", ""));
        accountLogin.setAuthToken(getPrefs().getString("auth_token", ""));
        accountLogin.setStatus(getPrefs().getBoolean("status", false));
        accountLogin.setMlmMemberId(getPrefs().getInt("mlm_member_id", 0));
    }

    public synchronized void saveAuthSession(String response_data) {
        Gson gson = new Gson();

        authLogin = gson.fromJson(response_data, AuthLogin.class);

        accountLogin.setMlmMemberId(authLogin.getAccount().id);
        accountLogin.setSession(authLogin.getSession());
        accountLogin.setAuthToken(authLogin.getAuthToken());

        if(authLogin.getStatus() == 200){
            accountLogin.setStatus(true);
        }else{
            accountLogin.setStatus(false);
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

    private synchronized SharedPreferences getPrefs() {
        return applicationContext.getSharedPreferences(applicationContext.getString(R.string.user_authentication), Context.MODE_PRIVATE);
    }
}
