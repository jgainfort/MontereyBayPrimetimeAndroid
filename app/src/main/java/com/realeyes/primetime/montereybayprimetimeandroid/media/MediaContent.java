package com.realeyes.primetime.montereybayprimetimeandroid.media;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.realeyes.primetime.montereybayprimetimeandroid.utils.HTTPController;

import org.json.JSONArray;
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
    // TODO: uncomment following line when rss feed reflects multiple items
//    private static final String feedUrl = "http://feed.theplatform.com/f/TMCXRC/3oI2GRBlbQ5_";
    // TODO: remove following line when rss feed reflects multiple items
    private static final String feedUrl = "http://office.realeyes.com/jgainfort/PrimetimeData/data.xml";
    private static final String mediaReqTag = "mediaRequest";

    private static final String KEY_RSS = "rss";
    private static final String KEY_CHANNEL = "channel";
    private static final String KEY_ITEM = "item";

    private static final String KEY_ITEM_TITLE = "title";
    private static final String KEY_ITEM_DESCRIPTION = "description";
    private static final String KEY_ITEM_PUB_DATE = "pubDate";
    private static final String KEY_ITEM_THUMBNAIL = "media:thumbnail";

    private static final String KEY_THUMBNAIL_URL = "url";

    /**
     * An array of media items.
     */
    public static List<MediaItem> ITEMS = new ArrayList<MediaItem>();

    /**
     * A map of media items, by ID.
     */
    public static Map<String, MediaItem> ITEM_MAP = new HashMap<String, MediaItem>();

    // Get media items from RSS Feed
    static {
        getMediaItems(feedUrl, mediaReqTag);
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
        public String pubDate;
        public String thumbnailUrl;
        public String description;

        public MediaItem(JSONObject item) {
            try {
                this.title = item.getString(KEY_ITEM_TITLE);
                this.description = item.getString(KEY_ITEM_DESCRIPTION);
                this.pubDate = item.getString(KEY_ITEM_PUB_DATE);

                JSONObject thumbnail = item.getJSONObject(KEY_ITEM_THUMBNAIL);
                this.thumbnailUrl = thumbnail.getString(KEY_THUMBNAIL_URL);
            } catch (JSONException e) {
                Log.e("JSON Exception", e.getMessage());
                e.printStackTrace();
            }
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
                try {
                    JSONObject jsonObj = XML.toJSONObject(s);
                    JSONObject rss = jsonObj.getJSONObject(KEY_RSS);
                    JSONObject channel = rss.getJSONObject(KEY_CHANNEL);
                    JSONArray items = channel.getJSONArray(KEY_ITEM);

                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        MediaContent.addItem(new MediaContent.MediaItem(item));
                    }

                } catch (JSONException e) {
                    Log.e("JSON Exception", e.getMessage());
                    e.printStackTrace();
                }
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
