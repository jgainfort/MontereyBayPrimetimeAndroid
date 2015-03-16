package com.realeyes.primetime.montereybayprimetimeandroid;

import android.content.Context;
import android.util.Log;
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
import com.android.volley.toolbox.NetworkImageView;
import com.realeyes.primetime.montereybayprimetimeandroid.media.MediaContent;
import com.realeyes.primetime.montereybayprimetimeandroid.utils.HTTPController;

import org.json.HTTP;

/**
* Created by JohnGainfort on 3/12/15.
*/
public class MediaListAdapter extends ArrayAdapter<MediaContent> {
    Context mContext;
    int mLayoutResourceId;
    List mData = null;
    ImageLoader mImageLoader;
    NetworkImageView mNetworkImageView;

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
            holder.pubDateView = (TextView) row.findViewById(R.id.mediaPubDateText);
//            holder.thumbnailView = (NetworkImageView) row.findViewById(R.id.mediaThumbnailView);
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

//        mNetworkImageView = (NetworkImageView) holder.thumbnailView;
//
//        mNetworkImageView.setImageUrl(media.thumbnailUrl, mImageLoader);

        mImageLoader.get(media.thumbnailUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer.getBitmap() != null) {
                    // Load image into imageView
                    holder.setImageBitmap(imageContainer.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.e("Image Load Error:", volleyError.getMessage());
            }
        });

//        int resId = mContext.getResources().getIdentifier(media.thumbnailName, "drawable", mContext.getPackageName());
//        int resId = mContext.getResources().getIdentifier("monterey_logo_small", "drawable", mContext.getPackageName());
//        holder.thumbnailView.setImageResource(resId);

        // Return row view
        return row;
    }

    private static class ViewHolder {
        TextView titleView;
        TextView pubDateView;
//        NetworkImageView thumbnailView;
        ImageView thumbnailView;
    }
}
