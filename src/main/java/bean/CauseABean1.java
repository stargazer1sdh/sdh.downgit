package bean;

public class CauseABean1 {
	public String repName; //gchq/Gaffer
	public String  sha ;
	public String  fullfile ;     //store-implementation/parquet-store/src/test/java/uk/gov/gchq/gaffer/parquetstore/utils/SortDataTest.java
	public String className;    //uk.gov.gchq.gaffer.federatedstore.FederatedStoreSchemaTest
	public String fieldName;   
	public String type0;   
	public String type1;
	public int id;
	
	public CauseABean1(String repName, String sha, String fullfile, String className, String fieldName, String type0,
			String type1) {
		super();
		this.repName = repName;
		this.sha = sha;
		this.fullfile = fullfile;
		this.className = className;
		this.fieldName = fieldName;
		this.type0 = type0;
		this.type1 = type1;
	}

	public CauseABean1() {
		// TODO Auto-generated constructor stub
	}   
}
