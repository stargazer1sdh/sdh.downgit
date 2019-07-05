package main1;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import bean.CauseABean1;
import bean.CauseBBean1;
import bean.CommitBean;
import bean.FilePair;
import ch.uzh.ifi.seal.changedistiller.model.classifiers.SourceRange;
import chdistill.MyChangedistiller;
import db.DBUtils;

public class MainFetchB1 {
	private static Logger log = Logger.getLogger(MainFetchB1.class.getName());

	public static void main(String[] args) {
		log.info("MainFetchB1 start");

		List<CauseABean1> cbs = DBUtils.getCauseA1s();
		for (Iterator<CauseABean1> iterator = cbs.iterator(); iterator.hasNext();) {
			CauseABean1 causeA1 = iterator.next();
			loopOfCommit(causeA1);
		}

		DBUtils.shut();
		log.info("MainFetchB1 end");
	}

	private static void loopOfCommit(CauseABean1 causeA1) {
		Set<CommitBean> cbs = DBUtils.getCommitsAround(causeA1.sha,1);
		for(CommitBean commitb : cbs) {
			List<FilePair> fps = DBUtils.getfilepairsIn(commitb.sha);
			for (FilePair fp : fps) {
				String pairDirstr = fp.dir + "\\";
				java.io.File pairDir = new java.io.File(pairDirstr);
				
				String leftpath = pairDirstr + "left.java";
				String rightpath = pairDirstr + "right.java";
				
				if (new java.io.File(rightpath).exists()) {
					List<SourceRange> sourceRangesInRight = MyChangedistiller.getSourceRangesInRight(leftpath,rightpath);
					for(SourceRange sourceRange : sourceRangesInRight) {
						CauseBBean1 causeb1 = new CauseBBean1(causeA1.repName, causeA1.id, commitb.sha, fp.id, sourceRange.getStart(), sourceRange.getEnd());
						DBUtils.insertCauseB1(causeb1);
					}
				} 
			}
			
		}
		
		

	}

}
