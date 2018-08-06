package com.functions;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

public class DirectoryLogger {

	public DirectoryLogger(String path, int layer, DefaultMutableTreeNode parent) {

		File f = new File(path);
		if (f.isDirectory()) {
			for (int i = 0; i < layer - 1; i++)
				System.out.print("\t");

			System.out.println(f.getAbsolutePath());
			String files[] = f.list();
			try {
				for (String file : files) {

					String name = f.getAbsolutePath() + File.separator + file;
					File f2 = new File(name);
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
					parent.add(node);
					if (f2.isDirectory()) {
						new DirectoryLogger(f.getAbsolutePath() + File.separator + file, layer + 1, node);

					} else {
						for (int i = 0; i < layer; i++)
							System.out.print("\t");

						System.out.println(f.getAbsolutePath() + System.getProperty("file.separator") + file);
					}
				}
				System.out.println();
			} catch (NullPointerException e) {

			}
		}
	}
}
