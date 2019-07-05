package bean;

public class CauseBBean1 {
	public String repName; //gchq/Gaffer
	public int causeAId; //causeA表
	public String  sha ; //B的commit表
	public int filePairId; //B的filePair表
//	public String  fullfile ;     //store-implementation/parquet-store/src/test/java/uk/gov/gchq/gaffer/parquetstore/utils/SortDataTest.java
	public int startPos;
	public int endPos;
//	public String className;    //uk.gov.gchq.gaffer.federatedstore.FederatedStoreSchemaTest
//	//fieldName与methodName中只有一个存在
//	public String fieldName;   //STRING
//	public String methodName;  //generatePreAggregatedData
	
	
	public CauseBBean1(String repName, int causeAId, String sha, int filePairId, int startPos, int endPos) {
		super();
		this.repName = repName;
		this.causeAId = causeAId;
		this.sha = sha;
		this.filePairId = filePairId;
		this.startPos = startPos;
		this.endPos = endPos;
	}
}
