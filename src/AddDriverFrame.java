import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;

public class AddDriverFrame extends JFrame {
	private DefaultTableModel model;
	private JButton btn_cancel, btn_reset, btn_save;
	
	public AddDriverFrame(DefaultTableModel mdl) {
		model = mdl;
		
		// frame components
		JLabel[] lbl_prompt = new JLabel[7];
		String[] labelStrings = {"License Number", "First Name", "Last Name", "Birthdate", "Gender", "Driver Type", "Years Driving"};
		for (int i = 0; i < 7; i++)
			lbl_prompt[i] = new JLabel(labelStrings[i]);
		
		JTextField tf_license = new JTextField();
		JTextField tf_fname = new JTextField();
		JTextField tf_lname = new JTextField();
		JTextField tf_bdate = new JTextField();
		JComboBox<String> cb_gender = new JComboBox<String>(new String[] {"Male", "Female"});
		JComboBox<String> cb_dtype = new JComboBox<String>(new String[] {"Professional", "Non-professional"});
		MyNumPicker np_years = new MyNumPicker();
		
		btn_cancel = new JButton("Cancel");
		btn_reset = new JButton("Reset");
		btn_save = new JButton("Save");
		
		// frame components layout
		JPanel pnl_prompt = new JPanel(new GridLayout(14, 1));
		pnl_prompt.add(lbl_prompt[0]);
		pnl_prompt.add(tf_license);
		pnl_prompt.add(lbl_prompt[1]);
		pnl_prompt.add(tf_fname);
		pnl_prompt.add(lbl_prompt[2]);
		pnl_prompt.add(tf_lname);
		pnl_prompt.add(lbl_prompt[3]);
		pnl_prompt.add(tf_bdate);
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
}
