package com.chans.myapplication;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.HashSet;
import java.util.Set;

public class PinyinUtil {

	/**
	 * 获取汉字串拼音首字母，英文字符不变
	 * 
	 * @param chinese
	 *            汉字串
	 * @return 汉语拼音首字母
	 */
	public static String cn2FirstSpell(String chinese) {
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
		hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		for (int i = 0; i < arr.length; i++) {
			if ((String.valueOf(arr[i]).matches("[\\u4E00-\\u9FA5]+"))) {
				try {
					String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i], hanYuPinOutputFormat);
					if (_t != null) {
						pybf.append(_t[0].charAt(0));
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else if (((int) arr[i] >= 65 && (int) arr[i] <= 90) || ((int) arr[i] >= 97 && (int) arr[i] <= 122)) {
				pybf.append(arr[i]);
			}
		}
		return pybf.toString().replaceAll("\\W", "").trim();
	}

	/**
	 * 获取汉字串拼音首字母，英文字符不变(不排除非法字符)
	 * 
	 * @param chinese
	 *            汉字串
	 * @return 汉语拼音首字母
	 */
	public static String cn2FirstSpellWithout(String chinese) {
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
		hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		for (int i = 0; i < arr.length; i++) {
			boolean ishanyu = String.valueOf(arr[0]).matches("[\\u4E00-\\u9FA5]+");//判断第一个字符是不是汉字或拼音
			boolean iszimu = ((int) arr[0] >= 65 && (int) arr[0] <= 90) || ((int) arr[0] >= 97 && (int) arr[0] <= 122);
			if (!ishanyu && !iszimu) {
				pybf.append("#");
			} else {
				if ((String.valueOf(arr[i]).matches("[\\u4E00-\\u9FA5]+"))) {
					try {
						String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i], hanYuPinOutputFormat);
						if (_t != null) {
							pybf.append(_t[0].charAt(0));
						}
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}
				} else if (((int) arr[i] >= 65 && (int) arr[i] <= 90) || ((int) arr[i] >= 97 && (int) arr[i] <= 122)) {
					pybf.append(arr[i]);
				}
			}
		}
		return pybf.toString().replaceAll("\\W", "").trim();
	}

	/**
	 * 获取汉字串拼音首字母，英文字符不变,标点，空格等保持不变
	 * @param chinese 汉字串
	 * @return 汉语拼音首字母（转化成大写）
	 */
	public static String cn2FirstSpellUppercase(String chinese) {
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
		hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		for (int i = 0; i < arr.length; i++) {
			boolean ishanyu = String.valueOf(arr[i]).matches("[\\u4E00-\\u9FA5]+");
			boolean iszimu = ((int) arr[i] >= 65 && (int) arr[i] <= 90) || ((int) arr[i] >= 97 && (int) arr[i] <= 122);
			//判断第一个字符是不是汉字或拼音
			if (!ishanyu && !iszimu) {
				pybf.append(arr[i]);
			} else {
				if ((String.valueOf(arr[i]).matches("[\\u4E00-\\u9FA5]+"))) {
					try {
						String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i], hanYuPinOutputFormat);
						if (_t != null) {
							pybf.append(_t[0].charAt(0));
						}
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}
				} else if (((int) arr[i] >= 65 && (int) arr[i] <= 90) || ((int) arr[i] >= 97 && (int) arr[i] <= 122)) {
					//转化成大写
					if ((int) arr[i] >= 97 && (int) arr[i] <= 122) {
						arr[i] -= 32;
					}
					pybf.append(arr[i]);
				}
			}
		}
		return pybf.toString();
	}

	/**
	 * 获取汉字串拼音，英文字符不变
	 * 
	 * @param chinese
	 *            汉字串
	 * @return 汉语拼音
	 */
	public static String cn2Spell(String chinese) {
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
		hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		for (int i = 0; i < arr.length; i++) {
			if ((String.valueOf(arr[i]).matches("[\\u4E00-\\u9FA5]+"))) {
				try {
					pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], hanYuPinOutputFormat)[0]);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else if (((int) arr[i] >= 65 && (int) arr[i] <= 90) || ((int) arr[i] >= 97 && (int) arr[i] <= 122)) {
				pybf.append(arr[i]);
			}
		}
		return pybf.toString();
	}

	/**
	 * 获取拼音集合
	 * 
	 * @author xp
	 * @param src
	 * @return Set<String>
	 */
	public static Set<String> getPinyin(String src) {
		if (src != null && !src.trim().equalsIgnoreCase("")) {
			char[] srcChar;
			srcChar = src.toCharArray();
			// 汉语拼音格式输出类
			HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();

			// 输出设置，大小写，音标方式等
			hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

			String[][] temp = new String[src.length()][];
			for (int i = 0; i < srcChar.length; i++) {
				char c = srcChar[i];
				// 是中文或者a-z或者A-Z转换拼音(我的需求，是保留中文或者a-z或者A-Z)
				if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
					try {
						temp[i] = PinyinHelper.toHanyuPinyinStringArray(srcChar[i], hanYuPinOutputFormat);
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}
				} else if (((int) c >= 65 && (int) c <= 90) || ((int) c >= 97 && (int) c <= 122)) {
					temp[i] = new String[] { String.valueOf(srcChar[i]) };
				} else {
					temp[i] = new String[] { "" };
				}
			}
			String[] pingyinArray = exchange(temp);
			Set<String> pinyinSet = new HashSet<String>();
			for (int i = 0; i < pingyinArray.length; i++) {
				pinyinSet.add(pingyinArray[i]);
			}
			return pinyinSet;
		}
		return null;
	}

	public static String[] exchange(String[][] strJaggedArray) {
		String[][] temp = doExchange(strJaggedArray);
		return temp[0];
	}

	private static String[][] doExchange(String[][] strJaggedArray) {
		int len = strJaggedArray.length;
		if (len >= 2) {
			int len1 = strJaggedArray[0].length;
			int len2 = strJaggedArray[1].length;
			int newlen = len1 * len2;
			String[] temp = new String[newlen];
			int Index = 0;
			for (int i = 0; i < len1; i++) {
				for (int j = 0; j < len2; j++) {
					temp[Index] = strJaggedArray[0][i] + strJaggedArray[1][j];
					Index++;
				}
			}
			String[][] newArray = new String[len - 1][];
			for (int i = 2; i < len; i++) {
				newArray[i - 1] = strJaggedArray[i];
			}
			newArray[0] = temp;
			return doExchange(newArray);
		} else {
			return strJaggedArray;
		}
	}

	public static void main(String[] args) {
		String str = "单雨";
	}

}
