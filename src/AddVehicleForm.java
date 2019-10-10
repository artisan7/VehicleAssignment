import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Vector;

public class AddVehicleForm extends JDialog implements ActionListener {
	private DefaultTableModel model;
	private JButton btn_cancel, btn_reset, btn_save;
	private MyInputGroup ig_plateNo, ig_model, ig_make, ig_year;
	
	// CONSTRUCTOR
	public AddVehicleForm(DefaultTableModel mdl) {
		model = mdl;
		
		// frame components
		ig_plateNo = new MyInputGroup(new JTextField(), "Plate Number", true);

		Vector<String> modifiedMakes = new Vector<String>();
		for (String m : Vehicle.getMakeList())
			modifiedMakes.add(m);
		modifiedMakes.add("----------ADD NEW MAKE----------");
		
		ig_make = new MyInputGroup(new JComboBox(modifiedMakes), "Make", false);
		// COMBOBOX ACTION LISTENER -> "ADD NEW MAKE" functionality
		((JComboBox) ig_make.getMainComponent()).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox src = (JComboBox) e.getSource();
				
				if (src.getSelectedIndex() == src.getItemCount()-1) {
					src.setEditable(true);
					src.setSelectedItem("");
				}
				else { src.setEditable(false); }
			}
		});
		
		ig_model = new MyInputGroup(new JTextField(), "Model", true);
		ig_year = new MyInputGroup(new MyNumPicker(2000, 1970, 2019), "Year", false);
		
		btn_cancel = new JButton("Cancel");
		btn_reset = new JButton("Reset");
		btn_save = new JButton("Save");
		
		btn_cancel.addActionListener(this);
		btn_reset.addActionListener(this);
		btn_save.addActionListener(this);
		
		// frame components layout
		JPanel pnl_prompt = new JPanel(new GridLayout(4, 1));
		pnl_prompt.add(ig_plateNo);
		pnl_prompt.add(ig_make);
		pnl_prompt.add(ig_model);
		pnl_prompt.add(ig_year);
		
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
		setTitle("Add New Vehicle");
		setSize(360, 360);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	// ACTION LISTENER -> listens to control buttons when clicked
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		
		if (src == btn_save) {
			String plateNo = (String) ig_plateNo.getValue();
			String make = (String) ((JComboBox) ig_make.getMainComponent()).getSelectedItem();
			String vModel = (String) ig_model.getValue();
			int year = (int) ig_year.getValue();
			
			// check validity
			if (!inputsValid())
				return;
			
			if (Vehicle.search(plateNo) != null) {
				ig_plateNo.changeState(1);
				JTextField tf = (JTextField) ig_plateNo.getMainComponent();
				tf.setText(tf.getText() + " already exists");
				return;
			}
			
			// add the new vehicle
			try {
				Vehicle v = new Vehicle(plateNo, make, vModel, year);
				
				// write to file
				BufferedWriter outFile = new BufferedWriter( new FileWriter("vehicle.txt", true) );
				outFile.newLine();
				outFile.write(String.format("%s,%s,%s,%d", plateNo, make, vModel, year));
				outFile.close();
				
				// add to table
				model.addRow(v.toArray());
				dispose();
			}
			catch (Exception ex) { System.out.println("ERROR 05: " + ex.getMessage()); }
		}
		else if (src == btn_reset) {
			// reset fields
			ig_plateNo.reset();
			ig_make.reset();
			ig_model.reset();
			ig_year.reset();
		}
		else { dispose(); }
	}
	
	// INPUTSVALID -> checks validity of input fields
	private boolean inputsValid() {
		boolean evaluation = true;
		
		if (!ig_plateNo.isValidInput())
			evaluation = false;
		if (!ig_make.isValidInput())
			evaluation = false;
		if (!ig_model.isValidInput())
			evaluation = false;
			
		return evaluation;
	}
}
