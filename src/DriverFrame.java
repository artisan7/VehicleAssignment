import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

public class DriverFrame extends JFrame implements ActionListener {
	private JTable tbl_drivers;
	private JButton btn_search, btn_add;
	private JTextField tf_search;
	
	
	
	@SuppressWarnings("unchecked")
	public DriverFrame(DefaultTableModel model) {
		// frame components
		tbl_drivers = new JTable(model);
		TableRowSorter srtr_drivers = new TableRowSorter(model);
		tbl_drivers.setRowSorter(srtr_drivers);
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
		add(new JScrollPane(tbl_drivers), BorderLayout.CENTER);
		
		// frame setup
		setTitle("Drivers");
		setSize(700, 550);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		
		if (src == btn_search) {	// search filter functionality
			DefaultRowSorter srtr_drivers = (DefaultRowSorter) tbl_drivers.getRowSorter();
			String searchedText = tf_search.getText();
			srtr_drivers.setRowFilter(RowFilter.regexFilter(searchedText, 0, 1));
		}
		else {	// add driver functionality
			new AddDriverFrame((DefaultTableModel) tbl_drivers.getModel());
		}
	}
}
