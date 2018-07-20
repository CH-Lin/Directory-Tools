package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

public class ListdirPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTree tree = null;

	/**
	 * Create the panel.
	 */
	public ListdirPanel() {
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));
		JScrollPane ScrollPane_List = new JScrollPane();
		ScrollPane_List.setBorder(null);
		add(ScrollPane_List);

		tree = new JTree(new Vector<String>());
		tree.setEditable(false);
		ScrollPane_List.setViewportView(tree);
		setPreferredSize(new Dimension(100, 300));
		this.setSize(100, 300);
	}

	public void updateList(DefaultTreeModel model) {
		tree.setModel(model);
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
	}

}
