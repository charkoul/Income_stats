package controller;

import controller.AlphaBankController;
import controller.EurobankController;
import controller.PiraeusBankController;
import obj.DataRecord;

import java.util.List;

import org.apache.log4j.Logger;

import utils.LoggerClass;

public class ImportFilesMain {
	
	static Logger logger = Logger.getLogger(ImportFilesMain.class);
		
	public static void main( String[] args ){
		LoggerClass.loggerStart();
		try {
			
			AlphaBankController alphaBankInstance = new AlphaBankController();
			EurobankController eurobankkInstance = new EurobankController();
			PiraeusBankController piraeusBankInstance = new PiraeusBankController();
			try {
				List<DataRecord> aplhaBankList = alphaBankInstance.getAlphaBankData();
			}catch (Exception ex){
				logger.error("AlphaBank files did not import due to Exception :" , ex);
			}
			try {
				List<DataRecord> euroBankList = eurobankkInstance.getERBData();	
			}catch (Exception ex){
				logger.error("Eurobank files did not import due to Exception :", ex);
			}
			try {
				List<DataRecord> piraeusBankList = piraeusBankInstance.getPiraeusBankData();
			}catch (Exception ex){
				logger.error("PiraeusBank files did not import due to Exception :", ex);
				
			}
		
		}catch (Exception ex) {
			logger.error(ex);
			
			
		}

		LoggerClass.loggerFin();
	}

}
