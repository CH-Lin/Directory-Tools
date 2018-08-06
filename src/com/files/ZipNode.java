package com.files;

public class ZipNode extends DataNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ZipNode(String path) {
		super(path);
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

	@Override
	public DataNode searchChild(String path) {
		// TODO search file in zip file
		return null;
	}

	@Override
	public void expand() {
		// TODO Auto-generated method stub

	}

}
