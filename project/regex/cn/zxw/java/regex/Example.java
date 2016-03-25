package cn.zxw.java.regex;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class Example {

	@Test
	public void test() {// check email address
		String str = "ceponline@yahoo.com.cn";
		Pattern pattern = Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		assertTrue(matcher.matches());
	}

	@Test
	public void test1() {// 去除html标记
		Pattern pattern = Pattern.compile("<.+?>", Pattern.DOTALL);
		Matcher matcher = pattern.matcher("<a href=/'index.html/'>主页</a>");
		String string = matcher.replaceAll("");
		System.out.println(string);
	}

	@Test
	public void test2() {// 查找html中对应条件字符串
		Pattern pattern = Pattern.compile("href='(.+?)'");
		Matcher matcher = pattern.matcher("<a href='index.html'>主页</a>");
		if (matcher.find()) {
			System.out.println(matcher.group(1));
		}
	}

	@Test
	public void test3() {// 截取url
		Pattern pattern = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:]+");
		Matcher matcher = pattern.matcher("dsdsds<http://www.baidu.com/address>fdfdfdf");
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			buffer.append(matcher.group());
			buffer.append("\r\n");
			System.out.println(buffer.toString());
		}
	}

	@Test
	public void test4() {// 替换指定{}中文字
		String str = "Java目前的发展史是由{0}年-{1}年";
		String[][] object = { new String[] { "//{0//}", "1995" },
				new String[] { "//{1//}", "2007" } };
		System.out.println(replace(str, object));

	}

	public static String replace(final String sourceString, Object[] object) {
		String temp = sourceString;
		for (int i = 0; i < object.length; i++) {
			String[] result = (String[]) object[i];
			Pattern pattern = Pattern.compile(result[0]);
			Matcher matcher = pattern.matcher(temp);
			temp = matcher.replaceAll(result[1]);
		}
		return temp;
	}

}
