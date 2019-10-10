import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Calendar;

public class MyDatePicker extends JComponent implements ActionListener {
	private JComboBox<String> cb_month;
	private JComboBox<Integer> cb_date, cb_year;
	
	public MyDatePicker() {
		cb_month = new JComboBox<String>(new String[] {"January", "February", "March", "April",
													   "May", "June", "July", "August",
													   "September", "October", "November", "December"});
		cb_month.addActionListener(this);
		
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
	
	public JComboBox getMonthComboBox() { return cb_month; }
	public JComboBox getDateComboBox() { return cb_date; }
	public JComboBox getYearComboBox() { return cb_year; }
	
	// RESET ENTRIES
	public void reset() {
		cb_month.setSelectedIndex(0);
		cb_date.setSelectedIndex(0);
		cb_year.setSelectedIndex(0);
	}
	
	// ACTION LISTENER -> adjusts number of days based on month selected
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
}