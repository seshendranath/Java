package cn.zxw.java.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import junit.framework.TestCase;

public class OrgJson extends TestCase{

	public void testProcessField() {
		String tagField="{'游戏':['塔防','射击']}";
		try {
			JSONObject jobj = new JSONObject(tagField);
			for (String name : JSONObject.getNames(jobj)) {
				JSONArray arr = jobj.getJSONArray(name);
				for (int i = 0; i < arr.length(); i++) {
					System.out.println(name + ":" + arr.getString(i));
				}
			}
		} catch (JSONException e) {
		}
	}
}
