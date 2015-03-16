package com.realeyes.primetime.montereybayprimetimeandroid.media;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.realeyes.primetime.montereybayprimetimeandroid.utils.HTTPController;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing media content for user interfaces
 */
public class MediaContent {

    private static final String mediaUrl = "http://feed.theplatform.com/f/TMCXRC/3oI2GRBlbQ5_";
    private static final String mediaReqTag = "mediaRequest";

    /**
     * An array of media items.
     */
    public static List<MediaItem> ITEMS = new ArrayList<MediaItem>();

    /**
     * A map of media items, by ID.
     */
    public static Map<String, MediaItem> ITEM_MAP = new HashMap<String, MediaItem>();

    static {
//        // Add 3 sample items.
//        addItem(new MediaItem("Otter Cam", "Monterey Bay Aquarium", "monterey_logo_small", "Watch some cute Otter's"));
//        addItem(new MediaItem("Shark Cam", "Monterey Bay Aquarium", "monterey_logo_small", "Watch some killer Shark's"));
//        addItem(new MediaItem("Whale Cam", "Monterey Bay Aquarium", "monterey_logo_small", "Watch some large Whale's"));
        getMediaItems(mediaUrl, mediaReqTag);
    }

    public static void addItem(MediaItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.title, item);
    }

    /**
     * A media item representing a piece of content.
     */
    public static class MediaItem {
        public String title;
        public String author;
        public String thumbnailName;
        public String description;

        public MediaItem(String title, String author, String thumbnailName, String description) {
            this.title = title;
            this.author = author;
            this.thumbnailName =thumbnailName;
            this.description = description;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    private static void getMediaItems(String url, String tag) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                // Convert the String xml response to json
                String mediaTitle = "";
                String mediaAuthor ="";
                String mediaDescription = "";
                try {
                    JSONObject jsonObj = XML.toJSONObject(s);
                    JSONObject rss = jsonObj.getJSONObject("rss");
                    JSONObject channel = rss.getJSONObject("channel");
                    // TODO: This will eventually be an array of item objects
                    JSONObject item = channel.getJSONObject("item");

                    mediaTitle = item.getString("title");
                    mediaAuthor = item.getString("pubDate");
                    mediaDescription = item.getString("description");

                } catch (JSONException e) {
                    Log.e("JSON Exception", e.getMessage());
                    e.printStackTrace();
                }

                MediaContent.addItem(new MediaContent.MediaItem(mediaTitle, mediaAuthor, "monterey_logo_small", mediaDescription));
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // Handle error response
                VolleyLog.d("VolleyError", volleyError.getMessage());
            }
        });

        // Add request to request queue
        HTTPController.getInstance().addToRequestQueue(request, tag);
    }
}
