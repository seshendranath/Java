package com.huanju.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取配置文件的工具类
 * @author ligl
 *
 */
public class ConfigUtil {
	private static Properties p = new Properties();
	static {
		try {
			//p.load(ClassLoader.getSystemResourceAsStream("config_localhost.properties"));
			//p.load(ClassLoader.getSystemResourceAsStream("config_test.properties"));
			p.load(ClassLoader.getSystemResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取zookeeper的端口
	 * @return
	 */
	public static String getZookeeperPort() {
		return p.getProperty("hbase.zookeeper.property.clientPort");
	}
	
	/**
	 * 获取zookeeper的地址
	 * @return
	 */
	public static String getZookeeperQuorum() {
		return p.getProperty("hbase.zookeeper.quorum");
	}
	
	/**
	 * 获取redis地址
	 * @return
	 */
	public static String getRedisHost(){
		return p.getProperty("redis.host");
	}
	
	/**
	 * 获取redis端口
	 * @return
	 */
	public static int getRedisPort(){
		return Integer.parseInt(p.getProperty("redis.port"));
	}
	
	/**
	 * 获取HBase列族名称info
	 * @return
	 */
	public static String getHBaseColumnfamilyInfo(){
		return p.getProperty("hbase.columnfamily.info");
	}
	
	/**
	 * 获取HBase列族名称app
	 * @return
	 */
	public static String getHBaseColumnfamilyApp(){
		return p.getProperty("hbase.columnfamily.app");
	}
	
	/**
	 * 获取HBase列名称：年龄
	 * @return
	 */
	public static String getHBaseColumnNameAge(){
		return p.getProperty("hbase.column.age");
	}
	
	/**
	 * 获取HBase列名称：性别
	 * @return
	 */
	public static String getHBaseColumnNameGender(){
		return p.getProperty("hbase.column.gender");
	}
	
	/**
	 * 获取HBase列名称：兴趣
	 * @return
	 */
	public static String getHBaseColumnNameInterestTags(){
		return p.getProperty("hbase.column.tags");
	}
	
	/**
	 * 获取HBase列名称：更新时间
	 * @return
	 */
	public static String getHBaseColumnNameDtTags(){
		return p.getProperty("hbase.column.dt");
	}
	
	/**
	 * 获取HBase列名称：应用安装包列表
	 * @return
	 */
	public static String getHBaseColumnNameApplist(){
		return p.getProperty("hbase.column.applist");
	}
	
	/**
	 * 获取HBase列名称：渠道名称
	 * @return
	 */
	public static String getHBaseColumnNameChannel(){
		return p.getProperty("hbase.column.channel");
	}
	
	/**
	 * 获取HBase列名称：渠道ID
	 * @return
	 */
	public static String getHBaseColumnNameChannelId(){
		return p.getProperty("hbase.column.channel_id");
	}
	
	/**
	 * 获取HBase列名称：省份
	 * @return
	 */
	public static String getHBaseColumnNameProvince(){
		return p.getProperty("hbase.column.province");
	}
	
	/**
	 * 获取HBase列名称：城市
	 * @return
	 */
	public static String getHBaseColumnNameCity(){
		return p.getProperty("hbase.column.city");
	}
	
	/**
	 * 获取HBase列名称：机型 ：格式是 品牌_型号
	 * @return
	 */
	public static String getHBaseColumnNameDevice(){
		return p.getProperty("hbase.column.device");
	}
	
	/**
	 * 获取HBase列名称：安卓版本ovr
	 * @return
	 */
	public static String getHBaseColumnNameOvr(){
		return p.getProperty("hbase.column.ovr");
	}
	

	/**
	 * 获取HBase列名称：创建时间
	 * @return
	 */
	public static String getHBaseColumnNameCt(){
		return p.getProperty("hbase.column.ct");
	}
	
	public static void main(String []args) {
        System.out.println(getZookeeperQuorum());
        System.out.println(getZookeeperPort());
        System.out.println(getRedisHost());
        System.out.println(getRedisPort());
        System.out.println(getHBaseColumnfamilyApp());
        System.out.println(getHBaseColumnfamilyInfo());
        System.out.println(getHBaseColumnNameAge());
        System.out.println(getHBaseColumnNameApplist());
        System.out.println(getHBaseColumnNameDtTags());
        System.out.println(getHBaseColumnNameInterestTags());
        System.out.println(getHBaseColumnNameChannel());
        System.out.println(getHBaseColumnNameChannelId());
    }

}
