package com.files;

import java.io.File;
import java.util.HashMap;;

public class DirectoryNode extends DataNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HashMap<String, DataNode> searchCatch;

	public DirectoryNode(String path) {
		super(path);
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

	@Override
	public void expand() {
		if (searchCatch != null)
			return;
		searchCatch = new HashMap<>();
		File current = new File(this.path);
		String cuttentPath = current.getAbsolutePath().equalsIgnoreCase(File.separator) ? File.separator
				: current.getAbsolutePath() + File.separator;
		if (current.isDirectory()) {
			String files[] = current.list();
			for (String file : files) {

				String name = cuttentPath + file;
				File child = new File(name);
				if (child.isDirectory()) {
					DataNode node = FileFactory.createFileNode(FileFactory.FileType.Directory, name);
					this.add(node);
					searchCatch.put(node.getAbsolutePath(), node);
				}
				/*
				 * else { this.add(new FileNode(current.getAbsolutePath() + File.separator +
				 * file)); }
				 */
			}
		}
	}

	public DataNode searchChild(String path) {
		if (searchCatch == null && this.getChildCount() == 0) {
			return null;
		} else if (searchCatch == null && this.getChildCount() != 0) {
			searchCatch = new HashMap<>();

			for (int i = 0; i < this.getChildCount(); i++) {
				DirectoryNode node = (DirectoryNode) this.getChildAt(i);
				searchCatch.put(node.getAbsolutePath(), node);
			}
		}
		return searchCatch.get(path);
	}
}
