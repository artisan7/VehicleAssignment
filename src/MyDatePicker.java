import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Calendar;

public class MyDatePicker extends JComponent {
	private JComboBox cb_month, cb_date, cb_year;
	
	public MyDatePicker() {
		cb_month = new JComboBox<String>(new String[] {"January", "February", "March", "April",
													   "May", "June", "July", "August",
													   "September", "October", "November", "December"});
		cb_month.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cb_date.removeAllItems();
				int days = 31;
				switch (cb_month.getSelectedIndex()) {
				case 1: days--;
				case 3:
				case 5:
				case 8:
				case 10: days--;
				}
				for (int i = 1; i <= days; i++)
					cb_date.addItem(i);				
			}
		});
		
		cb_date = new JComboBox();
		for (int i = 1; i <= 31; i++)
			cb_date.addItem(i);
		
		cb_year = new JComboBox();
		for (int i = 1970; i < 2020; i++)
			cb_year.addItem(i);
		
		setLayout(new GridLayout(1, 3));
		add(cb_month);
		add(cb_date);
		add(cb_year);
	}
	
	// GETTERS
	public Calendar getSelectedDate() {
		Calendar d = Calendar.getInstance();
		d.set(cb_year.getSelectedIndex()+1970, cb_month.getSelectedIndex(), cb_date.getSelectedIndex()+1);
		return d;
	}
	
	public String getDateFormatted() {
		return String.format("%d/%d/%d", cb_month.getSelectedIndex()+1, cb_date.getSelectedIndex()+1, cb_year.getSelectedIndex()+1970);
	}
	
	public void reset() {
		cb_month.setSelectedIndex(0);
		cb_date.setSelectedIndex(0);
		cb_year.setSelectedIndex(0);
	}
}