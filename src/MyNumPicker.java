import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class MyNumPicker extends JComponent implements ActionListener {
	private int val = 0;
	private JTextField tf_num;
	
	public MyNumPicker() {
		this(0);
	}
	
	public MyNumPicker(int startVal) {
		JButton btn_dec = new JButton("-");
		JButton btn_inc = new JButton("+");
		btn_dec.addActionListener(this);
		btn_inc.addActionListener(this);
		
		val = startVal;
		tf_num = new JTextField(Integer.toString(val));
		tf_num.setEditable(false);
		tf_num.setHorizontalAlignment(JTextField.CENTER);
		
		setLayout(new GridLayout(1, 3));
		add(btn_dec);
		add(tf_num);
		add(btn_inc);
	}
	
	public void actionPerformed(ActionEvent e) {
		String str_btn =((JButton) e.getSource()).getText();
		
		if (str_btn == "+")
			val++;
		else if (val > 0)
			val--;
		
		tf_num.setText(Integer.toString(val));
	}
	
	public int getValue() { return val; }
	
	public void setValue(int v) {
		val = v;
		tf_num.setText(Integer.toString(val));
	}
}