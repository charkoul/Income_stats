package utils;

import org.apache.log4j.Logger;

public class LoggerClass {
	 static Logger logger = Logger.getLogger(LoggerClass.class);
	 
	 public static final void loggerStart() {
			logger.info( "************************************************" );
			logger.info( "        INCOME IMPORT FILE v1.0                 " );
			logger.info( "@author:	Charalampos Koulaouzidis	          " );
			logger.info( "@email:	ch.koulaouzidis@gmail.com	          " );
			logger.info( "************************************************" );
			
		}
		
		
		public static final void loggerFin() {
			logger.info( "************************************************" );
			logger.info( "                  END							  " );
			logger.info( "************************************************" );
			
		}
		

}
