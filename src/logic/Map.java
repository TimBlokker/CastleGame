package logic;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

/*
 * This class creates the map based on the chosen difficulty. 
 * @author Ajay Sundaresan
 * @author Tim Blokker
 */
public class Map {
	/*
	 * This variable is used to set the default difficulty, this is e.g. used when
	 * the user supplies incorrect input. The default difficulty is easy to change
	 * using this constant.
	 */
	private static final int DEFAULT_DIFFICULTY = 11;
	/*
	 * The maximum sum of energy and skill that can be chosen at the start of the
	 * game. This makes it easy to adapt the maximum Energy/Skill that can be chosen
	 * in the beginning of the game.
	 */
	private static final int ALLOWED_SKILL_ENERGY = 20;
	/*
	 * The amount of Keys that need to be collected. This makes it easy to change
	 * the amount of keys required to win.
	 */
	private static final int NUMBER_OF_REQUIRED_KEYS = 3;
	/*
	 * The maximum difficulty that can be selected, easily changeably by this
	 * variable.
	 */
	private static final int Max_Difficulty = 20;
	/*
	 * The Scanner used for the text-based version to register user input.
	 */
	static Scanner scan = new Scanner(System.in);
	/*
	 * The difficulty of the game, influencing the size of the map.
	 */
	private int difficulty;
	/*
	 * The number of rows in the map.
	 */
	private int NumberOfRows;
	/*
	 * The number of column in the map.
	 */
	private int NumberOfColumns;
	/*
	 * The 2D array in which the map is stored. The complete map with all special
	 * tiles, without taking visibility into account.
	 */
	private int[][] mapWithTiles;
	/*
	 * Generating a random number.
	 */
	public static Random random = new Random();

	/*
	 * Create a map with special tiles, the amount and the location of the special
	 * are chosen by random. Special tiles are tiles with a number unequal to 0. The
	 * loop progresses one row per iteration and 0-5 columns (chosen by random). The
	 * row(r) and column(c) coordinates are assigned a new number randomly Lastly,
	 * the number linked to the keys (6) is assigned to exactly 3 tiles and the
	 * number linked to the castle (7) is assigned just to one tile.
	 */
	public int[][] createMap() {
		this.mapWithTiles = new int[this.getNumberOfRows()][this.getNumberOfColumns()];
		for (int r = 0; r < this.getNumberOfRows(); r++) {
			for (int c = 0; c < this.getNumberOfColumns(); c = c + random.nextInt(6)) {
				this.mapWithTiles[r][c] = random.nextInt(6);
			}
		}
		// ensure there are exactly three keys on the map
		int i = 0;
		while (i < NUMBER_OF_REQUIRED_KEYS) {
			int r = random.nextInt(this.getNumberOfRows());
			int c = random.nextInt(this.getNumberOfColumns());
			if (this.mapWithTiles[r][c] != 6) {
				this.mapWithTiles[r][c] = 6;
				i++;
			}
		}
		// ensure there is always exactly one castle on the map and the castle tile does
		// not overwrite a key tile
		int l = 0;
		while (l < 1) {
			int r = random.nextInt(this.getNumberOfRows());
			int c = random.nextInt(this.getNumberOfColumns());
			if (this.mapWithTiles[r][c] != 6) {
				this.mapWithTiles[r][c] = 7;
				l++;
			}
		}
		return this.mapWithTiles;
	}

	/*
	 * Returns the mapWithTiles.
	 */
	public int[][] getMapWithTiles() {
		return this.mapWithTiles;
	}

	/*
	 * Set the difficulty depending on user input in the text-based version. Errors
	 * are handled in the method only values between 2 and the allowed maximum
	 * energy are legal if a illegal number or character is entered the default
	 * difficulty is automatically assigned to the game. Two needs to be the minimum
	 * difficulty since 4 tiles are minimally required to accommodate 3 keys and 1
	 * castle tile.
	 */
	public void chooseDifficulty() {
		try {
			System.out.println("Select the difficulty as a value between 2 and " + getMaxDifficulty() + ".");
			int i = scan.nextInt();
			if (i <= this.getMaxDifficulty() & i >= 2) {
				this.difficulty = i;
			} else {
				throw new InputMismatchException();
			}
		} catch (InputMismatchException ime) {
			System.out.println("Only values between 2 and " + getMaxDifficulty() + " are allowed. The "
					+ "default was assumed and the difficulty is now set to: " + getDefaultDifficulty() + ".");
			this.difficulty = this.getDefaultDifficulty();
		}
	}

	/*
	 * The difficulty chosen by the user in the GUI version is set. Errors are
	 * handled in the method only values between 2 and the allowed maximum energy
	 * are legal if a illegal number or character is entered the default difficulty
	 * is automatically assigned to the game. Two needs to be the minimum difficulty
	 * since 4 tiles are minimally required to accommodate 3 keys and 1 castle tile.
	 */
	public void chooseDifficulty(int i) {
		try {
			if (i <= this.getMaxDifficulty() & i >= 2) {
				this.difficulty = i;
			} else {
				throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException ime) {
			JOptionPane.showMessageDialog(null, "Only values between 2 and " + getMaxDifficulty() + " are allowed. The "
					+ "default was assumed and the difficulty is now set to: " + getDefaultDifficulty() + ".");
			this.difficulty = this.getDefaultDifficulty();
		}
	}

	/*
	 * Returns the default difficulty.
	 */
	public int getDefaultDifficulty() {
		return DEFAULT_DIFFICULTY;
	}

	/*
	 * Returns the difficulty of the current game.
	 */
	public int getDifficulty() {
		return this.difficulty;
	}

	/*
	 * Set the number of rows in the map, which is equal to the level of difficulty
	 * chosen by the user.
	 */
	public void setNumberOfRows() {
		this.NumberOfRows = this.getDifficulty();
	}

	/*
	 * Returns the number of rows
	 */
	public int getNumberOfRows() {
		return this.NumberOfRows;
	}

	/*
	 * Sets the number of columns equal to the difficulty.
	 */
	public void setNumberOfColumns() {
		this.NumberOfColumns = this.getDifficulty();
	}

	/*
	 * Returns the number of columns.
	 */
	public int getNumberOfColumns() {
		return this.NumberOfColumns;
	}

	/*
	 * return the value of the tile on a specific coordinate
	 */
	public int getTileValue(int row, int column) {
		return this.mapWithTiles[row][column];
	}

	/*
	 * Sets the value of a tile to 0. This is called in case the player moves to a
	 * tile containing a potion, key or book. Also opponents and tournaments are
	 * removed in case the player wins the fight.
	 */
	public void setTileValueZero(int row, int column) {
		this.mapWithTiles[row][column] = 0;
	}

	/*
	 * Returns the allowed amount of skill and energy.
	 */
	public int getAllowedSkillEnergy() {
		return ALLOWED_SKILL_ENERGY;
	}

	/*
	 * Returns the number of Keys required to enter the castle and win the game.
	 */
	public int getNUMBER_OF_REQUIRED_KEYS() {
		return NUMBER_OF_REQUIRED_KEYS;
	}

	/*
	 * Returns the maximum difficulty.
	 */
	public int getMaxDifficulty() {
		return Max_Difficulty;
	}
}
