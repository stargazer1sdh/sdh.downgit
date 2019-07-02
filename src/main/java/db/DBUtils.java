package db;

import bean.CauseABean1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import bean.CauseABean;
import bean.CauseBBean;
import bean.CommitBean;
import bean.FilePair;

public class DBUtils {
	private static Logger logger = Logger.getLogger(DBUtils.class.getName());
	private static Connection conn = null;
	static {
		while (!setCon()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
				logger.error("dbcon err");
			}
		}
	}

	private static boolean setCon() {
		boolean flag = false;
		try {
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/mysql?user=root&password=root&useSSL=true");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/mysql?user=root&useSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
			if (null != conn)
				flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("dbcon err:"+e);
			flag = false;
		}
		return flag;
	}

	public static void shut() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("db shut err");
			}
		}
	}


	public static int insertCommit(CommitBean cb){
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "INSERT INTO conflict.commit (repName,dir,sha,parent,parent2) VALUES (?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			int i = 1;
			pstmt.setString(i++, cb.repName);
			pstmt.setString(i++, cb.dir);
			pstmt.setString(i++, cb.sha);
			pstmt.setString(i++, cb.parent);
			pstmt.setString(i++, cb.parent2);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(cb.sha + " p:" + cb.parent +" insertCommit err:" + e);
			return 0;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}

	public static int insertFilePair(FilePair fp){
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "INSERT INTO conflict.filePair (commitsha,dir,prevfpath,filename) VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			int i = 1;
			pstmt.setString(i++, fp.commitsha);
			pstmt.setString(i++, fp.dir);
			pstmt.setString(i++, fp.prevfpath);
			pstmt.setString(i++, fp.filename);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(fp.filename+" in "+fp.commitsha +" insertFilePair err:" + e);
			return 0;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}

	public static boolean hasInsertedCommit(String sha) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM conflict.commit where sha=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sha);
			rs = pstmt.executeQuery();
		    return rs.next();
		}
		catch (SQLException e) {
			e.printStackTrace();
			logger.error("db hasInsertedCommit "+sha+" : "+e);
			return hasInsertedCommit(sha);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}

	public static boolean hasInsertedFilePairs(String sha) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM conflict.filePair where commitsha=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sha);
			rs = pstmt.executeQuery();
		    return rs.next();
		}
		catch (SQLException e) {
			e.printStackTrace();
			logger.error("db hasInsertedFilePairs "+sha+" : "+e);
			return hasInsertedFilePairs(sha);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}
	public static boolean hasInsertedFilePair(String sha, String fileName) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM conflict.filePair where commitsha=? and filename=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sha);
			pstmt.setString(2, fileName);
			rs = pstmt.executeQuery();
		    return rs.next();
		}
		catch (SQLException e) {
			e.printStackTrace();
			logger.error("db hasInsertedFilePair sha:"+sha+" ,fileName: "+fileName+" err:"+e);
			return hasInsertedFilePair(sha,fileName);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}

	public static int deleteCommit(String sha) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "delete from conflict.commit where sha=?";
			pstmt = conn.prepareStatement(sql);
			int i = 1;
			pstmt.setString(i++, sha);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(sha +" deleteCommit err : " + e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
		return 0;
	}

	public static void deleteFilePairs(String sha) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "delete from conflict.filePair where commitsha=?";
			pstmt = conn.prepareStatement(sql);
			int i = 1;
			pstmt.setString(i++, sha);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(sha +" deleteFilePairs err : " + e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}

	public static int getInsertedFilePairsNo(String sha) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM conflict.filePair where commitsha=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sha);
			rs = pstmt.executeQuery();
			rs.next();
		    return rs.getInt(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
			logger.error("db getInsertedFilePairsNo "+sha+" : "+e);
			return getInsertedFilePairsNo(sha);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}

	public static List<CommitBean> getCommits() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT repName, dir, sha, parent, parent2 FROM conflict.commit";
		List<CommitBean> list = new ArrayList<CommitBean>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int i=1;
				CommitBean cb = new CommitBean(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++));
				list.add(cb);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			logger.error("db getCommits  : "+e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
		return list;
	}

	public static List<FilePair> getfilepairsIn(String sha) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT commitsha, dir, prevfpath, filename,id FROM conflict.filePair where commitsha=?";
		List<FilePair> list = new ArrayList<FilePair>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sha);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int i=1;
				FilePair fp = new FilePair(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++));
				fp.id = rs.getInt(i++);
				list.add(fp);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			logger.error("db getfilepairsIn "+sha+" : "+e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
		return list;
	}

	public static int insertcauseA(CauseABean causeA) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "INSERT INTO conflict.causeA (repName,sha,fullfile,changeStr,changedRoot,className,fieldName,methodName) VALUES (?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			int i = 1;
			pstmt.setString(i++, causeA.repName);
			pstmt.setString(i++, causeA.sha);
			pstmt.setString(i++, causeA.fullfile);
			pstmt.setString(i++, causeA.changeStr);
			pstmt.setString(i++, causeA.changedRoot);
			pstmt.setString(i++, causeA.className);
			pstmt.setString(i++, causeA.fieldName);
			pstmt.setString(i++, causeA.methodName);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(pstmt);
			logger.error(causeA.sha + " file:" + causeA.fullfile +" insertcauseA err:" + e);
			return 0;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}
	public static int insertcauseA(CauseABean1 causeA) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "INSERT INTO conflict.causeA1 (repName,sha,fullfile,className,fieldName,type0,type1) VALUES (?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			int i = 1;
			pstmt.setString(i++, causeA.repName);
			pstmt.setString(i++, causeA.sha);
			pstmt.setString(i++, causeA.fullfile);
			pstmt.setString(i++, causeA.className);
			pstmt.setString(i++, causeA.fieldName);
			pstmt.setString(i++, causeA.type0);
			pstmt.setString(i++, causeA.type1);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(pstmt);
			logger.error(causeA.sha + " file:" + causeA.fullfile +" insertcauseA1 err:" + e);
			return 0;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}

	public static List<CauseABean> getCauseAs() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT repName,sha,fullfile,changeStr,changedRoot,className,fieldName,methodName,id FROM conflict.causeA";
		List<CauseABean> list = new ArrayList<CauseABean>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int i=1;
				CauseABean causeA = new CauseABean();
				causeA.repName = rs.getString(i++);
				causeA.sha = rs.getString(i++);
				causeA.fullfile = rs.getString(i++);
				causeA.changeStr = rs.getString(i++);
				causeA.changedRoot = rs.getString(i++);
				causeA.className = rs.getString(i++);
				causeA.fieldName = rs.getString(i++);
				causeA.methodName = rs.getString(i++);
				causeA.id = rs.getInt(i++);
				list.add(causeA);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			logger.error("db getCauseAs : "+e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
		return list;
	}

	/*考虑到son commit中的信息都是以parent为基准，也就是说它实际上包含了parent2路径上的所有信息，所以不再考虑son与parent2之间的关系*/
	public static Set<CommitBean> getCommitsAround(String sha, int radius) {
		Set<CommitBean> set = new HashSet<CommitBean>();
		Set<CommitBean> children = getChildrenOfCommit(sha);
		Set<CommitBean> parents = getParentsOfCommit(sha);
		set.addAll(parents);
		set.addAll(children);
		while(--radius>0) {
			Set<CommitBean> newparents = new HashSet<CommitBean>();
			for(CommitBean cb : parents) {
				newparents.addAll(getParentsOfCommit(cb.sha));
			}
			parents = newparents;
			set.addAll(parents);
			
			Set<CommitBean> newchildren = new HashSet<CommitBean>();
			for(CommitBean cb : children) {
				newchildren.addAll(getChildrenOfCommit(cb.sha));
			}
			children = newchildren;
			set.addAll(children);
		}
		return set;
	}
	private static Set<CommitBean> getChildrenOfCommit(String sha) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT repName, dir, sha, parent, parent2 FROM conflict.commit where parent=?";
		Set<CommitBean> list = new HashSet<CommitBean>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sha);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int i=1;
				CommitBean cb = new CommitBean(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++));
				list.add(cb);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			logger.error("db getChildOfCommit "+sha+"  : "+e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
		return list;
	}
	private static Set<CommitBean> getParentOfCommit(String sha) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT repName, dir, sha, parent, parent2 FROM conflict.commit where sha = (SELECT parent FROM conflict.commit where sha = ?)";
		Set<CommitBean> list = new HashSet<CommitBean>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sha);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int i=1;
				CommitBean cb = new CommitBean(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++));
				list.add(cb);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			logger.error("db getParentsOfCommit "+sha+"  : "+e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
		return list;
	}
	private static Set<CommitBean> getParentsOfCommit(String sha) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT repName, dir, sha, parent, parent2 FROM conflict.commit where sha = (SELECT parent FROM conflict.commit where sha = ?)"
				+ " OR sha = (SELECT parent2 FROM conflict.commit where sha = ?)";
		Set<CommitBean> list = new HashSet<CommitBean>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sha);
			pstmt.setString(2, sha);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int i=1;
				CommitBean cb = new CommitBean(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++));
				list.add(cb);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			logger.error("db getParentsOfCommit "+sha+"  : "+e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
		return list;
	}

	public static void main(String[] args) {
//		getCommitsAround("77309b9996fbff6367d7d9ce83f3399a47ec76fa", 1);
		Set<CommitBean> set = getChildrenOfCommit("f7f8387c7097a941d6b380301337006e8f146436");
		set = getParentOfCommit("f7f8387c7097a941d6b380301337006e8f146436");
		set = getCommitsAround("f7f8387c7097a941d6b380301337006e8f146436", 1);
		System.out.println();
	}

	public static int insertCauseB(CauseBBean causeb) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "INSERT INTO conflict.causeB (repName,sha,causeAId,endPos,filePairId,startPos) VALUES (?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			int i = 1;
			pstmt.setString(i++, causeb.repName);
			pstmt.setString(i++, causeb.sha);
			pstmt.setInt(i++, causeb.causeAId);
			pstmt.setInt(i++, causeb.endPos);
			pstmt.setInt(i++, causeb.filePairId);
			pstmt.setInt(i++, causeb.startPos);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(pstmt);
			logger.error(causeb.sha + " filePairId:" + causeb.filePairId +" insertCauseB err:" + e);
			return 0;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}
}

