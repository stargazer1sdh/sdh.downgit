package zTest;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(("package test.ppa;\r\n" + 
				"\r\n" + 
				"class A extends B {\r\n" + 
				"\r\n" + 
				"  public boolean methodA() {\r\n" + 
				"    ").length());
	
		System.out.println("int index = 0;\r\n".length());
		
		String s = "package test.ppa;\r\n" + 
				"\r\n" + 
				"class A extends B {\r\n" + 
				"\r\n" + 
				"  public boolean methodA() {\r\n" + 
				"    int index = 0;\r\n" + 
				"    ";
		System.out.println(s.length());
		System.out.println("C varC = new D();".length());
		
		s = "package test.ppa;\r\n" + 
				"\r\n" + 
				"class A extends B {\r\n" + 
				"\r\n" + 
				"  public boolean methodA() {\r\n" + 
				"    int index = 0;\r\n" + 
				"    C varC = new D();\r\n" + 
				"    E varE = new E();\r\n" + 
				"    varC.field1 = index;\r\n" + 
				"    ";
		System.out.println(s.length());
		System.out.println("varC.method2(varC.field1,varE);".length());
	}

}
