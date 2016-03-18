package cn.zxw.collection.kv;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class TestKV {

	public static void main(String[] args) {
		Hashtable<String, String> ht = new Hashtable<String, String>();
		
		HashMap<String, String> hm = new HashMap<String, String>();
		Map<String, String> synMap = Collections.synchronizedMap(hm);

	}

}
