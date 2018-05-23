package com.veephealthoutloud.healthoutloud.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;

import com.veephealthoutloud.healthoutloud.Classes.Post;
import com.veephealthoutloud.healthoutloud.CreatePostActivity;
import com.veephealthoutloud.healthoutloud.Interfaces.IPost;
import com.veephealthoutloud.healthoutloud.PostAdapter;
import com.veephealthoutloud.healthoutloud.R;

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
        ArrayList<IPost> postMessages = GetPosts();
        postAdapter = new PostAdapter(getContext(), postMessages, R.menu.newsfeed_posts_popup);
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

    private ArrayList<IPost> GetPosts(){
        // TODO: Change to use request to server when that's set up
        ArrayList<IPost> list = new ArrayList<>();

        ArrayList<String> feelings = new ArrayList<>();
        feelings.add("sad");
        feelings.add("frustrated");

        IPost post1 = new Post("postID", "message", new Date(), feelings);
        IPost post2 = new Post("postID2", "message2", new Date(), feelings);
        IPost post3 = new Post("postID3", "message3", new Date(), feelings);
        list.add(post1);
        list.add(post2);
        list.add(post3);
        return list;
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
