package com.veephealthoutloud.healthoutloud.Classes;

import android.app.Application;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.veephealthoutloud.healthoutloud.ApplicationActivity;
import com.veephealthoutloud.healthoutloud.Classes.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

/**
 * Used to send requests to backend api
 */

public class Requests {

    private static String endpoint = "http://healthoutloud-api.herokuapp.com";

    private ArrayList<Post> processJSONPosts(JSONArray posts)
    {

        ArrayList<Post> allPosts = new ArrayList<>();
        Post _post;

        for(int index = 0; index < posts.length(); index++) {

            try {
                JSONObject JSONPost = (JSONObject) posts.get(index);
                JSONArray JSONFeelings = JSONPost.getJSONArray("feeling");

                ArrayList<String> postFeelings = new ArrayList<>();
                for(int feelingsIndex = 0; feelingsIndex < JSONFeelings.length(); feelingsIndex++){
                    postFeelings.add((String) JSONFeelings.get(feelingsIndex));
                }

                String dateStr = JSONPost.getString("date");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date postDate = sdf.parse(dateStr);

                _post = new Post(JSONPost.getString("_id"), JSONPost.getString("postBody"),
                        postDate, postFeelings);

                allPosts.add(_post);
            }
            catch (JSONException e) {
                // TODO: Exception Handler
            } catch (ParseException p) {
                // TODO: Exception Handler
            }
        }

        return allPosts;

    }

    public void getAllPosts()
    {

        final String postsURL = endpoint + "\\posts";

        JsonArrayRequest allPosts = new JsonArrayRequest(postsURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jO = null;

                for(int index = 0; index < response.length(); index++) {
                    try {
                        jO = response.getJSONObject(index);
                        System.out.print(jO.getString("_id"));
                    } catch (JSONException e) {
                        System.out.print("Unexpected JSON exception");
                    }
                }

                // TODO: Process JSONArray Response
                // NOTE: process JSONResponse is an example of how we could process response
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Error Handler
            }
        });

        ApplicationActivity.getInstance().addToRquestQueue(allPosts);

    }

}
