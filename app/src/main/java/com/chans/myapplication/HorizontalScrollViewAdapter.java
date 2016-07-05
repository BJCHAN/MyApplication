package com.chans.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class HorizontalScrollViewAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private List<Integer> mDatas;
	private ImageLoader imageLoader;

	public HorizontalScrollViewAdapter(Context context, List<Integer> mDatas) {
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
		imageLoader = ImageLoader.getInstance();
	}

	public int getCount() {
		return mDatas.size();
	}

	public Object getItem(int position) {
		return mDatas.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.gallery_item, parent, false);
			viewHolder.mImg = (ImageView) convertView.findViewById(R.id.gallery_item_image);
			viewHolder.mText = (TextView) convertView.findViewById(R.id.gallery_item_text);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mImg.setImageResource(mDatas.get(position));
		try {
			imageLoader.displayImage(String.valueOf(mDatas.get(position)), viewHolder.mImg, application.options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		viewHolder.mText.setText("some info ");

		return convertView;
	}

	private class ViewHolder {
		ImageView mImg;
		TextView mText;
	}

}
