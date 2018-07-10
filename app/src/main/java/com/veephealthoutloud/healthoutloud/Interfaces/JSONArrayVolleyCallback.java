package com.veephealthoutloud.healthoutloud.Interfaces;

import com.android.volley.VolleyError;

import org.json.JSONArray;

/**
 * Created by Arjun Mittal on 6/10/2018.
 */

public interface JSONArrayVolleyCallback {

    void onSuccess(JSONArray result);

    void onError(VolleyError result);
}
