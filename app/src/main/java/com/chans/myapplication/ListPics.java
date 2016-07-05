package com.chans.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description :
 * Created by Chans Renhenet
 * 2015/9/11
 */
public class ListPics extends Activity {

	private ListView list;
	private ListPicsAdapter adapter;
	private List<FileBean> fileList;

	private String filePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.listpic);
		list = (ListView) findViewById(R.id.list);

		filePath = getIntent().getStringExtra("path");
		fileList = new ArrayList<>();
		if (null != filePath) {
			fileList.addAll(getPictures(filePath));
			adapter = new ListPicsAdapter();
			list.setAdapter(adapter);
		}

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Toast.makeText(ListPics.this, "" + fileList.get(position).getFilePath(), Toast.LENGTH_SHORT).show();
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

	//简单适配
	class ListPicsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return fileList.size();
		}

		@Override
		public Object getItem(int position) {
			return fileList.get(position);
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
				convertView = LayoutInflater.from(ListPics.this).inflate(R.layout.list_item, parent, false);
				vh.icon = (TextView) convertView.findViewById(R.id.icon);
				vh.time = (TextView) convertView.findViewById(R.id.time);
				vh.type = (TextView) convertView.findViewById(R.id.type);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}

			if (fileList.get(position).getType() == 1) {
				vh.icon.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.picture), null, null, null);
				vh.type.setText("图片");
			} else {
				vh.icon.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.video), null, null, null);
				vh.type.setText("视频");
			}
			vh.icon.setText(fileList.get(position).getFileName());
			vh.time.setText(fileList.get(position).getCreateTime());
			return convertView;
		}

		class ViewHolder {
			TextView icon;
			TextView time;
			TextView type;
		}
	}
}
