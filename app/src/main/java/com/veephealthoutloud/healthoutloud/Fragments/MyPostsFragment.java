package com.veephealthoutloud.healthoutloud.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.veephealthoutloud.healthoutloud.Classes.Post;
import com.veephealthoutloud.healthoutloud.Interfaces.IPost;
import com.veephealthoutloud.healthoutloud.PostAdapter;
import com.veephealthoutloud.healthoutloud.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyPostsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyPostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPostsFragment extends Fragment {

    private ListView postListView;
    private PostAdapter postAdapter;
    private OnFragmentInteractionListener mListener;

    public MyPostsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyPostsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPostsFragment newInstance() {
        MyPostsFragment fragment = new MyPostsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create list of user posts
        ArrayList<IPost> postMessages = GetPosts();

        postAdapter = new PostAdapter(getContext(), postMessages, R.menu.my_posts_popup);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_posts, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        postListView = view.findViewById(R.id.my_posts_post_list_view);
        postListView.setAdapter(postAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    private ArrayList<IPost> GetPosts(){
        // TODO: Change to use request to server when that's set up
        ArrayList<IPost> list = new ArrayList<>();

        ArrayList<String> feelings = new ArrayList<>();
        feelings.add("angry");
        feelings.add("frustrated");

        IPost post1 = new Post("postID", "UserMessage1", new Date(), feelings);
        IPost post2 = new Post("postID2", "UserMessage2", new Date(), feelings);
        IPost post3 = new Post("postID3", "UserMessage3", new Date(), feelings);
        list.add(post1);
        list.add(post2);
        list.add(post3);
        return list;
    }
}
