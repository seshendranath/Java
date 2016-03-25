package cn.zxw.java.regex;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class TestRegex {

	@Test
	public void test() {
		//Pattern pattern=Pattern.compile("J{3,}");
		Pattern pattern=Pattern.compile("J{3,5}");
		Matcher matcher=pattern.matcher("JJJJJ");
		assertTrue(matcher.matches());
	}
	@Test
	public void test1() {// check email address
		String str = "ceponline@yahoo.com.cn";
		Pattern pattern = Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		assertTrue(matcher.matches());
	}
	@Test
	public void test2() {
		String log="/log?type=click&cuid=73B0320A35DF4BE3B7A84DC584016C72&device=Xiaomi_2014812&did=33&iid=8&imi=Iz99TCJanvu6VJVd3lHf/Q==&url=http://www.baidu.com&nocache=0.5464766153600067 HTTP/1.1";
		Pattern pattern=Pattern.compile(
		"([\\w\\.\\?/&=:]+)\\s+.*");
		Matcher matcher=pattern.matcher(log);
		assertTrue(matcher.matches());

	}
}
