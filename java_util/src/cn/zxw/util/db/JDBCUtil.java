package cn.zxw.util.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class JDBCUtil {
	private static Properties env = new Properties();
	private  Connection conn =null;
	private  Statement stm =null;
	private  ResultSet rs=null;
	private int status=0;


	static {
		try {
			InputStream is = JDBCUtil.class.getResourceAsStream("/conf/systemConfig.properties");
			env.load(is);
			is.close();
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public JDBCUtil() {
		super();
		createConn(true);
	}
	public JDBCUtil(boolean isCreateConn) {
		super();
		createConn(isCreateConn);
	}
	private void createConn(boolean isCreateConn) {
		try {
			if(isCreateConn){
				getConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getProperty(String key) {
		return env.getProperty(key);
	}

	public Connection getConnection(String url,String username,String password) throws Exception {
		close();
		Class.forName(env.getProperty("driver"));
		conn = DriverManager.getConnection(url,username,password);
		conn.setAutoCommit(false);
		return conn;
	}
	public Connection getConnection() throws Exception {
		close();
		Class.forName(env.getProperty("driver"));
		conn = DriverManager.getConnection(
					env.getProperty("url"),
					env.getProperty("username"),
					env.getProperty("password"));
		// 设置为自动提交
		conn.setAutoCommit(false);
		return conn;
	}
	// 增加数据
	public int add(String sql) {
		return update(sql);
	}
	// 增加数据
	public int add(String sql,List<String> params) {
		return update(sql,params);
	}
	// 删除数据
	public int delete(String sql) {
		return update(sql);
	}
	// 删除数据
	public int delete(String sql,List<String> params) {
		return update(sql,params);
	}
	// 查询数据
	public ResultSet select(String sql) {
		try {
			/*Statement stat = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);*/
			this.stm = conn.prepareStatement(sql);
			rs = stm.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	// 查询数据
	public ResultSet select(String sql,List<String> params) {
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			for(int i=0;i<params.size();i++){
				stat.setString(i+1, params.get(i));
			}
	        rs = stat.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	//改数据
	public int update(String sql) {
		try {
			stm = conn.createStatement();
			status = stm.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	//改数据
	public int update(String sql,List<String> params) {
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			for(int i=0;i<params.size();i++){
				stat.setString(i+1, params.get(i));
			}
			status = stat.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	public void close(ResultSet rs, Statement stm, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		if (stm != null){
			try {
				stm.close();
			} catch (Exception e) {
			}
		}
		if (conn != null){
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}
	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		if (stm != null){
			try {
				stm.close();
			} catch (Exception e) {
			}
		}
		if (conn != null){
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}
	
	
}
