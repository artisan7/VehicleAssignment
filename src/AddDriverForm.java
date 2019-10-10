import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;

public class AddDriverForm extends JDialog implements ActionListener {
	private DefaultTableModel model;
	private JButton btn_cancel, btn_reset, btn_save;
	private MyInputGroup ig_license, ig_fname, ig_lname, ig_bdate, ig_gender, ig_dtype, ig_years;
	
	// CONSTRUCTOR
	public AddDriverForm(DefaultTableModel mdl) {
		model = mdl;
		
		// frame components
		ig_license = new MyInputGroup(new JTextField(), "License Number", true);
		ig_fname = new MyInputGroup(new JTextField(), "First Name", true);
		ig_lname = new MyInputGroup(new JTextField(), "Last Name", true);
		ig_bdate = new MyInputGroup(new MyDatePicker(), "Birthdate", false);
		ig_gender = new MyInputGroup(new JComboBox<String>(new String[] {"Male", "Female"}), "Gender", false);
		ig_dtype = new MyInputGroup(new JComboBox<String>(new String[] {"Professional", "Non-professional"}), "Driver Type", false);
		ig_years = new MyInputGroup(new MyNumPicker(), "Years Driving", false);
		
		btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(this);
		btn_reset = new JButton("Reset");
		btn_save = new JButton("Save");
		btn_reset.addActionListener(this);
		btn_save.addActionListener(this);
		
		// frame components layout
		JPanel pnl_prompt = new JPanel(new GridLayout(7, 1));
		pnl_prompt.add(ig_license);
		pnl_prompt.add(ig_fname);
		pnl_prompt.add(ig_lname);
		pnl_prompt.add(ig_bdate);
		pnl_prompt.add(ig_gender);
		pnl_prompt.add(ig_dtype);
		pnl_prompt.add(ig_years);
		
		JPanel pnl_ctrl = new JPanel(new GridLayout(1, 3));
		pnl_ctrl.add(btn_cancel);
		pnl_ctrl.add(btn_reset);
		pnl_ctrl.add(btn_save);
		
		add(pnl_prompt, BorderLayout.CENTER);
		add(pnl_ctrl, BorderLayout.SOUTH);

		// stylize components
		Stylizer.applyTo(this);
		
		Font f = new Font("Sans Serif", Font.BOLD, 20);
		Stylizer.applyTo(btn_cancel, new Color(255, 71, 87), Color.WHITE, f);
		Stylizer.applyTo(btn_reset, new Color(255, 165, 2), Color.WHITE, f);
		Stylizer.applyTo(btn_save, new Color(46, 213, 115), Color.WHITE, f);
		
		// frame setup
		setTitle("Add New Driver");
		setSize(360, 480);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	// ACTION LISTENER -> listens to control buttons when clicked
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		
		if (src == btn_save) {
			String licenseNum = (String) ig_license.getValue();
			String fname = (String) ig_fname.getValue();
			String lname = (String) ig_lname.getValue();
			Calendar bdate = (Calendar) ig_bdate.getValue();;
			int g = (int) ig_gender.getValue();
			int dtype = (int) ig_dtype.getValue();
			int y = (int) ig_years.getValue();
			
			// check validity
			if (!inputsValid())
				return;
			
			if (Driver.search(licenseNum) != null) {
				ig_license.changeState(1);
				JTextField tf = (JTextField) ig_license.getMainComponent();
				tf.setText(tf.getText() + " already exists");
				return;
			}
			
			// add the new driver
			try {
				Driver d = new Driver(licenseNum, fname, lname, bdate, g, dtype, y);
				
				// write to file
				BufferedWriter outFile = new BufferedWriter( new FileWriter("driver.txt", true) );
				outFile.newLine();
				outFile.write(String.format("%s,%s,%s,%s,%d,%d,%d", licenseNum, lname, fname, DTFormatter.slashDate(bdate), g, dtype, y));
				outFile.close();
				
				// add to table
				model.addRow(d.toArray());
				dispose();
			}
			catch (Exception ex) { System.out.println("ERROR 04: " + ex.getMessage()); }
		}
		else if (src == btn_reset) {
			// reset fields
			ig_license.reset();
			ig_fname.reset();
			ig_lname.reset();
			ig_bdate.reset();
			ig_gender.reset();
			ig_dtype.reset();
			ig_years.reset();
		}
		else { dispose(); }
	}
	
	// INPUTSVALID -> checks validity of input fields
	private boolean inputsValid() {
		boolean evaluation = true;
		
		if (!ig_license.isValidInput())
			evaluation = false;
		if (!ig_fname.isValidInput())
			evaluation = false;
		if (!ig_lname.isValidInput())
			evaluation = false;
			
		return evaluation;
	}
}
