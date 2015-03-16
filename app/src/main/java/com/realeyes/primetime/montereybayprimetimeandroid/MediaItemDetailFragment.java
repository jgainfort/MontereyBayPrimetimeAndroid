package com.realeyes.primetime.montereybayprimetimeandroid;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.realeyes.primetime.montereybayprimetimeandroid.media.MediaContent;

/**
 * A fragment representing a single MediaItem detail screen.
 * This fragment is either contained in a {@link MediaItemListActivity}
 * in two-pane mode (on tablets) or a {@link MediaItemDetailActivity}
 * on handsets.
 */
public class MediaItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The media content this fragment is presenting.
     */
    private MediaContent.MediaItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MediaItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the media content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = MediaContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mediaitem_detail, container, false);

        // Show the media content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.mediaitem_detail)).setText(mItem.description);
        }

        return rootView;
    }
}
