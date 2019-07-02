package chdistill.source;
import uk.gov.gchq.gaffer.parquetstore.utils.SortDataTest;
public class L {
//	static {
//		new SortDataTest().generatePreAggregatedData();
//		new SortDataTest().param = 1;
//	}
	void f() {
	    SortDataTest dt = new SortDataTest();
		int a=1+dt.param;
		dt.generatePreAggregatedData();
		new SortDataTest().param = 3;
	}
}