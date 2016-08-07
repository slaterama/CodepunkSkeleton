package com.codepunk.skeleton.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

import java.net.HttpURLConnection;

@SuppressWarnings({ "unused", "WeakerAccess" })
public class VolleyManager {

    public interface OnRequestQueueReadyListener {
        void onRequestQueueReady(RequestQueue requestQueue);
    }

    public interface OnImageLoaderReadyListener {
        void onImageLoaderReady(ImageLoader imageLoader);
    }

    private static final class CustomImageCache implements ImageCache {
        // Default LRU cache size
        private static final int LRU_CACHE_SIZE = 20;

        private final LruCache<String, Bitmap> mCache;

        CustomImageCache() {
            mCache = new LruCache<>(LRU_CACHE_SIZE);
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }
    }

    // Default maximum disk usage in bytes
    private static final int DEFAULT_DISK_USAGE_BYTES = 25 * 1024 * 1024;

    // Default cache folder name
    private static final String DEFAULT_CACHE_DIR = "cache";

    private static final Object sLock = new Object();

    @SuppressWarnings("StaticFieldLeak")
    private static VolleyManager sInstance;

    public static synchronized VolleyManager getInstance(Context context) {
        synchronized (sLock) {
            if (sInstance == null) {
                // getApplicationContext() is key, it keeps you from leaking the
                // Activity or BroadcastReceiver if someone passes one in.
                sInstance = new VolleyManager(context.getApplicationContext());
            }
            return sInstance;
        }
    }

    private final Context mAppContext;

    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;

    private VolleyManager(Context context) {
        mAppContext = context;
        HttpURLConnection.setFollowRedirects(true);
    }

    public synchronized void getRequestQueue(final OnRequestQueueReadyListener listener) {
        synchronized (sLock) {
            if (mRequestQueue == null) {
                newRequestQueue(mAppContext, new OnRequestQueueReadyListener() {
                    @Override
                    public void onRequestQueueReady(RequestQueue requestQueue) {
                        mRequestQueue = requestQueue;
                        if (listener != null) {
                            listener.onRequestQueueReady(requestQueue);
                        }
                    }
                });
            } else if (listener != null) {
                listener.onRequestQueueReady(mRequestQueue);
            }
        }
    }

    public synchronized void getImageLoader(final OnImageLoaderReadyListener listener) {
        synchronized (sLock) {
            if (mImageLoader == null) {
                getRequestQueue(new OnRequestQueueReadyListener() {
                    @Override
                    public void onRequestQueueReady(RequestQueue requestQueue) {
                        mImageLoader = new ImageLoader(requestQueue, new CustomImageCache());
                        if (listener != null) {
                            listener.onImageLoaderReady(mImageLoader);
                        }
                    }
                });
            } else if (listener != null) {
                listener.onImageLoaderReady(mImageLoader);
            }
        }
    }

    public <T> void addToRequestQueue(final Request<T> req) {
        getRequestQueue(new OnRequestQueueReadyListener() {
            @Override
            public void onRequestQueueReady(RequestQueue requestQueue) {
                requestQueue.add(req);
            }
        });
    }

    private static void newRequestQueue(
            final Context context,
            final OnRequestQueueReadyListener listener) {
        AsyncTaskCompat.executeParallel(new AsyncTask<Void, Void, RequestQueue>() {
            @Override
            protected RequestQueue doInBackground(Void... params) {
                return Volley.newRequestQueue(context);
            }

            @Override
            protected void onPostExecute(RequestQueue requestQueue) {
                if (listener != null) {
                    listener.onRequestQueueReady(requestQueue);
                }
            }
        });
    }
}
