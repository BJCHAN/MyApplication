package com.chans.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chans.photoView.PhotoView;
import com.chans.photoView.PhotoViewAttacher;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * description :
 * Created by Chans Renhenet
 * 2015/9/17
 */
public class GalleryAdapter extends BaseAdapter {

    private Context mContext;
    private List<FileBean> fileList;
    private ImageLoader imageLoader;

    public GalleryAdapter(Context context, List<FileBean> fileList) {
        this.mContext = context;
        this.fileList = fileList;
        imageLoader = ImageLoader.getInstance();
    }

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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gallery_item, parent, false);
            viewHolder.mImg = (PhotoView) convertView.findViewById(R.id.gallery_item_image);
            viewHolder.mText = (TextView) convertView.findViewById(R.id.gallery_item_text);
            viewHolder.spinner = (ProgressBar) convertView.findViewById(R.id.loading);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.spinner.setVisibility(View.GONE);

        PhotoViewAttacher attacher = new PhotoViewAttacher(viewHolder.mImg);
        attacher.setRotatable(true);
        attacher.setToRightAngle(true);
        if (null != fileList.get(position).getFilePath() && !"".equals(fileList.get(position).getFilePath())) {
            try {
                viewHolder.spinner.setVisibility(View.VISIBLE);
                imageLoader.displayImage("file://" + fileList.get(position).getFilePath(), viewHolder.mImg, application.options);
//						new ImageAnimateFirstDisplayListener(viewHolder.spinner));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        viewHolder.mText.setText(fileList.get(position).getFileName());

        return convertView;
    }

    private class ViewHolder {
        ImageView mImg;
        TextView mText;
        ProgressBar spinner;
    }
}
