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
}
