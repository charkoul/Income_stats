package obj;

public enum JdbcEnum {

	INCOME_DB ("income_db");

	
	//constructors
	private final String jdbcValue;
	
	JdbcEnum(String jdbcValue){
		this.jdbcValue=jdbcValue;
	}
	
	public String getJdbcValue() {
		return jdbcValue;
	}
}
