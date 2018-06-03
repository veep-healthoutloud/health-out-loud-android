package com.veephealthoutloud.healthoutloud;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 *  Singleton class
 *  Used to ensure volley connection lasts the lifetime of app
 *  Sets up RequestQueue
 */

public class VolleyApplication extends Application {

    /**
     * Log or request TAG
     */
    private static final String TAG = "Healthoutloud";

    /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;

    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static VolleyApplication mNetworkManagerInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();

        // initialize singleton
        mNetworkManagerInstance = this;
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized VolleyApplication getInstance() {
        return mNetworkManagerInstance;
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getmRequestQueue(){
        //lazy initialize the mRequestQueue
        if(mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     */
    public <T> void addToRequestQueue(Request <T> req, String tag){
        // set default tag if one is not specified
        req.setTag(TextUtils.isEmpty(tag) ? TAG: tag);

        // add request to current queue
        getmRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     */
    public <T> void addToRquestQueue(Request <T> req){
        // use default tag
        req.setTag(TAG);

        // add request to current queue
        getmRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.*
     */
    public void cancelPendingRequests(Object tag){
        if(mRequestQueue != null) mRequestQueue.cancelAll(tag);
    }

}
