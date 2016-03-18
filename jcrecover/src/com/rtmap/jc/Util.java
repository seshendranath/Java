package com.rtmap.jc;

public class Util {

	public static String START_TIME = "recover_start_time";
	public static String END_TIME = "recover_end_time";
	public static String P_YEAR = "partition_year";
	
	public static String ajxxb = 
	"select 'ajxxb' as type,sysid,ajxxb_id,trim(lk_id) as lk_id,safe_flag,safe_no,safe_oper,to_char(to_date(trim(safe_time),'yyyyMMddhh24miss'),'yyyy-MM-dd hh24:mi:ss') as safe_time,bag_open,safe_out,safe_outno,safe_outtime,file_name,lk_date,lk_flight,flgt_id,process_status,last_update_date,last_update_date from log_sec_ajxxb partition(Ppartition_year) where last_update_date > to_date('recover_start_time','yyyy-mm-dd hh24:mi:ss') and last_update_date <= to_date('recover_end_time','yyyy-mm-dd hh24:mi:ss')";

	public static String barcode = 
	"select 'barcode' as type,data_source,id,passager_name,case when regexp_like(substr(trim(flight),2,1),'([A-Z]+|[A-Z])') and substr(trim(flight),3,2) = '00' then substr(trim(flight),1,2)||substr(trim(flight),5) when regexp_like(substr(trim(flight),2,1),'([A-Z]+|[A-Z])') and substr(trim(flight),3,1) = '0'  then substr(trim(flight),1,2)||substr(trim(flight),4) when length(trim(flight)) = 7 and substr(trim(flight),length(trim(flight)),1) = '0' then substr(trim(flight),1,length(trim(flight))-1) else replace(flight,' ','') end as flight,case when length(flight_date) = 8 and to_date(flight_date,'YYYYMMDDHH24:MI:SS') - to_date(to_char(last_scan_time,'YYYYMMDDHH24:MI:SS'),'YYYYMMDDHH24:MI:SS') <= 2 and to_date(flight_date,'YYYYMMDDHH24:MI:SS') - to_date(to_char(last_scan_time,'YYYYMMDDHH24:MI:SS'),'YYYYMMDDHH24:MI:SS')>= -2 then flight_date else to_char(last_scan_time,'YYYYMMDD') end as flight_date,ship,seat_no,to_number(regexp_substr(boarding_no, '[0-9]+')) as boarding_no,start_city,end_city,gate_name,barcode,first_scan_time,to_char(last_scan_time, 'yyyy-mm-dd hh24:mi:ss') as last_scan_time,scan_number,error_code,last_timestamp from log_barcode_record partition(Ppartition_year) where last_timestamp > to_timestamp('recover_start_time','yyyy-mm-dd hh24:mi:ss') and last_timestamp <= to_timestamp('recover_end_time','yyyy-mm-dd hh24:mi:ss') and (error_code = 0 OR error_code is null)";

	public static String lkxxb = 
	"select 'lkxxb' as type,sysid,trim(lk_id) as lk_id,case when regexp_like(substr(lk_flight,2,1),'([A-Z]+|[A-Z])') and substr(lk_flight,3,2) = '00' then substr(lk_flight,1,2)||substr(lk_flight,5) when regexp_like(substr(lk_flight,2,1),'([A-Z]+|[A-Z])') and substr(lk_flight,3,1) = '0'  then substr(lk_flight,1,2)||substr(lk_flight,4) when length(lk_flight) = 7 and substr(lk_flight,length(lk_flight),1) = '0' then substr(lk_flight,1,length(lk_flight)-1) else replace(lk_flight,' ','') end as lk_flight,trim(lk_date) as lk_date,lk_seat,lk_strt,lk_dest,trim(lk_bdno) as lk_bdno,lk_ename,lk_cname,lk_card,lk_cardid,lk_nation,lk_sex,lk_tel,lk_resr,lk_inf,lk_infname,lk_class,lk_chkn,lk_chkt,lk_gateno,lk_vip,lk_insur,lk_outtime,lk_del,ajxxb_id,safe_time,flgt_id,file_name,process_status,last_update_date from log_sec_lkxxb partition(Ppartition_year) where last_update_date > to_date('recover_start_time','yyyy-mm-dd hh24:mi:ss') and last_update_date <= to_date('recover_end_time','yyyy-mm-dd hh24:mi:ss') and lk_del = '0'";

			
}
