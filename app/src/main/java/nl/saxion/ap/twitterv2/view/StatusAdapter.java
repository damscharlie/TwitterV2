package nl.saxion.ap.twitterv2.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nl.saxion.ap.twitterv2.asyncTask.FavourAsyncTask;
import nl.saxion.ap.twitterv2.R;
import nl.saxion.ap.twitterv2.asyncTask.ReTweetAsyncTask;
import nl.saxion.ap.twitterv2.object.Status;

/**
 * Created by MindR on 25-Jun-16.
 */
public class StatusAdapter extends ArrayAdapter<Status> {
    int position;
    private Status status;
    Context context;
    /*
    I need an xlm "list_item".
     */

    public StatusAdapter(Context context, int resource, ArrayList<Status> statuses) {
        super(context, resource,statuses);
        this.context = context;
    }

    // make position final?
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        this.position = position;
        status =getItem(position);
        //textview
        final TextView statusText = (TextView) convertView.findViewById(R.id.status_text_tv);
        TextView userName = (TextView) convertView.findViewById(R.id.user_name_tv);
        TextView screeName = (TextView) convertView.findViewById(R.id.screen_name_tv);
        //imageview
        ImageView statusImage = (ImageView) convertView.findViewById(R.id.status_image_iv);
        ImageView profileImage = (ImageView) convertView.findViewById(R.id.user_profile_picture_iv);
        //button
        Button retweet = (Button) convertView.findViewById(R.id.retweet_btn);
        Button reply = (Button) convertView.findViewById(R.id.reply_btn);
        Button favorite = (Button) convertView.findViewById(R.id.favorites_btn);
        //onclicks
        retweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = getItem(position);
                ReTweetAsyncTask retweetTask =new ReTweetAsyncTask(status.getId(), context);
                retweetTask.execute();
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = getItem(position);
                FavourAsyncTask favourAsyncTask =new FavourAsyncTask(status.getId(), context);
                favourAsyncTask.execute();
            }
        });
        //return
        return convertView;
    }
}
