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
    static String baseURL = "http://10.0.2.2:3000/";

    private VolleyRequestsUtils(){

    }

    public static void getPostsByFeeling(String ACCESS_TOKEN, String feeling, Context context, final JSONArrayVolleyCallback callback) {
        System.out.println("IN VOLLEY, " + ACCESS_TOKEN);
        JSONObject jsonRequest = new JSONObject();
        //jsonRequest.put("token", ACCESS_TOKEN);

        final String postsURL = baseURL + "posts/feeling/" + feeling;
        JsonArrayRequest allPosts = new JsonArrayRequest
                (Request.Method.GET,
                        postsURL,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                System.out.println("Printing response");
                                System.out.println(response.toString());
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

    public static void getAllPosts(Context context, final JSONArrayVolleyCallback callback) {

        final String postsURL = baseURL + "posts";
        JsonArrayRequest allPosts = new JsonArrayRequest
                (Request.Method.GET,
                        postsURL,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                System.out.println("Printing response");
                                System.out.println(response.toString());
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

        final String loginURL = baseURL + "login";
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

    /**
     * Send a request to register a user and receive client id and verification code.
     * @param context the application context
     * @param email the email used to signup
     * @param password the password used to signup
     * @param callback the callback to happen when the request is processed
     */
    public static void register(Context context, String email, String password, final JSONObjectVolleyCallback callback) throws JSONException {

        final String signupURL = baseURL + "registerAccount";
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("email", email);
        jsonRequest.put("password", password);

        JsonObjectRequest register = new JsonObjectRequest
                (Request.Method.POST,
                        signupURL,
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

        VolleyApplication.getInstance(context).addToRequestQueue(register);
    }
}
