package cn.zxw.java.regex;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BCDLogParse {

	public static void main(String[] args) throws Exception {
		String log="[24/Mar/2016:09:44:19 +0000] 192.168.1.112 GET /log?type=click&cuid=73B0320A35DF4BE3B7A84DC584016C72&device=Xiaomi_2014812&did=33&iid=8&imi=Iz99TCJanvu6VJVd3lHf/Q==&url=http://www.baidu.com&nocache=0.5464766153600067 HTTP/1.1";
		Pattern pattern=Pattern.compile(
	    "\\s*\\[(.*)\\]\\s+(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})\\s+\\w+\\s+([\\w\\.\\?/&=:]+)\\s+.*");
		Matcher matcher=pattern.matcher(log);
		//System.out.println(matcher.matches());
		if (!matcher.find()) {
			System.out.println("Not Match");
			return;
		}
		String dt=matcher.group(1);
		String ip=matcher.group(2);
		String param=matcher.group(3);
		String type=null;
		String cuid=null;
		String device=null;
		String did=null;
		String iid=null;
		String imi=null;
		String url=null;
		String[] params=param.substring(param.indexOf("?")+1).split("&");
		for(String kv : params){
			int index=kv.indexOf("=");
			if(index < 0){
				System.out.println("param error");
				break;
			}
			String key=kv.substring(0,index);
			String value=kv.substring(index+1);
			if("nocache".equalsIgnoreCase(key)){
				continue;
			}else if("type".equalsIgnoreCase(key)){
				type=value;
			}else if("cuid".equalsIgnoreCase(key)){
				cuid=value;
			}else if("device".equalsIgnoreCase(key)){
				device=value;
			}else if("did".equalsIgnoreCase(key)){
				did=value;
			}else if("iid".equalsIgnoreCase(key)){
				iid=value;
			}else if("imi".equalsIgnoreCase(key)){
				imi=value;
			}else if("url".equalsIgnoreCase(key)){
				url=value;
			}
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy:hh:mm:ss Z", Locale.ENGLISH);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = formatter.parse(dt);
		System.out.println("转换后的日期格式："+format.format(date));
		dt=format.format(date);
		System.out.println(dt);
		System.out.println(ip);
		System.out.println(type);
		System.out.println(cuid);
		System.out.println(device);
		System.out.println(did);
		System.out.println(iid);
		System.out.println(imi);
		System.out.println(url);
		StringBuilder builder=new StringBuilder();
		builder.append(dt).append("\t").append(ip).append("\t")
		       .append(type).append("\t").append(cuid).append("\t")
		       .append(device).append("\t").append(did).append("\t")
		       .append(iid).append("\t").append(imi).append("\t")
		       .append(url);
		System.out.println(builder.toString());
	}

}
