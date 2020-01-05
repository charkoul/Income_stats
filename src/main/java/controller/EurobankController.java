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

import obj.DataRecord;
import utils.Properties;
import utils.Utils;

public class EurobankController {
 
	public static String eurobankPattern = "\"ΗΜ/ΝΙΑ ΚΙΝΗΣΗΣ\";ΗΜ/ΝΙΑ ΑΞΙΑΣ;ΠΕΡΙΓΡΑΦΗ;ΠΟΣΟ;ΥΠΟΛΟΙΠΟ";
	public static String accountNumberPattern = "\"\";;ΑΡΙΘΜΟΣ ΛΟΓΑΡΙΑΣΜΟΥ:";
	static Logger logger = Logger.getLogger(EurobankController.class);
	
	public List<DataRecord> getERBData(){
		
		File folder = new File(Properties.rootFolder + Properties.erbFolder);
		logger.info("Read folder = "+Properties.rootFolder + Properties.erbFolder );
		File[] listOfFiles = folder.listFiles();
		
		//list for all data per file
		List<DataRecord> erbBankList = new ArrayList<DataRecord>();
		try {
			//read files and check if is csv
			for (File file : listOfFiles) {
			    if (file.isFile() && file.getName().contains(Properties.csvExtension)) {
			    	logger.info("Read Eurobank's transaction file: " + file.getName());
			    	//TODO:: fix file to automatically convert to UTF8 from ANSI
			    	//Utils.transform(file , Properties.ANSI,Properties.UTF8);
			    	try {
				    	FileInputStream fstream = new FileInputStream(folder + Properties.backslash + file.getName() );
				    	DataInputStream in= new DataInputStream(fstream);
						BufferedReader br = new BufferedReader(new InputStreamReader(in , Properties.UTF8 ));
						
						List<DataRecord> dataList = new ArrayList<DataRecord>(); 
						boolean startCollectData = false;
						String strLine;
						String erbAccount = "";
						boolean stopRead = false;

						while ((strLine = br.readLine()) != null && !stopRead)   {
							//compareERB(strLine,eurobankPattern);
							if (strLine.equals(eurobankPattern))
								startCollectData = true;
							
							//get account number
							if (!strLine.equals(eurobankPattern) && startCollectData) {
								if (strLine.startsWith(accountNumberPattern)) {
									stopRead=true;
									erbAccount = clearAccount(Utils.trimText(strLine, accountNumberPattern, Properties.LEFT ));
								}
							}
							
							if (!strLine.equals(eurobankPattern) && startCollectData && !stopRead && !strLine.equals(Properties.ignore)) {
								
								DataRecord record = new DataRecord(1);
								String[] element = strLine.split(Properties.semicolon);
								//logger.info("read line :" + strLine );
								record.setTransactionDate(Utils.stringToDate(element[0], Properties.TYPICAL_DATE_FORMAT));
								record.setTransactionDescription(element[2]);
								record.setAmount(Double.parseDouble(Utils.changeDemicalSign(element[3])));
								record.setTUN(generateTUNErb(record.getTransactionDate(), record.getAmount() , Double.parseDouble(Utils.changeDemicalSign(element[4]))));
								dataList.add(record);
							}
						}
						//add the erb account----bug
						addErbAccountToList(dataList ,erbAccount );
						erbBankList.addAll(dataList);
						if (dataList.size()== 0)
							logger.info("Eurobank's transaction file: " + file.getName() + " return 0 records");
						
						
						//Close the input stream
						in.close();
					}catch (Exception e){//Catch exception if any
						logger.error("EurobankControllerException::",e);
					}
			    }
			}
			//for debug
			//Utils.printDataRecordList(erbBankList);
			if (erbBankList.size()== 0)
				logger.info("No records added from Eurobank's transaction file(s)");
			else
				logger.info("Add " +erbBankList.size() + " records from Eurobank's transaction file(s)");
			
		}catch(Exception ex){
			logger.error("EurobankControllerException::", ex);
		}
		return erbBankList;
	}
	
	private String generateTUNErb(Date date, double amount, double remainAmount) {
		String tunNew = "";
		try {
			String datetostr = utils.Utils.dateToString(date,"dd/MM/yyyy" );
			datetostr = utils.Utils.removeCharacter(datetostr, Properties.slash);
			String amountStr = String.valueOf(amount);
			amountStr =	utils.Utils.removeCharacter(amountStr, Properties.dot) ;
			amountStr = utils.Utils.removeCharacter(amountStr, Properties.minus) ;
			String remainAmountstr = String.valueOf(remainAmount);
			remainAmountstr =	utils.Utils.removeCharacter(remainAmountstr, Properties.dot) ;
			remainAmountstr = utils.Utils.removeCharacter(remainAmountstr, Properties.minus) ;
			//TUN = DATE + amount + remainAmountstr 
			tunNew = datetostr + amountStr + remainAmountstr;
			
		}catch (Exception ex) {
			logger.error(" generateTUN AlphaBankControllerException::", ex);
		}
		return tunNew;
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
