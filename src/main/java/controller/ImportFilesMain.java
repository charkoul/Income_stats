package controller;

import controller.AlphaBankController;
import controller.EurobankController;
import controller.PiraeusBankController;
import obj.DataRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import utils.LoggerClass;
import utils.Utils;

public class ImportFilesMain {
	
	static Logger logger = Logger.getLogger(ImportFilesMain.class);
		
	public static void main( String[] args ){
		
		LoggerClass.loggerStart();
		try {
			
			AlphaBankController alphaBankInstance = new AlphaBankController();
			EurobankController eurobankkInstance = new EurobankController();
			PiraeusBankController piraeusBankInstance = new PiraeusBankController();
			List<DataRecord> transactionList =new ArrayList<DataRecord>();
			try {
				List<DataRecord> aplhaBankList = alphaBankInstance.getAlphaBankData();
				transactionList.addAll(aplhaBankList);
			}catch (Exception ex){
				logger.error("AlphaBank files did not import due to Exception :" , ex);
			}
			try {
				List<DataRecord> euroBankList = eurobankkInstance.getERBData();	
				transactionList.addAll(euroBankList);
			}catch (Exception ex){
				logger.error("Eurobank files did not import due to Exception :", ex);
			}
			try {
				List<DataRecord> piraeusBankList = piraeusBankInstance.getPiraeusBankData();
				transactionList.addAll(piraeusBankList);
			}catch (Exception ex){
				logger.error("PiraeusBank files did not import due to Exception :", ex);
			}
			
			//for debug
			Utils.printDataRecordList(transactionList);
			//remove list from duplicates values based on TUN
			List<DataRecord> transactionListClean = new ArrayList<>(new HashSet<>(transactionList));
			//for debug
			Utils.printDataRecordList(transactionListClean);
			logger.info("Import " + transactionList.size() + " records from files. Duplicate records are " 
						+  (transactionList.size() - transactionListClean.size()) + ". Finally "
						+ transactionListClean.size() + " records are going to insert.");
			
			
			
		
		}catch (Exception ex) {
			logger.error(ex);
		}
		LoggerClass.loggerFin();
	}
	
	
	
	

}
