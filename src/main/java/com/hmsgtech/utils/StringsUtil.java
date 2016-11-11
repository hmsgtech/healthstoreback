package com.hmsgtech.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StringsUtil {

	public final static String EMPTY_VALUE = "";

	public static boolean isEmpty(String... strings) {
		for (String str : strings) {
			if (isEmpty(str)) {
				return true;
			}
		}
		return false;
	}

	public static int countMatches(String str, String sub) {
		if (str == null || str.length() == 0 || sub == null || sub.length() == 0)
			return 0;
		int count = 0;
		for (int idx = 0; (idx = str.indexOf(sub, idx)) != -1; idx += sub.length())
			count++;

		return count;
	}

	public static String substringBetween(String str, String tag) {
		return substringBetween(str, tag, tag);
	}

	public static String substringBetween(String str, String open, String close) {
		if (str == null || open == null || close == null)
			return null;
		int start = str.indexOf(open);
		if (start != -1) {
			int end = str.indexOf(close, start + open.length());
			if (end != -1)
				return str.substring(start + open.length(), end);
		}
		return null;
	}

	public static boolean isAllEmpty(String... strings) {
		int count = 0;
		for (String str : strings) {
			if (isEmpty(str)) {
				count = count + 1;
			}
		}
		return count == strings.length;
	}

	public static boolean isNotEmpty(String... strings) {
		return !isEmpty(strings);
	}

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str))
			return true;
		if ("".equals(str.trim()))
			return true;
		return false;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isEmpty(Character c) {
		if (null == c)
			return true;
		if ("".equals(c))
			return true;
		return false;
	}

	public static boolean isInt(String str) {
		return str.matches("^[0-9]*$");
	}

	// 嗖付支付传过来的金额是"分"
	public static boolean isFen(String str) {
		return str.matches("^[0-9]+$");
	}

	public static String join(Object array[], String separator) {
		if (array == null)
			return null;
		if (separator == null)
			separator = "";
		int arraySize = array.length;
		int bufSize = arraySize != 0 ? arraySize * ((array[0] != null ? array[0].toString().length() : 16) + (separator == null ? 0 : separator.length())) : 0;
		StringBuffer buf = new StringBuffer(bufSize);
		for (int i = 0; i < arraySize; i++) {
			if (separator != null && i > 0)
				buf.append(separator);
			if (array[i] != null)
				buf.append(array[i]);
		}

		return buf.toString();
	}

	public static String join(String split, String... values) {
		StringBuilder builder = new StringBuilder();
		for (String s : values) {
			builder.append(s).append(split);
		}
		if (!isEmpty(split)) {
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}

	public static String fillZero(int num, int width) {
		if (num < 0)
			return "";
		StringBuffer sb = new StringBuffer();
		String s = "" + num;
		if (s.length() < width) {
			int addNum = width - s.length();
			for (int i = 0; i < addNum; i++) {
				sb.append("0");
			}
			sb.append(s);
		} else {
			return s.substring(s.length() - width, s.length());
		}
		return sb.toString();
	}

	public static String fillZero(String preix, int num, int width) {
		if (num < 0)
			return "";
		StringBuffer sb = new StringBuffer();
		sb.append(preix);
		String s = "" + num;
		if (s.length() < width) {
			int addNum = width - s.length();
			for (int i = 0; i < addNum; i++) {
				sb.append("0");
			}
			sb.append(s);
		} else {
			return s.substring(s.length() - width, s.length());
		}
		return sb.toString();
	}

	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	public static String compressVersion(String version) {
		StringBuffer compressedVersion = new StringBuffer();
		if (version != null && !isEmpty(version)) {
			if (version.contains(".")) {
				String[] segments = version.split(".");
				for (String segment : segments) {
					compressedVersion.append(segment);
				}
			}
		}
		return compressedVersion.toString();
	}

	public static StringBuilder deleteEndTag(StringBuilder builder, String tag) {
		if (builder != null) {
			if (builder.toString().endsWith(tag)) {
				builder.deleteCharAt(builder.length() - 1);
			}
		}
		return builder;
	}

	/* 字符串转list */
	public static List<Integer> strToList(String str) {
		if (isEmpty(str)) {
			return new LinkedList<Integer>();
		}
		List<Integer> integers = new LinkedList<Integer>();
		String[] strs = str.replaceAll("[\\s()\\[\\]]", "").split(",");
		for (int i = 0; i < strs.length; i++) {
			integers.add(Integer.parseInt(strs[i]));
		}
		return integers;
	}

	/* 字符串转list */
	public static List<String> strToListStr(String str) {
		List<String> integers = new ArrayList<String>();
		String[] strs = str.replaceAll("[\\s()\\[\\]]", "").split(",");
		for (int i = 0; i < strs.length; i++) {
			integers.add((strs[i]));
		}
		return integers;
	}

	/* 去重复元素 */
	public static <T> void removeDuplicate(List<T> list) {
		HashSet<T> h = new HashSet<T>(list);
		list.clear();
		list.addAll(h);
	}

	/**
	 * 批量替换内容
	 * 
	 * @param map
	 * @param text
	 * @return
	 */
	public static String replaceMap(Map<String, String> map, String text) {
		for (Entry<String, String> entry : map.entrySet()) {
			if (isNotEmpty(entry.getKey())) {
				text = text.replace(entry.getKey(), entry.getValue());
			}
		}
		return text;
	}

}
