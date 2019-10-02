import java.util.Date;

public class Assignment {
	private Driver driver;
	private Vehicle vehicle;
	private String notes;
	private Date assignmentDate;
	
	//public Assignment()
	public Assignment(Driver d, Vehicle v, String n, Date adate) {
		driver = d;
		vehicle = v;
		notes = n;
		assignmentDate = adate;
	}
}
