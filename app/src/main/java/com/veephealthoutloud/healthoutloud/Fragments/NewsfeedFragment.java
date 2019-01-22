package com.veephealthoutloud.healthoutloud.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.VolleyError;
import com.veephealthoutloud.healthoutloud.Classes.Post;
import com.veephealthoutloud.healthoutloud.Classes.VolleyRequestsUtils;
import com.veephealthoutloud.healthoutloud.CreatePostActivity;
import com.veephealthoutloud.healthoutloud.Interfaces.IPost;
import com.veephealthoutloud.healthoutloud.Interfaces.JSONArrayVolleyCallback;
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
    private ArrayList<IPost> postsList;
    private ArrayAdapter<CharSequence> feelingsAdapter;
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


    String tokenTest;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState == null) {

            Bundle extras = getActivity().getIntent().getExtras();
            if(extras == null) {
                tokenTest= null;
            } else {
                tokenTest= extras.getString("token");
            }
        } else {
            tokenTest= (String) savedInstanceState.getSerializable("token");
        }
        System.out.println("TOKEN TEST: " + tokenTest);
        super.onCreate(savedInstanceState);

        feelingsAdapter = ArrayAdapter.createFromResource(getContext(), R.array.feelings_list,
                android.R.layout.simple_spinner_dropdown_item);
        feelingsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create list of posts
        postsList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postsList, R.menu.newsfeed_posts_popup);
        GetPosts();

        System.out.println("\n\n\nCreated newsfeedgragment\n\n");
        for (int i = 0; i < postsList.size(); i++) {
            System.out.println(i);
        }
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
        feelingsSpinner = view.findViewById(R.id.newsfeed_feelings_spinner);
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

        String selectedFeeling = adapterView.getSelectedItem().toString();
        selectedFeeling.replaceAll("\\s+","");
        System.out.println("SELECTED FEELING: " + "WS" + selectedFeeling + "WS");
        System.out.println("boolean" + selectedFeeling.equals("(select a feeling)"));
        if (!selectedFeeling.equals("(select a feeling)")) {
            System.out.println("SELECTED FEELING: " + selectedFeeling );
            VolleyRequestsUtils.getPostsByFeeling(tokenTest,selectedFeeling,getActivity().getApplicationContext(), new JSONArrayVolleyCallback() {
                @Override
                public void onSuccess(JSONArray result) {
                    postsList.clear();
                    ArrayList<IPost> newPosts = ParsePosts(result);
                    for(int i = 0; i < newPosts.size(); i++){
                        postsList.add(newPosts.get(i));
                    }
                    postAdapter.updateResults(postsList);
                }

                @Override
                public void onError(VolleyError error){
                    //TODO Handle error
                }
            });
        }
        else {
            System.out.println("FEELING IS (select a feeling)");
            GetPosts();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO: implement for when the user wants to view all posts
        System.out.println("NOTHING SELECTED FUNCTION");
        GetPosts();

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
        //String newString = tokenTest;
        System.out.println("IN GETPOSTS, Token Test: " + tokenTest);

        VolleyRequestsUtils.getAllPosts(getActivity().getApplicationContext(), new JSONArrayVolleyCallback() {
                    @Override
                    public void onSuccess(JSONArray result) {
                        //postsList.clear();
                        postsList.clear();
                        ArrayList<IPost> newPosts = ParsePosts(result);
                        for(int i = 0; i < newPosts.size(); i++){
                            postsList.add(newPosts.get(i));
                        }
                        postAdapter.updateResults(postsList);
                    }

                    @Override
                    public void onError(VolleyError error){
                        //TODO Handle error
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
