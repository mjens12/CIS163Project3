package package1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class RentalStoreGUI extends JFrame implements ActionListener {

	/**
	 * Holds menu bar
	 */
	private JMenuBar menus;

	/**
	 * menus in the menu bar
	 */
	private JMenu fileMenu;
	private JMenu actionMenu;

	/**
	 * menu items in each of the menus
	 */
	private JMenuItem openSerItem;
	private JMenuItem exitItem;
	private JMenuItem saveSerItem;
	private JMenuItem openTextItem;
	private JMenuItem saveTextItem;
	private JMenuItem rentDVD;
	private JMenuItem rentGame;
	private JMenuItem returnItem;
	private JMenuItem lateItem;

	/**
	 * Holds the list engine
	 */
	private RentalStore list;

	/**
	 * Holds JListArea
	 */
	private JList JListArea;

	/** Scroll pane */
	// private JScrollPane scrollList;

	public RentalStoreGUI() {

		// adding menu bar and menu items
		menus = new JMenuBar();
		fileMenu = new JMenu("File");
		actionMenu = new JMenu("Action");
		openSerItem = new JMenuItem("Open File");
		exitItem = new JMenuItem("Exit");
		saveSerItem = new JMenuItem("Save File");
		openTextItem = new JMenuItem("Open Text");
		saveTextItem = new JMenuItem("Save Text");
		rentDVD = new JMenuItem("Rent DVD");
		rentGame = new JMenuItem("Rent Game");
		returnItem = new JMenuItem("Return");
		lateItem = new JMenuItem("Check Days Late");

		// adding items to bar
		fileMenu.add(openSerItem);
		fileMenu.add(saveSerItem);
		fileMenu.add(exitItem);
		actionMenu.add(rentDVD);
		actionMenu.add(rentGame);
		actionMenu.add(returnItem);
		actionMenu.add(lateItem);

		menus.add(fileMenu);
		menus.add(actionMenu);

		// adding actionListener
		openSerItem.addActionListener(this);
		saveSerItem.addActionListener(this);
		exitItem.addActionListener(this);
		rentDVD.addActionListener(this);
		rentGame.addActionListener(this);
		returnItem.addActionListener(this);
		lateItem.addActionListener(this);

		setJMenuBar(menus);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// adding list to the GUI1024
		list = new RentalStore();
		JListArea = new JList(list);
		add(JListArea);
		// JListArea.setVisible(true);

		setVisible(true);
		setSize(600, 500);
		// setSize(new Dimension (550,400));
		// setMinimumSize(new Dimension(550,400));
		// setMaximumSize(new Dimension(550,400));

	}

	public void actionPerformed(ActionEvent e) {

		Object comp = e.getSource();

		if (openSerItem == comp || openTextItem == comp) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile()
						.getAbsolutePath();
				if (openSerItem == comp)
					list.loadFromSerializable(filename);
			}
		}

		if (saveSerItem == comp || saveTextItem == comp) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showSaveDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile()
						.getAbsolutePath();
				if (saveSerItem == e.getSource())
					list.saveAsSerializable(filename);
			}
		}

		// MenuBar options
		if (e.getSource() == exitItem) {
			System.exit(1);
		}
		if (e.getSource() == rentDVD) {
			DVD dvd = new DVD();
			RentDVDDialog dialog = new RentDVDDialog(this, dvd);
			list.add(dvd);
		}

		if (e.getSource() == rentGame) {
			Game game = new Game();
			RentGameDialog dialog = new RentGameDialog(this, game);
			list.add(game);
		}

		// TODO set leniencey to non-lenient for gregorian calendar
		if (returnItem == e.getSource()) {

			int index = JListArea.getSelectedIndex();
			if (index < 0) {
				JOptionPane.showMessageDialog(null,
						"Please select the DVD or game you are returning");
			} else {
				GregorianCalendar date = new GregorianCalendar();
				String inputDate = JOptionPane
						.showInputDialog("Enter return date: ");
				DVD unit = list.get(index);
				if (inputDate != null) {
					SimpleDateFormat df = new SimpleDateFormat(
							"MM/dd/yyyy");

					try {
						Date newDate = df.parse(inputDate);
						date.setTime(newDate);
						if (date.compareTo(
								list.get(index).getBought()) < 0) {
							JOptionPane.showMessageDialog(null,
									"You can not return an item before it was checked out! Please try again");
						} else {
							JOptionPane.showMessageDialog(null, ""
									+ unit.getNameOfRenter()
									+ ", thank you for returning "
									+ unit.getTitle() + ". You owe: "
									+ unit.getCost(date) + " dollars");
							list.remove(index);
						}
					} catch (ParseException pe) {
						JOptionPane.showMessageDialog(null,
								"Could not parse input date! Please try again");
					}

				}
			}

		}
		if (e.getSource() == lateItem) {
			GregorianCalendar date = new GregorianCalendar();
			String inputDate = JOptionPane.showInputDialog(
					"Please enter the date you are interested in: ");
			if (inputDate != null) {
				SimpleDateFormat df = new SimpleDateFormat(
						"MM/dd/yyyy");
				try {
					Date newDate = df.parse(inputDate);
					date.setTime(newDate);
					if (list.getLate(date).equals(""))
						JOptionPane.showMessageDialog(null,
								"No rented items are late as of "
										+ DateFormat.getDateInstance(
												DateFormat.SHORT)
												.format(newDate));
					else
						JOptionPane.showMessageDialog(null,
								"Below are the items that will be late as of "
										+ DateFormat.getDateInstance(
												DateFormat.SHORT)
												.format(newDate)
										+ ": \n" + list.getLate(date));
				} catch (ParseException pe) {
					JOptionPane.showMessageDialog(null,
							"Could not parse input date! Please try again");
				}
			}

		}

	}

	public static void main(String[] args) {
		new RentalStoreGUI();
	}

}
