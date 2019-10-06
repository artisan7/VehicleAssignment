import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;

public class AddDriverFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 8214564158263658619L;
	
	private DefaultTableModel model;
	private JButton btn_cancel, btn_reset, btn_save;
	private JTextField tf_license, tf_fname, tf_lname;
	private MyDatePicker dp_bdate;
	private JComboBox<String> cb_gender, cb_dtype;
	private MyNumPicker np_years;
	
	// CONSTRUCTOR
	public AddDriverFrame(DefaultTableModel mdl) {
		model = mdl;
		
		// frame components
		JLabel[] lbl_prompt = new JLabel[7];
		String[] labelStrings = {"License Number", "First Name", "Last Name", "Birthdate", "Gender", "Driver Type", "Years Driving"};
		for (int i = 0; i < 7; i++)
			lbl_prompt[i] = new JLabel(labelStrings[i]);
		
		tf_license = new JTextField();
		tf_fname = new JTextField();
		tf_lname = new JTextField();
		dp_bdate = new MyDatePicker();
		cb_gender = new JComboBox<String>(new String[] {"Male", "Female"});
		cb_dtype = new JComboBox<String>(new String[] {"Professional", "Non-professional"});
		np_years = new MyNumPicker();
		
		btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(this);
		btn_reset = new JButton("Reset");
		btn_save = new JButton("Save");
		btn_reset.addActionListener(this);
		btn_save.addActionListener(this);
		
		// frame components layout
		JPanel pnl_prompt = new JPanel(new GridLayout(14, 1));
		pnl_prompt.add(lbl_prompt[0]);
		pnl_prompt.add(tf_license);
		pnl_prompt.add(lbl_prompt[1]);
		pnl_prompt.add(tf_fname);
		pnl_prompt.add(lbl_prompt[2]);
		pnl_prompt.add(tf_lname);
		pnl_prompt.add(lbl_prompt[3]);
		pnl_prompt.add(dp_bdate);
		pnl_prompt.add(lbl_prompt[4]);
		pnl_prompt.add(cb_gender);
		pnl_prompt.add(lbl_prompt[5]);
		pnl_prompt.add(cb_dtype);
		pnl_prompt.add(lbl_prompt[6]);
		pnl_prompt.add(np_years);
		
		JPanel pnl_ctrl = new JPanel(new GridLayout(1, 3));
		pnl_ctrl.add(btn_cancel);
		pnl_ctrl.add(btn_reset);
		pnl_ctrl.add(btn_save);
		
		add(pnl_prompt, BorderLayout.CENTER);
		add(pnl_ctrl, BorderLayout.SOUTH);
		
		// frame setup
		setTitle("Add New Driver");
		setSize(300, 400);
		setVisible(true);
	}
	
	// ACTION LISTENER
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		
		if (src == btn_save) {
			String licenseNum = tf_license.getText();
			String fname = tf_fname.getText();
			String lname = tf_lname.getText();
			
			Calendar bdate = dp_bdate.getSelectedDate();
			
			int g = cb_gender.getSelectedIndex();
			int dtype = cb_dtype.getSelectedIndex();
			int y = np_years.getValue();
			
			try {
				Driver d = new Driver(licenseNum, fname, lname, bdate, g, dtype, y);
				
				// write to file
				BufferedWriter outFile = new BufferedWriter( new FileWriter("driver.txt", true) );
				outFile.newLine();
				outFile.write(String.format("%s,%s,%s,%s,%d,%d,%d", licenseNum, lname, fname, dp_bdate.getDateFormatted(), g, dtype, y));
				outFile.close();
				
				// add to table
				model.addRow(d.toArray());
				dispose();
			}
			catch (Exception ex) { System.out.println("ERROR 04: " + ex.getMessage()); }
		}
		else if (src == btn_reset) {
			// reset fields
			tf_license.setText(null);
			tf_fname.setText(null);
			tf_lname.setText(null);
			dp_bdate.reset();
			cb_gender.setSelectedIndex(0);
			cb_dtype.setSelectedIndex(0);
			np_years.setValue(0);
		}
		else { dispose(); }
	}
	
}
