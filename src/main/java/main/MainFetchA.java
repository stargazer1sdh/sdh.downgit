package main;

import java.io.IOException;
import java.util.Iterator;
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
import chdistill.MyChangedistiller;
import db.DBUtils;
import net.GzipDown;
import net.TextDown;
import utils.PropertiesUtils;

public class MainFetchA {
	private static Logger log = Logger.getLogger(MainFetchA.class.getName());
	public static String repName = null;
	public static String sha = null;
	public static String fullFileName = null;

	public static void main(String[] args) {
		log.info("MainFetchA start");

		List<CommitBean> cbs = DBUtils.getCommits();
		for (Iterator iterator = cbs.iterator(); iterator.hasNext();) {
			CommitBean cb = (CommitBean) iterator.next();
			loopOfCommit(cb);
		}

		DBUtils.shut();
		log.info("MainFetchA end");
	}

	private static void loopOfCommit(CommitBean cb) {
		List<FilePair> fps = DBUtils.getfilepairsIn(cb.sha);
		for (FilePair fp : fps) {
			String pairDirstr = fp.dir + "\\";
			java.io.File pairDir = new java.io.File(pairDirstr);

			String leftpath = pairDirstr + "left";
			String rightpath = pairDirstr + "right";

			repName = cb.repName;
			sha = cb.sha;
			fullFileName = fp.filename;
			if (new java.io.File(leftpath).exists() && new java.io.File(rightpath).exists()
					&& fp.filename.endsWith(".java")
					&& MyChangedistiller.hasChangedModifer(leftpath, rightpath)) {
//				log.info(cb.repName+" "+cb.sha + " hasChangedModifer in:" + fp.filename);
			} 
		}

	}

}
