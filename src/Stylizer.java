import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

// THIS CLASS IS RESPONSIBLE FOR THE STYLES AND COLOURS OF THE APP
public abstract class Stylizer {
	private static Color bg_frame;
	private static Color fg_button;
	private static Color fg_default;
	private static Color fg_input;
	
	private static Font fnt_button;
	private static Font fnt_default;
	private static Font fnt_input;
	
	// FUNCTIONS FOR JFRAME
	public static void setBackground(Color c) { bg_frame = c; }
	
	public static void applyTo(JFrame f) {
		f.getContentPane().setBackground(bg_frame);
		
		for (Component c : f.getContentPane().getComponents())
			applyTo((JPanel) c);
	}
	
	// FUNCTION FOR JPANEL
	public static void applyTo(JPanel p) {
		p.setBorder(new EmptyBorder(10, 10, 10, 10));
		p.setBackground(bg_frame);
		
		for (Component c : p.getComponents()) {
			if (c instanceof JPanel)
				applyTo((JPanel) c);
			else if (c instanceof JLabel)
				applyTo((JLabel) c);
			else if (c instanceof JTextField)
				applyTo((JTextField) c);
			else if (c instanceof MyInputGroup)
				applyTo((MyInputGroup) c);
		}
	}
	
	// FUNCTIONS FOR JDIALOG
	public static void applyTo(JDialog d) {
		d.getContentPane().setBackground(bg_frame);
		
		for (Component c : d.getContentPane().getComponents())
			applyTo((JPanel) c);
	}
	
	// FUNCTIONS FOR JBUTTON
	public static void setButtonForeground(Color c) { fg_button = c; }
	public static void setButtonFont(Font f) { fnt_button = f; }
	
	public static void applyTo(JButton btn) { applyTo(btn, null, fg_button, null); }
	public static void applyTo(JButton btn, Color bgcolor) { applyTo(btn, bgcolor, fg_button, null); }
	public static void applyTo(JButton btn, Color bgcolor, Color fgcolor) { applyTo(btn, bgcolor, fgcolor, null); }
	
	public static void applyTo(JButton btn, Color fgcolor, Font f) {
		applyTo(btn, null, fgcolor, f);
		
		btn.removeMouseListener(btn.getMouseListeners()[1]);
		// on mouse hover events
		btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btn.setForeground(btn.getForeground().darker());
			}
			
			public void mouseExited(MouseEvent e) {
				btn.setForeground(btn.getForeground().brighter());
			}
		});
	}
	
	public static void applyTo(JButton btn, Color bgcolor, Color fgcolor, Font f) {
		if (bgcolor == null)
			btn.setContentAreaFilled(false);
		else btn.setBackground((bgcolor));
		
		btn.setForeground(fgcolor);
		btn.setBorderPainted(false);
		btn.setFont((f == null) ? fnt_button : f);
		
		// on mouse hover events
		btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btn.setBackground(btn.getBackground().darker());
			}
			
			public void mouseExited(MouseEvent e) {
				btn.setBackground(btn.getBackground().brighter());
			}
		});
	}
	
	// FUNCTIONS FOR JLABEL
	public static void setDefaultForeground(Color c) { fg_default = c; }
	public static void setDefaultFont(Font f) { fnt_default = f; }
	
	public static void applyTo(JLabel l) {
		l.setHorizontalAlignment(JLabel.CENTER);
		l.setVerticalAlignment(JLabel.CENTER);
		l.setFont(fnt_default);
		l.setForeground(fg_default);
	}

	// FUNCTIONS FOR JTEXTFIELD
	public static void setInputForeground(Color c) { fg_input = c; }
	public static void setInputFont(Font f) { fnt_input = f; }
	
	public static void applyTo(JTextField tf) {
		tf.setOpaque(false);
		tf.setCaretColor(Color.LIGHT_GRAY);
		tf.setBorder(new MatteBorder(0, 0, 1, 0, fg_input));
		tf.setPreferredSize(new Dimension(250, 20));
		tf.setForeground(fg_input);
		tf.setFont(fnt_input);
	}
	
	// FUNCTIONS FOR JCOMBOBOX
	public static void applyTo(JComboBox cb) {
		cb.setFont(fnt_input);
	}
	
	// FUNCTIONS FOR MYNUMPICKER
	public static void applyTo(MyNumPicker np) {
		np.setFont(fnt_input);
		
		applyTo(np.getAddButton(), new Color(46, 213, 115), fnt_default.deriveFont(30.0f));
		applyTo(np.getSubtractButton(), new Color(255, 71, 87), fnt_default.deriveFont(30.0f));
	}
	
	// FUNCTIONS FOR MYDATEPICKER
	public static void applyTo(MyDatePicker dp) {
		dp.setFont(fnt_input);
		
		applyTo(dp.getMonthComboBox());
		applyTo(dp.getDateComboBox());
		applyTo(dp.getYearComboBox());
	}
	
	// FUNCTIONS FOR MYINPUTGROUP
	public static void applyTo(MyInputGroup ig) {
		ig.getPromptLabel().setForeground(fg_default);
		ig.getPromptLabel().setFont(fnt_default);
		
		if (ig.getMainComponent() instanceof JTextField) {
			ig.setInputColor(fg_input);
			ig.setInputFont(fnt_input);
		}
		
		Component c = ig.getMainComponent();
		if (c instanceof JTextField)
			applyTo((JTextField) c);
		else if (c instanceof JComboBox)
			applyTo((JComboBox) c);
		else if (c instanceof MyNumPicker)
			applyTo((MyNumPicker) c);
		else if (c instanceof MyDatePicker)
			applyTo((MyDatePicker) c);
	}
}
