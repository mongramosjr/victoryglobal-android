/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/21/17 8:51 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/21/17 8:51 AM
 */

package vg.victoryglobal.victoryglobal.model;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class AuthLoginManager {


    /**
     * The singleton AuthLoginManager instance.
     */
    private static AuthLoginManager authLoginManager;

    /**
     * The registered named loggers; maps the name of a Logger to
     * a WeakReference to it.
     */
    private Map<String, WeakReference<AuthLoginRequest>> authLoginRequests;

    protected AuthLoginManager() {
        authLoginRequests = new HashMap<>();
    }

    /**
     * Returns the globally shared LogManager instance.
     */
    public static synchronized AuthLoginManager getAuthLoginManager() {
        if (authLoginManager == null) {
            authLoginManager = makeAuthLoginManager();
            initLogManager();
        }
        return authLoginManager;
    }

    private static AuthLoginManager makeAuthLoginManager() {
        return new AuthLoginManager();
    }

    private static void initLogManager() {
        AuthLoginManager manager = getAuthLoginManager();
        //AuthLoginRequest.root.setLevel(Level.INFO);
        manager.addAuthLoginRequest(AuthLoginRequest.root);
    }

    /**
     * Returns a AuthLoginRequest given its name.
     *
     * @param name the name of the AuthLoginRequest.
     *
     * @return a named AuthLoginRequest, or <code>null</code> if there is no
     *     AuthLoginRequest with that name.
     *
     * @throw java.lang.NullPointerException if <code>name</code>
     *     is <code>null</code>.
     */
    public synchronized AuthLoginRequest getAuthLoginRequest(String name) {
        WeakReference<AuthLoginRequest> ref;
        /* Throw a NullPointerException if name is null. */
        name.getClass();
        ref = authLoginRequests.get(name);
        if (ref != null) return ref.get();
        else return null;
    }

    public synchronized boolean addAuthLoginRequest(AuthLoginRequest authLoginRequest) {
        String name;
        WeakReference ref;

        /* This will throw a NullPointerException if logger is null,
        *
        * as required by the API specification.
        */
        name = authLoginRequest.getName();
        ref = authLoginRequests.get(name);
        if (ref != null) {
            if (ref.get() != null) return false;
            /* There has been a logger under this name in the past,
            * but it has been garbage collected.
            */
            authLoginRequests.remove(ref);
        }

        authLoginRequests.put(name, new WeakReference<AuthLoginRequest>(authLoginRequest));

        return true;
    }

    private static Class locateClass(String name) throws ClassNotFoundException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            return Class.forName(name, true, loader);
        } catch (ClassNotFoundException e) {
            loader = ClassLoader.getSystemClassLoader();
            return Class.forName(name, true, loader);
        }
    }
}
