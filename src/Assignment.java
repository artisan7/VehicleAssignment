import java.util.Calendar;

public class Assignment {
	private Driver driver;
	private Vehicle vehicle;
	private String notes;
	private Calendar assignmentDate;
	
	//public Assignment()
	public Assignment(Driver d, Vehicle v, String n, Calendar adate) {
		driver = d;
		vehicle = v;
		notes = n;
		assignmentDate = adate;
	}
}
