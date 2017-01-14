package Core;




import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class DisplayModel extends AbstractTableModel {
	private Main m;
	private ArrayList <String> implementation;
	public DisplayModel(Main m) {
		this.m = m;
	}
	public void setVocabulary() {
		this.implementation = m.getBaseVocabulary();
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return implementation.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public String getColumnName(int c) {
		return "Phrases";
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return implementation.get(rowIndex);
	}
	public void implement(ArrayList <String> implementation) {
		this.implementation = implementation;
		fireTableDataChanged();
	}
}
