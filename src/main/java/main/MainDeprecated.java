package main;


import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCommit.File;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.kohsuke.github.PagedIterator;

import net.TextDown;


public class MainDeprecated {
	private static Logger log = Logger.getLogger(MainDeprecated.class.getName());
	static String BASEDir = "E:\\方向论文\\隐性冲突\\downGaffer\\";

	public static void main(String[] args) {
		log.info("start");
		GitHub github = null;
		try {
			github = GitHub.connectUsingPassword("stargazer1sdh", "");
//			GHRepository rep = github.getRepository("gchq/Gaffer");
			GHRepository rep = github.getRepository("stargazer1sdh/remote");
			PagedIterable<GHCommit> commits = rep.listCommits();
			PagedIterator<GHCommit> it = commits._iterator(20);
			while(it.hasNext()) {
				GHCommit co = it.next();
				String coDirstr = BASEDir+co.getSHA1()+"\\";
				java.io.File coDir = new java.io.File(coDirstr);
				coDir.mkdirs();
				List<String> parents = co.getParentSHA1s();
				if(parents.size()<1) {
					log.info(co.getSHA1()+" no parents");
					continue;
				}
				String parentsha = parents.get(0);
				StringBuilder sb = new StringBuilder(co.getSHA1()).append(" p: ").append(parentsha);
				log.info(sb.toString());
				List<File> fs = co.getFiles();
				int fi=0;
				for(File f:fs) {
					System.err.println(f.getFileName());
					String pairstr = coDirstr+fi+"\\";
					java.io.File pairDir = new java.io.File(pairstr);
					pairDir.mkdirs();
					String prevfpath = f.getPreviousFilename();
					String frawurl = f.getRawUrl().toString();//如果在此commit中，此文件被删，则返回的是parent的
					String flasturl;
					if(prevfpath!=null && !prevfpath.trim().equals("")) {
						int rawi = frawurl.indexOf(co.getSHA1());
						StringBuilder sb2 = new StringBuilder(frawurl);
						sb2.delete(rawi, sb2.length()-1).append(parentsha).append("/").append(prevfpath);
						flasturl = sb2.toString();
					}else {
						flasturl = frawurl.replace(co.getSHA1(), parentsha);
					}
//					if(frawurl.endsWith(".java")&&flasturl.endsWith(".java")) {
						TextDown.down(flasturl,pairstr+"left.java");
						TextDown.down(f.getRawUrl(),pairstr+"right.java");
//					}
					fi++;
				}
			}
		} catch (Exception e) {
			log.info("error:"+e.getMessage());
			e.printStackTrace();
		}
		log.info("end");
	}

}
