package com.veephealthoutloud.healthoutloud.Classes;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.veephealthoutloud.healthoutloud.Interfaces.VolleyCallback;
import com.veephealthoutloud.healthoutloud.VolleyApplication;

import org.json.JSONArray;

/**
 * Created by Arjun Mittal on 6/10/2018.
 */

public final class VolleyRequestsUtils {

    private VolleyRequestsUtils(){
    }

    public static void getAllPosts(Context context, final VolleyCallback callback) {

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
                        // TODO: Error Handler
                    }
                });

        VolleyApplication.getInstance(context).addToRequestQueue(allPosts);

    }
}
