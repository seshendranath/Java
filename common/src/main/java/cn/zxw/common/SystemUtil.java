package cn.zxw.common;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SystemUtil {
	public static String KEY_MD5="MD5";
	public static String KEY_SHA1="SHA-1";
	public static String KEY_SHA256="SHA-256";
	public static String CHARSET_ISO88591="ISO-88591";
	public static String CHARSET_UTF8="UTF-8";

	/** 
     * Get MD5 of one file:hex string
     *  
     * @param file 
     * @return 
     */  
    public static String getFileMD5(File file) {  
        if (!file.exists() || !file.isFile()) { 
            return null;  
        }  
        MessageDigest digest = null;  
        FileInputStream in = null;  
        byte buffer[] = new byte[1024];
        int len;  
        try {  
            digest = MessageDigest.getInstance(KEY_MD5);  
            in = new FileInputStream(file);  
            while ((len = in.read(buffer, 0, 1024)) != -1) {  
                digest.update(buffer, 0, len);  
            }  
            in.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
        BigInteger bigInt = new BigInteger(1, digest.digest());  
        return bigInt.toString(16);
    }  
  
    /**
     * Get MD5 of one file!
     *  
     * @param filepath 
     * @return 
     */  
    public static String getFileMD5(String filepath) {  
        File file = new File(filepath);  
        return getFileMD5(file);  
    }
  
    /** 
     * MD5 encrypt
     *  
     * @param data 
     * @return byte[] 
     * @throws Exception 
     */  
    public static byte[] encryptMD5(byte[] data) throws Exception {  
  
        MessageDigest md5 = MessageDigest.getInstance(SystemUtil.KEY_MD5);  
        md5.update(data);  
        return md5.digest();  
    }  
  
    public static byte[] encryptMD5(String data) throws Exception {  
        return encryptMD5(data.getBytes(SystemUtil.CHARSET_ISO88591));  
    }  
    
    /**
     * compare two file by md5 
     *  
     * @param file1 
     * @param file2 
     * @return 
     */  
    public static boolean isSameMd5(File file1,File file2){  
        String md5_1=SystemUtil.getFileMD5(file1);  
        String md5_2=SystemUtil.getFileMD5(file2);  
        return md5_1.equals(md5_2);  
    }
    
    /**
     * compare two file by md5 
     *  
     * @param filepath1 
     * @param filepath2 
     * @return 
     */  
    public static boolean isSameMd5(String filepath1,String filepath2){
        File file1=new File(filepath1);  
        File file2=new File(filepath2);  
        return isSameMd5(file1, file2);  
    }

    /**
     * 
     * @param strSrc  string will be encrypted
     * @param encName  the algorithm name will be used.dafault to "MD5"
     * @return
     */
    public static String encrypt(String str,String encName) {
		MessageDigest md=null;
		//String strDes=null;
		byte[] bt=str.getBytes();
		try {
			if (encName==null||"".equals(encName)) {
				encName=KEY_MD5;
            }
			md=MessageDigest.getInstance(encName);
			md.update(bt);
		    //strDes=bytes2Hex(md.digest());  //to HexString
		}catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid algorithm.");
			return null;
		}
        //return strDes;
		BigInteger bigInt = new BigInteger(1, md.digest());  
        return bigInt.toString(16);
    }
    
    /**
     * 
     * @param filePath  filePath will be encrypted
     * @param encName  the algorithm name will be used.dafault to "MD5"
     * @return
     */
    public static String encryptFile(String filePath,String encName) {
    	File file=new File(filePath);
    	if (!file.exists() || !file.isFile()) { 
            return null;  
        }  
    	if (encName==null||"".equals(encName)) {
			encName=KEY_MD5;
        }
        MessageDigest digest = null;  
        FileInputStream in = null;  
        byte buffer[] = new byte[1024];
        int len;
        //String strDes=null;
        try {  
            digest = MessageDigest.getInstance(encName);  
            in = new FileInputStream(file);  
            while ((len = in.read(buffer, 0, 1024)) != -1) {  
                digest.update(buffer, 0, len);  
            }  
            in.close();  
            //strDes=bytes2Hex(digest.digest());  //to HexString
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        } 
        //return strDes;
        BigInteger bigInt = new BigInteger(1, digest.digest());  
        return bigInt.toString(16);
    }
    
    /**
     * 字节转化为十六进制??
     * @param bts
     * @return
     */
	public static String bytes2Hex(byte[]bts) {
		String des="";
		String tmp=null;
		for (int i=0;i<bts.length;i++) {
	        tmp=(Integer.toHexString(bts[i] & 0xFF));
	        if (tmp.length()==1) {
	           des ="0";
	        }
	        des =tmp;
	    }
	    return des;
	}

	public static void main(String[] args) {
        /*String strSrc="可以加密汉字.Oh,and english";
        System.out.println("Source String:"+ strSrc);
        System.out.println("Encrypted String:");
        System.out.println("Use Def:"+ encrypt(strSrc,null));
        System.out.println("Use MD5:"+ encrypt(strSrc,"MD5"));
        System.out.println("Use SHA:"+ encrypt(strSrc,"SHA-1"));
        System.out.println("Use SHA-256:" + encrypt(strSrc,"SHA-256"));*/
		System.out.println("Use MD5:"+ encrypt("admin","MD5"));
	}
}
