package Vocabulary;




import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

public class FormEditor extends JDialog implements ActionListener {
	private Generator g;
	private JToolBar controls;
	private JComboBox <String> selectForm;
	private JButton addForm, deleteForm, editwordbank;
	private GridLayout gl;
	private JPanel centerPanel;
	public FormEditor(Generator g) {
		this.g = g;
		setBounds(200, 200, 800, 400);
		setTitle("Form Editor");
		setModal(true);
		setResizable(false);
		controls = new JToolBar();
		controls.setLayout(new FlowLayout(FlowLayout.CENTER));
		controls.setFloatable(false);
		selectForm = new JComboBox <String> ();
		selectForm.addItem("Select Form");
		selectForm.setSelectedIndex(0);
		selectForm.addActionListener(this);
		controls.add(selectForm);
		addForm = new JButton("Add Form");
		addForm.addActionListener(this);
		controls.add(addForm);
		deleteForm = new JButton("Delete Form");
		deleteForm.addActionListener(this);
		controls.add(deleteForm);
		editwordbank = new JButton("Edit Word Bank");
		editwordbank.addActionListener(this);
		controls.add(editwordbank);
		add(controls, BorderLayout.NORTH);
		centerPanel = new JPanel();
		gl = new GridLayout();
		centerPanel.setLayout(gl);
		add(new JScrollPane(centerPanel), BorderLayout.CENTER);
		load();
		setVisible(true);
	}
	public void load() {
		for (int i = 0; i < g.formssize(); i++) {
			selectForm.addItem(String.valueOf(i+1));
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == selectForm) {
			if (selectForm.getSelectedIndex() == 0) {
				return;
			}
			centerPanel.removeAll();
			centerPanel.repaint();
			Form f = g.get(selectForm.getSelectedIndex()-1);
			ArrayList <TypeSelector> typeSelectors = new ArrayList <TypeSelector> ();
			for (int i = 0; i < f.size(); i++) {
				typeSelectors.add(new TypeSelector(f, i));
			}
			gl = new GridLayout((int)Math.ceil((double)(typeSelectors.size()/4.0)), 4);
			centerPanel.setLayout(gl);
			for (int i = 0; i < typeSelectors.size(); i++) {
				centerPanel.add(typeSelectors.get(i)); 
			}
			centerPanel.repaint();
			setVisible(true);
		} else if (e.getSource() == addForm) {
			String input = JOptionPane.showInputDialog("Enter a phrase: ");
			if (input == null || input.equals("") || input.equals(" ") ) {
				return;
			}
			int index = g.parse(input);
			selectForm.addItem(String.valueOf(index + 1));
			selectForm.setSelectedIndex(index + 1);
		} else if (e.getSource() == deleteForm) {
			int index = selectForm.getSelectedIndex();
			if (index == 0) {
				return;
			}
			selectForm.removeItemAt(index);
			g.delete(index-1);
			centerPanel.removeAll();
			centerPanel.repaint();
			selectForm.setSelectedIndex(0);
			repaint();
			setVisible(true);
		} else if (e.getSource() == editwordbank) {
			WordBankEditor wbe = new WordBankEditor(g);
		}
	}
}
//connection editor?
class WordBankEditor extends JDialog implements ActionListener, ListSelectionListener {
	private Generator g;
	private WordBankModel wbe;
	private ConnectionsModel cm;
	private JTable jt, jt0;
	private JToolBar controls;
	private WordTypeSelector wts;
	private JButton add, delete;
	private JTextField connection;
	private JButton addConnection, deleteConnection;
	public WordBankEditor(Generator g) {
		this.g = g;
		setTitle("Wordbank Editor");
		setBounds(200, 200, 900, 400);
		setModal(true);
		controls = new JToolBar();
		controls.setLayout(new FlowLayout(FlowLayout.CENTER));
		controls.setFloatable(false);
		wts = new WordTypeSelector();
		wts.addActionListener(this);
		controls.add(wts);
		add = new JButton("Add Word");
		add.addActionListener(this);
		controls.add(add);
		delete = new JButton("Delete Word");
		delete.addActionListener(this);
		controls.add(delete);
		connection = new JTextField(20);
		connection.addActionListener(this);
		controls.add(connection);
		addConnection = new JButton("Add Connection");
		addConnection.addActionListener(this);
		controls.add(addConnection);
		deleteConnection = new JButton("Delete Connection");
		deleteConnection.addActionListener(this);
		controls.add(deleteConnection);
		add(controls, BorderLayout.NORTH);
		wbe = new WordBankModel(g);
		jt = new JTable(wbe);
		jt.getSelectionModel().addListSelectionListener(this);
		jt.setPreferredScrollableViewportSize(new Dimension(450, 300));
		JPanel x = new JPanel();
		x.setLayout(new BorderLayout());
		x.add(new JScrollPane(jt), BorderLayout.CENTER);
		x.setPreferredSize(new Dimension(450, 300));
		add(x, BorderLayout.WEST);
		cm = new ConnectionsModel(g);
		jt0 = new JTable(cm);
		jt0.setPreferredScrollableViewportSize(new Dimension(450, 300));
		JPanel y = new JPanel();
		y.setLayout(new BorderLayout());
		y.add(new JScrollPane(jt0), BorderLayout.CENTER);
		y.setPreferredSize(new Dimension(450, 300));
		add(y, BorderLayout.EAST);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == wts) {
			PartOfSpeech t = wts.getType();
			if (jt.getSelectedRow() >= 3) {
				g.setWord(jt.getSelectedRow(), t, String.valueOf(wbe.getValueAt(jt.getSelectedRow(), 0)));
			}
		} else if (e.getSource() == add) {
			g.add(JOptionPane.showInputDialog("Add"));
			wbe.fireTableDataChanged();
		} else if (e.getSource() == delete) {
			if (jt.getSelectedRow() >= 3) {
				g.deleteWord(jt.getSelectedRow());
			}
			wbe.fireTableDataChanged();
		} else if (e.getSource() == connection || e.getSource() == addConnection) {
			g.addConnection(connection.getText());
			cm.fireTableDataChanged();
		} else if (e.getSource() == deleteConnection) {
			if (jt0.getSelectedRow() != -1) {
				g.getWordBank().deleteConnectionFromIndex(jt0.getSelectedRow());
				cm.fireTableDataChanged();
			}
		}
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (jt.getSelectedRow() != -1) {
			wts.setType(g.getType(jt.getSelectedRow()));
		}
	}
}

class ConnectionsModel extends AbstractTableModel {
	private Generator g;
	private WordBank wb;
	public ConnectionsModel (Generator g) {
		this.g = g;
		this.wb = g.getWordBank();
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return wb.getConnectionsSize();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	@Override
	public String getColumnName(int c) {
		if (c == 0) {
			return "Keys";
		} else if (c == 1) {
			return "Values";
		} else {
			return null;
		}
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if (columnIndex == 0) {
			return wb.getKeyFromIndex(rowIndex);
		} else {
			return wb.getConnectionFromIndex(rowIndex);
		}
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			wb.setKeyAtIndex(rowIndex, String.valueOf(value));
		} else {
			wb.setConnectionAtIndex(rowIndex, String.valueOf(value));
		}
		fireTableDataChanged();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
}
class WordBankModel extends AbstractTableModel {
	private Generator g;
	public WordBankModel (Generator g) {
		this.g = g;
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return g.wordbanksize();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public String getColumnName(int c) {
		return "Words";
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return g.getWord(rowIndex);
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		g.setWord(rowIndex, g.getType(rowIndex), String.valueOf(value));
		fireTableDataChanged();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (rowIndex >= 3) {
			return true;
		} else {
			return false;
		}
	}
}