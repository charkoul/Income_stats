package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import obj.DataRecord;



public class Utils {
	
	static Logger logger = Logger.getLogger(LoggerClass.class);
	
	 
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
		  return text;
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
		 return dem.replace(",", ".");
		 
	 }
	 
	 
	 public static void printDataRecord(DataRecord record) {
			
			logger.info("accountNumber: " + record.getAccountNumber()
						+ " ,transactionDescription "+ record.getTransactionDescription()
						+ " ,transactionComment: " + record.getTransactionComment() 
						+ " ,amount: " + record.getAmount()
						+ " ,transactionNumber: " + record.getTransactionNumber());
		}
		
		
		public static void printDataRecordList(List<DataRecord> dataList) {
			for (int i=0 ; i<dataList.size(); i++) {
				printDataRecord(dataList.get(i));
			}
			
		}
	    
	   
	   
	  
	 
	    
	    
	    
	

}
