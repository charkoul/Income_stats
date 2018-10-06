package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import obj.Banks;
import obj.DataRecord;



public class Utils {
	
	static Logger logger = Logger.getLogger(Utils.class);
	
	 
	  /**
	 * @param text : the text you want to split in half
	 * @param split : the element key 
	 * @param trimFrom : the part of the word you want to get
	 * @return
	 */
	public static String trimText(String text, String split , String trimFrom) {
		  if (text != null &&  split !=null &&  trimFrom != null) {
			 if (trimFrom.equals(Properties.LEFT))
				 text = text.split(split)[1];
			 else if ((trimFrom.equals(Properties.RIGHT)))
				 text = text.split(split)[0];
		  }
		  return text.trim();
	}
	
	
	
	 public static Date stringToDate(String dateStr , String formatStr ) throws ParseException {

	     // SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	     //String dateInString = "07/06/2013";
		 SimpleDateFormat formater= new SimpleDateFormat(formatStr);
		 Date date = null;
		 try {
			 date = formater.parse(dateStr);
	        //System.out.println(date);
	        //System.out.println(formater.format(date));
	        return date;
	
	    } catch (ParseException e) {
	       e.printStackTrace();
	    }
		 return date;
	
	}
	 
	 /**
	  * Change "," from demical(String) to "."
	  * @param dem : demical number
	  * @return
	  */
	 public static String changeDemicalSign (String dem) {
		 if (dem.contains("."))
			 dem = dem.replace(".", "");
		 
		 return dem.replace(",", ".");
		 
	 }
	 
	 
	 public static void printDataRecord(DataRecord record) {
			
		logger.info("accountNumber: " + record.getAccountNumber()
					+ " ,transactionDescription "+ record.getTransactionDescription()
					+ " ,transactionComment: " + record.getTransactionComment() 
					+ " ,amount: " + record.getAmount()
					+ " ,transactionNumber: " + record.getTransactionNumber()
					+ " ,bankID: " + record.getBank());
	}
		
		
	public static void printDataRecordList(List<DataRecord> dataList) {
		for (int i=0 ; i<dataList.size(); i++) {
			printDataRecord(dataList.get(i));
		}
	}
		
		
		
		
		/**
		 * change encoding from file
		 * @param source file
		 * @param srcEncoding : source file encoding
		 * @param target file
		 * @param tgtEncoding : target file encoding
		 * @throws IOException
		 */
		public static void transform(File source, String srcEncoding, String tgtEncoding) throws IOException {
		    BufferedReader br = null;
		    BufferedWriter bw = null;
		    try{
		    	//get source file path
		    	String path = getPathOfFile(source);
		    	File target = new File(path +"new_"+ source.getName());
		        br = new BufferedReader(new InputStreamReader(new FileInputStream(source),srcEncoding));
		        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), tgtEncoding));
		        char[] buffer = new char[16384];
		        int read;
		        while ((read = br.read(buffer)) != -1)
		            bw.write(buffer, 0, read);
		        
		        source.delete();
		    } finally {
		        try {
		            if (br != null)
		                br.close();
		        } finally {
		            if (bw != null)
		                bw.close();
		        }
		    }
		}
	    
		
		
		/**
		 * Return path from a given file
		 * @param filename
		 * @return
		 */
		public static String getPathOfFile(File filename) {
			String absolutePath = filename.getAbsolutePath();
			String filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
			return filePath+"\\";
		}
	   
	   
		
		public static void setBankToDataList(List<DataRecord> listRecords , int bankId) {
			for (int i=0 ; i<listRecords.size() ; i++) {
				listRecords.get(i).setBank(bankId);
			}
		}
	  
	 
	    
	    
	    
	

}
