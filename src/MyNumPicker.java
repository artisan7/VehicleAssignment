import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyNumPicker extends JComponent implements ActionListener {
	protected JTextField tf_num;
	protected JButton btn_dec, btn_inc;
	
	protected int val;
	protected int defaultVal;
	protected int min;
	protected int max;
	
	// CONSTRUCTORS
	public MyNumPicker() { this(0, 0, Integer.MAX_VALUE); }
	public MyNumPicker(int startVal, int mn, int mx) {
		val = defaultVal = startVal;
		min = mn;
		max = mx;
		
		btn_dec = new JButton("-");
		btn_inc = new JButton("+");
		btn_dec.addActionListener(this);
		btn_inc.addActionListener(this);
		
		tf_num = new JTextField(Integer.toString(val));
		tf_num.setEditable(false);
		tf_num.setHorizontalAlignment(JTextField.CENTER);
		
		setLayout(new GridLayout(1, 3));
		add(btn_dec);
		add(tf_num);
		add(btn_inc);
	}
	
	// ACTION LISTENER -> changes value when buttons are clicked
	public void actionPerformed(ActionEvent e) {
		JButton src =(JButton) e.getSource();
		
		if (src == btn_inc && val < max)
			val++;
		else if (src == btn_dec && val > min)
			val--;
		
		tf_num.setText(Integer.toString(val));
	}
	
	// GETTERS
	public JButton getAddButton() { return btn_inc; }
	public JButton getSubtractButton() { return btn_dec; }
	public int getValue() { return val; }
	
	// SETTERS
	public void reset() { setValue(defaultVal); }
	public void setValue(int v) {
		val = v;
		tf_num.setText(Integer.toString(val));
	}
}