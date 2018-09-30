package controller;

import controller.AlphaBankController;
import controller.EurobankController;
import controller.PiraeusBankController;

import org.apache.log4j.Logger;

import utils.LoggerClass;

import utils.Utils;



public class ImportFilesMain {
	
	 static Logger logger = Logger.getLogger(LoggerClass.class);
		
	public static void main( String[] args ){
		LoggerClass.loggerStart();
		try {
			
			AlphaBankController alphaBankInstance = new AlphaBankController();
			EurobankController eurobankkInstance = new EurobankController();
			PiraeusBankController piraeusBankInstance = new PiraeusBankController();
			try {
				alphaBankInstance.readFilesAlpha();
			}catch (Exception ex){
				logger.info("AlphaBankController ERROR :" + ex.getMessage());
				logger.info( ex.fillInStackTrace());
			}
			try {
				eurobankkInstance.readsFileERB();	
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
