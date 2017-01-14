package Core;




import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class Display extends JDialog implements ActionListener, KeyListener {
	private Main m;
	private JToolBar tools;
	private JButton add, delete;
	private DisplayModel dm;
	private JTable jtab;
	private JTextField searchField;
	private JButton search;
	public Display(Main m) {
		this.m = m;
		setTitle("Display");
		setBounds(450, 200, 400, 400);
		setLayout(new BorderLayout());
		tools = new JToolBar();
		tools.setFloatable(false);
		add = new JButton("Add");
		add.addActionListener(this);
		tools.add(add);
		delete = new JButton("Delete");
		delete.addActionListener(this);
		tools.add(delete);
		searchField = new JTextField(10);
		searchField.addKeyListener(this);
		tools.add(searchField);
		search = new JButton("Search");
		search.addActionListener(this);
		tools.add(search);
		add(tools, BorderLayout.NORTH);
		dm = new DisplayModel(m);
		jtab = new JTable(dm);
		add(new JScrollPane(jtab), BorderLayout.CENTER);
	}
	public void setVocabulary() {
		dm.setVocabulary();
		dm.fireTableDataChanged();
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == add) {
			String input = JOptionPane.showInputDialog("Add Phrase: ");
			if (input == null || input.equals("")) {
				return;
			}
			if (m.getBaseVocabulary().contains(input)) {
				JOptionPane.showMessageDialog(this, "This vocabulary entry has already been used.");
				return;
			}
			m.addBaseVocabularyEntry(input);
			setVocabulary();
		} else if (ae.getSource() == delete) {
			if (jtab.getSelectedRow() != -1) {
				String x = (String)dm.getValueAt(jtab.getSelectedRow(), 0);
				m.deleteBaseVocabularyEntry(x);
				String searchText = searchField.getText();
				ArrayList <String> searchResults = new ArrayList <String> ();
				ArrayList <String> allPhrases = m.getBaseVocabulary();
				for (int i = 0; i < allPhrases.size(); i++) {
					if (allPhrases.get(i).toLowerCase().contains(searchText.toLowerCase())) {
						searchResults.add(allPhrases.get(i));
					}
				}
				dm.implement(searchResults);
				dm.fireTableDataChanged();
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && searchField.getText().length() == 1) {
			dm.implement(m.getBaseVocabulary());
			dm.fireTableDataChanged();
			return;
		}
		String searchText = searchField.getText();
		ArrayList <String> searchResults = new ArrayList <String> ();
		ArrayList <String> allPhrases = m.getBaseVocabulary();
		for (int i = 0; i < allPhrases.size(); i++) {
			if (allPhrases.get(i).toLowerCase().contains(searchText.toLowerCase())) {
				searchResults.add(allPhrases.get(i));
			}
		}
		dm.implement(searchResults);
		dm.fireTableDataChanged();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}