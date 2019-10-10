import java.util.Vector;
import java.util.Calendar;

public class Driver {
	private static Vector<Driver> drivers = new Vector<Driver>();
	
	private String licenseNumber;
	private String lastName;
	private String firstName;
	private Calendar birthDate;
	private int gender;
	private int driverType;
	private int yearsDriving;
	
	// CONSTRUCTOR
	public Driver(String licenseNum, String ln, String fn, Calendar bdate, int g, int dt, int y) {
		licenseNumber = licenseNum;
		lastName = ln;
		firstName = fn;
		birthDate = bdate;
		gender = g;
		driverType = dt;
		yearsDriving = y;
		
		drivers.add(this);
	}
	
	// GETTERS
	public String getLicenseNumber() { return licenseNumber; }
	public String getLastName() { return lastName; }
	public String getFirstName() { return firstName; }
	public Calendar getBirthDate() { return birthDate; }
	
	public String getGender() { return (gender == 0) ? "Male" : "Female"; }
	public String getDriverType() { return (driverType == 0) ? "Professional" : "Non-professional"; }
	public int getYearsDriving() { return yearsDriving; }
	
	// TO STRING AKA GET FULL NAME
	public String toString() {
		return lastName + ", " + firstName;
	}
	
	// TO ARRAY
	public String[] toArray() {
		return new String[] {licenseNumber, lastName, firstName, DTFormatter.slashDate(birthDate), getGender(), getDriverType(), Integer.toString(yearsDriving) };
	}
	
	// DRIVER SEARCH
	public static Driver search(String licenseNum) {
		for (Driver d : drivers)
			if (d.licenseNumber.equals(licenseNum))
				return d;
		return null;
	}
}