package com.files;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

import com.files.strategies.Strategy;

public abstract class DataNode extends DefaultMutableTreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String path = null;

	private Strategy expandStrategy;

	private Strategy searchStrategy;

	public DataNode(String path) {
		super((path.lastIndexOf(File.separator) == -1) || (path.length() == 1) ? path
				: path.substring(path.lastIndexOf(File.separator) + 1));
		this.path = path;
	}

	public String getAbsolutePath() {
		return path;
	}

	public abstract boolean isDirectory();

	public abstract void expand();

	public abstract DataNode searchChild(String path);
}
