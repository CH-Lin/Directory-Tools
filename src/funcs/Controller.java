package funcs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.ini4j.Wini;

import ui.MainUI;

public class Controller implements ActionListener {
	private MainUI view;
	private PrintStream out = null, err = null;

	public Controller(MainUI view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equalsIgnoreCase("...")) {
			openDir(view.getDirectory());
		} else if (arg0.getActionCommand().equalsIgnoreCase("Go")) {
			// if (tabbedPane.getSelectedIndex() == 1)
			outputDir(view.getDirectory());
			// else
			startRemoveDot(view.getSelected());
		} else if (arg0.getActionCommand().equalsIgnoreCase("→")) {
			view.addToList();
		} else if (arg0.getActionCommand().equalsIgnoreCase("←")) {
			view.removeFromList();
		}

	}

	private void openDir(String path) {

		JFileChooser chooser = new JFileChooser(path);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = chooser.showOpenDialog(view);
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			view.listDir(chooser.getSelectedFile());

			if (chooser.getSelectedFile().isDirectory()) {
				view.setDirectory(chooser.getSelectedFile().getAbsolutePath());
			} else {
				view.setDirectory(chooser.getSelectedFile().getParent());
			}

			try {
				Wini ini = new Wini(new File("config.ini"));
				ini.put("dict", "currDir", path);
				ini.store();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void outputDir(String path) {
		setOutput(path);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(path);
		DefaultTreeModel model = new DefaultTreeModel(root);
		new Directory(path, 0, root);
		view.updateList(model);
		resetOutput();
	}

	private void startRemoveDot(String items[]) {
		String path = view.getDirectory();
		String dir = path + System.getProperty("file.separator");

		for (String name : items) {
			File file = new File(dir + name);

			if (file.isFile()) {
				int j = name.lastIndexOf('.');
				File newfile = new File(dir + name.substring(0, j).replace(".", " ").concat(name.substring(j)));
				file.renameTo(newfile);
			}
		}

		view.cleanSelected();
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
