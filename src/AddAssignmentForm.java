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

public class AddAssignmentForm extends JDialog implements ActionListener, ListSelectionListener {
	private JTable tbl_vehicles, tbl_drivers;
	private JButton btn_cancel, btn_reset, btn_save;
	private MyInputGroup ig_vehicle, ig_driver, ig_date, ig_time, ig_notes;
	
	private Calendar now;
	
	// CONSTRUCTOR
	public AddAssignmentForm() {
		// frame components
		
		// vehicle table setup
		tbl_vehicles = new JTable(MainApp.mdl_vehicles);
		tbl_vehicles.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbl_vehicles.getSelectionModel().addListSelectionListener(this);
		
		// vehicle table sorter and filterer
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
		
		// driver table sorter
		TableRowSorter<DefaultTableModel> srtr_drivers = new TableRowSorter<DefaultTableModel>(MainApp.mdl_drivers);
		srtr_drivers.toggleSortOrder(1);
		tbl_drivers.setRowSorter(srtr_drivers);
		
		// prompts setup
		ig_vehicle = new MyInputGroup(new JTextField(), "Vehicle Details", false);
		((JTextField) ig_vehicle.getMainComponent()).setEditable(false);
		ig_driver = new MyInputGroup(new JTextField(), "Driver Details", false);
		((JTextField) ig_driver.getMainComponent()).setEditable(false);
		
		now = Calendar.getInstance();		// get current date and time
		ig_date = new MyInputGroup(new JTextField(DTFormatter.longDate(now)), "Assignment Date", false);
		((JTextField) ig_date.getMainComponent()).setEditable(false);
		ig_time = new MyInputGroup(new JTextField(DTFormatter.nonMilitaryTime(now)), "Assignment Time", false);
		((JTextField) ig_time.getMainComponent()).setEditable(false);
		
		ig_notes = new MyInputGroup(new JTextField(), "Notes", false);
		
		btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(this);
		btn_reset = new JButton("Reset");
		btn_save = new JButton("Save");
		btn_reset.addActionListener(this);
		btn_save.addActionListener(this);
		
		// frame components layout
		JPanel pnl_tables = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.weighty = 1;
		
		// vehicle table layout settings
		gbc.gridx = 0; gbc.gridy = 0;
		gbc.weightx = 2;
		pnl_tables.add( new MyInputGroup( new JScrollPane(tbl_vehicles), "Vehicles", false ) , gbc);
		
		// driver table layout settings
		gbc.gridx = 1; gbc.gridy = 0;
		gbc.weightx = 5;
		pnl_tables.add( new MyInputGroup( new JScrollPane(tbl_drivers), "Drivers", false ) , gbc);
		
		// prompts layout settings
		JPanel pnl_prompt = new JPanel(new GridLayout(5, 1));
		pnl_prompt.add(ig_vehicle);
		pnl_prompt.add(ig_driver);
		pnl_prompt.add(ig_date);
		pnl_prompt.add(ig_time);
		pnl_prompt.add(ig_notes);
		
		// control buttons layout settings
		JPanel pnl_ctrl = new JPanel(new GridLayout(1, 3));
		pnl_ctrl.add(btn_cancel);
		pnl_ctrl.add(btn_reset);
		pnl_ctrl.add(btn_save);
		
		JPanel pnl_details = new JPanel(new BorderLayout());
		
		pnl_details.add(pnl_prompt, BorderLayout.CENTER);
		pnl_details.add(pnl_ctrl, BorderLayout.SOUTH);
		
		add(pnl_tables, BorderLayout.CENTER);
		add(pnl_details, BorderLayout.EAST);

		// stylize components
		Stylizer.applyTo(this);
		
		Font f = new Font("Sans Serif", Font.BOLD, 20);
		Stylizer.applyTo(btn_cancel, new Color(255, 71, 87), Color.WHITE, f);
		Stylizer.applyTo(btn_reset, new Color(255, 165, 2), Color.WHITE, f);
		Stylizer.applyTo(btn_save, new Color(46, 213, 115), Color.WHITE, f);
		
		// frame setup
		setTitle("Add New Assignment");
		setSize(1500, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	// ACTION LISTENER -> listens to the control buttons when clicked
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		
		if (src == btn_save) {
			
			// check validity
			if (!inputsValid())
				return;

			try {
				Driver d = Driver.search((String) tbl_drivers.getValueAt(tbl_drivers.getSelectedRow(), 0));
				Vehicle v = Vehicle.search((String) tbl_vehicles.getValueAt(tbl_vehicles.getSelectedRow(), 0));
				String n = (String) ig_notes.getValue();
				
				if (d != null && v != null) {
					Assignment a = new Assignment(d, v, n, now);
					
					// write to file
					BufferedWriter outFile = new BufferedWriter( new FileWriter("assignment.txt", true) );
					outFile.newLine();
					outFile.write(String.format("%s,%s,%s,%s,%s", d.getLicenseNumber(),
																  v.getPlateNumber(),
																  DTFormatter.slashDate(now),
																  DTFormatter.militaryTime(now),
																  (n.length() > 0) ? n : " "));
					outFile.close();
					
					// add to table
					MainApp.mdl_assignments.addRow(a.toArray());
					dispose();
				}
				
			}
			catch (Exception ex) { System.out.println("ERROR 06: " + ex.getMessage()); }
			
		}
		else if (src == btn_reset) {
			// reset fields
			tbl_vehicles.clearSelection();
			tbl_drivers.clearSelection();
			
			ig_vehicle.reset();
			ig_driver.reset();
			ig_notes.reset();
			
			now = Calendar.getInstance();		// get current date and time
			((JTextField) ig_date.getMainComponent()).setText(DTFormatter.longDate(now));
			((JTextField) ig_time.getMainComponent()).setText(DTFormatter.nonMilitaryTime(now));
		}
		else { dispose(); }
	}
	
	// INPUTSVALID -> checks validity of input fields
	private boolean inputsValid() {
		boolean evaluation = true;
		
		if (!ig_vehicle.isValidInput()) {
			evaluation = false;
			((JTextField) ig_vehicle.getMainComponent()).setText("Please select a vehicle from the table.");
		}
		if (!ig_driver.isValidInput()) {
			evaluation = false;
			((JTextField) ig_driver.getMainComponent()).setText("Please select a driver from the table.");
		}
			
		return evaluation;
	}
	
	// LIST SELECTION LISTENER -> enables selection of vehicle and driver through the tables
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			if (e.getSource() == tbl_drivers.getSelectionModel() && tbl_drivers.getSelectedRow() > -1) {
				// driver text field display selected row
				int viewIndex = tbl_drivers.getSelectedRow();
				int modelIndex = tbl_drivers.getRowSorter().convertRowIndexToModel(viewIndex);
				
				String lname = (String) MainApp.mdl_drivers.getValueAt(modelIndex, 1);
				String fname = (String) MainApp.mdl_drivers.getValueAt(modelIndex, 2);
				
				JTextField tf_driver = (JTextField) ig_driver.getMainComponent();
				Stylizer.applyTo(tf_driver);
				tf_driver.setText(lname + ", " + fname);
			}
			else if (e.getSource() == tbl_vehicles.getSelectionModel() && tbl_vehicles.getSelectedRow() > -1) {
				// vehicle text field display selected row
				int viewIndex = tbl_vehicles.getSelectedRow();
				int modelIndex = tbl_vehicles.getRowSorter().convertRowIndexToModel(viewIndex);
				
				String plateNo = (String) MainApp.mdl_vehicles.getValueAt(modelIndex, 0);
				String make = (String) MainApp.mdl_vehicles.getValueAt(modelIndex, 1);
				String model = (String) MainApp.mdl_vehicles.getValueAt(modelIndex, 2);
				String year = (String) MainApp.mdl_vehicles.getValueAt(modelIndex, 3);
				
				JTextField tf_vehicle = (JTextField) ig_vehicle.getMainComponent();
				Stylizer.applyTo(tf_vehicle);
				tf_vehicle.setText(String.format("%s %s %s (%s)", make, model, year, plateNo));
			}
		}
	}
	
}
