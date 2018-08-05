package funcs;

import java.io.File;
import java.util.HashMap;;

public class DirectoryNode extends DataNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HashMap<String, DirectoryNode> map;

	public DirectoryNode(String path) {
		super(path);
		System.out.println("New node: " + path);
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

	public DirectoryNode getChild(String path) {
		if (map == null && this.getChildCount() == 0) {
			return null;
		} else if (map == null && this.getChildCount() != 0) {
			map = new HashMap<>();

			for (int i = 0; i < this.getChildCount(); i++) {
				DirectoryNode node = (DirectoryNode) this.getChildAt(i);
				map.put(node.getAbsolutePath(), node);
			}
		}
		return map.get(path);
	}

	@Override
	public void expand() {
		if (map != null)
			return;
		map = new HashMap<>();
		File current = new File(path);
		String cuttentPath = current.getAbsolutePath().equalsIgnoreCase(File.separator) ? File.separator
				: current.getAbsolutePath() + File.separator;
		if (current.isDirectory()) {
			String files[] = current.list();
			for (String file : files) {

				String name = cuttentPath + file;
				File child = new File(name);
				if (child.isDirectory()) {
					DirectoryNode node = new DirectoryNode(cuttentPath + file);
					this.add(node);
					map.put(node.getAbsolutePath(), node);
				}
				/*
				 * else { this.add(new FileNode(current.getAbsolutePath() + File.separator +
				 * file)); }
				 */
			}
		}
	}
}
