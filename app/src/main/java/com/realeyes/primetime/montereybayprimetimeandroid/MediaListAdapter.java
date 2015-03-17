package com.realeyes.primetime.montereybayprimetimeandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.realeyes.primetime.montereybayprimetimeandroid.media.MediaContent;
import com.realeyes.primetime.montereybayprimetimeandroid.utils.HTTPController;
import com.realeyes.primetime.montereybayprimetimeandroid.media.ViewHolder;

/**
* Created by JohnGainfort on 3/12/15.
*/
public class MediaListAdapter extends ArrayAdapter<MediaContent> {
    Context mContext;
    int mLayoutResourceId;
    List mData = null;
    ImageLoader mImageLoader;
    ViewHolder holder = null;

    public MediaListAdapter(Context context, int resource, List data) {
        super(context, resource, data);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            // Create a new view
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new ViewHolder();

            holder.titleView = (TextView) row.findViewById(R.id.mediaTitleText);
            holder.pubDateView = (TextView) row.findViewById(R.id.mediaPubDateText);
            holder.thumbnailView = (ImageView) row.findViewById(R.id.mediaThumbnailView);

            row.setTag(holder);
        } else {
            // Otherwise use an existing one
            holder = (ViewHolder) row.getTag();
        }

        // Get media item from MediaItems List
        MediaContent.MediaItem media = (MediaContent.MediaItem) mData.get(position);

        holder.titleView.setText(media.title);
        holder.pubDateView.setText(media.pubDate);

        mImageLoader = HTTPController.getInstance().getImageLoader();

        mImageLoader.get(media.thumbnailUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer.getBitmap() != null) {
                    // Load image into imageView
                    holder.thumbnailView.setImageBitmap(imageContainer.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.e("Image Load Error:", volleyError.getMessage());
            }
        });

        // Return row view
        return row;
    }
}
