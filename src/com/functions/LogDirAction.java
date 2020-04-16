package com.functions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.DefaultTreeModel;

public class LogDirAction extends Action {

	private PrintStream out = null, err = null;

	public LogDirAction(String path) {
		super("Log dir");
		this.setPath(path);
	}

	@Override
	public void execute() {
		setOutput(getPath());
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(getPath());
		// DefaultTreeModel model = new DefaultTreeModel(root);
		new DirectoryLogger(getPath(), 0, root);
		resetOutput();
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
