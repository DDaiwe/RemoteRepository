package com.example.imageloader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	public static final String TAG = "info";
	public static final String LOCATION = "MainActivity  ";

	private ImageView mImageView;
	private ImageLoader mImageLoader;
	private String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mImageLoader = new ImageLoader();
		mImageView = (ImageView) findViewById(R.id.image);
		url = "http://p1.gexing.com/shaitu/20120729/1831/501510e694278.jpg";
		mImageLoader.displayImage(url, mImageView);
	}
}
