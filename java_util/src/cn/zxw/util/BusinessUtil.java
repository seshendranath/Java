package cn.zxw.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.zxw.util.io.IOUtil;

public class BusinessUtil {
	/**
	 * 对一个目录下所有原始数据做mac去重，得到mac数即客流量
	 * @param dataDir
	 * @param destFile 记录mac去重后数据,为null即不生成文件
	 * @param resNumFile 记录mac数目,为null即不生成文件
	 */
	public static void filterInitDataMul(String dataDir,String destFile,String resNumFile) {
		//read initial data
		Set<String> res=new HashSet<String>();//过滤后的记录数
		Set<String> data=new HashSet<String>();//过滤后的数据
		File fileParentPath=new File(dataDir);
		FileReader fr=null;
		BufferedReader br=null;
		String tempString = null;
		try {
			for(File f:fileParentPath.listFiles()){
				if(f.getName().indexOf("processed")>=0||f.getName().endsWith("pre.csv")/*||f.getName().indexOf("2014-12-23 23")>=0*/){
					continue;
				}
				fr=new FileReader(f);
				br=new BufferedReader(fr);
				tempString = null;
				while((tempString = br.readLine()) != null){
					String[] arr=tempString.split("\t");
					data.add(arr[1]);
				}
				br.close();  
				fr.close();
			}
			res.add("all mac not mul:"+data.size()+"");
			System.out.println("mac count:"+data.size());
			if(destFile!=null||!"".equals(destFile)){
				IOUtil.writeSetToFile(data,destFile);
			}
			if(resNumFile!=null||!"".equals(resNumFile)){
				IOUtil.writeSetToFile(res,resNumFile);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 2.filter [black_list] -->get number [total]
	 */
	public static void filterBlackList(String macAllNoMulFileStr,String blackListFileStr,String filterBlackListMac,String lastReportFile) {
		System.out.println("filterBlackList start...");
		Set<String> res=new HashSet<String>();
		Set<String> resLen=new HashSet<String>();
		FileReader fr=null;
		BufferedReader br=null;
		String tempString = null;
		try {
			//read macAllNoMulFile
			Set<String> data=new HashSet<String>();
			File macAllNoMulFile=new File(macAllNoMulFileStr);
			fr=new FileReader(macAllNoMulFile);
			br=new BufferedReader(fr);
			tempString = null;
			while((tempString = br.readLine()) != null){
				data.add(tempString);
			}
			br.close();  
			fr.close();
			System.out.println(data.size());
			//read blackListFile
			Set<String> blackList=new HashSet<String>();
			File blackListFile=new File(blackListFileStr);
			fr=new FileReader(blackListFile);
			br=new BufferedReader(fr);
			tempString = null;
			while((tempString = br.readLine()) != null){
				String[] arr=tempString.split("\t");
				blackList.add(arr[2]);
			}
			br.close();  
			fr.close();
			System.out.println(blackList.size());
			//filter
			for(String mac:data){
				if(!blackList.contains(mac)){
					res.add(mac);
				}
			}
			resLen.add("validMac(filter blackList):"+res.size()+"");
			System.out.println("validMac(filter blackList):"+res.size());
			
			IOUtil.writeSetToFile(res,filterBlackListMac);
			IOUtil.writeSetToFile(resLen,lastReportFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 3.get mac_pre and count
	 */
	public static void getMacPreAndCount(String filterBlackListMac,String macPreAndCount) {
		//read filterBlackListMac
		Map<String,String> res=new HashMap<String,String>();
		FileReader fr=null;
		BufferedReader br=null;
		String tempString = null;
		try {
			//read macAllNoMulFile
			File filterBlackListMacFile=new File(filterBlackListMac);
			fr=new FileReader(filterBlackListMacFile);
			br=new BufferedReader(fr);
			tempString = null;
			while((tempString = br.readLine()) != null){
				String mac_pre=tempString.substring(0, 6);
				if(res.containsKey(mac_pre)){
					int value=Integer.parseInt(res.get(mac_pre));
					value++;
					res.put(mac_pre, value+"");
				}else{
					res.put(mac_pre, "1");
				}
			}
			br.close();  
			fr.close();
			IOUtil.writeMapSSToFile(res,macPreAndCount);
			System.out.println("getMacPreAndCount finished!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 4.filter [mac_co] -->get number [other_total]
	 */
	public static void filterMacCoToOtherAll(String macPreAndCount,String macCo,String resFile,String lastReportFile) {
		System.out.println("filterMacCoToOtherAll start...");
		Map<String,String> res=new HashMap<String,String>();
		FileReader fr=null;
		BufferedReader br=null;
		String tempString = null;
		try {
			//read MacCo
			Set<String> mac_co=new HashSet<String>();
			File macCoFile=new File(macCo);
			fr=new FileReader(macCoFile);
			br=new BufferedReader(fr);
			tempString = null;
			while((tempString = br.readLine()) != null){
				String[] arr=tempString.split("\t");
				mac_co.add(arr[1]);
			}
			br.close();  
			fr.close();
			System.out.println("mac_co size:"+mac_co.size());
			//read macPreAndCount
			Map<String,String> macPreAndCountMap=new HashMap<String,String>();
			File macPreAndCountFile=new File(macPreAndCount);
			fr=new FileReader(macPreAndCountFile);
			br=new BufferedReader(fr);
			tempString = null;
			while((tempString = br.readLine()) != null){
				String[] arr=tempString.split("\t");
				macPreAndCountMap.put(arr[0],arr[1]);
			}
			br.close();  
			fr.close();
			//filter
			for(String key:macPreAndCountMap.keySet()){
				if(!mac_co.contains(key)){
					res.put(key, macPreAndCountMap.get(key));
				}
			}
			IOUtil.writeMapSSToFile(res,resFile);
			//Set<String> resLen=new HashSet<String>();
			System.out.println("filterMacCoToOtherAll finished!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 5.[other_total] filter [macpre_blacklist]-->get number [calc_other]
	 */
	public static void filterMacPreBlackListToValidOther(String filterMacCo,String macpreBlackList,String resFile,String lastReportFile) {
		System.out.println("filterMacPreBlackListToValidOther start...");
		FileReader fr=null;
		BufferedReader br=null;
		String tempString = null;
		try {
			//read macpreBlackList
			Set<String> macpre_BlackList=new HashSet<String>();
			File macpreBlackListFile=new File(macpreBlackList);
			fr=new FileReader(macpreBlackListFile);
			br=new BufferedReader(fr);
			tempString = null;
			while((tempString = br.readLine()) != null){
				String[] arr=tempString.split("\t");
				macpre_BlackList.add(arr[0]);
			}
			br.close();  
			fr.close();
			System.out.println("macpre_BlackList size:"+macpre_BlackList.size());
			//read filterMacCo
			Map<String,String> filterMacCoMap=new HashMap<String,String>();
			File filterMacCoFile=new File(filterMacCo);
			fr=new FileReader(filterMacCoFile);
			br=new BufferedReader(fr);
			tempString = null;
			while((tempString = br.readLine()) != null){
				String[] arr=tempString.split("\t");
				if(arr.length!=2){
					System.out.println(tempString);
					continue;
				}
				if(!macpre_BlackList.contains(arr[0])){
					filterMacCoMap.put(arr[0],arr[1]);
				}
			}
			br.close();  
			fr.close();
			IOUtil.writeMapSSToFile(filterMacCoMap,resFile);
			//
			Set<String> last=new HashSet<String>();
			int validOtherNum=0;
			for(String key:filterMacCoMap.keySet()){
				int value=Integer.parseInt(filterMacCoMap.get(key));
				validOtherNum+=value;
			}
			last.add(validOtherNum+"");
			IOUtil.writeSetToFile(last,lastReportFile);
			System.out.println("filterMacPreBlackListToValidOther finished!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 6.calc_other/(total-other_total+calc_other) --> other zhanbi
	 * 7.calc_other sort
	 */
	public static void getResult(String filterMacpreBlackList,String macNet,String resFile,String lastReportFile) {
		System.out.println("getResult start...");
		FileReader fr=null;
		BufferedReader br=null;
		String tempString = null;
		try {
			//read macNet
			Map<String,String> macNetMap=new HashMap<String,String>();
			File macNetFile=new File(macNet);
			fr=new FileReader(macNetFile);
			br=new BufferedReader(fr);
			tempString = null;
			while((tempString = br.readLine()) != null){
				String[] arr=tempString.split("\t");
				macNetMap.put(arr[0],arr[1]);
			}
			br.close();  
			fr.close();
			System.out.println("macNetMap size:"+macNetMap.size());
			//read filterMacpreBlackList
			Map<String,String> filterMacpreBlackListMap=new HashMap<String,String>();
			File filterMacpreBlackListFile=new File(filterMacpreBlackList);
			fr=new FileReader(filterMacpreBlackListFile);
			br=new BufferedReader(fr);
			tempString = null;
			while((tempString = br.readLine()) != null){
				String[] arr=tempString.split("\t");
				if(macNetMap.keySet().contains(arr[0])){
					filterMacpreBlackListMap.put(arr[0],arr[1]+"\t"+macNetMap.get(arr[0]));
				}else{
					filterMacpreBlackListMap.put(arr[0], arr[1]+"\t");
				}
			}
			br.close();  
			fr.close();
			IOUtil.writeMapSSToFile(filterMacpreBlackListMap,resFile);
			//6.calc_other/(total-other_total+calc_other) --> other zhanbi
			/*Set<String> last=new HashSet<String>();
			File lastFile=new File(lastReportFile);
			fr=new FileReader(lastFile);
			br=new BufferedReader(fr);
			tempString = null;
			while((tempString = br.readLine()) != null){
				String[] arr=tempString.split("\t");
				macNetMap.put(arr[0],arr[1]);
			}
			br.close();  
			fr.close();
			last.add(validOtherNum+"");
			IOUtil.writeSetToFile(last,resFile);*/
			//7.calc_other sort
			IOUtil.sort(resFile, null, 1, true, resFile+"-sort.txt");
			System.out.println("getResult finished!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 处理网上取到的mac前缀对应厂商文件,得到[mac_pre factory]
	 * @param macFile
	 * @param newFile
	 */
	public static boolean filterMacFromNet(String macFile,String newFile) {
		try {
			//load mac.txt
			Set<String> macMap=new HashSet<String>();
			File f=new File(macFile);
			if(!f.exists()){
				System.out.println("file not exist:"+macFile);
				return false;
			}
			FileReader fr=new FileReader(f);
			BufferedReader br=new BufferedReader(fr);
			String tempString=null;
			while((tempString = br.readLine()) != null){
				tempString=tempString.trim();
				if(tempString.length()>6 && tempString.indexOf("(base 16)")>=0){
					String[] arr=tempString.split("\\(base 16\\)");
					if(arr[0].trim().length()==6 && arr.length==2){
						macMap.add(arr[0].trim()+"\t"+arr[1].trim());
					}else{
						System.out.println(tempString);
					}
				}
			}
			br.close();  
			fr.close();
			IOUtil.writeSetToFile(macMap,newFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * 根据厂家添加黑名单MacPre_BlackList
	 * @param macFile
	 * @param newFile
	 * @return
	 */
	public static boolean getMacPreBlackListBaseFactory(String macFile,String newFile,List<String> facs) {
		try {
			//factory
			List<String> factory=facs;
			if(facs==null){
				factory=new ArrayList<String>();
				factory.add("Murata");
				factory.add("InPro Comm");
				factory.add("TP-LINK");
				factory.add("Intel Corporate");
				factory.add("H3C");
			}
			//load mac.txt
			Set<String> macMap=new HashSet<String>();
			File f=new File(macFile);
			if(!f.exists()){
				System.out.println("file not exist:"+macFile);
				return false;
			}
			FileReader fr=new FileReader(f);
			BufferedReader br=new BufferedReader(fr);
			String tempString=null;
			while((tempString = br.readLine()) != null){
				String[] arr=tempString.split("\t");
				for(String fac:factory){
					if(arr[1].toLowerCase().contains(fac.toLowerCase())){
						macMap.add(arr[0]);
						System.out.println(tempString);
					}
				}
			}
			br.close();  
			fr.close();
			//SQLUtil.getMacPreBlackListInsertSql(null, null, macMap);
			IOUtil.writeSetToFile(macMap,newFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static boolean deleteFileSuffix_dotuploaded(String fileDir,String suffix) {
		try {
			File fDir=new File(fileDir);
			if(!fDir.exists()){
				System.out.println("file not exist:"+fDir);
				return false;
			}
			//for(File day:fDir.listFiles()){
			for(File f:fDir.listFiles()){
				String name=f.getAbsolutePath();
				if(name.indexOf(suffix)>=0){
					f.renameTo(new File(name.replaceAll("\\.uploaded", "")));
				}
			}
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
}
