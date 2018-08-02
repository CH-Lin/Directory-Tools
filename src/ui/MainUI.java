package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Vector;

import org.ini4j.Wini;

import funcs.Controller;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel topPanel;
	private JPanel Panel_Action;
	private JPanel listdirPanel;
	private JScrollPane fileListScrollPane;

	private JButton btn_dir;
	private JButton btn_start;
	private JTextField text_Dir;

	private JTree tree = null;
	private JList<String> list_source;

	private Thread minotor = null;
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
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		topPanel = new JPanel();

		listdirPanel = new JPanel();

		JPanel taskList = new JPanel();

		JScrollPane taskScrollPane = new JScrollPane(taskList);

		fileListScrollPane = new JScrollPane();
		fileListScrollPane.setViewportBorder(null);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(taskScrollPane, GroupLayout.DEFAULT_SIZE, 968, Short.MAX_VALUE).addGap(6))
				.addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING)
						.addComponent(topPanel, GroupLayout.DEFAULT_SIZE, 964, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addComponent(listdirPanel, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(fileListScrollPane, GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)))
						.addGap(10)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addComponent(
						topPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false).addComponent(fileListScrollPane)
						.addComponent(listdirPanel, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(taskScrollPane, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)));

		contentPane.setLayout(gl_contentPane);
		this.setMinimumSize(new Dimension(1000, 710));
		this.addTopComponent();
		this.addListComponent();
		this.addFileListScrollPane();
	}

	private void addTopComponent() {
		topPanel.setOpaque(false);
		topPanel.setLayout(new BorderLayout(0, 0));
		topPanel.setBorder(new LineBorder(new Color(191, 205, 219), 1, true));
		text_Dir = new JTextField();
		text_Dir.setFont(new Font("Calibri", Font.BOLD, 14));
		text_Dir.setBorder(null);
		text_Dir.setEditable(false);
		text_Dir.setOpaque(false);
		topPanel.add(text_Dir, BorderLayout.CENTER);
		text_Dir.setColumns(10);

		Panel_Action = new JPanel();
		Panel_Action.setOpaque(false);
		topPanel.add(Panel_Action, BorderLayout.EAST);
		Panel_Action.setLayout(new BorderLayout(0, 0));

		btn_dir = new JButton("...");
		Panel_Action.add(btn_dir);
		btn_dir.setOpaque(false);
		btn_dir.setPreferredSize(new Dimension(42, 23));
		btn_dir.setBorder(null);

		btn_start = new JButton("GO");
		Panel_Action.add(btn_start, BorderLayout.EAST);
		btn_start.setOpaque(false);
		btn_start.addActionListener(this);
		btn_dir.addActionListener(this);
	}

	private void addListComponent() {
		listdirPanel.setOpaque(false);
		listdirPanel.setLayout(new BorderLayout(0, 0));
		JScrollPane ScrollPane_List = new JScrollPane();
		ScrollPane_List.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ScrollPane_List.setBorder(null);
		listdirPanel.add(ScrollPane_List);

		tree = new JTree(new Vector<String>());
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println(tree.getSelectionPath());
			}
		});
		tree.setEditable(false);
		ScrollPane_List.setViewportView(tree);
		listdirPanel.setPreferredSize(new Dimension(100, 300));
		listdirPanel.setSize(100, 300);
	}

	private void addFileListScrollPane() {
		list_source = new JList<>();
		fileListScrollPane.setViewportView(list_source);
		fileListScrollPane.setOpaque(false);
		fileListScrollPane.setBorder(new LineBorder(new Color(191, 205, 219), 1, true));
	}

	private void setListData(final File f) {
		// list_selected.setListData(new String[0]);

		if (f.isDirectory()) {
			list_source.setListData(f.list());

			if (minotor != null) {
				minotor.interrupt();
				minotor = null;
			}

			minotor = new Thread(new Runnable() {

				public void run() {
					try {
						WatchService watcher = FileSystems.getDefault().newWatchService();
						Path dir = f.toPath();

						WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
								StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

						for (;;) {

							try {
								key = watcher.take();
							} catch (InterruptedException x) {
								return;
							}

							for (WatchEvent<?> event : key.pollEvents()) {
								WatchEvent.Kind<?> kind = event.kind();

								if (kind == StandardWatchEventKinds.OVERFLOW) {
									continue;
								}

								else if (kind == StandardWatchEventKinds.ENTRY_CREATE
										|| kind == StandardWatchEventKinds.ENTRY_DELETE
										|| kind == StandardWatchEventKinds.ENTRY_MODIFY) {
								}
								list_source.setListData(f.list());

							}

							boolean valid = key.reset();
							if (!valid) {
								break;
							}
						}
					} catch (IOException x) {
						System.err.println(x);
					}
				}
			});

			minotor.start();

		} else {
			String list[] = new String[] { f.getName() };
			list_source.setListData(list);
		}
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

		setDirectory(dir);
		controller = new Controller();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equalsIgnoreCase("...")) {
			String dir = selectDir(getDirectory());
			controller.openDir(dir);
			updateList(controller.getRoot());
		} else if (arg0.getActionCommand().equalsIgnoreCase("Go")) {
			// if (tabbedPane.getSelectedIndex() == 1)
			// updateList(controller.logDir(getDirectory()));
			// else
			controller.startRemoveDot(getDirectory(), getSelected());
			cleanSelected();
		} else if (arg0.getActionCommand().equalsIgnoreCase("→")) {
			addToList();
		} else if (arg0.getActionCommand().equalsIgnoreCase("←")) {
			removeFromList();
		}

	}

	public String getDirectory() {
		return text_Dir.getText();
	}

	public void setDirectory(String path) {
		text_Dir.setText(path);
	}

	public void updateList(DefaultMutableTreeNode root) {
		DefaultTreeModel model = new DefaultTreeModel(root);
		tree.setModel(model);
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
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
		setListData(f);
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
