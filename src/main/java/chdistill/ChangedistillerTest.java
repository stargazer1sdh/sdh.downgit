package chdistill;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import ch.uzh.ifi.seal.changedistiller.ChangeDistiller;
import ch.uzh.ifi.seal.changedistiller.ChangeDistiller.Language;
import ch.uzh.ifi.seal.changedistiller.distilling.FileDistiller;
import ch.uzh.ifi.seal.changedistiller.model.classifiers.ChangeType;
import ch.uzh.ifi.seal.changedistiller.model.entities.Move;
import ch.uzh.ifi.seal.changedistiller.model.entities.SourceCodeChange;
import ch.uzh.ifi.seal.changedistiller.model.entities.Update;

public class ChangedistillerTest {

	public static void main(String[] args) {
		String f1 = "E:\\eclipse-workspace\\wsThesis\\com.sdh.downgit\\src\\main\\java\\chdistill\\source\\L.java";
		String f2 = "E:\\eclipse-workspace\\wsThesis\\com.sdh.downgit\\src\\main\\java\\chdistill\\source\\R2.java";
		File left = new File(f1);
		File right = new File(f2);
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
		    	System.out.println(change.getChangedEntity()+"\t||\t"+change.getChangedEntity().getSourceRange()+"\t");
		    	System.out.println(change.getLabel()+"\t"+change.getChangeType());
		    	if(change instanceof Update) {
		    		System.out.println("Update in right:\t"+((Update) change).getNewEntity().getSourceRange() );
		    	}else if(change instanceof Move) {
		    		System.out.println("Move in right:\t"+((Move) change).getNewEntity().getSourceRange() );
		    	}

	    		String mes = change+"\t||\t"+change.getParentEntity() +"\t||\t" + change.getRootEntity();
	    		
	    		if(change.getChangeType().equals(ChangeType.ATTRIBUTE_TYPE_CHANGE)) {
	    			String class_field_type0 = change.getParentEntity().toString();  //FIELD: chdistill.source.L.g : int
	    			String[] ss = class_field_type0.split(":");
	    			String class_field = ss[1].trim();
	    			String type0 = ss[2].trim();
	    			int lastdoti = class_field.lastIndexOf('.');
	    			String className = class_field.substring(0, lastdoti);
	    			String fieldName = class_field.substring(lastdoti+1);
	    			String type1 = change.getRootEntity().toString().split(":")[1].trim();
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
	    		
	    		System.out.println(mes);
	    		System.out.println();
		    }
		}
		for(String[] className_fieldName_type0 : className_fieldName_type0s) {
			for(String[] className_fieldName_type1 : className_fieldName_type1s) {
				if(className_fieldName_type0[0].equals(className_fieldName_type1[0])&&className_fieldName_type0[1].equals(className_fieldName_type1[1])) {
					String className = className_fieldName_type0[0];
	    			String fieldName = className_fieldName_type0[1];
	    			String type0 = className_fieldName_type0[2];
	    			String type1 = className_fieldName_type1[2];
	    			System.out.println("~~~~"+className+","+fieldName+","+type0+","+type1);
	    			break;
				}
			}
		}
	}
//className fieldName type0  type1
}
