package com.files;

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

	@Override
	public DataNode searchChild(String path) {
		return null;
	}

	public void add(DataNode arg0) {
		// do nothing
	}

}
