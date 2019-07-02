package downgit.com.sdh.downgit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import chdistill.MyChangedistiller;


public class MychangedistllerTest {

//	@Test
//	public void test() {
//		String left = "E:\\eclipse-workspace\\wsThesis\\com.sdh.downgit\\src\\main\\java\\chdistill\\source\\C1.java";
//		String right = "E:\\eclipse-workspace\\wsThesis\\com.sdh.downgit\\src\\main\\java\\chdistill\\source\\C2.java";
//		assertEquals(true, MyChangedistiller.hasChangedModifer(left, right));
//	}
//	
//	@Test
//	public void testf() {
//		String left = "E:\\eclipse-workspace\\wsThesis\\com.sdh.downgit\\src\\main\\java\\chdistill\\source\\C1.java";
//		String right = "E:\\eclipse-workspace\\wsThesis\\com.sdh.downgit\\src\\main\\java\\chdistill\\source\\C3.java";
//		assertEquals(false, MyChangedistiller.hasChangedModifer(left, right));
//	}
	
	@Test
	public void test3() {
		String left = "E:\\eclipse-workspace\\wsThesis\\com.sdh.downgit\\src\\main\\java\\chdistill\\source\\C3.java";
		String right = "E:\\eclipse-workspace\\wsThesis\\com.sdh.downgit\\src\\main\\java\\chdistill\\source\\C4.java";
		assertEquals(false, MyChangedistiller.hasChangedModifer(left, right));
	}
}
