package cn.zxw.java.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class JavaRemoteShellTool {
	public static void main(String[] args) {
		RunShell(args[0],args[1],args[2]);
	}
	public static int RunShell(String hostname,String username,String password) {
		Connection conn = new Connection(hostname);
		Session ssh = null;
		InputStream  is = null;
		BufferedReader brs = null;
		try {
			conn.connect();
			boolean isconn = conn.authenticateWithPassword(username, password);
			if(!isconn){
				System.out.println("用户名称或者是密码不正确");
			}else{
				System.out.println("已经连接OK");
				ssh = conn.openSession();
				String cmd="echo 'hello,test!' >> /home/zxw/remoteShell.txt";
				System.out.println("开始执行的shell命令为:"+cmd);
				ssh.execCommand(cmd);
				is = new StreamGobbler(ssh.getStdout());
				brs = new BufferedReader(new InputStreamReader(is));
				while(true){
					String line = brs.readLine();
					if(line==null){
						break;
					}
					System.out.println(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}finally{
			IOUtils.closeQuietly(brs);
			IOUtils.closeQuietly(is);
			if(ssh!=null)ssh.close();
			if(conn!=null)conn.close();
		}
		return 1;
	}
}
