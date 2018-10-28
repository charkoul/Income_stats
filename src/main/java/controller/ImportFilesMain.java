package controller;

import controller.AlphaBankController;
import controller.EurobankController;
import controller.PiraeusBankController;
import obj.DataRecord;

import java.util.ArrayList;
import java.util.List;

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
			
		
		}catch (Exception ex) {
			logger.error(ex);
		}
		LoggerClass.loggerFin();
	}

}
