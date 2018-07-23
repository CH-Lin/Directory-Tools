package funcs;

public class FileNode extends DataNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileNode(String path) {
		super(path);
	}

	public boolean hasChildren() {
		return false;
	}
}
