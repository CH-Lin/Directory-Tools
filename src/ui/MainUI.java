package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeModel;

import java.io.File;
import java.io.IOException;
import org.ini4j.Wini;

import funcs.Controller;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;

public class MainUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TopPanel topPanel;

	private ListdirPanel listdirPanel;
	private FileListScrollPane fileListScrollPane;

	private JScrollPane taskScrollPane;
	private Controller controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception exception) {
					exception.printStackTrace();
				}

				MainUI frame = new MainUI();
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainUI() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 720);
		setTitle("DIRECTORY TOOL");
		addComponent();
		setInitialValue();
		// Panel_RemoveDot.listDir(new File(text_Dir.getText()));
	}

	private void addComponent() {
		Controller controller = new Controller(this);
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		topPanel = new TopPanel(controller);

		listdirPanel = new ListdirPanel();

		JPanel taskList = new JPanel();

		taskScrollPane = new JScrollPane(taskList);

		fileListScrollPane = new FileListScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addComponent(listdirPanel, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(topPanel, GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE).addGap(6))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(fileListScrollPane, GroupLayout.PREFERRED_SIZE, 759,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap())))
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(taskScrollPane, GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE).addGap(6)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(fileListScrollPane, GroupLayout.PREFERRED_SIZE, 463,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(listdirPanel,
								GroupLayout.PREFERRED_SIZE, 513, GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(taskScrollPane, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)));

		contentPane.setLayout(gl_contentPane);
		this.setMinimumSize(new Dimension(1000, 710));
	}

	private void setInitialValue() {
		Wini ini;
		String dir = null;
		try {
			ini = new Wini(new File("config.ini"));
			dir = ini.get("dict", "currDir");
		} catch (IOException e1) {
			File f = new File("config.ini");
			try {
				dir = System.getProperty("user.dir");
				f.createNewFile();
				Wini ini2 = new Wini(f);
				ini2.put("dict", "currDir", dir);
				ini2.store();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		topPanel.setDirectory(dir);
	}

	public String getDirectory() {
		return topPanel.getDirectory();
	}

	public void setDirectory(String path) {
		topPanel.setDirectory(path);
	}

	public void updateList(DefaultTreeModel model) {
		listdirPanel.updateList(model);
	}

	public String selectDir(String path) {
		JFileChooser chooser = new JFileChooser(path);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			listDir(chooser.getSelectedFile());

			String new_path = null;
			if (chooser.getSelectedFile().isDirectory()) {
				new_path = chooser.getSelectedFile().getAbsolutePath();
			} else {
				new_path = chooser.getSelectedFile().getParent();

			}
			setDirectory(new_path);
			controller.saveConfig(path);
			return new_path;
		}
		return null;
	}

	public void listDir(final File f) {
		fileListScrollPane.setListData(f);
	}

	public void addToList() {
		// Panel_RemoveDot.addToList();
	}

	public void removeFromList() {
		// Panel_RemoveDot.removeFromList();
	}

	public String[] getSelected() {
		return null;// Panel_RemoveDot.getSelected();
	}

	public void cleanSelected() {
		// Panel_RemoveDot.clean();
	}

}
