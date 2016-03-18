package cn.zxw.util.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.zxw.util.date.DateUtils;

public class IOUtil {
	/**
	 * 
	 * @param path "ex:c:\\dir"
	 */
	public static void mvFile(String path) {
		File fileDir = new File(path);
		if (!fileDir.exists() || !fileDir.isDirectory()) {
			System.out.println("file not exists() || !fileDir.isDirectory()");
			return;
		}
		for (File file : fileDir.listFiles()) {
			String name = file.getName();
			String date = name.substring(19, 29);
			String newFilePath=path + "\\" + date +"\\"+ name;
			System.out.println(newFilePath);
			File newfile = new File(newFilePath);
			if (!newfile.exists()) {
				if (!newfile.getParentFile().exists()) {
					newfile.getParentFile().mkdirs();
				}
				// newfile.createNewFile();
			}
			file.renameTo(newfile);
		}
	}
	/**
	 * 将Set<String>写入文件
	 * @param set
	 * @param fileName
	 */
	public static void writeSetToFile(Set<String> set,String fileName) {
		try{
			File file = new File(fileName);
			if (!file.exists()) {
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			for(String s:set){
				bw.write(s + "\r\n");
			}
			bw.close();
			fw.close();
		}catch(IOException e1){
			e1.printStackTrace();
		}
	}
	/**
	 * 将Set<String>写入文件
	 * @param set
	 * @param fileName
	 */
	public static void writeListToFile(List<String> set,String fileName) {
		try{
			File file = new File(fileName);
			if (!file.exists()) {
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			for(String s:set){
				bw.write(s + "\r\n");
			}
			bw.close();
			fw.close();
		}catch(IOException e1){
			e1.printStackTrace();
		}
	}
	/**
	 * 将Map<Object, Object>写入文件
	 * @param map
	 * @param fileName
	 */
	public static void writeMapOOToFile(Map<Object, Object> map, String fileName) {
		try{
			File file = new File(fileName);
			if (!file.exists()) {
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			for(Object s:map.keySet()){
				bw.write(s.toString() + "\t"+map.get(s)+"\r\n");
			}
			bw.close();
			fw.close();
		}catch(IOException e1){
			e1.printStackTrace();
		}
	}
	/**
	 * 将Map<String, String>写入文件
	 * @param map
	 * @param fileName
	 */
	public static void writeMapSSToFile(Map<String, String> map, String fileName) {
		try{
			File file = new File(fileName);
			if (!file.exists()) {
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			for(String s:map.keySet()){
				bw.write(s + "\t"+map.get(s)+"\r\n");
			}
			bw.close();
			fw.close();
		}catch(IOException e1){
			e1.printStackTrace();
		}
	}
	/**
	 * 读取文件
	 * @param fileName
	 * @param separator 字段分隔符,默认为\t,默认时传入null,传空串""时表示不切分
	 * @return Map<Object, Object[]>, key:行号,value:行切分后数组
	 */
	public static Map<Object, Object[]> readFile(String fileName,String separator) {
		if(separator==null){
			separator="\t";
		}
		Map<Object, Object[]> res=new HashMap<Object, Object[]>();
		try{
			File file = new File(fileName);
			if (!file.exists()) {
				System.out.println("ERROR:文件不存在["+fileName+"]");
				return res;
			}
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String tempString = null;
			int line=0;
			String[] arr=null;
			while((tempString = br.readLine()) != null){
				if("".equals(separator)){
					arr=new String[]{tempString};
				}else{
					arr=tempString.split(separator);
				}
				res.put(++line,arr);
			}
			br.close();  
			fr.close();
		}catch(IOException e1){
			e1.printStackTrace();
		}
		return res;
	}
	/**
	 * 将文件排序输出
	 * @param fileName  原始文件
	 * @param separator  字段分隔符,默认为\t,默认时传入null,传空串""时表示不切分
	 * @param sortField  排序的字段位置,从0开始,如第二个字段排序时传1
	 * @param isDesc 是否降序
	 * @param newFileName 排序后输出的文件名
	 * @return
	 */
	public static boolean sort(String fileName,String separator,int sortField,boolean isDesc,String newFileName) {
		if(separator==null){
			separator="\t";
		}
		Map<Integer, List<String>> res=new HashMap<Integer, List<String>>();
		try{
			//read file
			File file = new File(fileName);
			if (!file.exists()) {
				System.out.println("ERROR:文件不存在["+fileName+"]");
				return false;
			}
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String tempString = null;
			String[] arr=null;
			List<String> value=null;
			while((tempString = br.readLine()) != null){
				if("".equals(separator)){
					arr=new String[]{tempString};
				}else{
					arr=tempString.split(separator);
				}
				Integer key=new Integer(arr[sortField]);
				if(res.containsKey(key)){
					value=res.get(key);
				}else{
					value=new ArrayList<String>();
				}
				value.add(tempString);
				res.put(key, value);
			}
			br.close();  
			fr.close();
			//sort
			List<Integer> sort=new ArrayList<Integer>(res.keySet());
			Collections.sort(sort);
			//write file
			File wfile = new File(newFileName);
			if (!wfile.exists()) {
				if(!wfile.getParentFile().exists()){
					wfile.getParentFile().mkdirs();
				}
				wfile.createNewFile();
			}
			FileWriter fw = new FileWriter(wfile.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			int len=sort.size();
			for(int i=0;i<len;i++){
				Integer key=null;
				if(isDesc){
					key=sort.get(len-i-1);
				}else{
					key=sort.get(i);
				}
				List<String> vs=res.get(key);
				for(String s:vs){
					bw.write(s+"\r\n");
				}
			}
			bw.close();
			fw.close();
		}catch(IOException e1){
			e1.printStackTrace();
		}
		return true;
	}
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @throws ParseException
	 */
	public static void mkdirDate(String startDate,String endDate,String path) throws ParseException{
		Calendar start = Calendar.getInstance();
		start.setTime(DateUtils.parseToDate(startDate));
		Long startTime = start.getTimeInMillis();

		Calendar end = Calendar.getInstance();
		end.setTime(DateUtils.parseToDate(endDate));
		Long endTime = end.getTimeInMillis();

		Long oneDay = 1000 * 60 * 60 * 24l;

		Long time = startTime;
		
		while (time <= endTime) {
			Date d = new Date(time);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String dir=path+"\\"+df.format(d);
			File file=new File(dir);
			if(!file.exists()){
				file.mkdirs();
			}
			time += oneDay;
		}

	}
	/**
	 * 读取输入流数据，返回字符串
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
