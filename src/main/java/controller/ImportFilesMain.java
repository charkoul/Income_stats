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
				logger.info("AlphaBankController ERROR :" + ex.getMessage());
				logger.info( ex.fillInStackTrace());
			}
			try {
				List<DataRecord> euroBankList = eurobankkInstance.getERBData();	
			}catch (Exception ex){
				logger.info("EurobankController ERROR :" +ex.getMessage());
				logger.info( ex.fillInStackTrace());
			}
			try {
				piraeusBankInstance.readFilesPiraeus();
			}catch (Exception ex){
				logger.info("PiraeusBankController ERROR :" +ex.getMessage());
				logger.info( ex.fillInStackTrace());
			}
		
		}catch (Exception ex) {
			logger.info(ex.getMessage());
			logger.info( ex.fillInStackTrace());
			
		}

		LoggerClass.loggerFin();
	}

}
