import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class AddVehicleFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = -8983759586518923897L;
	
	private DefaultTableModel model;
	private JButton btn_addMake, btn_cancel, btn_reset, btn_save;
	private JTextField tf_plateNo, tf_model;
	private JComboBox<String> cb_make;
	private MyNumPicker np_year;
	
	// CONSTRUCTOR
	public AddVehicleFrame(DefaultTableModel mdl) {
		model = mdl;
		
		// frame components
		JLabel[] lbl_prompt = new JLabel[4];
		String[] labelStrings = new String[] { "Plate Number", "Make", "Model", "Year" };
		for (int i = 0; i < 4; i++)
			lbl_prompt[i] = new JLabel(labelStrings[i]);
		
		tf_plateNo = new JTextField();
		cb_make = new JComboBox<String>(Vehicle.getMakeList());
		btn_addMake = new JButton("+");
		btn_addMake.addActionListener(this);
		tf_model = new JTextField();
		np_year = new MyNumPicker(2000);
		
		btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(this);
		btn_reset = new JButton("Reset");
		btn_save = new JButton("Save");
		btn_reset.addActionListener(this);
		btn_save.addActionListener(this);
		
		// frame components layout
		JPanel pnl_prompt = new JPanel(new GridLayout(8, 1));
		pnl_prompt.add(lbl_prompt[0]);
		pnl_prompt.add(tf_plateNo);
		pnl_prompt.add(lbl_prompt[1]);
		
		JPanel pnl_make = new JPanel(new GridLayout(1, 2));
		pnl_make.add(cb_make);
		pnl_make.add(btn_addMake);
		pnl_prompt.add(pnl_make);
		
		pnl_prompt.add(lbl_prompt[2]);
		pnl_prompt.add(tf_model);
		pnl_prompt.add(lbl_prompt[3]);
		pnl_prompt.add(np_year);
		
		JPanel pnl_ctrl = new JPanel(new GridLayout(1, 3));
		pnl_ctrl.add(btn_cancel);
		pnl_ctrl.add(btn_reset);
		pnl_ctrl.add(btn_save);
		
		add(pnl_prompt, BorderLayout.CENTER);
		add(pnl_ctrl, BorderLayout.SOUTH);
		
		// frame setup
		setTitle("Add New Vehicle");
		setSize(300, 400);
		setVisible(true);
	}
	
	// ACTION LISTENER
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		
		if (src == btn_addMake) {	// add a new make
			JDialog dlg = new JDialog(this, "Add New Make", true);
			
			JTextField tf_newMake = new JTextField();
			JButton btn_add = new JButton("Add");
			btn_add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Vehicle.addMake(tf_newMake.getText());
					cb_make.setSelectedItem(tf_newMake.getText());
					dlg.dispose();
				}
			});
						
			dlg.setLayout(new GridLayout(2, 1));
			
			dlg.add(tf_newMake);
			dlg.add(btn_add);
			
			dlg.setSize(100, 100);
			dlg.setVisible(true);
		}
		else if (src == btn_save) {
			String plateNo = tf_plateNo.getText();
			String make = (String) cb_make.getSelectedItem();
			String vModel = tf_model.getText();
			int year = np_year.getValue();
			
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
			catch (Exception ex) { System.out.println(ex.getMessage()); }
		}
		else if (src == btn_reset) {
			// reset fields
			tf_plateNo.setText(null);
			cb_make.setSelectedIndex(0);
			tf_model.setText(null);
			np_year.setValue(2000);
		}
		else { dispose(); }
	}
	
}
