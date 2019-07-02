package main;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import bean.CauseABean;
import bean.CauseBBean;
import bean.CommitBean;
import bean.FilePair;
import ch.uzh.ifi.seal.changedistiller.model.classifiers.SourceRange;
import chdistill.MyChangedistiller;
import db.DBUtils;

public class MainFetchB1 {
	private static Logger log = Logger.getLogger(MainFetchB1.class.getName());

	public static void main(String[] args) {
		log.info("MainFetchB1 start");

		List<CauseABean> cbs = DBUtils.getCauseAs();
		for (Iterator<CauseABean> iterator = cbs.iterator(); iterator.hasNext();) {
			CauseABean causeA = iterator.next();
			loopOfCommit(causeA);
		}

		DBUtils.shut();
		log.info("MainFetchB1 end");
	}

	private static void loopOfCommit(CauseABean causeA) {
		Set<CommitBean> cbs = DBUtils.getCommitsAround(causeA.sha,1);
		for(CommitBean commitb : cbs) {
			List<FilePair> fps = DBUtils.getfilepairsIn(commitb.sha);
			for (FilePair fp : fps) {
				String pairDirstr = fp.dir + "\\";
				java.io.File pairDir = new java.io.File(pairDirstr);
				
				String leftpath = pairDirstr + "left";
				String rightpath = pairDirstr + "right";
				
				if (new java.io.File(rightpath).exists()
						&& fp.filename.endsWith(".java")) {
					List<SourceRange> sourceRangesInRight = MyChangedistiller.getSourceRangesInRight(leftpath,rightpath);
					for(SourceRange sourceRange : sourceRangesInRight) {
						CauseBBean causeb = new CauseBBean(causeA.repName, causeA.id, commitb.sha, fp.id, sourceRange.getStart(), sourceRange.getEnd());
						DBUtils.insertCauseB(causeb);
					}
				} 
			}
			
		}
		
		

	}

}
