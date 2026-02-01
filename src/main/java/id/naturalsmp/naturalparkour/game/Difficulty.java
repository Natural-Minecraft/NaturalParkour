package id.naturalsmp.naturalparkour.game;

/**
 * The difficulty of a {@link id.naturalsmp.naturalparkour.game.PkArea PkArea}.
 * @author NaturalDev
 *
 */
public enum Difficulty {
	EASY, MEDIUM, HARD, EXPERT, BALANCED;

	private int min = 1;
	private int max = 1;

	public int getMin() {
		return min;
	}
	public int getMax() {
		return max;
	}
	public void setMin(int m) {
		min = m;
	}
	public void setMax(int m) {
		max = m;
	}
}
