package main;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class AddAppendix {
	private static Logger log = Logger.getLogger(AddAppendix.class.getName());
	public static void main(String[] args) {
		String[] bases = new String[] {"E:\\方向论文\\隐性冲突\\dianping","E:\\方向论文\\隐性冲突\\tinkerpop","E:\\方向论文\\隐性冲突\\apache",
				"E:\\方向论文\\隐性冲突\\Adobe-Consulting-Services","E:\\方向论文\\隐性冲突\\CloudSlang","E:\\方向论文\\隐性冲突\\CorfuDB",
				"E:\\方向论文\\隐性冲突\\gchq","E:\\方向论文\\隐性冲突\\stargazer1sdh"};
		FileFilter dirfilter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		};
		for(String base :  bases) {
			File basef = new File(base);
			File[] indirs = basef.listFiles(dirfilter);
			for(File dir : indirs) {
				File[] indirs2 = dir.listFiles(dirfilter);
				for(File dir2 : indirs2) {
					File[] indirs3 = dir2.listFiles(dirfilter);
					for(File dir3 : indirs3) {
						String dirName = dir3.getName();
						int doti = dirName.indexOf('.');
						if(doti>=0) {
							String appendix = dirName.substring(doti);
							File[] leftRight = dir3.listFiles();
							for(File lOrR : leftRight) {
								String fName = lOrR.getName();
								if(fName.startsWith("left") || fName.startsWith("right")) {
									String parent = lOrR.getParent();
									File newFile = new File(parent +"/left" + appendix);
									if(fName.startsWith("right")){
										newFile = new File(parent +"/right" + appendix);
									}
									if (!lOrR.renameTo(newFile)) {
										String mes = lOrR + "add appendix fails";
										System.err.println(mes);
										log.info(mes);
									}
								}
							}
						}
					}
				}
			}
			System.out.println(Arrays.asList(indirs));
		}
	}

}
