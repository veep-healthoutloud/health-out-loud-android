package com.veephealthoutloud.healthoutloud;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.veephealthoutloud.healthoutloud.Interfaces.IPost;

import java.util.ArrayList;

public class CreatePostActivity extends AppCompatActivity {

    private Spinner feelingsSpinner;
    private ArrayAdapter<CharSequence> feelingsAdapter;
    private ArrayList<String> selectedFeelings;
    private ListView selectedFeelingsListView;
    private SelectedFeelingsAdapter selectedFeelingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // Set the options in the feelings dropdown menu
        feelingsSpinner = findViewById(R.id.create_post_feelings_spinner);
        feelingsAdapter = ArrayAdapter.createFromResource(this, R.array.feelings_list,
                android.R.layout.simple_spinner_dropdown_item);
        feelingsSpinner.setAdapter(feelingsAdapter);
        feelingsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feelingsSpinner.setOnItemSelectedListener(GetOnFeelingSelectedListener());
        selectedFeelings = new ArrayList<>();

        // Set up the selected feelings list
        selectedFeelingsListView = findViewById(R.id.create_post_activity_selected_feelings_list);
        selectedFeelingsAdapter = new SelectedFeelingsAdapter(this, selectedFeelings);
        selectedFeelingsListView.setAdapter(selectedFeelingsAdapter);
    }

    /***
     * Create a new post by the user.
     * @param view the delete button
     */
    public void OnSubmitButtonClick(View view){
        // TODO: Add server call to create a post

        Intent but1 = new Intent(CreatePostActivity.this, MainActivity.class);
        startActivity(but1);
    }

    private OnItemSelectedListener GetOnFeelingSelectedListener(){
        return new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String feeling = adapterView.getSelectedItem().toString();
                if (i != 0 && !selectedFeelings.contains(feeling)){
                    selectedFeelings.add(adapterView.getSelectedItem().toString());
                    selectedFeelingsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
    }

    private static class SelectedFeelingsAdapter extends BaseAdapter {

        static class ViewHolder{
            TextView feelingTextView;
            Button deleteButton;
        }

        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<String> mDataSource;

        SelectedFeelingsAdapter(Context context, ArrayList<String> selectedFeelings){
            mContext = context;
            mDataSource = selectedFeelings;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mDataSource.size();
        }

        @Override
        public Object getItem(int i) {
            return mDataSource.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;

            if (view == null){
                view = mInflater.inflate(R.layout.list_selected_feelings, viewGroup, false);

                viewHolder = new ViewHolder();
                viewHolder.feelingTextView = view.findViewById(R.id.selected_feeling_text_view);
                viewHolder.deleteButton = view.findViewById(R.id.delete_selected_feeling_button);
                view.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.feelingTextView.setText((String)getItem(i));
            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDataSource.remove(i);
                    notifyDataSetChanged();
                }
            });

            return view;
        }
    }
}
