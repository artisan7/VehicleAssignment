import java.util.Calendar;
import java.util.Locale;

public class Driver {
	private String licenseNumber;
	private String firstName;
	private String lastName;
	private Calendar birthDate;
	private int gender;
	private int licenseType;
	private int yearsDriving;
	
	//public Driver()
	public Driver(String licenseNum, String fn, String ln, Calendar bdate, int g, int lt, int y) {
		licenseNumber = licenseNum;
		firstName = fn;
		lastName = ln;
		birthDate = bdate;
		gender = g;
		licenseType = lt;
		yearsDriving = y;
	}
	
	// GETTERS
	public String getLicenseNumber() { return licenseNumber; }
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public String getFullName() { return String.format("%s, %s", lastName, firstName); }
	
	public String getBirthDate() {
		String month = birthDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
		int day = birthDate.get(Calendar.DAY_OF_MONTH);
		int year = birthDate.get(Calendar.YEAR);
		
		return String.format("%s-%d-%d", month, day, year);
	}
	
	public String getGender() { return (gender == 0) ? "Male" : "Female"; }
	public String getLicenseType() { return (licenseType == 0) ? "Professional" : "Non-professional"; }
	public int getYearsDriving() { return yearsDriving; }
	
	public Object[] toArray() {
		return new Object[] {licenseNumber, getFullName(), getBirthDate(), getGender(), getLicenseType(), yearsDriving};
	}
}