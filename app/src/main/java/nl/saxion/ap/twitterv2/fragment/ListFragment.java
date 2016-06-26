package nl.saxion.ap.twitterv2.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import nl.saxion.ap.twitterv2.R;
import nl.saxion.ap.twitterv2.view.StatusAdapter;
import nl.saxion.ap.twitterv2.model.StatusModel;

/**
 * Created by MindR on 25-Jun-16.
 */
public class ListFragment extends Fragment {
    private ListView listView;
    private StatusAdapter statusAdapter;
    private OnStatusListCallback listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);

        listView = (ListView) view.findViewById(R.id.activity_myprofile_listViewLV);


        if (StatusModel.getInstance().getNewsfeed()!=null) {
            statusAdapter = new StatusAdapter(getActivity(), 0, StatusModel.getInstance().getNewsfeed());
            statusAdapter.notifyDataSetChanged();
            listView.setAdapter(statusAdapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onItemClicked(position);
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnStatusListCallback) {
            listener = (OnStatusListCallback) activity;
        } else throw new ClassCastException("Class must implement this interface!");
    }

    public interface OnStatusListCallback {
        void onItemClicked(int position);
    }

    public void refresh() {
        statusAdapter.notifyDataSetChanged();
    }

    public StatusAdapter getStatusAdapter() {
        return statusAdapter;
    }
}
