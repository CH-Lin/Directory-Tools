package com.functions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.ini4j.Wini;

import com.files.DataNode;
import com.files.DirectoryNode;
import com.files.FileFactory;

public class Controller {

	private static Controller controller;

	private PrintStream out = null, err = null;
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

	public DefaultMutableTreeNode getRoot() {
		return root;
	}

	public DefaultTreeModel logDir(String path) {
		setOutput(path);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(path);
		DefaultTreeModel model = new DefaultTreeModel(root);
		new DirectoryLogger(path, 0, root);
		resetOutput();
		return model;
	}

	public void startRemoveDot(String path, String items[]) {
		String dir = path + System.getProperty("file.separator");

		for (String name : items) {
			File file = new File(dir + name);

			if (file.isFile()) {
				int j = name.lastIndexOf('.');
				File newfile = new File(dir + name.substring(0, j).replace(".", " ").concat(name.substring(j)));
				file.renameTo(newfile);
			}
		}
	}

	private void setOutput(String start) {
		out = System.out;
		err = System.err;

		FileSystemView fv = FileSystemView.getFileSystemView();
		File f = new File(start);
		String DisplayName;

		DisplayName = fv.getSystemDisplayName(f);

		try {
			File log = null;
			try {

				log = new File("logs" + File.separator + DisplayName.replace(":", ""));
			} catch (StringIndexOutOfBoundsException e) {
				log = new File("logs");
			}
			if (!log.exists()) {
				log.mkdirs();
			}
			String date = Calendar.getInstance().getTime().toString().replace(':', ' ');
			System.setOut(
					new PrintStream(new FileOutputStream(log.getAbsolutePath() + File.separator + date + "_Lists.txt"),
							false, "UTF-8"));
			System.setErr(new PrintStream(
					new FileOutputStream(log.getAbsolutePath() + File.separator + date + "_err.txt"), false, "UTF-8"));

			if (fv.isRoot(f))
				System.out.println("Start from " + DisplayName);
			else {
				System.out.println("Start from " + f.getAbsolutePath());
			}
			System.out.println();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void resetOutput() {
		System.err.close();
		System.out.close();
		System.setErr(err);
		System.setOut(out);
	}
}
