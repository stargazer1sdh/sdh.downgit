package main;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCommit.File;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.HttpConnector;
import org.kohsuke.github.PagedIterable;
import org.kohsuke.github.PagedIterator;
import org.kohsuke.github.extras.ImpatientHttpConnector;

import bean.CommitBean;
import bean.FilePair;
import db.DBUtils;
import net.GzipDown;
import net.TextDown;
import utils.PropertiesUtils;

public class MainDown {
	private static final String RAW = "/raw/";
	private static final String HTTPS_GITHUB_COM = "https://github.com/";
	private static Logger log = Logger.getLogger(MainDown.class.getName());
	// static String repName = "stargazer1sdh/remote";
//	static String repName = "gchq/Gaffer";
//	static String repName = "CorfuDB/CorfuDB";
//	static String repName = "CloudSlang/cloud-slang";
//	static String repName = "Adobe-Consulting-Services/acs-aem-commons";
//	static String repName = "apache/kafka";
//	static String repName = "apache/zookeeper";
//	static String repName = "tinkerpop/blueprints";
	static String repName = "dianping/cat";
	static String BASEDir = "E:\\方向论文\\隐性冲突\\" + repName + "\\";

	public static void main(String[] args) {
		log.info("start");
		GHRepository rep = null;
		while (rep == null)
			rep = connectRep();

//		PagedIterable<GHCommit> commits = rep.listCommits();
		String fromsha = PropertiesUtils.getFromsha();
		PagedIterable<GHCommit> commits = rep.queryCommits().from(fromsha).list();
		PagedIterator<GHCommit> it = commits._iterator(1);

		while (!loopOfCommit(it)) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			rep = connectRep();
		}

		DBUtils.shut();
		log.info("end");
	}

	private static boolean loopOfCommit(PagedIterator<GHCommit> it) {
		try {
			while (it.hasNext()) {
				GHCommit co = it.next();
				boolean checkfpInserted = false;
				boolean needInsertCommit = true;
				int fpinno = 0; //已经在以前完成的filepair数量
				List<File> fs = null;
				if (DBUtils.hasInsertedCommit(co.getSHA1())) {
					needInsertCommit = false;
					try {
						fs = co.getFiles();
					} catch (Exception efs) {// 为了应对网络报错，程序中断
						efs.printStackTrace();
						log.info("efs:" + efs.getMessage() + " reconnect");
						return false;
					}
					fpinno =DBUtils.getInsertedFilePairsNo(co.getSHA1());
					if (fpinno==fs.size()) {
						continue;
					} else {
						checkfpInserted = true;
					}
				}
				if(fs==null) {
					try {
						fs = co.getFiles();
					} catch (Exception efs) {// 为了应对网络报错，程序中断
						efs.printStackTrace();
						log.info("efs2:" + efs.getMessage() + " reconnect");
						return false;
					}
				}
				PropertiesUtils.updateSha(co.getSHA1());
				String coDirstr = BASEDir + co.getSHA1() + "\\";
				java.io.File coDir = new java.io.File(coDirstr);
				coDir.mkdirs();
				List<String> parents = co.getParentSHA1s();
				CommitBean cb = new CommitBean(repName, coDirstr, co.getSHA1(), null, null);
				String parentsha = null;
				if (parents.size() < 1) {
					log.info(co.getSHA1() + " no parents");
				} else {
					parentsha = parents.get(0);
					cb.parent = parentsha;
					if (parents.size() > 1) {
						cb.parent2 = parents.get(1);
						if (parents.size() > 2) 
							log.info(co.getSHA1() + " has " + parents.size() + " parents");
					}
				}
				if(needInsertCommit)
					DBUtils.insertCommit(cb);
				
				int fi = fpinno;
				for (File f : fs) {
					if(checkfpInserted && DBUtils.hasInsertedFilePair(co.getSHA1(),f.getFileName()))
						continue;
					String[] ss = f.getFileName().split("/");
					String fname = ss[ss.length - 1];
					String pairDirstr = coDirstr + fi + fname + "\\";
					java.io.File pairDir = new java.io.File(pairDirstr);
					pairDir.mkdirs();
					String prevfpath = f.getPreviousFilename();
					String furl = HTTPS_GITHUB_COM + repName + RAW + co.getSHA1() + "/" + f.getFileName();
					String flasturl = null;
					if (parentsha != null) {
						if (prevfpath != null && !prevfpath.trim().equals("")) {
							flasturl = HTTPS_GITHUB_COM + repName + RAW + parentsha + "/" + prevfpath;
						} else {
							flasturl = HTTPS_GITHUB_COM + repName + RAW + parentsha + "/" + f.getFileName();
						}
					}
					FilePair fpbean = new FilePair(co.getSHA1(), pairDirstr, prevfpath, f.getFileName());

					String leftpath = pairDirstr + "left";
					String rightpath = pairDirstr + "right";
					if (flasturl != null)
						if(!GzipDown.down(flasturl, leftpath)) {
	//						log.info(co.getSHA1() + " GzipDown fail");
						}
					if(!GzipDown.down(furl, rightpath)) {// https://github.com/stargazer1sdh/remote/raw/aac98373a0d349adca4a9395741c002d430881f9/plus
	//					log.info(co.getSHA1() + " GzipDown fail");
					}
					
					DBUtils.insertFilePair(fpbean);
					// if (new java.io.File(leftpath).exists() && new
					// java.io.File(rightpath).exists()
					// && MyChangedistiller.hasChangedModifer(leftpath, rightpath)) {
					// log.info(co.getSHA1() + " hasChangedModifer in:" + f.getFileName());
					// } else {
					// // del
					// DelFile.delDir(pairDir);
					// }
					fi++;
				}

			}
		} catch (Exception eit) {// 为了应对it.hasNext()因网络报错，程序中断
			eit.printStackTrace();
			log.info("eit:" + eit.getMessage() + " reconnect");
			return false;
		}
		return true;
	}

	private static GHRepository connectRep() {
		GitHub github;
		GHRepository rep = null;
		try {

			// github = GitHubBuilder.fromCredentials()
			// .withConnector(new OkHttpConnector(new OkUrlFactory(new
			// OkHttpClient().setCache(cache))))
			// .build();
			String propertyFileName = "E:\\eclipse-workspace\\wsThesis\\com.sdh.downgit\\src\\main\\connect.properties";
			github = GitHubBuilder.fromPropertyFile(propertyFileName)
					.withConnector(new ImpatientHttpConnector(HttpConnector.DEFAULT, 60000 * 2, 60000 * 2)).build();
			// github = GitHub.connectUsingPassword("", "");
			rep = github.getRepository(repName);
		} catch (IOException e) {
			log.info("connectRep err:" + e.getMessage());
			e.printStackTrace();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			return null;
		}
		return rep;
	}

}
