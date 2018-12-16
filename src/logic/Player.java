package logic;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

/*
 * This abstract class is used to choose the ratio of the player's skill versus the player's energy, the player's name and also implements an abstract representation of the 
 * method which is used to choose the character. The chooseCharacter method is then overridden in the Character class.
 * Additionally the most important method of this game are implemented in this class. 
 * 		- The two methods to print (text-based) or store(GUI) the map, hereby determining the visible part of the map;
 * 		- The two methods moving the player (text-based);
 * 		- the method analysing the impact of each tilevalue the player is standing on.
 * Other important methods are:
 * 		- chooseEnergy() (text-based) and chooseEnergy(int i) (GUI)
 * 		- chooseName()  (text-based) and setName(String) (GUI)
 */
public abstract class Player {
	/*
	 * Variable setting the level of chance that is involved in the skill needed to
	 * beat an opponent or win a tournament.
	 */
	private final static int LEVEL_OF_CHANCE = 8;
	/*
	 * Generating a random number.
	 */
	public static Random r = new Random();
	/*
	 * The Scanner used for the text-based version to register user input.
	 */
	public static Scanner scan = new Scanner(System.in);
	/*
	 * The name of the player.
	 */
	private String name;
	/*
	 * The amount of prices the player has won (by winning tournaments or beating
	 * opponents).
	 */
	private int wonPrices = 0;
	/*
	 * Energy of the player.
	 */
	private int energy;
	/*
	 * Skill of the player.
	 */
	private int skill;
	/*
	 * Current column the player is located in.
	 */
	private int column;
	/*
	 * current row the player is located in.
	 */
	private int row;
	/*
	 * The skill required to win at a tournament. This is set by random.
	 */
	private int requiredSkillTournament;
	/*
	 * The skill required to win against an opponent. This is set by random.
	 */
	private int requiredSkillOpponent;
	/*
	 * Number of moves made by the player.
	 */
	private int numberOfMoves;
	/*
	 * Number of keys found by the player.
	 */
	private int numberOfKeys;
	/*
	 * Boolean keeping track of whether the player has won.
	 */
	private boolean won;
	private int[][] finalMapWithTiles;
	/*
	 * Instance of the class Map, which is instantiated in the constructor when a
	 * player instance is created and a map instance is passed to the player class.
	 */
	private Map map;

	/*
	 * this is the constructor for the the objects inheriting from player, the class
	 * Player itself is set to abstract and does not have any objects instantiated
	 * this constructor is called when an archer, monk or warrior object is created
	 * this constructor then calls the setName, setColumn, setRow, setEnergy and
	 * chooseSkill methods as well as the super constructor Map
	 */
	public Player(int r, int c, Map map) {
		this.map = map;
		this.row = r;
		this.column = c;
	}

	/*
	 * Abstract representation of the method which is implemented in the Character
	 * class. The abstract representation is necessary because the getCharacter
	 * method in the subclass is bound dynamically and thus is not available until
	 * run-time.
	 */
	public abstract String getCharacter();

	/*
	 * This method prints the map to the console and indicates the location of the
	 * player using brackets. The visibility is set depending on the character
	 * chosen. This method is used in the text-based version.
	 */
	public void printMap() {
		switch (this.getCharacter()) {
		case "Monk":
		case "Archer":
			for (int r = 0; r < map.getNumberOfRows(); r++) {
				for (int c = 0; c < map.getNumberOfColumns(); c++) {
					// position of player
					if (r == this.getRow() & c == this.getColumn()) {
						System.out.print("(" + map.getTileValue(r, c) + ")");
						// this is the range of visibility for archer and monk, thus the actual values
						// of the tiles are printed
					} else if ((r == this.getRow() - 2 | r == this.getRow() - 1) & c == this.getColumn()
							| (r == this.getRow() + 2 | r == this.getRow() + 1) & c == this.getColumn()
							| (c == this.getColumn() - 2 | c == this.getColumn() - 1) & r == this.getRow()
							| (c == this.getColumn() + 2 | c == this.getColumn() + 1) & r == this.getRow()) {
						System.out.print(" " + map.getTileValue(r, c) + " ");
						// this is the visibility unique to the Archer character and also here the
						// actual values are printed
					} else if (this.getCharacter().equals("Archer")
							& ((c == this.getColumn() + 1 | c == this.getColumn() - 1)
									& (r == this.getRow() + 1 | r == this.getRow() - 1))) {
						System.out.print(" " + map.getTileValue(r, c) + " ");
						// if a tile is out of the range of the visibility a x is printed instead
					} else {
						System.out.print(" x ");
					}
				}
				System.out.print("\n");
			}
			break;
		case "Warrior":
			for (int r = 0; r < map.getNumberOfRows(); r++) {
				for (int c = 0; c < map.getNumberOfColumns(); c++) {
					if (r == this.getRow() & c == this.getColumn()) {
						// position of the player when being a Warrior
						System.out.print("(" + map.getTileValue(r, c) + ")");
						// visible part of map
					} else if (r == this.getRow() - 1 & c == this.getColumn()
							| r == this.getRow() + 1 & c == this.getColumn()
							| c == this.getColumn() - 1 & r == this.getRow()
							| c == this.getColumn() + 1 & r == this.getRow()) {
						System.out.print(" " + map.getTileValue(r, c) + " ");
						// part of the map that is not visible
					} else {
						System.out.print(" x ");
					}
				}
				System.out.print("\n");
			}
			break;
		}
	}

	/*
	 * For the GUI method. This method stores the map, with implemented visibility
	 * radius in a new variable: finalMapWithTiles. The location of the player does
	 * not need to be indicated here, this is done later by assigning the image of
	 * the player to the correct tile. The visibility is set to according to the
	 * character being played.
	 */
	public int[][] storeMap() {
		this.finalMapWithTiles = new int[map.getMapWithTiles().length][map.getMapWithTiles()[0].length];
		switch (this.getCharacter()) {
		case "Monk":
		case "Archer":
			for (int r = 0; r < map.getNumberOfRows(); r++) {
				for (int c = 0; c < map.getNumberOfColumns(); c++) {
					// Position of player:this is actually not required in the GUI version but we
					// kept it to have it similar to the text-based version,
					// and keep the extra maintenance to a minimum
					if (r == this.getRow() & c == this.getColumn()) {
						this.finalMapWithTiles[r][c] = map.getTileValue(r, c);
						// visible part of the map for Archer and Monk
					} else if ((r == this.getRow() - 2 | r == this.getRow() - 1) & c == this.getColumn()
							| (r == this.getRow() + 2 | r == this.getRow() + 1) & c == this.getColumn()
							| (c == this.getColumn() - 2 | c == this.getColumn() - 1) & r == this.getRow()
							| (c == this.getColumn() + 2 | c == this.getColumn() + 1) & r == this.getRow()) {
						this.finalMapWithTiles[r][c] = map.getTileValue(r, c);
						// visible part of the map when being an Archer
					} else if (this.getCharacter().equals("Archer")
							& ((c == this.getColumn() + 1 | c == this.getColumn() - 1)
									& (r == this.getRow() + 1 | r == this.getRow() - 1))) {
						this.finalMapWithTiles[r][c] = map.getTileValue(r, c);
						// invisible part of the map
					} else {
						this.finalMapWithTiles[r][c] = 50;
					}
				}
			}
			break;
		case "Warrior":
			for (int r = 0; r < map.getNumberOfRows(); r++) {
				for (int c = 0; c < map.getNumberOfColumns(); c++) {
					// Position of player:this is actually not required in the GUI version but we
					// kept it to have it similar to the text-based version,
					// and keep the extra maintenance to a minimum
					if (r == this.getRow() & c == this.getColumn()) {
						this.finalMapWithTiles[r][c] = map.getTileValue(r, c);
						// visible part of the map for Warriors
					} else if (r == this.getRow() - 1 & c == this.getColumn()
							| r == this.getRow() + 1 & c == this.getColumn()
							| c == this.getColumn() - 1 & r == this.getRow()
							| c == this.getColumn() + 1 & r == this.getRow()) {
						this.finalMapWithTiles[r][c] = map.getTileValue(r, c);
						// invisible part of the map
					} else {
						this.finalMapWithTiles[r][c] = 50;
					}
				}
			}
			break;
		}
		return finalMapWithTiles;
	}

	/*
	 * Getter to return the finalMapWithTiles.
	 */
	public int[][] getFinalMapWithTiles() {
		return this.finalMapWithTiles;
	}

	/*
	 * 4 cases to move the player UP, DOWN, LEFT and RIGHT. This is implemented
	 * using a switch. At every move one energy point is deducted, this is also
	 * printed to the console. The number of moves is counted here to be used in the
	 * score calculation. The new position of the Player is printed to the console
	 * after being updated via the setRow() and setColumn() method. This method also
	 * throws an ArrayOutOfBoundsException if the player reaches the end of the map.
	 * All exception are caught and handled in the main method for the text based
	 * version. InputMismatchExceptions are thrown when the user enters invalid
	 * input. 1 extra case is written to allow for the special abilities of the
	 * Monk.
	 */
	public void movePlayer() throws InputMismatchException, ArrayIndexOutOfBoundsException {
		System.out.println("Move...");
		String move = scan.nextLine();
		switch (move) {
		// Down
		case "5":
			if (this.getRow() == map.getNumberOfRows() - 1) {
				throw new ArrayIndexOutOfBoundsException();
			} else {
				this.setRow(this.getRow() + 1);
				this.setEnergy(this.getEnergy() - 1);
				System.out.println("Energy: " + this.getEnergy() + " Skill: " + this.getSkill());
				break;
			}
			// UP
		case "8":
			if (this.getRow() == 0) {
				throw new ArrayIndexOutOfBoundsException();
			} else {
				this.setRow(this.getRow() - 1);
				this.setEnergy(this.getEnergy() - 1);
				System.out.println("Energy: " + this.getEnergy() + " Skill: " + this.getSkill());
				break;
			}
			// right
		case "6":
			if (this.getColumn() == map.getNumberOfColumns() - 1) {
				throw new ArrayIndexOutOfBoundsException();
			} else {
				this.setColumn(this.getColumn() + 1);
				this.setEnergy(this.getEnergy() - 1);
				System.out.println("Energy: " + this.getEnergy() + " Skill: " + this.getSkill());
				break;
			}
			// left
		case "4":
			if (this.getColumn() == 0) {
				throw new ArrayIndexOutOfBoundsException();
			} else {
				this.setColumn(this.getColumn() - 1);
				this.setEnergy(this.getEnergy() - 1);
				System.out.println("Energy: " + this.getEnergy() + " Skill: " + this.getSkill());
				break;
			}
			// special ability of the Monk
		case "99":
			if (this.getCharacter().equals("Monk")) {
				System.out.println("Do you want to transfer skill to energy or energy to skill?"
						+ "If you want to trade 1 skill point for Energy press E if you want more Skill press S."
						+ " It is a 1:1 trade always.");
				System.out.println("Please enter S or E or any other key if you do ot want to trade");
				String s = scan.nextLine();
				if (s.equals("S")) {
					this.setEnergy(getEnergy() - 1);
					this.setSkill(this.getSkill() + 1);
					System.out.println("Energy: " + this.getEnergy() + "Skill: " + this.getSkill());
					break;
				} else if (s.equals("E")) {
					this.setEnergy(getEnergy() + 1);
					this.setSkill(this.getSkill() - 1);
					System.out.println("Energy: " + this.getEnergy() + ", Skill: " + this.getSkill());
					break;
				} else {
					System.out.println("Please enter either S or E");

				}
			} else {
				System.out.println("You need to dedicate your life to god if you want to use this power."
						+ "\nConsider to choose the life of a Monk next time. ");
				break;
			}

		default:
			throw new InputMismatchException();

		}
	}

	/*
	 * 4 cases to move the player UP, DOWN, LEFT and RIGHT. This is implemented
	 * using a switch. At every move one energy point is deducted, this is also
	 * visible in the GUI. Also the number of moves is counted here. The new
	 * position of the Player is updated via the setRow() and setColumn() methods.
	 * This method also throws an ArrayOutOfBoundsException if the player reaches
	 * the end of the map. All exception are caught and handled in the MapPanel
	 * class where the movePlayer(int i) method is called. 2 extra cases are written
	 * to allow for the special abilities of the Monk.
	 */
	public void movePlayer(int i) throws InputMismatchException, ArrayIndexOutOfBoundsException {
		switch (i) {
		// Down
		case 5:
			if (this.getRow() == map.getNumberOfRows() - 1) {
				throw new ArrayIndexOutOfBoundsException();
			} else {
				this.setRow(this.getRow() + 1);
				this.setEnergy(this.getEnergy() - 1);
				this.setNumberOfMoves(this.getNumberOfMoves() + 1);
				break;
			}
			// UP
		case 8:
			if (this.getRow() == 0) {
				throw new ArrayIndexOutOfBoundsException();
			} else {
				this.setRow(this.getRow() - 1);
				this.setEnergy(this.getEnergy() - 1);
				this.setNumberOfMoves(this.getNumberOfMoves() + 1);
				break;
			}
			// right
		case 6:
			if (this.getColumn() == map.getNumberOfColumns() - 1) {
				throw new ArrayIndexOutOfBoundsException();
			} else {
				this.setColumn(this.getColumn() + 1);
				this.setEnergy(this.getEnergy() - 1);
				this.setNumberOfMoves(this.getNumberOfMoves() + 1);
				break;
			}
			// left
		case 4:
			if (this.getColumn() == 0) {
				throw new ArrayIndexOutOfBoundsException();
			} else {
				this.setColumn(this.getColumn() - 1);
				this.setEnergy(this.getEnergy() - 1);
				this.setNumberOfMoves(this.getNumberOfMoves() + 1);
				break;
			}
			// Secial ability of the monk to trade energy for skill points
		case 1:
			if (this.getCharacter().equals("Monk") & this.getEnergy() > 1) {
				this.setEnergy(getEnergy() - 1);
				this.setSkill(this.getSkill() + 1);
			} else {
				if (this.getCharacter().equals("Monk")) {
					JOptionPane.showMessageDialog(null, "You do not have enough energy to trade, this would kill you.");
				} else {
					JOptionPane.showMessageDialog(null,
							"You need to dedicate your life to god if you want to use this power."
									+ "\nConsider to choose the life of a Monk next time.");
				}
			}
			break;
		// special ability of the monk to trade skill points for energy
		case 2:
			if (this.getCharacter().equals("Monk") & this.getSkill() > 0) {
				this.setEnergy(getEnergy() + 1);
				this.setSkill(this.getSkill() - 1);
			} else {
				if (this.getCharacter().equals("Monk")) {
					JOptionPane.showMessageDialog(null, "You do not have skill to trade.");
				} else {
					JOptionPane.showMessageDialog(null,
							"<html><div>You need to dedicate your life to god if you want to use this power."
									+ "<br>Consider to choose the life of a Monk next time</div></html>");
				}
			}
			break;
		default:
			throw new InputMismatchException();
		}
	}

	/*
	 * This method links the impact of the tiles to the players attributes, like the
	 * skill and the energy, also prices can be won when tournament is won or a
	 * opponent is beaten. The amount of prices won is also counted and can be used
	 * to calculate the score in the end. To get the current location the getRow()
	 * and getColumn() methods are called and these values are given to the
	 * getMapWithTiles() method to get the value of the tile the player is standing
	 * on. If the map is also updated in case of book, potion, beaten opponent, won
	 * tournament and found key, so that each of these items can only be gathered
	 * once.
	 */
	public void tileImpact() {
		int tileValue = map.getTileValue(this.getRow(), this.getColumn());
		switch (tileValue) {
		// normal tile
		case 0:
			break;
		// stony ground
		case 1:
			this.setSkill(this.getSkill() - 2);
			JOptionPane.showMessageDialog(null,
					"Stony ground" + " -> Skill -2\n Your new skill level is " + this.getSkill() + ".");
			break;
		// Book
		case 2:
			this.setSkill(this.getSkill() + 3);
			JOptionPane.showMessageDialog(null, "A long lost book full of wisdom" + " -> Skill + 3");
			map.setTileValueZero(this.getRow(), this.getColumn());
			break;
		// tournament
		case 3:
			this.setRequiredSkillTournament();
			JOptionPane.showMessageDialog(null, "A tournament, good luck!\n" + "The Skill needed to win is "
					+ this.getRequiredSkillTournament() + ".");
			if (this.getSkill() > this.getRequiredSkillTournament()) {
				this.setWonPrices(this.getWonPrices() + 1);
				JOptionPane.showMessageDialog(null, "Congratulation! You were the best.\n" + "You won a price, you won "
						+ this.getWonPrices() + " price(s) so far.");
				map.setTileValueZero(this.getRow(), this.getColumn());
				break;
			} else {
				JOptionPane.showMessageDialog(null,
						"You lost. The laughter of the crowd still rings in your ears, learn to ride a horse "
								+ this.name + ".");
				this.energy = this.getEnergy() - 2;
				break;
			}
			// opponent
		case 4:
			this.setRequiredSkillOpponent();
			JOptionPane.showMessageDialog(null,
					"You met an opponent!" + "Required skill: " + this.getRequiredSkillOpponent());
			if (this.getSkill() > this.getRequiredSkillOpponent()) {
				this.setWonPrices(this.getWonPrices() + 1);
				JOptionPane.showMessageDialog(null, "Congratulation! Your opponent did not stand a chance.\n"
						+ "You won a price, Total price(s) =" + this.getWonPrices() + ".");
				map.setTileValueZero(this.getRow(), this.getColumn());
				break;
			} else {
				JOptionPane.showMessageDialog(null, "You lost, Energy -2");
				this.energy = this.getEnergy() - 2;
				break;
			}
		case 5:
			// Potion
			this.energy = this.getEnergy() + 3;
			JOptionPane.showMessageDialog(null, "A potion" + " -> Energy + 3 :-)");
			map.setTileValueZero(this.getRow(), this.getColumn());
			break;
		case 6:
			// Key
			this.setNumberOfKeys(getNumberOfKeys() + 1);
			JOptionPane.showMessageDialog(null,
					"A Key. You found this amount of keys so far:" + this.getNumberOfKeys());
			map.setTileValueZero(this.getRow(), this.getColumn());
			break;
		case 7:
			// Castle
			if (this.getNumberOfKeys() == map.getNUMBER_OF_REQUIRED_KEYS()) {
				JOptionPane.showMessageDialog(null, "You won! Congratulations!");
				setWon(true);
			} else {
				JOptionPane.showMessageDialog(null, "You have not found all keys yet. Continue on your quest");
				break;
			}
		}
	}

	/*
	 * Sets the boolean won to either true or false. If the player has won the game
	 * won is set to true
	 */
	public void setWon(boolean won) {
		this.won = won;
	}

	/*
	 * Returns the value of won.
	 */
	public boolean getWon() {
		return this.won;
	}

	/*
	 * Returns the number of keys the player has found.
	 */
	public int getNumberOfKeys() {
		return this.numberOfKeys;
	}

	/*
	 * Sets the number of keys the player has found.
	 */
	public void setNumberOfKeys(int numberOfKeys) {
		this.numberOfKeys = numberOfKeys;
	}

	/*
	 * This method is invoked in the MainGame class for the text-based version.
	 * Exceptions are handled in this method directly.
	 */
	public void chooseName() {
		System.out.println("What should I call you?");
		try {
			this.name = scan.nextLine();
		} catch (InputMismatchException ime) {
			System.out.println("Something went wrong.");
		}
	}

	/*
	 * Returns the name of the player.
	 */
	public String getName() {
		return this.name;
	}

	/*
	 * This method sets the name of the player. This method is called from an
	 * actionlistener in the SelectionGui class to set the name of the player, when
	 * playing the GUI version.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * Sets the energy of the player.
	 */
	public void setEnergy(int energy) {
		this.energy = energy;
	}

	/*
	 * Returns the energy of the player.
	 */
	public int getEnergy() {
		return energy;
	}

	/*
	 * this method is called from the MainGame class and asks for user input to set
	 * the energy for the user to begin the game with. the energy can be set between
	 * 0 and the current allowed maximum. Exceptions are handled in this method and
	 * the user informed if the input was illegal and a default energy level is
	 * selected. Also sets the skill without user input by making a tradeoff between
	 * energy and skill
	 */
	public void chooseEnergy() {
		try {
			System.out.println("Please choose the energy you would like to start with. "
					+ "It will be a tradeoff between Energy and Skill and the sum will be "
					+ map.getAllowedSkillEnergy() + ".");
			int i = scan.nextInt();
			if (i <= map.getAllowedSkillEnergy() & i >= 0) {
				this.energy = i;
			} else {
				throw new InputMismatchException();
			}
		} catch (InputMismatchException ime) {
			System.out.println("Only values between 0 and " + map.getAllowedSkillEnergy() + " are allowed. "
					+ "The default was set to:" + this.getDefaultEnergy() + ".");
			this.energy = this.getDefaultEnergy();
			scan.nextLine();
		}
		this.skill = map.getAllowedSkillEnergy() - this.getEnergy();

		if (this.getCharacter().equals("Warrior")) {
			System.out.println("Your training as a Warrior made you powerful." + "\nEnergy +4.");
			this.energy = this.energy + 4;
		}
		scan.nextLine();
	}

	/*
	 * This method is called from the Actionlisteners in the SelectionGui class and
	 * asks for user input to set the energy for the user to begin the game with.
	 * the energy can be set between 0 and the current allowed
	 * maximum(ALLOWED_SKILL_ENERGY). IllegalArgument exceptions do not need to be
	 * handled when using a JSlider for input. Also sets the skill without user
	 * input by making a tradeoff between energy and skill.
	 */
	public void chooseEnergy(int i) {
		try {
			if (i <= map.getAllowedSkillEnergy() & i >= 0) {
				this.energy = i;
			} else {
				throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException iae) {
		}
		this.skill = map.getAllowedSkillEnergy() - this.getEnergy();

		if (this.getCharacter().equals("Warrior")) {
			this.energy = this.energy + 4;
		}
	}

	/*
	 * Method for the default energy in case the user gives illegal input
	 * (text-based).
	 */
	public int getDefaultEnergy() {
		return map.getAllowedSkillEnergy() / 2;
	}

	/*
	 * Returns the skill of the player.
	 */
	public int getSkill() {
		return this.skill;
	}

	/*
	 * Sets the skill of the player.In order to prevent the skill becoming negative
	 * during game play (e.g. when walking over stony ground) negative numbers are
	 * handled here and 0 is set a value if negative values would be calculated.
	 */
	public void setSkill(int skill) {
		if (skill >= 0) {
			this.skill = skill;
		} else {
			this.skill = 0;
		}
	}

	/*
	 * Returns the column of the players current location.
	 */
	public int getColumn() {
		return column;
	}

	/*
	 * Sets the column of the players location.
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/*
	 * Returns the row of the players current location.
	 */
	public int getRow() {
		return row;
	}

	/*
	 * Sets the row of the players location.
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/*
	 * Returns the amount of prices the player has won (by beating oponents or
	 * winning battles)
	 */
	public int getWonPrices() {
		return wonPrices;
	}

	/*
	 * Method used to set the amount of prices a player has won.
	 */
	public void setWonPrices(int wonPrices) {
		this.wonPrices = wonPrices;
	}

	/*
	 * Returns the skill that is required to beat a juror.
	 */
	public int getRequiredSkillTournament() {
		return requiredSkillTournament;
	}

	/*
	 * The skill a juror requires is set here. the amount of chance can be set via a
	 * variable.
	 */
	public void setRequiredSkillTournament() {
		this.requiredSkillTournament = map.getAllowedSkillEnergy() / 2 - LEVEL_OF_CHANCE / 2
				+ r.nextInt(LEVEL_OF_CHANCE);
	}

	/*
	 * The skill that is required to win against an opponent is returned by this
	 * method.
	 */
	public int getRequiredSkillOpponent() {
		return this.requiredSkillOpponent;
	}

	/*
	 * The skill that is required to win against an opponent is set here.
	 */
	public void setRequiredSkillOpponent() {
		this.requiredSkillOpponent = map.getAllowedSkillEnergy() / 2 - LEVEL_OF_CHANCE / 3 + r.nextInt(LEVEL_OF_CHANCE);
	}

	/*
	 * tThis method is returning the number of moves a player has made.
	 */
	public int getNumberOfMoves() {
		return this.numberOfMoves;
	}

	/*
	 * Sets the number of moves a player has made.
	 */
	public void setNumberOfMoves(int numberOfMoves) {
		this.numberOfMoves = numberOfMoves;
	}
}