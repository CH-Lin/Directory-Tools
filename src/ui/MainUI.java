package ui;

import java.awt.Color;
import java.awt.BorderLayout;
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

public class MainUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TopPanel Panel_Top;

	private ListdirPanel Panel_Listdir;
	private RemoveDotPanel Panel_RemoveDot;

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
		setBounds(100, 100, 500, 400);
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
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		Panel_Top = new TopPanel(controller);
		contentPane.add(Panel_Top, BorderLayout.NORTH);

		Panel_RemoveDot = new RemoveDotPanel(controller);
		contentPane.add(Panel_RemoveDot, BorderLayout.CENTER);

		Panel_Listdir = new ListdirPanel();
		contentPane.add(Panel_Listdir, BorderLayout.WEST);
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

		Panel_Top.setDirectory(dir);
	}

	public String getDirectory() {
		return Panel_Top.getDirectory();
	}

	public void setDirectory(String path) {
		Panel_Top.setDirectory(path);
	}

	public void updateList(DefaultTreeModel model) {
		Panel_Listdir.updateList(model);
	}

	public void listDir(File file) {
		Panel_RemoveDot.listDir(file);
	}

	public void addToList() {
		Panel_RemoveDot.addToList();
	}

	public void removeFromList() {
		Panel_RemoveDot.removeFromList();
	}

	public String[] getSelected() {
		return Panel_RemoveDot.getSelected();
	}

	public void cleanSelected() {
		Panel_RemoveDot.clean();
	}
}
