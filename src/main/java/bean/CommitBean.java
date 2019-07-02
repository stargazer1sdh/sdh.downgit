package bean;

public class CommitBean {
	public String repName;//"gchq/Gaffer"
	public String dir;//本机目录
	public String sha;
	public String parent;
	public String parent2;
	
	public CommitBean(String repName, String dir, String sha, String parent, String parent2) {
		this.repName = repName;
		this.dir = dir;
		this.sha = sha;
		this.parent = parent;
		this.parent2 = parent2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dir == null) ? 0 : dir.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((parent2 == null) ? 0 : parent2.hashCode());
		result = prime * result + ((repName == null) ? 0 : repName.hashCode());
		result = prime * result + ((sha == null) ? 0 : sha.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommitBean other = (CommitBean) obj;
		if (dir == null) {
			if (other.dir != null)
				return false;
		} else if (!dir.equals(other.dir))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (parent2 == null) {
			if (other.parent2 != null)
				return false;
		} else if (!parent2.equals(other.parent2))
			return false;
		if (repName == null) {
			if (other.repName != null)
				return false;
		} else if (!repName.equals(other.repName))
			return false;
		if (sha == null) {
			if (other.sha != null)
				return false;
		} else if (!sha.equals(other.sha))
			return false;
		return true;
	}
}
