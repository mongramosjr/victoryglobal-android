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
import android.util.Log;

import com.google.gson.Gson;

import java.net.CookieStore;
import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;

public class AuthLoginRequest {


    /**
     * Singleton instance of authentication request
     */
    static final AuthLoginRequest root = new AuthLoginRequest("", null);

    private final String name;

    /**
     * The Context of the AuthLoginRequest
     */
    private final Context applicationContext;

    // request
    private AccountLogin accountLogin  = new AccountLogin();

    // response
    private AuthLogin authLogin = new AuthLogin();

    // cookie
    private CookieStore cookieStore;

    // response error
    private ArrayList<MlmResponseError> mlmResponseErrors = new ArrayList<>();

    // response status
    private Boolean success = false;

    /**
     * Constructs a AuthLoginRequest for a subsystem.  Most applications do not
     * need to create new AuthLoginRequest explicitly; instead, they should call
     * the static factory methods
     * {@link #getAuthLoginRequest(String, android.content.Context) getAuthLoginRequest}.
     *
     * @param context the context for the AuthLoginRequest
     */
    protected AuthLoginRequest(String name, Context context) {
        this.name = name;
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
    public static AuthLoginRequest getAuthLoginRequest(String name, Context context){

        AuthLoginManager alm = AuthLoginManager.getAuthLoginManager();
        AuthLoginRequest     result;

        if (name == null) {
            throw new NullPointerException();
        }

        synchronized (alm)
        {
            result = alm.getAuthLoginRequest(name);
            if (result == null) {
                boolean couldBeAdded;
                result = new AuthLoginRequest(name, context);
                couldBeAdded = alm.addAuthLoginRequest(result);
                if (!couldBeAdded) {
                    throw new IllegalStateException("cannot register new authloginrequest");
                }
            }else{
                Context existing_context = result.getApplicationContext();
                if(existing_context != null) {
                    if ((existing_context.equals(context)) && (context != null)) {
                        return result;
                    }
                }
                if(existing_context==null) {
                    if (!existing_context.equals(context)) {
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
        return result;
    }

    public static AuthLoginRequest getAuthLoginRequest(String name)
    {
        return getAuthLoginRequest(name, null);
    }

    //getter and setter

    /**
     * Returns the context of this authlogin.
     *
     * @return the context of this authlogin
     */
    public Context getApplicationContext() {
        /* Note that the context of a AuthLoginRequest cannot be changed during
        * its lifetime, so no synchronization is needed.
        */
        return applicationContext;
    }

    /**
     * Returns the name of this logger.
     *
     * @return the name of this logger, or <code>null</code> if
     *         the logger is anonymous.
     */
    public String getName()
    {
        /* Note that the name of a AuthLoginRequest cannot be changed during
        * its lifetime, so no synchronization is needed.
        */
        return name;
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


    public synchronized Boolean isSuccess() {
        return success;
    }

    public synchronized void setSuccess(Boolean success) {
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
            setSuccess(true);
        }else{
            accountLogin.setStatus(false);
            setSuccess(false);
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
