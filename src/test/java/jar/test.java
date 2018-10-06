package jar;

public class test {

	public static void main( String[] args ) {
		String str="﻿Τίτλος;Κινήσεις Λογαριασμού: GR6901401880188002340018108";
		
		System.out.println(utils.Utils.trimText(str, ":", "left"));
		
		String no_trim_text = "     text      no trimed   ";
		
		String trimed = no_trim_text.trim();
		System.out.println("no_trim_text:" + no_trim_text + ".");
		System.out.println("trimed:" + trimed +".");
		
		
		try {
			utils.Utils.stringToDate("30/9/2018" , "dd/MM/yyyy");
		}catch (Exception ex){
			
		}
		
	}
}
