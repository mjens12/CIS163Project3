package package1;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class RentDVDDialog extends JDialog implements ActionListener {

	private JTextField titleTxt;
	private JTextField renterTxt;
	private JTextField rentedOnTxt;
	private JTextField dueBackTxt;

	private JButton okButton;
	private JButton cancelButton;
	private boolean closeStatus;

	private DVD unit;

	public RentDVDDialog(JFrame parent, DVD d) {
		// call parent and create a 'modal' dialog
		super(parent, true);

		setTitle("Rent a DVD:");
		closeStatus = false;
		setSize(400, 200);

		unit = d;
		// prevent user from closing window
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		// instantiate and display text fields

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(6, 2));

		textPanel.add(new JLabel("Your Name:"));
		renterTxt = new JTextField("John Doe", 30);
		textPanel.add(renterTxt);

		textPanel.add(new JLabel("Title of DVD:"));
		titleTxt = new JTextField("Avengers", 30);
		textPanel.add(titleTxt);

		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		textPanel.add(new JLabel("Rented on Date: "));
		rentedOnTxt = new JTextField(df.format(date), 30);
		textPanel.add(rentedOnTxt);

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1); // number of days to add
		date = c.getTime();

		textPanel.add(new JLabel("Due Back: "));
		dueBackTxt = new JTextField(df.format(date), 15);
		textPanel.add(dueBackTxt);

		getContentPane().add(textPanel, BorderLayout.CENTER);

		// Instantiate and display two buttons
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		setSize(300, 300);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		JButton button = (JButton) e.getSource();

		// if OK clicked the fill the object
		if (button == okButton) {
			// save the information in the object
			closeStatus = true;

			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

			GregorianCalendar cal1 = new GregorianCalendar();
			GregorianCalendar cal2 = new GregorianCalendar();

			try {
				cal1.setTime(format.parse(rentedOnTxt.getText()));
				cal2.setTime(format.parse(dueBackTxt.getText()));
				if (cal1.compareTo(cal2) <= 0) {
					unit.setNameOfRenter(renterTxt.getText());
					unit.setTitle(titleTxt.getText());
					unit.setBought(cal1);
					unit.setDueBack(cal2);
					dispose();
				} else
					JOptionPane.showMessageDialog(null,
							"Please enter a due date that is later than the rented on date");
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(null, "Please enter valid rented on and due back dates");
			}
		}
		if (button == cancelButton) {
			// make the dialog disappear
			closeStatus = true;
			dispose();
		}
	}

	public boolean closeOK() {
		return closeStatus;
	}
}
