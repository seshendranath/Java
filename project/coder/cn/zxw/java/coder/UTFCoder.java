package cn.zxw.java.coder;

import junit.framework.TestCase;

public class UTFCoder extends TestCase{
	//UTF-8编码转换为字符
	public void testRevert() {
		String str = "\\u89d2\\u8272\\u626e\\u6f14";
		if (str != null && str.trim().length() > 0) {
			String un = str.trim();
			StringBuffer sb = new StringBuffer();
			int idx = un.indexOf("\\u");
			while (idx >= 0) {
				if (idx > 0) {
					sb.append(un.substring(0, idx));
				}
				String hex = un.substring(idx + 2, idx + 2 + 4);
				sb.append((char) Integer.parseInt(hex, 16));
				un = un.substring(idx + 2 + 4);
				idx = un.indexOf("\\u");
			}
			sb.append(un);
			System.out.println(sb.toString());
		}
	}

}
