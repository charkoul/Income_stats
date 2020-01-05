package controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.omg.IOP.ExceptionDetailMessage;

import obj.DataRecord;
import utils.LoggerClass;
import utils.Properties;
import utils.Utils;



public class AlphaBankController {
	
	
	public static  String alphaBankPattern ="Α/Α;Ημ/νία;Αιτιολογία;Κατάστημα;Τοκισμός από;Αρ. συναλλαγής;Ποσό;Πρόσημο ποσού";
	static Logger logger = Logger.getLogger(AlphaBankController.class);
	
	public List<DataRecord> getAlphaBankData() throws Exception {
		
		File folder = new File(Properties.rootFolder + Properties.alphaFolder);
		
		logger.info("Read folder = "+Properties.rootFolder + Properties.alphaFolder );
		File[] listOfFiles = folder.listFiles();
		
		//list for all data per file
		List<DataRecord> alphaBankList = new ArrayList<DataRecord>();
		try {
			//read files and check if is csv
			for (File file : listOfFiles) {
			    if (file.isFile() && file.getName().contains(Properties.csvExtension)) {
			    	logger.info("Read AlphaBank's transaction file: " + file.getName());
			    	try {
				    	FileInputStream fstream = new FileInputStream(folder + Properties.backslash + file.getName());
				    	DataInputStream in= new DataInputStream(fstream);
						BufferedReader br = new BufferedReader(new InputStreamReader(in , Properties.UTF8));
						
						List<DataRecord> dataList = new ArrayList<DataRecord>(); 
						boolean startCollectData = false;
						String strLine;
						String alphaAccount = "";
						//get the account number from the first line
						if ((strLine = br.readLine()) != null){
							alphaAccount = utils.Utils.trimText(strLine, Properties.colon, Properties.LEFT).replaceAll(Properties.space, "").replaceAll(Properties.semicolon, "");
						} 
						while ((strLine = br.readLine()) != null)   {
							if (strLine.equals(alphaBankPattern))
								startCollectData = true;
							
							if (!strLine.equals(alphaBankPattern) && startCollectData) {
								
								DataRecord record = new DataRecord(2);
								String[] element = strLine.split(Properties.semicolon);
								//logger.info("read line :" + strLine );
								record.setTransactionDate(Utils.stringToDate(element[1], Properties.TYPICAL_DATE_FORMAT));
								record.setTransactionDescription(cleanDescription(element[2]));
								record.setTransactionNumber(element[5]);
								
								String amount =  Utils.changeDemicalSign(element[6]);
								if (element[7].equals(Properties.signExpense)) {
									amount = "-" + amount;
								}
								record.setAmount(Double.parseDouble(amount));
								
								record.setAccountNumber(alphaAccount);
								record.setTUN(generateTUNAlpha(record.getTransactionNumber(), record.getAmount()));
								
								dataList.add(record);
							}
						}
						alphaBankList.addAll(dataList);
						if (dataList.size()== 0)
							logger.info("AlphaBank's transaction file: " + file.getName() + " return 0 records");
						
						//Close the input stream
						in.close();
						
					}catch (Exception e){//Catch exception if any
						logger.error("AlphaBankControllerException::", e);
					}
			    }
			}
			//for debug
			//Utils.printDataRecordList(alphaBankList);
			if (alphaBankList.size()== 0)
				logger.info("No records added from AlphaBank's transaction file(s)");
			else
				logger.info("Add " +alphaBankList.size() + " records from AlphaBank's transaction file(s)");
			
		}catch(Exception ex){
			logger.error("AlphaBankControllerException::", ex);
		}
		return alphaBankList;
	}
	
	
	private String generateTUNAlpha(String transactionNumber, double amount) {
		String tunNew = "";
		try {
			String amountStr = String.valueOf(amount);
			amountStr =	utils.Utils.removeCharacter(amountStr, Properties.dot) ;
			amountStr = utils.Utils.removeCharacter(amountStr, Properties.minus) ;
			//TUN =TransactionNumber + Amount
			tunNew = transactionNumber + amountStr;
		}catch (Exception ex) {
			logger.error(" generateTUN AlphaBankControllerException::", ex);
		}
		return tunNew;
	}


	


	public String cleanDescription(String text) {
		if (text != null) {
			text = text.replaceAll("=\"" , "");
			text = text.replace("\"\"", "");
		}
		return text;
	}
	
	
	
}
