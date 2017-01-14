package Core;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;

public class Decision extends JDialog implements ActionListener {
	private Main m;
	private JToolBar organizer;
	private JTextField question;
	private JTextField optionInput;
	private JButton addOption, deleteOption;
	private DecisionModel dm;
	private JTable options;
	private JButton makeDecision;
	private String decision;
	public Decision(Main m) {
		this.m = m;
		setTitle("Decision");
		setBounds(400, 400, 900, 400);
		setModal(true);
		organizer = new JToolBar();
		organizer.setFloatable(false);
		question = new JTextField(20);
		organizer.add(new JLabel("Question: "));
		organizer.add(question);
		organizer.add(new JLabel("Option Input: "));
		optionInput = new JTextField(20);
		organizer.add(optionInput);
		addOption = new JButton("Add Option");
		addOption.addActionListener(this);
		organizer.add(addOption);
		deleteOption = new JButton("Delete Option");
		deleteOption.addActionListener(this);
		organizer.add(deleteOption);
		dm = new DecisionModel();
		options = new JTable(dm);
		makeDecision = new JButton("Make Decision");
		makeDecision.addActionListener(this);
		organizer.add(makeDecision);
		add(organizer, BorderLayout.NORTH);
		add(options, BorderLayout.CENTER);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addOption) {
			dm.addOption(optionInput.getText());
			dm.fireTableDataChanged();
		} else if (e.getSource() == deleteOption) {
			if (options.getSelectedRow() != -1) {
				dm.deleteOption(options.getSelectedRow());
				dm.fireTableDataChanged();
			}
		} else if (e.getSource() == makeDecision) {
			decision = m.makeDecision(question.getText(), dm.getOptions());
			setVisible(false);
		}
	}
	public String getQuestion() {
		return question.getText();
	}
	public String getDecision() {
		return decision;
	}
}

class DecisionModel extends AbstractTableModel {
	private ArrayList <String> options;
	public DecisionModel() {
		options = new ArrayList <String> ();
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return options.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public String getColumnName(int c) {
		return "Options";
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return options.get(rowIndex);
	}
	public void addOption(String option) {
		options.add(option);
	}
	public void deleteOption(int rowIndex) {
		options.remove(rowIndex);
	}
	public ArrayList <String> getOptions() {
		return options;
	}
}