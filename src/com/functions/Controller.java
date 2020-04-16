package com.functions;

import java.io.File;
import java.io.IOException;
import javax.swing.tree.DefaultMutableTreeNode;

import org.ini4j.Wini;

import com.files.DataNode;
import com.files.DirectoryNode;
import com.files.FileFactory;

public class Controller {

	private static Controller controller;

	private DataNode root;

	// Singleton Design Pattern to make sure only one instance of controller exist
	public static Controller getcontroller() {
		synchronized (Controller.class) {
			if (controller == null) {
				controller = new Controller();
			}
		}
		return controller;
	}

	private Controller() {
		root = new DirectoryNode("");
	}

	public void saveConfig(String path) {
		try {
			Wini ini = new Wini(new File("config.ini"));
			ini.put("dict", "currDir", path);
			ini.store();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DefaultMutableTreeNode openDir(String path) {
		DataNode current = root;
		path += File.separator;

		int preIdx = 0, index = -1;
		while ((index = path.indexOf(File.separator, preIdx)) != -1) {
			String subpath = path.substring(0, index == 0 ? 1 : index);
			DataNode node;
			node = current.searchChild(subpath);
			if (node == null) {
				node = FileFactory.createFileNode(FileFactory.FileType.Directory, subpath);
				current.add(node);
			}
			node.expand();
			current = node;
			preIdx = index + 1;
		}

		return root;
	}
}
