package com.chans.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description :
 * Created by Chans Renhenet
 * 2015/9/14
 */
public class GalleryPics extends Activity {

	private ImageView leftIv, rightIv;
	private Gallery gallery;
	private GalleryAdapter adapter;

	private String filePath;
	List<FileBean> fileList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		gallery = (Gallery) findViewById(R.id.gallery);
		leftIv = (ImageView) findViewById(R.id.leftIv);
		rightIv = (ImageView) findViewById(R.id.rightIv);
	}

	private void initData() {
		filePath = getIntent().getStringExtra("path");
		fileList = new ArrayList<>();
		if (null != filePath) {
			fileList.addAll(getPictures(filePath));
		}
		adapter = new GalleryAdapter(this, fileList);
		gallery.setAdapter(adapter);
		if (fileList.size() > 4) {
			gallery.setSelection(2);
		}
	}

	private void initListener() {


		gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(GalleryPics.this, "点击；" + position, Toast.LENGTH_SHORT).show();
			}
		});

		leftIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int select = gallery.getSelectedItemPosition();
				if (select > 0) {
					Toast.makeText(GalleryPics.this, "←左" + select, Toast.LENGTH_SHORT).show();
					gallery.setSelection(select - 1);
				}
			}
		});

		rightIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int select = gallery.getSelectedItemPosition();
				if (select < fileList.size() - 1) {
					Toast.makeText(GalleryPics.this, "右→", Toast.LENGTH_SHORT).show();
					gallery.setSelection(select + 1);
				}
			}
		});
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
}
