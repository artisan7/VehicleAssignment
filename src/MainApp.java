import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.Scanner;
import java.io.FileReader;
import java.util.Calendar;

public class MainApp extends JFrame implements ActionListener {
	private static final long serialVersionUID = -8963769804747863125L;

	static DefaultTableModel mdl_drivers, mdl_vehicles, mdl_assignments;
	JButton btn_driver, btn_vehicle, btn_assignment, btn_exit;
	
	// CONSTRUCTOR
	public MainApp() {
		// driver table setup
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
		
		// vehicle table setup
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
		
		// assignment table setup
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
		new MainApp();
	}
	
}
