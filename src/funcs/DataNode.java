package funcs;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class DataNode extends DefaultMutableTreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String path = null;

	public DataNode(String path) {
		this.path = path;
	}

	public String getAbsolutePath() {
		return path;
	}

	public abstract boolean hasChildren();
}
