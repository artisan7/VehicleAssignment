import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class TableFrame extends JFrame implements ActionListener, TableModelListener {
	private static final long serialVersionUID = 1529973752046791377L;
	
	private DefaultTableModel model;
	private TableRowSorter<DefaultTableModel> sorter;
	private JTable table;
	private JScrollPane scrollPane;
	
	private JButton btn_search, btn_add;
	private JTextField tf_search;
	
	public TableFrame(DefaultTableModel mdl) {
		// frame components
		model = mdl;
		model.addTableModelListener(this);
		
		table = new JTable(model);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sorter = new TableRowSorter<DefaultTableModel>(model);
		sorter.toggleSortOrder(0);
		table.setRowSorter(sorter);
		
		tf_search = new JTextField();
		btn_search = new JButton("Search");
		btn_search.addActionListener(this);
		btn_add = new JButton("Add");
		btn_add.addActionListener(this);
		
		// frame components layout
		JPanel pnl_ctrl = new JPanel(new GridLayout(1, 3));
		pnl_ctrl.add(tf_search);
		pnl_ctrl.add(btn_search);
		pnl_ctrl.add(btn_add);
		
		add(pnl_ctrl, BorderLayout.NORTH);
		
		scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
		
		// frame setup
		setTitle("Drivers");
		setSize(table.getColumnCount()*175, 550);
		setVisible(true);
	}
	
	// ACTION LISTENER
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		
		if (src == btn_search) {	// search filter functionality
			DefaultRowSorter<?, ?> sorter = (DefaultRowSorter<?, ?>) table.getRowSorter();
			String searchedText = "(?i)" + tf_search.getText();
			sorter.setRowFilter(RowFilter.regexFilter(searchedText, 0, 1, 2));
		}
		else {
			if (model == MainApp.mdl_drivers)		// add driver functionality
				new AddDriverFrame(model);
			else if (model == MainApp.mdl_vehicles)
				new AddVehicleFrame(model);
			else if (model == MainApp.mdl_assignments)
				new AddAssignmentFrame();
		}
	}
	
	// TABLE MODEL LISTENER
	public void tableChanged(TableModelEvent e) {
		if (e.getType() == TableModelEvent.INSERT) {
			// resets search field
			tf_search.setText(null);
			btn_search.doClick();
			
			// selects the newly added row
			int r = sorter.convertRowIndexToView(e.getFirstRow());
			table.setRowSelectionInterval(r, r);
			scrollPane.getViewport().setViewPosition(new Point(0, r*table.getRowHeight()));
		}
	}
}
