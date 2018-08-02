package funcs;

public class FileNode extends DataNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileNode(String path) {
		super(path);
	}

	@Override
	public boolean isDirectory() {
		return false;
	}

	@Override
	public void expand() {
		// do nothing
	}

	public void add(DataNode arg0) {
		// do nothing
	}
}
