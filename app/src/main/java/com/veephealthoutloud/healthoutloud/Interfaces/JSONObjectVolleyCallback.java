package com.veephealthoutloud.healthoutloud.Interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface JSONObjectVolleyCallback {

    void onSuccess(JSONObject result);

    void onError(VolleyError result);
}
