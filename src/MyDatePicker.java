import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Calendar;

public class MyDatePicker extends JComponent {
	private static final long serialVersionUID = 4163830363284674679L;
	
	private JComboBox<String> cb_month;
	private JComboBox<Integer> cb_date, cb_year;
	
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
		
		cb_date = new JComboBox<Integer>();
		for (int i = 1; i <= 31; i++)
			cb_date.addItem(i);
		
		cb_year = new JComboBox<Integer>();
		for (int i = 2019; i >= 1970; i--)
			cb_year.addItem(i);
		
		setLayout(new GridLayout(1, 3));
		add(cb_month);
		add(cb_date);
		add(cb_year);
	}
	
	// GETTERS
	public Calendar getSelectedDate() {
		Calendar d = Calendar.getInstance();
		d.set((int) cb_year.getSelectedItem(), cb_month.getSelectedIndex(), cb_date.getSelectedIndex()+1);
		return d;
	}
	
	public String getDateFormatted() {
		Calendar d = getSelectedDate();
		return String.format("%d/%d/%d", d.get(Calendar.MONTH), d.get(Calendar.DAY_OF_MONTH), d.get(Calendar.YEAR));
	}
	
	// RESET ENTRIES
	public void reset() {
		cb_month.setSelectedIndex(0);
		cb_date.setSelectedIndex(0);
		cb_year.setSelectedIndex(0);
	}
}