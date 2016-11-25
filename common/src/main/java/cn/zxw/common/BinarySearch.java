package com.huanju.util;

public class BinarySearch {
	public static void main(String[] args) {
		int[] arr = new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 };
		System.out.println(binarySearch(arr, 21));
	}

	/**
	 * 二分查找算法
	 * @param a
	 * @param searchKey
	 * @return
	 */
	public static int binarySearch(int[] a, int searchKey) {
		int low = 0;
		int max = a.length - 1;
		while (true) {
			// 取中间位置
			int mid = (low + max) / 2;
			if (a[mid] == searchKey) {
				return mid;
			} else if (low > max) {
				return -1;
			} else if (a[mid] > searchKey) {
				max = mid - 1;
			} else if (a[mid] < searchKey) {
				low = mid + 1;
			}
		}
	}
}
