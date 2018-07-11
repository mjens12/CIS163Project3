package package1;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class DVD implements Serializable {

	private static final long serialVersionUID = 1L;
	protected GregorianCalendar bought;
	protected GregorianCalendar dueBack;
	protected String title;
	protected String nameOfRenter;

	public DVD() {
	}

	public DVD(GregorianCalendar bought, GregorianCalendar dueBack,
			String title, String name) {
		super();
		this.bought = bought;
		this.dueBack = dueBack;
		this.title = title;
		this.nameOfRenter = name;
	}

	public GregorianCalendar getBought() {
		return bought;
	}

	public void setBought(GregorianCalendar bought) {
		this.bought = bought;
	}

	public GregorianCalendar getDueBack() {
		return dueBack;
	}

	public void setDueBack(GregorianCalendar dueBack) {
		this.dueBack = dueBack;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNameOfRenter() {
		return nameOfRenter;
	}

	public void setNameOfRenter(String nameOfRenter) {
		this.nameOfRenter = nameOfRenter;
	}

	public double getCost(GregorianCalendar date) {
		if (date.compareTo(dueBack) <= 0)
			return 1.20;
		else
			return 3.20;
	}
}
