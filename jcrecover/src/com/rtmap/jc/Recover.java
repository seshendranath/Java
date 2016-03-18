package com.rtmap.jc;

import java.sql.ResultSet;

public class Recover {
	
	public static void main(String[] args) throws Exception {
		if(args.length != 4){
			System.out.println("2016-01-01".substring(0, 4));
			System.out.println("param1(ajxxb/barcode/lkxxb),param2(start_time),param3(end_time),param4(dataFile)");
			return;
		}
		
		OracleOperator operator=null;
		ResultSet rs=null;
		try {
			operator = new OracleOperator();
			String sql = null;
			String year = args[1].substring(0, 4);
			if("ajxxb".equals(args[0])){
				sql=Util.ajxxb;
			}else if("barcode".equals(args[0])){
				sql=Util.barcode;
			}else if("lkxxb".equals(args[0])){
				sql=Util.lkxxb;
			}else{
				System.out.println("param1(ajxxb/barcode/lkxxb)");
				return;
			}
			sql=sql.replaceAll(Util.START_TIME, args[1]).replaceAll(Util.END_TIME, args[2]).replaceAll(Util.P_YEAR, year);
			System.out.println(sql);
			rs = operator.executeQuery(sql);
			SQLUtil.saveResultSetAsCSV(rs, args[3]);
			System.out.println("-----------finish-----------");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs != null){rs.close();}
			operator.close();
		}
		
	}
}
