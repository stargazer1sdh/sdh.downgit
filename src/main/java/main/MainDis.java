package main;


import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCommit.File;

import chdistill.MyChangedistiller;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.kohsuke.github.PagedIterator;

import net.TextDown;


public class MainDis {
	private static final String RAW = "/raw/";
	private static final String HTTPS_GITHUB_COM = "https://github.com/";
	private static Logger log = Logger.getLogger(MainDis.class.getName());
//	static String repName = "stargazer1sdh/remote";
	static String repName = "gchq/Gaffer";
	static String BASEDir = "E:\\方向论文\\隐性冲突\\"+repName+"\\";
	

	public static void main(String[] args) {
		log.info("start");
		GitHub github = null;
		try {
			github = GitHub.connectUsingPassword("stargazer1sdh", "");
			GHRepository rep = github.getRepository(repName);
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
				StringBuilder sb = new StringBuilder(co.getSHA1()).append(" p ").append(parentsha);
				log.info(sb.toString());
				List<File> fs = co.getFiles();
				int fi=0;
				for(File f:fs) {
					String pairDirstr = coDirstr+fi+"\\";
					java.io.File pairDir = new java.io.File(pairDirstr);
					pairDir.mkdirs();
					String prevfpath = f.getPreviousFilename();
					String furl = HTTPS_GITHUB_COM+repName+RAW+co.getSHA1()+"/"+f.getFileName();
					String flasturl;
					if(prevfpath!=null && !prevfpath.trim().equals("")) {
						flasturl = HTTPS_GITHUB_COM+repName+RAW+parentsha+"/"+prevfpath;
					}else {
						flasturl = HTTPS_GITHUB_COM+repName+RAW+parentsha+"/"+f.getFileName();
					}
					if(furl.endsWith(".java")&&flasturl.endsWith(".java")) {
						String leftpath = pairDirstr+"left.java";
						String rightpath = pairDirstr+"right.java";
						TextDown.down(flasturl,leftpath);
						TextDown.down(furl,rightpath,true);//https://github.com/stargazer1sdh/remote/raw/aac98373a0d349adca4a9395741c002d430881f9/plus
						if(new java.io.File(leftpath).exists() && new java.io.File(rightpath).exists() &&
								MyChangedistiller.hasChangedModifer(leftpath,rightpath)) {
							log.info(co.getSHA1()+" hasChangedModifer in:"+f.getFileName());
						}else {
							//del 
							DelFile.delDir(pairDir);
						}
					}
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
