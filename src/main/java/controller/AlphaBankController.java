package controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import obj.DataRecord;
import utils.LoggerClass;
import utils.Properties;
import utils.Utils;



public class AlphaBankController {
	
	
	public static  String alphaBankPattern ="Α/Α;Ημ/νία;Αιτιολογία;Κατάστημα;Τοκισμός από;Αρ. συναλλαγής;Ποσό;Πρόσημο ποσού;";
	static Logger logger = Logger.getLogger(LoggerClass.class);
	
	public List<DataRecord> getAlphaBankData(){
		
		File folder = new File(Properties.rootFolder + Properties.alphaFolder);
		File[] listOfFiles = folder.listFiles();
		
		//list for all data per file
		List<DataRecord> alphaBankList = new ArrayList<DataRecord>();
		try {
			//read files and check if is csv
			for (File file : listOfFiles) {
			    if (file.isFile() && file.getName().contains(Properties.csvExtension)) {
			    	logger.info("Read AlphaBank's transactrion file: " + file.getName());
			    	
			    	
			    	
			    	
			    	
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
							alphaAccount = utils.Utils.trimText(strLine, Properties.colon, Properties.LEFT).replaceAll(" ", "");
						} 
						while ((strLine = br.readLine()) != null)   {
							if (strLine.equals(alphaBankPattern))
								startCollectData = true;
							
							if (!strLine.equals(alphaBankPattern) && startCollectData) {
								
								DataRecord record = new DataRecord();
								String[] element = strLine.split(Properties.semicolon);
								//logger.info("read line :" + strLine );
								record.setTransactionDate(Utils.stringToDate(element[1], Properties.TYPICAL));
								record.setTransactionDescription(cleanDescription(element[2]));
								record.setTransactionNumber(element[3]);
								
								String amount =  Utils.changeDemicalSign(element[6]);
								if (element[7].equals(Properties.signExpense)) {
									amount = "-" + amount;
								}
								record.setAmount(Double.parseDouble(amount));
								
								record.setAccountNumber(alphaAccount);
								
								dataList.add(record);
							}
						}
						alphaBankList.addAll(dataList);
						//Close the input stream
						in.close();
					}catch (Exception e){//Catch exception if any
						logger.error("Error: " + e.getMessage());
					}
			    }
			}
			//for debug
			//Utils.printDataRecordList(alphaBankList);
		}catch(Exception ex){
			logger.error(ex);
		}
		return alphaBankList;
	}
	
	
	public String cleanDescription(String text) {
		if (text != null) {
			text = text.replaceAll("=\"" , "");
			text = text.replace("\"\"", "");
		}
		return text;
	}
	
	
	
}
