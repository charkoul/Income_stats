package obj;

public enum Banks {
	
	
	EUROBANK (1),
	ALPHABANK (2),
	PIRAEUSBANK(3);

	
	//constructors
	private final int bankID;
	
	Banks(int bankID){
		this.bankID=bankID;
	}
	
	public int getBankID() {
		return bankID;
	}

   
}
