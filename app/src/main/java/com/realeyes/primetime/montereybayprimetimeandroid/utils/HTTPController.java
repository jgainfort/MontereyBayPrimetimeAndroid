package com.realeyes.primetime.montereybayprimetimeandroid.utils;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * Created by JohnGainfort on 3/16/15.
 */
public class HTTPController extends Application {

    public static final String TAG = HTTPController.class.getSimpleName();
    static JSONObject jsonObj = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static HTTPController mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized HTTPController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }

        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

//    public JSONObject getJSONFromUrl(String url) {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                // Convert the String xml response to json
//                try {
//                    jsonObj = XML.toJSONObject(s);
//                } catch (JSONException e) {
//                    Log.e("JSON Exception", e.getMessage());
//                    e.printStackTrace();
//                }
//
//                return jsonObj;
//            }
//        },
//        new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                // Handle error response
//                Log.e("HTTP Request Error:", volleyError.getMessage());
//            }
//        });
//
//        queue.add(request);
//    }
}
