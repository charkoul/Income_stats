package obj;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import utils.LoggerClass;

public class DataRecord {
	
	private Date transactionDate;
	private String transactionDescription;
	private String transactionComment;
	private double amount;
	private String accountNumber;
	private String transactionNumber;
	private int bank;
	
	
	
	

	public DataRecord() {
		super();
	}
	
		
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionDescription() {
		return transactionDescription;
	}
	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}
	public String getTransactionComment() {
		return transactionComment;
	}
	public void setTransactionComment(String transactionComment) {
		this.transactionComment = transactionComment;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	
	public int getBank() {
		return bank;
	}


	public void setBank(int bank) {
		this.bank = bank;
	}

	
	
	

}
