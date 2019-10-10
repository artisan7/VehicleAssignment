import java.util.Calendar;
import java.util.Locale;

// THIS CLASS IS RESPONSIBLE FOR CONVERTING A CALENDAR OBJECT INTO FORMATTED STRINGS
public abstract class DTFormatter {
	// Date-> MM/DD/YYYY
	public static String slashDate(Calendar d) {
		return String.format("%d/%d/%d", d.get(Calendar.MONTH)+1, d.get(Calendar.DAY_OF_MONTH), d.get(Calendar.YEAR));
	}
	
	// Date-> MMM-DD-YYYY
	public static String dashDate(Calendar d) {
		String month = d.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
		int day = d.get(Calendar.DAY_OF_MONTH);
		int year = d.get(Calendar.YEAR);
		
		return String.format("%s-%d-%d", month, day, year);
	}
	
	// Date-> MMMM DD, YYYY
	public static String longDate(Calendar d) {
		String month = d.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		int day = d.get(Calendar.DAY_OF_MONTH);
		int year = d.get(Calendar.YEAR);
		
		return String.format("%s %d, %d", month, day, year);
	}
	
	// Time-> HH:MM:SS AM/PM
	public static String nonMilitaryTime(Calendar t) {
		int hour = t.get(Calendar.HOUR);
		if (hour == 0)
			hour = 12;
		int minute = t.get(Calendar.MINUTE);
		int second = t.get(Calendar.SECOND);
		String meridiem = t.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.ENGLISH);
		
		return String.format("%02d:%02d:%02d %s", hour, minute, second, meridiem);
	}
	
	// Time-> HH:MM:SS
	public static String militaryTime(Calendar t) {
		int hour = t.get(Calendar.HOUR_OF_DAY);
		int minute = t.get(Calendar.MINUTE);
		int second = t.get(Calendar.SECOND);
		
		return String.format("%02d:%02d:%02d", hour, minute, second);
	}
}
