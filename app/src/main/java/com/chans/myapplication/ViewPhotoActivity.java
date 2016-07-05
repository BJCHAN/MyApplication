package com.chans.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ViewPhotoActivity extends Activity {

	private ArrayList<View> listViews = null;
	private ViewPager pager;
	private MyPageAdapter adapter;
	private String filePath;
	private List<FileBean> fileList;
	private LayoutInflater inflater;
	private int DEFAULT_IMAGE;

	public int max;
	private int currentItem = 0;
	private int count;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);

		inflater = getLayoutInflater();
		DEFAULT_IMAGE = R.drawable.a;
		Intent intent = getIntent();
		final int id = intent.getIntExtra("ID", 0);
		filePath = getIntent().getStringExtra("path");
		fileList = new ArrayList<>();
		if (null != filePath) {
			fileList.addAll(getPictures(filePath));
		}
		currentItem = id + 1;
		max = fileList.size();
		pager = (ViewPager) findViewById(R.id.viewpager);
		adapter = new MyPageAdapter(listViews);// 构造adapter
		pager.setAdapter(adapter);// 设置适配器
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				currentItem = arg0 + 1;
				count = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		pager.setCurrentItem(id);
	}

	class MyPageAdapter extends PagerAdapter {

		private ImageLoader imageLoader;
		private int size;// 页数

		private DisplayImageOptions options;

		public MyPageAdapter(ArrayList<View> listViews) {// 构造函数
			size = fileList.size();
			options = new DisplayImageOptions.Builder().showImageOnLoading(DEFAULT_IMAGE).showImageForEmptyUri(DEFAULT_IMAGE)
					.showImageOnFail(DEFAULT_IMAGE).cacheInMemory(true).cacheOnDisk(true)
					.imageScaleType(ImageScaleType.EXACTLY_STRETCHED).considerExifParams(true).build();
			imageLoader = ImageLoader.getInstance();
		}

		public int getCount() {// 返回数量
			return size;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {// 销毁view对象
			((ViewPager) arg0).removeView((View) arg2);
		}

		public void finishUpdate(View arg0) {
		}

		public Object instantiateItem(View arg0, int arg1) {// 返回view对象
			String item = fileList.get(arg1).getFilePath().toString();
			final View imageLayout = inflater.inflate(R.layout.gallery_item, null);
			final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.gallery_item_image);
			TextView mText = (TextView) imageLayout.findViewById(R.id.gallery_item_text);
			imageView.setTag(item);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

			try {
				imageLoader.displayImage("file://" + fileList.get(arg1).getFilePath(), imageView, application.options);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mText.setText(fileList.get(arg1).getFileName());

			((ViewPager) arg0).addView(imageLayout, 0);
			return imageLayout;
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}


	class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 1000);
					displayedImages.add(imageUri);
				}
			}
		}

		@Override
		public void onLoadingStarted(String imageUri, View view) {
			super.onLoadingStarted(imageUri, view);
		}

		@Override
		public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
			super.onLoadingFailed(imageUri, view, failReason);
			if (null != view) {
				ImageView imageView = (ImageView) view;
				imageView.setImageResource(DEFAULT_IMAGE);
			}

		}

		@Override
		public void onLoadingCancelled(String imageUri, View view) {
			super.onLoadingCancelled(imageUri, view);
			if (null != view) {
				ImageView imageView = (ImageView) view;
				imageView.setImageResource(DEFAULT_IMAGE);
			}
		}
	}
	/**
	 * 获取指定路径下所有的图片路径
	 * @param strPath
	 * @return
	 */
	public static List<FileBean> getPictures(final String strPath) {
		List<FileBean> list = new ArrayList<FileBean>();
		File file = new File(strPath);
		File[] allFiles = file.listFiles();
		if (allFiles == null) {
			return null;
		}
		for (int k = 0; k < allFiles.length; k++) {
			final File fi = allFiles[k];
			if (fi.isFile()) {
				int idx = fi.getPath().lastIndexOf(".");
				if (idx <= 0) {
					continue;
				}

				String suffix = fi.getPath().substring(idx);
				FileBean fileBean = new FileBean();
				Date date = new Date(fi.lastModified());
				SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd");
				String time = sf.format(date);
				fileBean.setCreateTime(time);
				fileBean.setFileName(fi.getName());
				fileBean.setFilePath(fi.getAbsolutePath());
				if (suffix.toLowerCase().equals(".jpg") || suffix.toLowerCase().equals(".jpeg")
						|| suffix.toLowerCase().equals(".bmp") || suffix.toLowerCase().equals(".png")
						|| suffix.toLowerCase().equals(".gif")) {
					fileBean.setType(1);
				} else if (suffix.toLowerCase().equals(".avi") || suffix.toLowerCase().equals(".mp4")) {
					fileBean.setType(2);
				}
				list.add(fileBean);
			}
		}
		return list;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
