package obj;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
	private String TUN;
	
	
	public String getTUN() {
		return TUN;
	}

	public void setTUN(String tUN) {
		TUN = tUN;
	}

	public DataRecord(int bank) {
		super();
		this.bank = bank;
	}

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
	
	@Override
    public boolean equals(Object o) {
	    if (o instanceof DataRecord) {
	        DataRecord that = (DataRecord) o;
	        return Objects.equals(this.TUN, that.TUN);
	    }
	    return false;
    }
	
	@Override
    public int hashCode() {
           return Objects.hashCode(TUN);
    }

}
