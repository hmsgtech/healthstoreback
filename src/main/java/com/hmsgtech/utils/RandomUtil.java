package com.hmsgtech.utils;

import java.util.Random;

/**
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。
 */

public class RandomUtil {

	// 这个类不能实例化
	private RandomUtil() {
	}

	public static String genRandomNum(int n) {
		Random random = new Random();
		StringBuffer sRand = new StringBuffer();
		for (int i = 0; i < n; i++) {
			sRand.append(random.nextInt(10));
		}
		return sRand.toString();
	}

	/**
	 * 生成激活码:总共10位
	 * 
	 * @param oid
	 * @return
	 */
	public static String createActivationCode(Long oid) {
		String suff = genRandomNum(10 - String.valueOf(oid).length());
		return oid.toString() + suff;
	}

	/**
	 * 幸运抽奖(3次随机:小奖多，大奖少)
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int luckDraw(int min, int max) {
		Random random = new Random();
		int x = random.nextInt(max - min) + 1;// (1-80)
		// int y = random.nextInt(x) + 1;// (1-80)
		return random.nextInt(x) + min;
	}

	/**
	 * 随机出数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static long inverseRand(int min, int max) {
		Random random = new Random();
		int[] a = new int[30];
		int[] first = new int[30];
		int[] second = new int[30];
		for (int i = 0; i < a.length; i++) {
			int x = random.nextInt(max) + 1;// (1-100)
			first[i] = x;
			int y = random.nextInt(x) + 1;//
			second[i] = y;

			a[i] = random.nextInt(y) + 1;
		}
		return 0;
	}

	/**
	 * 
	 * 冒泡排序
	 * 
	 * @return
	 */
	public static long sor(int[] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length - i - 1; j++) {
				// 这里-i主要是每遍历一次都把最大的i个数沉到最底下去了，没有必要再替换了
				if (a[j] > a[j + 1]) {
					int temp = a[j];
					a[j] = a[j + 1];
					a[j + 1] = temp;
				}
			}
		}
		System.out.println("排序之后：");
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
		return 0;
	}

}