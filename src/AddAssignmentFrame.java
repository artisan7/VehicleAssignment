import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Locale;

public class AddAssignmentFrame extends JFrame implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 8214564158263658619L;

	private JTable tbl_vehicles, tbl_drivers;
	private JButton btn_cancel, btn_reset, btn_save;
	private JTextField tf_vehicle, tf_driver, tf_date, tf_time, tf_notes;
	
	private Calendar now;
	
	// CONSTRUCTOR
	public AddAssignmentFrame() {
		// frame components
		JLabel[] lbl_prompt = new JLabel[7];
		String[] labelStrings = {"Vehicle", "Driver", "Vehicle Details", "Driver Details", "Current Date", "Current Time", "Notes"};
		for (int i = 0; i < 7; i++)
			lbl_prompt[i] = new JLabel(labelStrings[i]);
		
		// vehicle table setup
		tbl_vehicles = new JTable(MainApp.mdl_vehicles);
		tbl_vehicles.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbl_vehicles.getSelectionModel().addListSelectionListener(this);
		
		// vehicle table sorter and filter
		TableRowSorter<DefaultTableModel> srtr_vehicles = new TableRowSorter<DefaultTableModel>(MainApp.mdl_vehicles);
		srtr_vehicles.toggleSortOrder(1);
		RowFilter<DefaultTableModel, Integer> fltr_vehicles = new RowFilter<DefaultTableModel, Integer>() {
			public boolean include(Entry e) {
				for (Assignment a : Assignment.getAll())
					if (a.getVehicle().getPlateNumber().equals(e.getStringValue(0)))
						return true;
				return false;
			}
		};
		srtr_vehicles.setRowFilter(RowFilter.notFilter(fltr_vehicles));
		tbl_vehicles.setRowSorter(srtr_vehicles);
		
		// driver table setup
		tbl_drivers = new JTable(MainApp.mdl_drivers);
		tbl_drivers.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbl_drivers.getSelectionModel().addListSelectionListener(this);
		
		// driver table sorter and filter
		TableRowSorter<DefaultTableModel> srtr_drivers = new TableRowSorter<DefaultTableModel>(MainApp.mdl_drivers);
		srtr_drivers.toggleSortOrder(1);
		RowFilter<DefaultTableModel, Integer> fltr_drivers = new RowFilter<DefaultTableModel, Integer>() {
			public boolean include(Entry e) {
				for (Assignment a : Assignment.getAll())
					if (a.getDriver().getLicenseNumber().equals(e.getStringValue(0)))
						return true;
				return false;
			}
		};
		srtr_drivers.setRowFilter(RowFilter.notFilter(fltr_drivers));
		tbl_drivers.setRowSorter(srtr_drivers);
		
		tf_vehicle = new JTextField();
		tf_vehicle.setEditable(false);
		tf_driver = new JTextField();
		tf_driver.setEditable(false);
		
		now = Calendar.getInstance();		// get current date and time
		tf_date = new JTextField(getDateStr(now));
		tf_date.setEditable(false);
		tf_time = new JTextField(getTimeStr(now));
		tf_time.setEditable(false);
		
		tf_notes = new JTextField();
		
		btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(this);
		btn_reset = new JButton("Reset");
		btn_save = new JButton("Save");
		btn_reset.addActionListener(this);
		btn_save.addActionListener(this);
		
		// frame components layout
		JPanel pnl_tables = new JPanel(new GridLayout(1, 2));
		pnl_tables.add(new JScrollPane(tbl_vehicles));
		pnl_tables.add(new JScrollPane(tbl_drivers));
		
		JPanel pnl_prompt = new JPanel(new GridLayout(11, 1));
		pnl_prompt.add(lbl_prompt[2]);
		pnl_prompt.add(tf_vehicle);
		pnl_prompt.add(lbl_prompt[3]);
		pnl_prompt.add(tf_driver);
		pnl_prompt.add(lbl_prompt[4]);
		pnl_prompt.add(tf_date);
		pnl_prompt.add(lbl_prompt[5]);
		pnl_prompt.add(tf_time);
		pnl_prompt.add(lbl_prompt[6]);
		pnl_prompt.add(tf_notes);
		
		JPanel pnl_ctrl = new JPanel(new GridLayout(1, 3));
		pnl_ctrl.add(btn_cancel);
		pnl_ctrl.add(btn_reset);
		pnl_ctrl.add(btn_save);
		pnl_prompt.add(pnl_ctrl);
		
		add(pnl_tables, BorderLayout.CENTER);
		add(pnl_prompt, BorderLayout.EAST);

		// frame setup
		setTitle("Add New Assignment");
		setSize(1500, 500);
		setVisible(true);
	}
	
	// GET DATE STRING
	private String getDateStr(Calendar c) {
		String month = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int year = c.get(Calendar.YEAR);
		
		return String.format("%s %d, %d", month, day, year);
	}
	
	// GET TIME STRING
	private String getTimeStr(Calendar c) {
		int hour = c.get(Calendar.HOUR);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		String meridiem = c.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.ENGLISH);
		
		return String.format("%2d:%02d:%02d %s", hour, minute, second, meridiem);
	}
	
	// ACTION LISTENER
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		
		if (src == btn_save) {
			Driver d = Driver.search((String) tbl_drivers.getValueAt(tbl_drivers.getSelectedRow(), 0));
			Vehicle v = Vehicle.search((String) tbl_vehicles.getValueAt(tbl_vehicles.getSelectedRow(), 0));
			String n = tf_notes.getText();
			
			if (d != null && v != null) {
				try {
					Assignment a = new Assignment(d, v, n, now);
					
					// write to file
					BufferedWriter outFile = new BufferedWriter( new FileWriter("assignment.txt", true) );
					outFile.newLine();
					outFile.write(String.format("%s,%s,%s,%s,%s", d.getLicenseNumber(),
																  v.getPlateNumber(),
																  a.getAssignmentDateSlashFormat(),
																  a.getAssignmentTimeMilitary(),
																  (n != null) ? n : " "));
					outFile.close();
					
					// add to table
					MainApp.mdl_assignments.addRow(a.toArray());
					dispose();
				}
				catch (Exception ex) { System.out.println(ex.getMessage()); }
			}
			
		}
		else if (src == btn_reset) {
			
			// reset fields
			tf_vehicle.setText(null);
			tf_driver.setText(null);
			
			now = Calendar.getInstance();		// get current date and time
			tf_date.setText(getDateStr(now));
			tf_time.setText(getTimeStr(now));
			
			tf_notes.setText(null);
			
		}
		else { dispose(); }
	}
	
	// LIST SELECTION LISTENER
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			if (e.getSource() == tbl_drivers.getSelectionModel()) {
				// driver text field display selected row
				int viewIndex = tbl_drivers.getSelectedRow();
				int modelIndex = tbl_drivers.getRowSorter().convertRowIndexToModel(viewIndex);
				
				String lname = (String) MainApp.mdl_drivers.getValueAt(modelIndex, 1);
				String fname = (String) MainApp.mdl_drivers.getValueAt(modelIndex, 2);
				
				tf_driver.setText(lname + ", " + fname);
			}
			else {
				// vehicle text field display selected row
				int viewIndex = tbl_vehicles.getSelectedRow();
				int modelIndex = tbl_vehicles.getRowSorter().convertRowIndexToModel(viewIndex);
				
				String plateNo = (String) MainApp.mdl_vehicles.getValueAt(modelIndex, 0);
				String make = (String) MainApp.mdl_vehicles.getValueAt(modelIndex, 1);
				String model = (String) MainApp.mdl_vehicles.getValueAt(modelIndex, 2);
				String year = (String) MainApp.mdl_vehicles.getValueAt(modelIndex, 3);
				
				tf_vehicle.setText(String.format("%s %s %s (%s)", make, model, year, plateNo));
			}
		}
	}
	
}
