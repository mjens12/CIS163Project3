package package1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;

/**********************************************************************
 * RentalStore class that extends AbstractListModel, manages the list of
 * rented items
 * 
 * @author Max Jensen and Monica Klosin
 * @version 1.0
 *********************************************************************/
public class RentalStore extends AbstractListModel {

	/** ArrayList of DVDs that holds items in the store */
	private ArrayList<DVD> listDVDs;

	/******************************************************************
	 * Default constructor that calls the AbstractListModel constructor
	 * and creates the arraylist of DVDs
	 *****************************************************************/
	public RentalStore() {
		super();
		listDVDs = new ArrayList<DVD>();
	}

	/******************************************************************
	 * Method for adding a DVD to the arraylist, also triggers a GUI
	 * update to show new list contents
	 * 
	 * @param a
	 *            DVD to be added
	 *****************************************************************/
	public void add(DVD a) {
		listDVDs.add(a);
		fireIntervalAdded(this, 0, listDVDs.size());
	}

	/******************************************************************
	 * Method for removing a DVD from the arraylist, also triggers a GUI
	 * update to show new list contents
	 * 
	 * @param a
	 *            DVD to be added
	 *****************************************************************/
	public void remove(int a) {
		listDVDs.remove(a);
		fireIntervalAdded(this, 0, listDVDs.size());
	}

	public DVD get(int i) {
		return listDVDs.get(i);
	}

	public Object getElementAt(int arg0) {

		DVD unit = listDVDs.get(arg0);

		try {
			String rentedOnDateStr = DateFormat
					.getDateInstance(DateFormat.SHORT)
					.format(unit.getBought().getTime());

			String dueBackOnDateStr = DateFormat
					.getDateInstance(DateFormat.SHORT)
					.format(unit.getDueBack().getTime());

			String line = "Name: "
					+ listDVDs.get(arg0).getNameOfRenter() + " ";

			line += "Title: " + unit.getTitle() + ", ";
			line += "Rented On: " + rentedOnDateStr + ", ";
			line += "Due Back: " + dueBackOnDateStr;

			if (unit instanceof Game)
				line += ", Player: " + ((Game) unit).getPlayer();

			return line;
		} catch (Exception ex) {
			return null;
		}
	}

	public int getSize() {
		// return 5;
		return listDVDs.size();
	}

	public void saveAsSerializable(String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(listDVDs);
			os.close();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Error in saving db");

		}
	}

	public void loadFromSerializable(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream is = new ObjectInputStream(fis);

			listDVDs = (ArrayList<DVD>) is.readObject();
			fireIntervalAdded(this, 0, listDVDs.size() - 1);
			is.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error in loading db");
		}
	}

	public String getLate(GregorianCalendar lateDate) {
		String lateThings = "";
		for (int i = 0; i < listDVDs.size(); i++) {
			if (listDVDs.get(i).getDueBack().compareTo(lateDate) <= 0) {
				try {
					DVD unit = listDVDs.get(i);
					String rentedOnDateStr = DateFormat
							.getDateInstance(DateFormat.SHORT)
							.format(unit.getBought().getTime());

					String dueBackOnDateStr = DateFormat
							.getDateInstance(DateFormat.SHORT)
							.format(unit.getDueBack().getTime());

					String line = "Name: "
							+ listDVDs.get(i).getNameOfRenter() + " ";

					line += "Title: " + unit.getTitle() + ", ";
					line += "Rented On: " + rentedOnDateStr + ", ";
					line += "Due Back: " + dueBackOnDateStr;

					if (unit instanceof Game)
						line += ", Player: "
								+ ((Game) unit).getPlayer();

					line += (", "
							+ daysBetween(lateDate, unit.getDueBack())
							+ " days late");
					lateThings += (line + "\n");

				} catch (Exception ex) {

				}
			}
		}
		return lateThings;
	}

	private int daysBetween(GregorianCalendar d1,
			GregorianCalendar d2) {
		return (int) ((d1.getTimeInMillis() - d2.getTimeInMillis())
				/ (1000 * 60 * 60 * 24));
	}
}
