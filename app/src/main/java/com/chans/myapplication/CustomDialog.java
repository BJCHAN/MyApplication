package com.chans.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * description :提示框
 * Created by Chans Renhenet
 * 2015/9/16
 */
public class CustomDialog {

	private Context context;
	private MyDialogClickListener myDialogClickListener;
	public final static int SURE_BUTTON = 1;
	public final static int CANCEL_BUTTON = 0;

	public interface MyDialogClickListener {
		void onclick(int id);
	}

	public CustomDialog(Context context, MyDialogClickListener myDialogClickListener) {
		this.context = context;
		this.myDialogClickListener = myDialogClickListener;
	}

	private Dialog mAlertDialog;
	private TextView dialogTitleTv;
	private Button sureButton;
	private Button cancelButton;
	private TextView contentTv;
	private ImageView contentIv;

	/**
	 * 
	 * @param context
	 * @param title  标题：传null/“”不显示
	 * @param imgResourceId 提示图标：传 0 不显示<此处传图片的id></>
	 * @param cancelString 取消按钮： 传 null 不显示
	 * @param sureString 确定按钮 ：传 null 不显示
	 * @param content 提示内容
	 */
	public void createDialog(Context context, String title, int imgResourceId, String cancelString, String sureString,
			String content) {
		this.context = context;
		RelativeLayout view = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
		mAlertDialog = new Dialog(context, R.style.TranslucentUnfullwidthWinStyle);
		mAlertDialog.setContentView(view);

		dialogTitleTv = (TextView) view.findViewById(R.id.dialog_title);
		contentIv = (ImageView) view.findViewById(R.id.content_iv);
		contentTv = (TextView) view.findViewById(R.id.content_tv);
		sureButton = (Button) view.findViewById(R.id.dialog_sure_bt);
		cancelButton = (Button) view.findViewById(R.id.dialog_cancel_bt);
		if (!TextUtils.isEmpty(title)) {
			dialogTitleTv.setVisibility(View.VISIBLE);
			dialogTitleTv.setText(title);
		} else {
			dialogTitleTv.setVisibility(View.GONE);
		}
		if (imgResourceId == 0) {
			contentIv.setVisibility(View.GONE);
		} else {
			contentIv.setVisibility(View.VISIBLE);
			contentIv.setImageResource(imgResourceId);
		}
		contentTv.setText(content);
		cancelButton.setVisibility(TextUtils.isEmpty(cancelString) ? View.GONE : View.VISIBLE);
		sureButton.setVisibility(TextUtils.isEmpty(sureString) ? View.GONE : View.VISIBLE);

		mAlertDialog.setCanceledOnTouchOutside(true);
		mAlertDialog.show();
		sureButton.setOnClickListener(new ButtonListener());
		cancelButton.setOnClickListener(new ButtonListener());
	}

	/**
	 * 点击的回调
	 */
	class ButtonListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.dialog_sure_bt:
				if (!TextUtils.isEmpty(cancelButton.getText().toString().trim())) {
					myDialogClickListener.onclick(SURE_BUTTON);
				}
				mAlertDialog.dismiss();
				break;
			case R.id.dialog_cancel_bt:
				myDialogClickListener.onclick(CANCEL_BUTTON);
				mAlertDialog.dismiss();
				break;

			default:
				break;
			}
		}
	}

}
