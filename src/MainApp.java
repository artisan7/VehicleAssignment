import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.FileReader;
import java.util.Scanner;
import java.util.Calendar;

public class MainApp extends JFrame implements ActionListener {
	static DefaultTableModel mdl_drivers, mdl_vehicles, mdl_assignments;
	JButton btn_driver, btn_vehicle, btn_assignment, btn_exit;
	
	// CONSTRUCTOR
	public MainApp() {
		// Stylizer class setup
		Stylizer.setBackground(new Color(47, 53, 66));
		Stylizer.applyTo(this);
		
		// frame components
		btn_driver = new JButton("DRIVER");
		btn_vehicle = new JButton("VEHICLE");
		btn_assignment = new JButton("ASSIGNMENT");
		btn_exit = new JButton("EXIT");
		
		btn_driver.addActionListener(this);
		btn_vehicle.addActionListener(this);
		btn_assignment.addActionListener(this);
		btn_exit.addActionListener(this);
		
		// layout for top panel
		JPanel pnl_top = new JPanel(new GridLayout(1, 2));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
		pnl_top.add(btn_driver);
		pnl_top.add(btn_vehicle);
		
		// layout for bottom panel
		JPanel pnl_bottom = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.weightx = 2; gbc.weighty = 1;
		pnl_bottom.add(btn_assignment, gbc);
		gbc.gridx = 1;
		gbc.weightx = 1;
		pnl_bottom.add(btn_exit, gbc);
		
		// overall layout
		setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.weighty = 2;
		add(pnl_top, gbc);
		
		gbc.gridx = 0; gbc.gridy = 1;
		gbc.weighty = 1;
		add(pnl_bottom, gbc);
		
		// stylize components
		Stylizer.setButtonForeground(Color.WHITE);
		Stylizer.setButtonFont(new Font("Sans Serif", Font.BOLD, 52));
		Stylizer.applyTo(btn_driver, new Color(255, 165, 2));
		Stylizer.applyTo(btn_vehicle, new Color(55, 66, 250));
		Stylizer.setButtonFont(new Font("Sans Serif", Font.BOLD, 48));
		Stylizer.applyTo(btn_assignment, new Color(46, 213, 115));
		Stylizer.applyTo(btn_exit, new Color(255, 71, 87), Color.BLACK);
		
		// frame setup
		setTitle("Vehicle Assignment System");
		setSize(700, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	// ACTION LISTENER
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		
		if (src == btn_driver)
			new TableFrame(mdl_drivers);
		else if (src == btn_vehicle)
			new TableFrame(mdl_vehicles);
		else if (src == btn_assignment)
			new TableFrame(mdl_assignments);
		else 
			processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	// MAIN
	public static void main(String[] args) {
		// driver table setup with data from driver.txt
		try {
			Scanner inFile = new Scanner(new FileReader("driver.txt"));
			
			mdl_drivers = new DefaultTableModel(inFile.nextLine().split(","), 0);
			while (inFile.hasNext()) {
				String[] token = inFile.nextLine().split(",");
				
				String licenseNum = token[0];
				String lname = token[1];
				String fname = token[2];
				
				String[] bdateToken = token[3].split("/");
				Calendar bdate = Calendar.getInstance();
				bdate.set(Integer.parseInt(bdateToken[2]), Integer.parseInt(bdateToken[0])-1, Integer.parseInt(bdateToken[1]));
				
				int g = Integer.parseInt(token[4]);
				int t = Integer.parseInt(token[5]);
				int y = Integer.parseInt(token[6]);
				
				Driver d = new Driver(licenseNum, lname, fname, bdate, g, t, y);
				mdl_drivers.addRow(d.toArray());
			}
			
			inFile.close();
		}
		catch (Exception e) { System.out.println("ERROR 001: " + e.getMessage()); }
		
		// vehicle table setup with data from vehicle.txt
		try {
			Scanner inFile = new Scanner(new FileReader("vehicle.txt"));
			
			mdl_vehicles = new DefaultTableModel(inFile.nextLine().split(","), 0);
			while (inFile.hasNext()) {
				String[] token = inFile.nextLine().split(",");
				
				String plateNo = token[0];
				String make = token[1];
				String model = token[2];
				int yr = Integer.parseInt(token[3]);
				
				Vehicle v = new Vehicle(plateNo, make, model, yr);
				mdl_vehicles.addRow(v.toArray());
			}
			
			inFile.close();
		}
		catch (Exception e) { System.out.println("ERROR 002: " + e.getMessage()); }
		
		// assignment table setup with data from assignment.txt
		try {
			Scanner inFile = new Scanner(new FileReader("assignment.txt"));
			
			mdl_assignments = new DefaultTableModel(inFile.nextLine().split(","), 0);
			while (inFile.hasNext()) {
				String[] token = inFile.nextLine().split(",");
				
				Driver d = Driver.search(token[0]);
				Vehicle v = Vehicle.search(token[1]);
				
				String[] adateToken = token[2].split("/");
				String[] atimeToken = token[3].split(":");
				Calendar adate = Calendar.getInstance();
				adate.set(Integer.parseInt(adateToken[2]), Integer.parseInt(adateToken[0])-1, Integer.parseInt(adateToken[1]),
						Integer.parseInt(atimeToken[0]), Integer.parseInt(atimeToken[1]), Integer.parseInt(atimeToken[2]));
				
				String n = token[4];
				
				if (d != null && v != null) {
					Assignment a = new Assignment(d, v, n, adate);
					mdl_assignments.addRow(a.toArray());
				}
			}
			
			inFile.close();
		}
		catch (Exception e) { System.out.println("ERROR 003: " + e.getMessage()); }
		
		new MainApp();	// call main window
	}
	
}
