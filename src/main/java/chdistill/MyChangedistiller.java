package chdistill;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import bean.CauseABean;
import bean.CauseABean1;
import ch.uzh.ifi.seal.changedistiller.ChangeDistiller;
import ch.uzh.ifi.seal.changedistiller.ChangeDistiller.Language;
import ch.uzh.ifi.seal.changedistiller.distilling.FileDistiller;
import ch.uzh.ifi.seal.changedistiller.model.classifiers.ChangeType;
import ch.uzh.ifi.seal.changedistiller.model.classifiers.SourceRange;
import ch.uzh.ifi.seal.changedistiller.model.entities.Insert;
import ch.uzh.ifi.seal.changedistiller.model.entities.Move;
import ch.uzh.ifi.seal.changedistiller.model.entities.SourceCodeChange;
import ch.uzh.ifi.seal.changedistiller.model.entities.Update;
import db.DBUtils;
import main.MainFetchA;

public class MyChangedistiller {
	private static Logger log = Logger.getLogger(MyChangedistiller.class.getName());
	public static boolean hasChangedModifer(String leftDirstr, String rightDirstr) {
		File left = new File(leftDirstr);
		File right = new File(rightDirstr);
		
		FileDistiller distiller = ChangeDistiller.createFileDistiller(Language.JAVA);
		try {
		    distiller.extractClassifiedSourceCodeChanges(left, right);
		} catch(Exception e) {
		    /* An exception most likely indicates a bug in ChangeDistiller. Please file a
		       bug report at https://bitbucket.org/sealuzh/tools-changedistiller/issues and
		       attach the full stack trace along with the two files that you tried to distill. */
		    System.err.println(leftDirstr+"\t"+rightDirstr+ "Warning: error while change distilling. " + e.getMessage());
		    log.info(MainFetchA.sha+" hasChangedModifer err:"+leftDirstr+"\t"+rightDirstr+":\t"+e);
		}

		List<SourceCodeChange> changes = distiller.getSourceCodeChanges();
		if(changes != null) {
		    for(SourceCodeChange change : changes) {
//		    	System.out.println(change.getLabel()+"\t"+change.getChangeType()+"\t"+change.getParentEntity()+"\t"+change.getRootEntity()+"\t");
		    	if(/*change.getChangeType().equals(ChangeType.INCREASING_ACCESSIBILITY_CHANGE) ||*/ change.getChangeType().equals(ChangeType.DECREASING_ACCESSIBILITY_CHANGE)) {
//		    		String mes = change+"\t||\t"+change.getParentEntity() +"\t||\t" + change.getRootEntity();
		    		String changeStr = change.toString();
		    		String changedRoot = change.getRootEntity().toString();
		    		CauseABean causeA = new CauseABean(MainFetchA.repName, MainFetchA.sha, MainFetchA.fullFileName, changeStr, changedRoot);
		    		DBUtils.insertcauseA(causeA);
//		    		log.info(mes);
		    		return true;
		    	}
		    }
		}
		return false;
	}

	public static void main(String[] args) {
		File left = new File("E:\\eclipse-workspace\\wsThesis\\com.sdh.downgit\\src\\main\\java\\chdistill\\source\\L.java");
		File right = new File("E:\\eclipse-workspace\\wsThesis\\com.sdh.downgit\\src\\main\\java\\chdistill\\source\\R2.java");
		FileDistiller distiller = ChangeDistiller.createFileDistiller(Language.JAVA);
		try {
		    distiller.extractClassifiedSourceCodeChanges(left, right);
		} catch(Exception e) {
		    System.err.println("Warning: error while change distilling. " + e.getMessage());
		}

		List<SourceCodeChange> changes = distiller.getSourceCodeChanges();
		if(changes != null) {
		    for(SourceCodeChange change : changes) {
		    	System.out.println(change.getChangedEntity()+"\t||\t"+change.getChangedEntity().getSourceRange()+"\t");
		    	System.out.println(change.getLabel()+"\t"+change.getChangeType());
		    	if(change instanceof Update) {
		    		System.out.println("Update in right:\t"+((Update) change).getNewEntity().getSourceRange() );
		    	}else if(change instanceof Move) {
		    		System.out.println("Move in right:\t"+((Move) change).getNewEntity().getSourceRange() );
		    	}

	    		String mes = change+"\t||\t"+change.getParentEntity() +"\t||\t" + change.getRootEntity();
	    		System.out.println(mes);
	    		System.out.println();
		    }
		}
	}

	public static List<SourceRange> getSourceRangesInRight(String leftpath, String rightpath) {
		List<SourceRange> ans = new LinkedList<SourceRange>();
		File left = new File(leftpath);
		if(!left.exists()) {//left not exist, right exist
			SourceRange range = new SourceRange();//(-1,-1)
			ans.add(range); 
			return ans;
		}
		File right = new File(rightpath);
		FileDistiller distiller = ChangeDistiller.createFileDistiller(Language.JAVA);
		try {
		    distiller.extractClassifiedSourceCodeChanges(left, right);
		} catch(Exception e) {
			System.err.println(left+"\t"+right+ "Warning: error while change distilling. " + e.getMessage());
		    log.info("getSourceRangesInRight err:"+left+"\t"+right+":\t"+e);
		}

		List<SourceCodeChange> changes = distiller.getSourceCodeChanges();
		if(changes != null) {
		    for(SourceCodeChange change : changes) {
		    	if(change instanceof Update) {
		    		ans.add( ((Update) change).getNewEntity().getSourceRange() );
		    	}else if(change instanceof Move) {
		    		ans.add( ((Move) change).getNewEntity().getSourceRange() );
		    	}else if(change instanceof Insert) {
		    		ans.add( change.getChangedEntity().getSourceRange() );
		    	}
		    }
		}
		return ans;
	}

	public static boolean hasChangedAttributeType(String leftpath, String rightpath, String repName, String sha,
			String filename) {
		File left = new File(leftpath);
		File right = new File(rightpath);
		FileDistiller distiller = ChangeDistiller.createFileDistiller(Language.JAVA);
		try {
		    distiller.extractClassifiedSourceCodeChanges(left, right);
		} catch(Exception e) {
		    System.err.println("Warning: error while change distilling. " + e.getMessage());
		}

		List<SourceCodeChange> changes = distiller.getSourceCodeChanges();
		List<String[]> className_fieldName_type0s = new LinkedList<String[]>();
		List<String[]> className_fieldName_type1s = new LinkedList<String[]>();
		if(changes != null) {
		    for(SourceCodeChange change : changes) {
//		    	System.out.println(change.getChangedEntity()+"\t||\t"+change.getChangedEntity().getSourceRange()+"\t");
//		    	System.out.println(change.getLabel()+"\t"+change.getChangeType());
//		    	if(change instanceof Update) {
//		    		System.out.println("Update in right:\t"+((Update) change).getNewEntity().getSourceRange() );
//		    	}else if(change instanceof Move) {
//		    		System.out.println("Move in right:\t"+((Move) change).getNewEntity().getSourceRange() );
//		    	}
//	    		String mes = change+"\t||\t"+change.getParentEntity() +"\t||\t" + change.getRootEntity();
	    		if(change.getChangeType().equals(ChangeType.ATTRIBUTE_TYPE_CHANGE)) {
	    			String class_field_type0 = change.getParentEntity().toString();  //FIELD: chdistill.source.L.g : int
	    			String[] ss = class_field_type0.split(":");
	    			if(ss.length!=3) {
	    				String mes = "repName:"+repName+",sha:"+sha+",filename:"+filename+",change:"+change+",parent:"+change.getParentEntity()+",root:"+change.getRootEntity();
	    				System.out.println(mes);
	    				log.info(mes);
	    				continue;
	    			}
	    			String class_field = ss[1].trim();
	    			String type0 = ss[2].trim();
	    			int lastdoti = class_field.lastIndexOf('.');
	    			String className = class_field.substring(0, lastdoti);
	    			String fieldName = class_field.substring(lastdoti+1);
	    			String type1 = change.getRootEntity().toString().split(":")[1].trim();
	    			CauseABean1 causea = new CauseABean1(repName, sha, filename, className, fieldName, type0, type1);
	    			DBUtils.insertcauseA(causea);
	    		}else if(change.getChangeType().equals(ChangeType.REMOVED_OBJECT_STATE)) {
	    			String class_field_type0 = change.getChangedEntity().toString(); //FIELD: chdistill.source.L.f : int
	    			String[] ss = class_field_type0.split(":");
	    			String class_field = ss[1].trim();
	    			String type0 = ss[2].trim();
	    			int lastdoti = class_field.lastIndexOf('.');
	    			String className = class_field.substring(0, lastdoti);
	    			String fieldName = class_field.substring(lastdoti+1);
	    			className_fieldName_type0s.add(new String[] {className,fieldName,type0});
	    		}else if(change.getChangeType().equals(ChangeType.ADDITIONAL_OBJECT_STATE)) {
	    			String class_field_type1 = change.getChangedEntity().toString(); //FIELD: chdistill.source.L.f : long
	    			String[] ss = class_field_type1.split(":");
	    			String class_field = ss[1].trim();
	    			String type1 = ss[2].trim();
	    			int lastdoti = class_field.lastIndexOf('.');
	    			String className = class_field.substring(0, lastdoti);
	    			String fieldName = class_field.substring(lastdoti+1);
	    			className_fieldName_type1s.add(new String[] {className,fieldName,type1});
	    		}
		    }
		}
		for(String[] className_fieldName_type0 : className_fieldName_type0s) {
			for(String[] className_fieldName_type1 : className_fieldName_type1s) {
				if(className_fieldName_type0[0].equals(className_fieldName_type1[0])&&className_fieldName_type0[1].equals(className_fieldName_type1[1])) {
					String className = className_fieldName_type0[0];
	    			String fieldName = className_fieldName_type0[1];
	    			String type0 = className_fieldName_type0[2];
	    			String type1 = className_fieldName_type1[2];
	    			CauseABean1 causea = new CauseABean1(repName, sha, filename, className, fieldName, type0, type1);
	    			DBUtils.insertcauseA(causea);
	    			break;
				}
			}
		}
		return false;
	}
}
