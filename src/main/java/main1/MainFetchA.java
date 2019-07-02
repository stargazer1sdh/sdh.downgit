package main1;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import bean.CommitBean;
import bean.FilePair;
import chdistill.MyChangedistiller;
import db.DBUtils;

public class MainFetchA {
	private static Logger log = Logger.getLogger(MainFetchA.class.getName());
//	public static String repName = null;
//	public static String sha = null;
//	public static String fullFileName = null;

	public static void main(String[] args) {
		log.info("1MainFetchA start");

		List<CommitBean> cbs = DBUtils.getCommits();
		for (Iterator iterator = cbs.iterator(); iterator.hasNext();) {
			CommitBean cb = (CommitBean) iterator.next();
			loopOfCommit(cb);
		}

		DBUtils.shut();
		log.info("1MainFetchA end");
	}

	private static void loopOfCommit(CommitBean cb) {
		List<FilePair> fps = DBUtils.getfilepairsIn(cb.sha);
		for (FilePair fp : fps) {
			String pairDirstr = fp.dir + "\\";
			java.io.File pairDir = new java.io.File(pairDirstr);

			String leftpath = pairDirstr + "left.java";
			String rightpath = pairDirstr + "right.java";

//			repName = cb.repName;
//			sha = cb.sha;
//			fullFileName = fp.filename;
			if (new java.io.File(leftpath).exists() && new java.io.File(rightpath).exists()
					&& MyChangedistiller.hasChangedAttributeType(leftpath, rightpath,cb.repName,cb.sha,fp.filename)) {
			} 
		}

	}

}
