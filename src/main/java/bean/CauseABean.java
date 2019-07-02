package bean;

public class CauseABean {
	public String repName; //gchq/Gaffer
	public String  sha ;
	public String  fullfile ;     //store-implementation/parquet-store/src/test/java/uk/gov/gchq/gaffer/parquetstore/utils/SortDataTest.java
	public String  changeStr  ; //Update: public  //Delete: public
	public String  changedRoot  ;            //uk.gov.gchq.gaffer.parquetstore.utils.SortDataTest.generatePreAggregatedData()
	                             //uk.gov.gchq.gaffer.federatedstore.FederatedStoreSchemaTest.STRING : String

	public String className;    //uk.gov.gchq.gaffer.federatedstore.FederatedStoreSchemaTest
	//fieldName与methodName中只有一个存在
	public String fieldName;   //STRING
	public String methodName;  //generatePreAggregatedData
	public int id;
	
	public CauseABean(String repName, String sha, String fullfile, String changeStr, String changedRoot) {
		super();
		this.repName = repName;
		this.sha = sha;
		this.fullfile = fullfile;
		this.changeStr = changeStr;
		this.changedRoot = changedRoot;
		
		if(changedRoot.contains("(")) {//method
			String temp = changedRoot.split("\\(")[0].trim();//uk.gov.gchq.gaffer.parquetstore.utils.SortDataTest.generatePreAggregatedData
			int lastdotIndex = temp.lastIndexOf('.');
			this.methodName = temp.substring(lastdotIndex+1);
			this.className = temp.substring(0,lastdotIndex);
		}else {                       //field
			String temp = changedRoot.split(":")[0].trim();//uk.gov.gchq.gaffer.federatedstore.FederatedStoreSchemaTest.STRING
			int lastdotIndex = temp.lastIndexOf('.');
			this.fieldName = temp.substring(lastdotIndex+1);
			this.className = temp.substring(0,lastdotIndex);
		}
	}

	public CauseABean() {
	}
	
}
