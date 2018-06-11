package com.veephealthoutloud.healthoutloud.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.IpPrefix;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.veephealthoutloud.healthoutloud.Classes.Post;
import com.veephealthoutloud.healthoutloud.Classes.VolleyRequestsUtils;
import com.veephealthoutloud.healthoutloud.CreatePostActivity;
import com.veephealthoutloud.healthoutloud.Interfaces.IPost;
import com.veephealthoutloud.healthoutloud.Interfaces.VolleyCallback;
import com.veephealthoutloud.healthoutloud.PostAdapter;
import com.veephealthoutloud.healthoutloud.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnNewsfeedFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsfeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsfeedFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner feelingsSpinner;
    private ListView postListView;
    private PostAdapter postAdapter;
    private ArrayAdapter<String> feelingsAdapter;
    private OnNewsfeedFragmentInteractionListener mListener;

    public NewsfeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsfeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsfeedFragment newInstance() {
        NewsfeedFragment fragment = new NewsfeedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        feelingsAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, GetFeelings());
        feelingsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create list of posts
        GetPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_newsfeed, container, false);
        FloatingActionButton addPostButton = view.findViewById(R.id.add_post_button);
        addPostButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create dropdown menu for feelings
        feelingsSpinner = view.findViewById(R.id.feelings_spinner);
        feelingsSpinner.setAdapter(feelingsAdapter);
        feelingsSpinner.setOnItemSelectedListener(this);

        postListView = view.findViewById(R.id.newsfeed_post_list_view);
        postListView.setAdapter(postAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewsfeedFragmentInteractionListener) {
            mListener = (OnNewsfeedFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNewsfeedFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_post_button:
                AddPostButtonOnClick();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // TODO: implement for when the user chooses a feeling to filter posts by
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO: implement for when the user wants to view all posts
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnNewsfeedFragmentInteractionListener {
        // TODO: Update argument type and name
        void onNewsfeedFragmentInteraction(Uri uri);
    }

    public void AddPostButtonOnClick(){
        Intent intent = new Intent(getActivity(), CreatePostActivity.class);
        startActivity(intent);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void GetPosts(){
        VolleyRequestsUtils.getAllPosts(getActivity().getApplicationContext(), new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONArray result) {
                        ArrayList<IPost> allPosts = ParsePosts(result);
                        postAdapter = new PostAdapter(getContext(), allPosts, R.menu.newsfeed_posts_popup);
                        postListView.setAdapter(postAdapter);
                    }
                });
    }


    private ArrayList<IPost> ParsePosts(JSONArray postsJSONArray) {

        ArrayList<IPost> allPosts = new ArrayList<>();

        for(int index = 0; index < postsJSONArray.length(); index++) {
            try {
                JSONObject JSONPost = (JSONObject) postsJSONArray.get(index);
                JSONArray JSONFeelings = JSONPost.getJSONArray("feelings");

                ArrayList<String> postFeelings = new ArrayList<>();
                for (int feelingsIndex = 0; feelingsIndex < JSONFeelings.length(); feelingsIndex++) {
                    postFeelings.add((String) JSONFeelings.get(feelingsIndex));
                }

                String dateStr = JSONPost.getString("date");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                Date postDate = sdf.parse(dateStr);

                IPost post = new Post(JSONPost.getString("_id"), JSONPost.getString("postBody"),
                        postDate, postFeelings);
                allPosts.add(post);
                
            } catch (JSONException e) {
                // TODO: Exception Handler
            } catch (ParseException p) {
                // TODO: Exception Handler
            }
        }

        return allPosts;

    }

    private ArrayList<String> GetFeelings(){
        // Placeholder for now, add call to api later
        ArrayList<String> feelings = new ArrayList<>();
        feelings.add("sad");
        feelings.add("frustrated");
        feelings.add("angry");
        return feelings;
    }
}
