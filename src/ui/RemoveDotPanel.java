package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class RemoveDotPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton btn_sel;
	private JButton btn_rm;

	private JList<String> list_source;
	private JList<String> list_selected;

	private Thread minotor = null;

	/**
	 * Create the panel.
	 */
	public RemoveDotPanel(MainUI parent) {
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));

		JScrollPane ScrollPane_Source = new JScrollPane();
		ScrollPane_Source.setBorder(new TitledBorder(new LineBorder(new Color(191, 205, 219), 1, true), "Source",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		ScrollPane_Source.setOpaque(false);
		ScrollPane_Source.setPreferredSize(new Dimension(210, 2));
		add(ScrollPane_Source, BorderLayout.WEST);

		list_source = new JList<String>();
		ScrollPane_Source.setViewportView(list_source);

		JScrollPane ScrollPane_Selected = new JScrollPane();
		ScrollPane_Selected.setBorder(new TitledBorder(new LineBorder(new Color(191, 205, 219), 1, true), "Selected",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		ScrollPane_Selected.setOpaque(false);
		ScrollPane_Selected.setPreferredSize(new Dimension(210, 2));
		add(ScrollPane_Selected, BorderLayout.EAST);

		list_selected = new JList<String>();
		ScrollPane_Selected.setViewportView(list_selected);

		JPanel Panel_Sel = new JPanel();
		Panel_Sel.setOpaque(false);
		add(Panel_Sel, BorderLayout.CENTER);
		Panel_Sel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btn_sel = new JButton("→");
		btn_sel.setOpaque(false);
		btn_sel.addActionListener(parent);
		Panel_Sel.add(btn_sel);

		btn_rm = new JButton("←");
		btn_rm.addActionListener(parent);
		Panel_Sel.add(btn_rm);
	}

	public void addToList() {
		List<String> list = list_source.getSelectedValuesList();

		ListModel<String> model = list_selected.getModel();
		DefaultListModel<String> newmodel = new DefaultListModel<String>();

		for (int i = 0; i < model.getSize(); i++) {
			newmodel.addElement(model.getElementAt(i));
		}

		boolean find = false;
		for (String sel : list) {
			find = false;
			for (int i = 0; i < model.getSize(); i++) {
				String name = model.getElementAt(i);
				if (sel.equals(name))
					find = true;
			}
			if (!find)
				newmodel.addElement(sel);
		}
		list_selected.setModel(newmodel);
	}

	public void removeFromList() {
		List<String> selected = list_selected.getSelectedValuesList();
		ListModel<String> model = list_selected.getModel();

		DefaultListModel<String> newmodel = new DefaultListModel<String>();

		boolean find = false;
		for (int i = 0; i < model.getSize(); i++) {
			find = false;
			String name = model.getElementAt(i);
			for (String sel : selected) {
				if (sel.equals(name))
					find = true;
			}
			if (!find)
				newmodel.addElement(name);
		}

		list_selected.setModel(newmodel);
	}

	public void listDir(final File f) {

		list_selected.setListData(new String[0]);

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
										|| kind == StandardWatchEventKinds.ENTRY_MODIFY)
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

	public String[] getSelected() {
		ListModel<String> model = list_selected.getModel();
		String values[] = new String[model.getSize()];
		for (int i = 0; i < model.getSize(); i++) {
			values[i] = model.getElementAt(i);
		}
		return values;
	}

	public void clean() {
		ListModel<String> model = new DefaultListModel<String>();
		list_selected.setModel(model);
	}
}
