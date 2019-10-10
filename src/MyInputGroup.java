import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//CREATES A CUSTOM PANEL WHERE A COMPONENT HAS A LABEL AND ERROR CHECKING
// USED MAINLY ON COMPONENTS IN FORM WINDOWS
public class MyInputGroup extends JComponent implements FocusListener {
	protected JLabel lbl_prompt;
	protected Component comp_main;
	protected boolean autocheck;
	
	protected Color c_input;
	protected Color c_error;
	protected Font fnt_input;
	protected Font fnt_error;
	
	// CONSTRUCTOR
	public MyInputGroup(Component component, String prompt, boolean autocheckValidity) {
		// default error font style and color
		c_error = Color.RED;
		fnt_error = new Font("Sans Serif", Font.ITALIC, 12);
		
		// panel components
		lbl_prompt = new JLabel(prompt);
		comp_main = component;
		comp_main.addFocusListener(this);
		autocheck = autocheckValidity;
		
		// panel components layout
		setLayout(new BorderLayout());
		add(lbl_prompt, BorderLayout.NORTH);
		add(component, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	// GETTERS
	public JLabel getPromptLabel() { return lbl_prompt; }
	public Component getMainComponent() { return comp_main; }
	
	// SETTERS
	public void setInputColor(Color c) { c_input = c; }
	public void setInputFont(Font f) { fnt_input = f; }
	
	// RESET -> resets the fields of the main component
	public void reset() {
		if (comp_main instanceof JTextField)
			((JTextField) comp_main).setText(null);
		else if (comp_main instanceof JComboBox)
			((JComboBox) comp_main).setSelectedIndex(0);
		else if (comp_main instanceof MyNumPicker)
			((MyNumPicker) comp_main).reset();
		else if (comp_main instanceof MyDatePicker)
			((MyDatePicker) comp_main).reset();
	}
	
	// GETVALUE -> returns the value of the main component
	public Object getValue() {
		if (comp_main instanceof JTextField)
			return ((JTextField) comp_main).getText();
		else if (comp_main instanceof JComboBox)
			return ((JComboBox) comp_main).getSelectedIndex();
		else if (comp_main instanceof MyNumPicker)
			return ((MyNumPicker) comp_main).getValue();
		else if (comp_main instanceof MyDatePicker)
			return ((MyDatePicker) comp_main).getSelectedDate();
		
		return null;
	}
	
	// ISINPUTVALID -> changes state between error and normal aka changes font style and color
	public void changeState(int state) {
		
		if (state == 0) {	// normal state
			comp_main.setForeground(c_input);
			comp_main.setFont(fnt_input);
		}
		else if (state == 1) {	// error state
			comp_main.setForeground(c_error);
			comp_main.setFont(fnt_error);
		}
	}
	
	// ISINPUTVALID -> checks whether an input is valid or not
	public boolean isValidInput() {
		if (comp_main instanceof JTextField) {
			JTextField tf = (JTextField) comp_main;
			
			if (tf.getText().trim().length() == 0 || tf.getForeground() == c_error) {
				changeState(1);
				tf.setText("Input Field cannot be empty.");
				return false;
			}
		}
		else if (comp_main instanceof JComboBox) {
			JComboBox cb = (JComboBox) comp_main;
			
			if (cb.getSelectedItem() == null || ((String) cb.getSelectedItem()).trim().length() == 0) {
				changeState(1);
				cb.insertItemAt("Input Field cannot be empty.", 0);
				cb.setSelectedIndex(0);

				return false;
			}
			else if (cb.getForeground() == c_error) { return false; }
		}
		
		return true;
	}

	// FOCUS LISTENER -> enables automatic input validation
	public void focusGained(FocusEvent e) {
		if (comp_main.getForeground() == c_error) {
			changeState(0);
			
			if (comp_main instanceof JTextField)
				((JTextField) comp_main).setText(null);
			else if (comp_main instanceof JComboBox)
				((JComboBox) comp_main).removeItemAt(0);
		}
	}
	
	public void focusLost(FocusEvent e) {
		if (autocheck)
			isValidInput();
	}
	
}
