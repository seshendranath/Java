package cn.zxw.common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CommonUtils {
	/**
	 * 判断楼层编号与楼层是否是一个
	 * @param floor
	 * @param floorid
	 * @return
	 */
	private static boolean equalFloor(String floor,String floorid){
		if(floor==null || floorid==null){
			return false;
		}
		try {
			StringBuffer sb = new StringBuffer();
			//第一部分
			if(floorid.startsWith("2")){
				sb.append("F");
			}else if(floor.startsWith("1")){
				sb.append("B");
			}
			//第二部分
			int middle = Integer.parseInt(floorid.substring(1,floorid.length()-1));
			sb.append(middle);
			//第三部分
			if(floorid.endsWith("5")){
				sb.append("5");
			}
			
			if(floor.equals(sb.toString())){
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		
		return false;
	}
	/**
	 * floor transfer to floorid
	 * @param floor
	 * @return
	 */
	public static String parseFloorToId(String floor){
		if(floor==null || floor.length()<2){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		
		if(floor.startsWith("F")){
			sb.append("200");
		}else if(floor.startsWith("B")){
			sb.append("100");
		}
		sb.append(floor.substring(1, 2));
		
		if(floor.length()>2){
			sb.append("5");
		}else{
			sb.append("0");
		}
		return sb.toString();
	}
	/**
	 * 获得俩俩组合
	 * @param input
	 * @return
	 */
	public static List<String> getCombination(String input){
		if(input==null){
			return null;
		}
		String[] inputArray = input.split("\t");
		if(inputArray.length<2){
			return null;
		}
		List<String> pairs = new ArrayList<String>();
		for(int i=0;i<inputArray.length;i++){
			if(i==inputArray.length-1){
				break;
			}
			for(int j=i+1;j<inputArray.length;j++){
				pairs.add(inputArray[i]+"|"+inputArray[j]);
			}
		}
		return pairs;
	}
	
	public static double getFormat(Double d){
		DecimalFormat formater = new DecimalFormat("#.###");
		String r = formater.format(d);
		if(r!=null){
			return Double.parseDouble(r);
		}
		return 0;
	}
	
	
	public static void main(String[] args) {
		
	}
	
}
