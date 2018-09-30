package controller;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import utils.LoggerClass;
import utils.Properties;
import java.io.File;


public class AlphaBankController {
	
	
	public static  String alphaBankPattern ="";
	static Logger logger = Logger.getLogger(LoggerClass.class);
	
	public void readFilesAlpha(){
		
		File folder = new File(Properties.rootFolder + Properties.alphaFolder);
		File[] listOfFiles = folder.listFiles();
		try {
			//read files and check the html files
			for (File file : listOfFiles) {
			    if (file.isFile() && file.getName().contains(Properties.htmlExtension)) {
			    	logger.info(file.getName());
			    	 
			    	String stringFromFile = FileUtils.readFileToString(new File(folder + file.getName()), "UTF-8");
			       
			        String unescaped = stringFromFile;
		        
			        logger.info("Unescaped: " + unescaped);
			        
			                
			    
			   }
			
			
			}
		}catch(Exception ex){
			
		}
		
	}
}
