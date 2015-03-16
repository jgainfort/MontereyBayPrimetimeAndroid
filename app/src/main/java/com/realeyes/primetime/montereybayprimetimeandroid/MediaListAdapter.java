package com.realeyes.primetime.montereybayprimetimeandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import com.realeyes.primetime.montereybayprimetimeandroid.media.MediaContent;

/**
* Created by JohnGainfort on 3/12/15.
*/
public class MediaListAdapter extends ArrayAdapter<MediaContent> {
    Context mContext;
    int mLayoutResourceId;
    List mData = null;

    public MediaListAdapter(Context context, int resource, List data) {
        super(context, resource, data);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            // Create a new view
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new ViewHolder();

            holder.titleView = (TextView) row.findViewById(R.id.mediaTitleText);
            holder.authorView = (TextView) row.findViewById(R.id.mediaAuthorText);
            holder.thumbnailView = (ImageView) row.findViewById(R.id.mediaThumbnailView);

            row.setTag(holder);
        } else {
            // Otherwise use an existing one
            holder = (ViewHolder) row.getTag();
        }

        // Get media item from MediaItems List
        MediaContent.MediaItem media = (MediaContent.MediaItem) mData.get(position);

        holder.titleView.setText(media.title);
        holder.authorView.setText(media.author);

        int resId = mContext.getResources().getIdentifier(media.thumbnailName, "drawable", mContext.getPackageName());
        holder.thumbnailView.setImageResource(resId);

        // Return row view
        return row;
    }

    private static class ViewHolder {
        TextView titleView;
        TextView authorView;
        ImageView thumbnailView;
    }
}
