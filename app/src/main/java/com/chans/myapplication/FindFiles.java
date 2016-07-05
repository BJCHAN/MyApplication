package com.chans.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * description :
 * Created by Chans Renhenet
 * 2015/9/10
 */
public class FindFiles extends Activity {

	private String localPath = Environment.getExternalStorageDirectory() + "/Test";
	private GridView gridview;
	private List<FileBean> filesList;
	private GridAdapter gridAdapter;
	private HorizontalScrollView gallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);
		gridview = (GridView) findViewById(R.id.gridview);
		filesList = getFileName(localPath);
		gridAdapter = new GridAdapter(filesList);
		gridview.setAdapter(gridAdapter);

		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(FindFiles.this, "路径：" + filesList.get(position).getFilePath(), Toast.LENGTH_SHORT).show();
				Intent i = new Intent(FindFiles.this, GalleryPics.class);
//				Intent i = new Intent(FindFiles.this, ViewPhotoActivity.class);
				i.putExtra("path", filesList.get(position).getFilePath());
				startActivity(i);
			}
		});

	}

	/**
	 * 获取指定路径下的所有文件夹名
	 */
	public List<FileBean> getFileName(final String strPath) {
		List<FileBean> list = new ArrayList<FileBean>();
		File file = new File(strPath);
		File[] allFiles = file.listFiles();
		if (allFiles == null) {
			return null;
		}
		for (int k = 0; k < allFiles.length; k++) {
			final File fi = allFiles[k];
			if (fi.isDirectory()) {
				FileBean fb = new FileBean();
				fb.setFileName(fi.getName());
				fb.setFilePath(fi.getAbsolutePath());
				list.add(fb);
			}
		}
		return list;
	}

	class GridAdapter extends BaseAdapter {
		private List<FileBean> filesList;

		public GridAdapter(List<FileBean> filesList) {
			this.filesList = filesList;
		}

		@Override
		public int getCount() {
			return filesList.size();
		}

		@Override
		public Object getItem(int position) {
			return filesList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh = null;
			if (convertView == null) {
				vh = new ViewHolder();
				convertView = LayoutInflater.from(FindFiles.this).inflate(R.layout.gridview_item, parent, false);
				vh.ivImg = (ImageView) convertView.findViewById(R.id.ItemImage);
				vh.tvName = (TextView) convertView.findViewById(R.id.ItemText);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			vh.ivImg.setImageResource(R.drawable.file_icon);
			vh.tvName.setText(filesList.get(position).getFileName());
			return convertView;
		}

		class ViewHolder {
			ImageView ivImg;
			TextView tvName;
		}
	}

	//		List<String> list = getPictures(Environment.getExternalStorageDirectory() + "");
	//		if (list != null) {
	//			Log.d("---", "list.size = " + list.size());
	//			for (int i = 0; i < list.size(); i++) {
	//				Bitmap bm = BitmapFactory.decodeFile(list.get(i));
	//				int top = 30;
	//				if (i > 0) {
	//					top += BitmapFactory.decodeFile(list.get(i - 1)).getHeight() + 2;
	//				}
	//				canvas.drawBitmap(bm, 0, top, paint);
	//			}
	//		} else {
	//			Log.d("---", "list is null!!!");
	//		}

	//	//selection: 指定查询条件
	//	String selection = MediaStore.Images.Media.DATA + " like %?";
	//	//设定查询目录
	//	String path = "/mnt/sdcard/youpicpath";
	//	//定义selectionArgs：
	//	String[] selectionArgs = { path + "%" };
	//	Cursor c = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, selection, selectionArgs,
	//			null);

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent i = new Intent();
			i.putExtra("test", "test");
			setResult(99, i);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
