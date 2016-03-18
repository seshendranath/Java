package com.rtmap.jc;

import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVWriter;

public class SQLUtil {
	private static final Logger logger = LoggerFactory.getLogger(SQLUtil.class);
	
	public static boolean saveResultSetAsCSV(ResultSet rs, String fileName) throws Exception {
		if(rs == null || fileName == null){
			logger.error("Save ResultSet As CSV Error: ResultSet or fileName is null");
			return false;
		}
		List<String[]> allRows = new ArrayList<String[]>();
		String[] row=null;
		ResultSetMetaData meta = rs.getMetaData();
		int num = meta.getColumnCount();
		while (rs.next()) {
			row = new String[num];
			for (int i = 1; i <= num; ++i) {
				if (rs.getObject(i) != null){
					row[i-1] = rs.getObject(i).toString();
				}else{
					row[i-1] = "";
				}
			}
			allRows.add(row);
		}
		if(allRows.size() == 0){
			logger.info("result is nothing");
			return false;
		}
		File csvFile = new File(fileName);
		FileUtils.forceMkdir(csvFile.getParentFile());
		if(csvFile.exists() && csvFile.isFile()){
			logger.info("File exist,delete first: "+fileName);
			FileUtils.deleteQuietly(csvFile);
		}
	    CSVWriter csvWriter= new CSVWriter(new FileWriter(fileName), '\t', CSVWriter.NO_QUOTE_CHARACTER);
	    csvWriter.writeAll(allRows);
        csvWriter.flush();
        csvWriter.close();
        return true;
	}
}
