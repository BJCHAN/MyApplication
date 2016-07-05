package com.chans.myapplication;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * description :
 * Created by Chans Renhenet
 * 2015/9/14
 */
public class application extends Application {

	public static final int IMAGE_CACHE_MAX_SIZE_IN_BYTE = 1024 * 1024 * 15;
	public static DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.a)
			.showImageForEmptyUri(R.drawable.a).showImageOnFail(R.drawable.a).cacheInMemory(true)
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED).considerExifParams(true).build();

	@Override
	public void onCreate() {
		super.onCreate();
		//Universal Imageloader初始化
		initImageLoader(getApplicationContext());
	}

	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory().diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).defaultDisplayImageOptions(options)
				.memoryCache(new LRULimitedMemoryCache(IMAGE_CACHE_MAX_SIZE_IN_BYTE)).build();
		ImageLoader.getInstance().init(config);
	}

}
