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

public class EurobankController {
 
	public static String eurobankPattern = "Ημερομηνία Κίνησης;Ημερομηνία Αξίας;Περιγραφή;Ποσό;Υπόλοιπο";
	public static String accountNumberPattern = ";;Αριθμός Λογαριασμού:";
	static Logger logger = Logger.getLogger(LoggerClass.class);
	
	public List<DataRecord> getERBData(){
		
		File folder = new File(Properties.rootFolder + Properties.erbFolder);
		File[] listOfFiles = folder.listFiles();
		
		//list for all data per file
		List<DataRecord> erbBankList = new ArrayList<DataRecord>();
		try {
			//read files and check if is csv
			for (File file : listOfFiles) {
			    if (file.isFile() && file.getName().contains(Properties.csvExtension)) {
			    	logger.info("Read Eurobank's transactrion file: " + file.getName());
			    	
			    	try {
				    	FileInputStream fstream = new FileInputStream(folder + Properties.backslash + file.getName() );
				    	DataInputStream in= new DataInputStream(fstream);
						BufferedReader br = new BufferedReader(new InputStreamReader(in , "UTF-8" ));
						
						List<DataRecord> dataList = new ArrayList<DataRecord>(); 
						boolean startCollectData = false;
						String strLine;
						String erbAccount = "";
						boolean stopRead = false;
//						//get the account number from the first line
//						if ((strLine = br.readLine()) != null){
//							erbAccount = utils.Utils.trimText(strLine, Properties.colon, Properties.LEFT).replaceAll(" ", "");
//						} 
						while ((strLine = br.readLine()) != null && !stopRead)   {
							if (strLine.equals(eurobankPattern))
								startCollectData = true;
							
							if (!strLine.equals(eurobankPattern) && startCollectData) {
								
								DataRecord record = new DataRecord();
								String[] element = strLine.split(Properties.semicolon);
								logger.info("read line :" + strLine );
								record.setTransactionDate(Utils.stringToDate(element[1], Properties.TYPICAL));
								record.setTransactionDescription(element[3]);
								record.setAmount(Double.parseDouble(Utils.changeDemicalSign(element[4])));
								dataList.add(record);
							}
							//get account number
							if (!strLine.equals(eurobankPattern) && startCollectData) {
								if (strLine.startsWith(accountNumberPattern))
									stopRead=true;
								erbAccount = clearAccount(Utils.trimText(strLine, accountNumberPattern, Properties.LEFT ));
							}
							
							
						}
						erbBankList.addAll(dataList);
						
						addErbAccountToList(erbBankList ,erbAccount );
						//Close the input stream
						in.close();
					}catch (Exception e){//Catch exception if any
						logger.error("Error: " + e.getMessage());
					}
			    }
			}
			//for debug
			Utils.printDataRecordList(erbBankList);
		}catch(Exception ex){
			logger.error(ex);
		}
		return erbBankList;
	}
	
	public String clearAccount(String account) {
		account = account.replaceAll(Properties.semicolon, "");
		return account;
	}
	
	public void addErbAccountToList(List<DataRecord> list, String account) {
		for (int i=0 ; i<list.size(); i++) {
			list.get(i).setAccountNumber(account);
		}
	}
	
	
	
	
}
