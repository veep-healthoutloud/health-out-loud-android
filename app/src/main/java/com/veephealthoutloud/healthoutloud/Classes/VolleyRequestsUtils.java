package com.veephealthoutloud.healthoutloud.Classes;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.veephealthoutloud.healthoutloud.Interfaces.JSONArrayVolleyCallback;
import com.veephealthoutloud.healthoutloud.Interfaces.JSONObjectVolleyCallback;
import com.veephealthoutloud.healthoutloud.VolleyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Arjun Mittal on 6/10/2018.
 */

public final class VolleyRequestsUtils {

    private VolleyRequestsUtils(){
    }

    public static void getAllPosts(Context context, final JSONArrayVolleyCallback callback) {

        final String postsURL = "http://healthoutloud-api.herokuapp.com/posts";
        JsonArrayRequest allPosts = new JsonArrayRequest
                (Request.Method.GET,
                        postsURL,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                callback.onSuccess(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                });

        VolleyApplication.getInstance(context).addToRequestQueue(allPosts);

    }

    /**
     * Send a request to get the login for a certain user.
     * @param context the application context
     * @param email the email used to login
     * @param password the password used to login
     * @param callback the callback to happen when the request is processed
     */
    public static void login(Context context, String email, String password, final JSONObjectVolleyCallback callback) throws JSONException {

        final String loginURL = "http://healthoutloud-api.herokuapp.com/login";
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("email", email);
        jsonRequest.put("password", password);

        JsonObjectRequest allPosts = new JsonObjectRequest
                (Request.Method.POST,
                        loginURL,
                        jsonRequest,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                callback.onSuccess(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                });

        VolleyApplication.getInstance(context).addToRequestQueue(allPosts);
    }
}
