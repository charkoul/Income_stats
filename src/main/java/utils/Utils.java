package utils;

import org.apache.log4j.Logger;

public class Utils {
	
	static Logger logger = Logger.getLogger(LoggerClass.class);
	
	protected static final void loggerStart() {
		logger.info( "************************************************" );
		logger.info( "               HTML TO DOC v1.0                 " );
		logger.info( "@author:	Charalampos Koulaouzidis	          " );
		logger.info( "@email:	ch.koulaouzidis@gmail.com	          " );
		logger.info( "************************************************" );
		
	}
	
	
	protected static final void loggerFin() {
		logger.info( "************************************************" );
		logger.info( "                  END							  " );
		logger.info( "************************************************" );
		
	}
	

}
