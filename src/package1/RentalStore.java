package package1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;

public class RentalStore extends AbstractListModel {

	private ArrayList<DVD> listDVDs;

	private boolean filter;

	public RentalStore() {
		super();
		filter = false;
		listDVDs = new ArrayList<DVD>();
	}

	public void add(DVD a) {
		listDVDs.add(a);
		fireIntervalAdded(this, 0, listDVDs.size());
	}

	public DVD get(int i) {
		return listDVDs.get(i);
	}

	public Object getElementAt(int arg0) {

		// return "Happy";

		DVD unit = listDVDs.get(arg0);

		String rentedOnDateStr = DateFormat
				.getDateInstance(DateFormat.SHORT)
				.format(unit.getBought().getTime());

		String dueBackOnDateStr = DateFormat
				.getDateInstance(DateFormat.SHORT)
				.format(unit.getDueBack().getTime());

		String line = "Name: " + listDVDs.get(arg0).getNameOfRenter()
				+ " ";

		line += "Title: " + unit.getTitle() + ", ";
		line += "Rented On: " + rentedOnDateStr + ", ";
		line += "Due Back: " + dueBackOnDateStr;

		if (unit instanceof Game)
			line += ", Player: " + ((Game) unit).getPlayer();

		return line;
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
}
