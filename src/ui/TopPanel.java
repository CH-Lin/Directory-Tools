package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class TopPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel Panel_Action;
	private JTextField text_Dir;
	private JButton btn_dir;
	private JButton btn_start;

	/**
	 * Create the panel.
	 */
	public TopPanel(ActionListener parent) {
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));
		setBorder(new TitledBorder(new LineBorder(new Color(191, 205, 219), 1, true), "DIRECTORY", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		text_Dir = new JTextField();
		text_Dir.setFont(new Font("Calibri", Font.BOLD, 14));
		text_Dir.setBorder(null);
		text_Dir.setEditable(false);
		text_Dir.setOpaque(false);
		add(text_Dir, BorderLayout.CENTER);
		text_Dir.setColumns(10);

		Panel_Action = new JPanel();
		Panel_Action.setOpaque(false);
		add(Panel_Action, BorderLayout.EAST);
		Panel_Action.setLayout(new BorderLayout(0, 0));

		btn_dir = new JButton("...");
		Panel_Action.add(btn_dir);
		btn_dir.setOpaque(false);
		btn_dir.setPreferredSize(new Dimension(42, 23));
		btn_dir.setBorder(null);

		btn_start = new JButton("GO");
		Panel_Action.add(btn_start, BorderLayout.EAST);
		btn_start.setOpaque(false);
		btn_start.addActionListener(parent);
		btn_dir.addActionListener(parent);

	}

	public String getDirectory() {
		return text_Dir.getText();
	}

	public void setDirectory(String path) {
		if (path != null)
			text_Dir.setText(path);
	}

}
