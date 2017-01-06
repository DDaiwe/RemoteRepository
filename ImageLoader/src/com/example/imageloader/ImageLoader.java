package com.example.imageloader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.R.integer;
import android.app.ActionBar.Tab;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoader {

	public static final String LOCATION = "ImageLoader  ";

	LruCache<String, Bitmap> mImageCache;
	ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime
			.getRuntime().availableProcessors());

	public ImageLoader() {
		initImageCache();
	}

	private void initImageCache() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 4;
		mImageCache = new LruCache<String, Bitmap>(cacheSize) {

			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight() / 1024;
			}
		};
	}

	/**
	 * 
	 * @param url
	 * @param imageView
	 */
	public void displayImage(final String url, final ImageView imageView) {
		imageView.setTag(url);
		mExecutorService.submit(new Runnable() {

			@Override
			public void run() {
				Bitmap bitmap = downloadImage(url);
				if (bitmap == null) {
					return;
				}
				if (imageView.getTag().equals(url)) {
					imageView.setImageBitmap(bitmap);
				}
				mImageCache.put(url, bitmap);
			}
		});
	}

	/**
	 * 
	 * @param imageUrl
	 * @return
	 */
	protected Bitmap downloadImage(String imageUrl) {

		Bitmap bitmap = null;
		try {
			URL url = new URL(imageUrl);
			final HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			bitmap = BitmapFactory.decodeStream(connection.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}

}
