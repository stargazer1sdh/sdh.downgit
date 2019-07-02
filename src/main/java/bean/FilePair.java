package bean;

public class FilePair {
	public String commitsha;
	public String dir;//本机目录
	public String prevfpath; //default null,  Previous path, in case file has moved
	public String filename;  //  src/main/java/uk/gov/gchq/gaffer/store/operation/handler/GetTraitsHandler.java
	public int id;
	
	public FilePair(String commitsha, String dir, String prevfpath, String filename) {
		this.commitsha = commitsha;
		this.dir = dir;
		this.prevfpath = prevfpath;
		this.filename = filename;
	}
}
