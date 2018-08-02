package funcs;

import java.io.File;

public class DirectoryNode extends DataNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DirectoryNode(String path) {
		super(path);
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

	@Override
	public void expand() {
		File current = new File(path);
		if (current.isDirectory()) {
			String files[] = current.list();
			for (String file : files) {

				String name = current.getAbsolutePath() + File.separator + file;
				File child = new File(name);
				if (child.isDirectory()) {
					this.add(new DirectoryNode(current.getAbsolutePath() + File.separator + file));
				}
				/*
				 * else { this.add(new FileNode(current.getAbsolutePath() + File.separator +
				 * file)); }
				 */
			}
		}
	}
}
