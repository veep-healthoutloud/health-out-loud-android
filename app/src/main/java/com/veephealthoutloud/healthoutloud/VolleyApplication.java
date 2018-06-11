package com.veephealthoutloud.healthoutloud;

import android.app.Application;
import android.content.Context;

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
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;

    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static VolleyApplication mInstance;

    /**
     * Context of singleton class
     */
    private static Context mContext;

    /**
     * @param context
     * Instantiating VolleyApplication
     */
    private VolleyApplication(Context context){
        mContext = context;
        mRequestQueue = getMRequestQueue();
    }

    /**
     * @param context
     * @return mInstance
     */
    public static synchronized VolleyApplication getInstance(Context context){
        if(mInstance == null) mInstance = new VolleyApplication(context);
        return mInstance;
    }

    /**
     * @return mRequestQueue
     * the queue will be created if it is null
     */
    public RequestQueue getMRequestQueue(){
        if(mRequestQueue == null) mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        return mRequestQueue;
    }

    /**
     * @param req
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     */
    public <T> void addToRequestQueue(Request <T> req){
        getMRequestQueue().add(req);
    }

    /**
     * @param tag
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.*
     */
    public void cancelPendingRequests(Object tag){
        if(mRequestQueue != null) mRequestQueue.cancelAll(tag);
    }

}
