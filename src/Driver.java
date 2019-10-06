import java.util.Vector;
import java.util.Calendar;
import java.util.Locale;

public class Driver {
	private static Vector<Driver> drivers = new Vector<Driver>();
	
	private String licenseNumber;
	private String lastName;
	private String firstName;
	private Calendar birthDate;
	private int gender;
	private int licenseType;
	private int yearsDriving;
	
	// CONSTRUCTOR
	public Driver(String licenseNum, String ln, String fn, Calendar bdate, int g, int lt, int y) {
		licenseNumber = licenseNum;
		lastName = ln;
		firstName = fn;
		birthDate = bdate;
		gender = g;
		licenseType = lt;
		yearsDriving = y;
		
		drivers.add(this);
	}
	
	// GETTERS
	public String getLicenseNumber() { return licenseNumber; }
	public String getLastName() { return lastName; }
	public String getFirstName() { return firstName; }
	
	public String getBirthDate() {
		String month = birthDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
		int day = birthDate.get(Calendar.DAY_OF_MONTH);
		int year = birthDate.get(Calendar.YEAR);
		
		return String.format("%s-%d-%d", month, day, year);
	}
	
	public String getGender() { return (gender == 0) ? "Male" : "Female"; }
	public String getLicenseType() { return (licenseType == 0) ? "Professional" : "Non-professional"; }
	public int getYearsDriving() { return yearsDriving; }
	
	// TO STRING AKA GET FULL NAME
	public String toString() {
		return lastName + ", " + firstName;
	}
	
	// TO ARRAY
	public String[] toArray() {
		return new String[] {licenseNumber, lastName, firstName, getBirthDate(), getGender(), getLicenseType(), Integer.toString(yearsDriving) };
	}
	
	// DRIVER SEARCH
	public static Driver search(String licenseNum) {
		for (Driver d : drivers)
			if (d.licenseNumber.equals(licenseNum))
				return d;
		return null;
	}
}