package com.chans.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

/**
 * description : �ı�����
 * Created by Chans 
 * 2015/7/13
 */
public class HighlightTextUtil {

	/**
	 * @param toString ����������string
	 * @param context  ����
	 * @return
	 */
	public SpannableString toHighlight(Context ct, String toString, String context) {
		SpannableString spannableString = new SpannableString(context);
		if (!TextUtils.isEmpty(toString)) {
			//			String[] c = context.split(toString);
			//			int length = 0;
			//			for (int i = 0, s = c.length; i < s; i++) {
			//				if (length + c[i].length() == context.length())//��󲻰��������
			//					break;
			//				spannableString.setSpan(new ForegroundColorSpan(Color.RED), length + c[i].length(), length + c[i].length()
			//						+ toString.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			//				length += c[i].length() + toString.length();
			//			}
			char[] c = toString.toCharArray();
			for (int i = 0, s = c.length; i < s; i++) {
				if ((c[i] >= 'A' && c[i] <= 'Z') || (c[i] >= 'a' && c[i] <= 'z')) {
					//转化为大写去比较
					if ((c[i] >= 'a' && c[i] <= 'z')) {
						c[i] -= 32;
					}
					String pyContext = PinyinUtil.cn2FirstSpellUppercase(context);//转化成拼音
					int position = pyContext.indexOf(c[i]);
					while (position != -1) {
						spannableString.setSpan(new ForegroundColorSpan(Color.RED), position, position + 1,
								Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
						position = pyContext.indexOf(c[i], position + 1);
					}
				} else {
					int position = context.indexOf(c[i]);
					while (position != -1) {
						spannableString.setSpan(new ForegroundColorSpan(Color.RED), position, position + 1,
								Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
						position = context.indexOf(c[i], position + 1);
					}
				}
			}
		}
		return spannableString;
	}
}
