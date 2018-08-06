package com.files;

public class TextNode extends DataNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextNode(String path) {
		super(path);
	}

	@Override
	public boolean isDirectory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DataNode searchChild(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void expand() {
		// TODO Auto-generated method stub

	}

}
