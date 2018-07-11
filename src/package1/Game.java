package package1;

import java.util.GregorianCalendar;

public class Game extends DVD {
	private PlayerType player;

	public Game() {
	}

	public PlayerType getPlayer() {
		return player;
	}

	public void setPlayer(PlayerType player) {
		this.player = player;
	}

	public double getCost(GregorianCalendar date) {
		if (date.compareTo(dueBack) <= 0)
			return 5.0;
		else
			return 15.0;
	}
}
