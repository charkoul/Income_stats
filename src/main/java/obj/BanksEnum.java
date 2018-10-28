package obj;

public enum BanksEnum {
	
	
	EUROBANK (1),
	ALPHABANK (2),
	PIRAEUSBANK(3);

	
	//constructors
	private final int bankID;
	
	BanksEnum(int bankID){
		this.bankID=bankID;
	}
	
	public int getBankID() {
		return bankID;
	}

   
}
