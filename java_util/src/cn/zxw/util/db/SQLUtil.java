package cn.zxw.util.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import cn.zxw.util.io.IOUtil;

public class SQLUtil {
	/**
	 * 得到macpre_blacklist表的新插入语句
	 * @param dataFile 得到要插入的数据
	 * @param destFile 结果SQL文件
	 */
	public static void getMacPreBlackListInsertSql(String dataFile,String destFile,Set<String> set) {
		String s1="INSERT INTO macpre_blacklist(mac_pre) VALUES ('";
		String s2="');";
		Set<String> sqls=new HashSet<String>();
		try {
			/*File f=new File(dataFile);
			if (!f.exists()) {
				System.out.println("file not exist:"+dataFile);
				return;
			}
			FileReader fr=null;
			BufferedReader br=null;
			fr=new FileReader(f);
			br=new BufferedReader(fr);
			String tempString = null;
				while((tempString = br.readLine()) != null){
					String[] arr=tempString.split("\t");
					String sql=s1+arr[0]+s2+arr[1]+s3;
					System.out.println(sql);
					sqls.add(sql);
				}
			br.close();  
			fr.close();*/
			for(String s:set){
				String sql=s1+s+s2;
				System.out.println(sql);
			}
			if(destFile!=null){
				IOUtil.writeSetToFile(sqls, destFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 得到mac_co表的新插入语句
	 * @param dataFile 得到要插入的数据
	 * @param destFile 结果SQL文件
	 */
	public static void getMacCoInsertSql(String dataFile,String destFile) {
		String s1="INSERT INTO mac_co(mac,co) VALUES ('";
		String s2="', '";
		String s3="');";
		Set<String> sqls=new HashSet<String>();
		try {
			File f=new File(dataFile);
			if (!f.exists()) {
				System.out.println("file not exist:"+dataFile);
				return;
			}
			FileReader fr=null;
			BufferedReader br=null;
			fr=new FileReader(f);
			br=new BufferedReader(fr);
			String tempString = null;
				while((tempString = br.readLine()) != null){
					String[] arr=tempString.split("\t");
					String sql=s1+arr[0]+s2+arr[1]+s3;
					System.out.println(sql);
					sqls.add(sql);
				}
			br.close();  
			fr.close();	
			if(destFile!=null){
				IOUtil.writeSetToFile(sqls, destFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
