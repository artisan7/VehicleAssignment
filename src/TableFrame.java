import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class TableFrame extends JFrame implements ActionListener, TableModelListener, DocumentListener, FocusListener {
	private DefaultTableModel model;
	private TableRowSorter<DefaultTableModel> sorter;
	private JTable table;
	private JScrollPane scrollPane;
	
	private JButton btn_add;
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
		
		tf_search = new JTextField("Search");
		tf_search.addFocusListener(this);
		
		btn_add = new JButton("ADD NEW");
		btn_add.addActionListener(this);
		
		// frame components layout
		JPanel pnl_ctrl = new JPanel(new BorderLayout(20, 20));
		JLabel label = new JLabel((mdl==MainApp.mdl_drivers) ? "DRIVERS" : (mdl==MainApp.mdl_vehicles) ? "VEHICLES" : "ASSIGNMENTS");
		
		pnl_ctrl.add(tf_search, BorderLayout.CENTER);
		pnl_ctrl.add(label, BorderLayout.WEST);
		pnl_ctrl.add(btn_add, BorderLayout.EAST);
		
		add(pnl_ctrl, BorderLayout.NORTH);
		
		scrollPane = new JScrollPane(table);
		JPanel pnl_table = new JPanel(new BorderLayout());
		
		pnl_table.add(scrollPane, BorderLayout.CENTER);
		add(pnl_table, BorderLayout.CENTER);
		
		// Stylize
		Stylizer.applyTo(btn_add, new Color(46, 213, 115), new Font("Sans Serif", Font.BOLD, 24));
		Stylizer.setDefaultForeground(new Color(230, 231, 235));
		Stylizer.setDefaultFont(new Font("Sans Serif", Font.BOLD, 20));
		Stylizer.setInputForeground(new Color(241, 242, 246));
		Stylizer.setInputFont(new Font("Sans Serif", 0, 14));
		Stylizer.applyTo(this);
		
		// frame setup
		setTitle((mdl==MainApp.mdl_drivers) ? "Driver" : (mdl==MainApp.mdl_vehicles) ? "Vehicle" : "Assignment");
		setSize(table.getColumnCount()*175, 550);
		setLocationRelativeTo(null);
		setVisible(true);
		
		table.requestFocusInWindow();
	}
	
	// ACTION LISTENER -> listens to 'ADD' button when clicked
	public void actionPerformed(ActionEvent e) {
		if (model == MainApp.mdl_drivers)			// add driver
			new AddDriverForm(model);
		else if (model == MainApp.mdl_vehicles)		// add vehicle
			new AddVehicleForm(model);
		else if (model == MainApp.mdl_assignments)	// add assignment
			new AddAssignmentForm();
	}
	
	// TABLE MODEL LISTENER -> selects the newly added row when one is added
	public void tableChanged(TableModelEvent e) {
		if (e.getType() == TableModelEvent.INSERT) {
			// resets search field
			tf_search.getDocument().addDocumentListener(this);
			tf_search.setText("");
			
			
			// selects the newly added row
			int r = sorter.convertRowIndexToView(e.getFirstRow());
			table.setRowSelectionInterval(r, r);
			scrollPane.getViewport().setViewPosition(new Point(0, r*table.getRowHeight()));
		}
	}
	
	// DOCUMENT LISTENER -> enables real-time filtering while typing in search bar
	public void insertUpdate(DocumentEvent e) { updateView(); }
	public void removeUpdate(DocumentEvent e) { updateView(); }
	public void changedUpdate(DocumentEvent e) { updateView(); }
	
	public void updateView() {
		String searchedText = "^(?i)" + tf_search.getText();
		
		if (model == MainApp.mdl_assignments)
			sorter.setRowFilter(RowFilter.regexFilter(searchedText, 0, 1));
		else sorter.setRowFilter(RowFilter.regexFilter(searchedText, 0, 1, 2));
	}

	// FOCUS LISTENER -> placeholder effect in the searchbar
	public void focusGained(FocusEvent e) {
		tf_search.getDocument().addDocumentListener(this);
		
		if (tf_search.getText().equals("Search"))
			tf_search.setText(null);
	}

	public void focusLost(FocusEvent e) {
		tf_search.getDocument().removeDocumentListener(this);
		
		if (tf_search.getText().length() == 0)
			tf_search.setText("Search");
	}

}
