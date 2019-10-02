import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.Scanner;
import java.io.*;
import java.util.Calendar;

public class MainApp extends JFrame implements ActionListener {
	JButton btn_driver, btn_vehicle, btn_assignment, btn_exit;
	DefaultTableModel mdl_drivers, mdl_vehicles, mdl_assignments;
	
	public MainApp() {
		// file reading
		try {
			Scanner inFile = new Scanner(new FileReader("driver.txt"));
			
			mdl_drivers = new DefaultTableModel(new String[] {"License No.", "Name", "Birthdate", "Gender", "License Type", "Years Driving"}, 0);
			
			inFile.nextLine();
			while (inFile.hasNext()) {
				String[] token = inFile.nextLine().split(",");
				String licenseNum = token[0];
				String fname = token[1];
				String lname = token[2];
				
				String[] bdateToken = token[3].split("/");
				Calendar bdate = Calendar.getInstance();
				bdate.set(Integer.parseInt(bdateToken[2]), Integer.parseInt(bdateToken[0])-1, Integer.parseInt(bdateToken[1]));
				
				boolean g = (Integer.parseInt(token[4]) == 0) ? false : true;
				boolean t = (Integer.parseInt(token[5]) == 0) ? false : true;
				int y = Integer.parseInt(token[6]);
				
				Driver d = new Driver(licenseNum, fname, lname, bdate, g, t, y);
				mdl_drivers.addRow(d.toArray());
			}
			
			inFile.close();
		}
		catch (Exception e) { System.out.println(e.getMessage()); }
		
		// frame components
		btn_driver = new JButton("Driver");
		btn_driver.addActionListener(this);
		btn_vehicle = new JButton("Vehicle");
		btn_vehicle.addActionListener(this);
		btn_assignment = new JButton("Assignment");
		btn_assignment.addActionListener(this);
		btn_exit = new JButton("Exit");
		btn_exit.addActionListener(this);
		
		// frame components layout
		setLayout(new GridLayout(2,2));
		add(btn_driver);
		add(btn_vehicle);
		add(btn_assignment);
		add(btn_exit);
		
		// frame setup
		setTitle("Vehicle Assignment System");
		setSize(550, 550);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		
		if (src == btn_driver)
			new DriverFrame(mdl_drivers);
		else if (src == btn_vehicle)
			System.out.println("VEHICLE");
		else if (src == btn_assignment)
			System.out.println("ASSIGNMENT");
		else 
			processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public static void main(String[] args) {
		new MainApp();
	}
	
}
