package cn.zxw.java.regex;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Regex Function
 */
public class JavaRegex {

	@Test
	public void test() {//匹配
		Pattern pattern = Pattern.compile("^Java.*");
		Matcher matcher = pattern.matcher("Java速度");
		boolean b = matcher.matches();
		assertTrue(b);
	}
	@Test
	public void test1() {//分割字符串时
		Pattern pattern = Pattern.compile("[, |]+");
		String[] strs = pattern.split("Java Hello World  Java,Hello,,World|Sun");
		for (int i=0;i<strs.length;i++) {
		    System.out.println(strs[i]);
		}
	}
	@Test
	public void test2() {//替换
		Pattern pattern = Pattern.compile("正则表达式");
		Matcher matcher = pattern.matcher("正则表达式 Hello World,正则表达式 Hello World");
		System.out.println(matcher.replaceFirst("Java"));
		System.out.println(matcher.replaceAll("Java"));
	}
	@Test
	public void test3() {//置换字符
		Pattern pattern = Pattern.compile("正则表达式");
		Matcher matcher = pattern.matcher("正则表达式 Hello World,正则表达式 Hello World ");
		StringBuffer sbr = new StringBuffer();
		while (matcher.find()) {
		    matcher.appendReplacement(sbr, "Java");
		}
		matcher.appendTail(sbr);
		System.out.println(sbr.toString());
	}
	
}
