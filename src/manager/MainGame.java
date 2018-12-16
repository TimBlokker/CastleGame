package manager;

import java.util.InputMismatchException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.MapFrame;
import gui.SelectionGui;
import logic.Character;
import logic.Map;
import logic.Scores;

/*
 * This class is managing the flow of the Game. It contains 2 methods besides the constructor, one of them is used to control the flow of the 
 * text-based version (playTextBased()) and the other of the GUI version (play()).
 * The play() method guides the flow using a switch and the playTextBased via just calling the methods one by one.
 * For the GUI part of the game is not controlled within the MainGame but within the MapFrame class (while loop to move player and evaluate tile impact).
 */
public class MainGame {
	/*
	 * Variables keeping track of the progress in the game. Used in the switch in
	 * the play() method.
	 */
	public static int num;
	/*
	 * Instance of the Map class.
	 */
	private Map map;
	/*
	 * Instance of the Character class.
	 */
	private Character player;
	/*
	 * Instance of the Scores class.
	 */
	private Scores score1;
	/*
	 * Instance of the MainGame class.
	 */
	private MainGame self;

	/*
	 * Constructor of the MainGame class. This constructor sets the look and feel of
	 * the program to the system look and feel. Creates new instances of the Map
	 * class, Character class and Scores class.
	 */
	public MainGame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		num = 0;
		self = this;
		map = new Map();
		player = new Character(0, 0, map);
		score1 = new Scores(player, map);

		play();
	}

	/*
	 * This method is used to the control the flow of the GUI version using a
	 * switch.
	 */
	public void play() {
		switch (num) {
		case 0:
			new SelectionGui(map, player, self);
			break;
		case 1:
			new SelectionGui(self, map, player, "Setup Castle Game",
					"<html><div><H3><center>The Castle Game.</center></H3><br><H4>There are three keys hidden across the map. In order to win you need to find them all "
							+ "and then open the castle to free the sweet princess or prince.</H4>"
							+ "*Please navigate using the arrow keys.</div></html>",
					"Choose the energy as a value between 2 and " + map.getAllowedSkillEnergy()
							+ " the skill will be set to " + map.getAllowedSkillEnergy()
							+ " minus the energy you chose.",
					"Select the difficulty as a value between 2 and " + map.getMaxDifficulty(), "Name:", "Archer",
					"Monk", "Warrior", "Highscore", "Start");
			break;
		case 2:
			player.storeMap();
			score1.calculateScore();
			new MapFrame(map, player, score1, self);
			break;
		case 3:
			new SelectionGui(map, player, self, "For sure!", "Hell, no!");
			break;
		case 4:
			System.exit(1);
			break;
		case 5:
			new MainGame();
			break;
		}
	}

	/*
	 * This method controls the flow of the text-based version of the game. If the
	 * player chooses to play another time a new instance of the MainGame class is
	 * created.
	 */
	public void playTextBased() {
		System.out.println(
				"Welcome to the Castle Game. There are three keys hidden across the map.\nIn order to win you need to find them all "
						+ "and then open the castle to free the sweet prince/princess, whichever you prefer.\n\n"
						+ "Please navigate using: DOWN[5], UP[8], RIGHT[6], LEFT[4].\n");
		map.chooseDifficulty();
		map.setNumberOfRows();
		map.setNumberOfColumns();
		map.createMap();
		player.chooseCharacter();
		player.chooseEnergy();
		player.chooseName();
		System.out.println("Ok " + player.getName() + ", let the adventure begin! "
				+ " You will start with an energy level of " + player.getEnergy() + " and your skill level is "
				+ player.getSkill() + "." + " The difficulty was set to: " + map.getDifficulty());
		player.printMap();
		while (player.getEnergy() >= 0 & player.getWon() == false) {
			try {
				player.movePlayer();
				player.tileImpact();
				player.printMap();
			} catch (InputMismatchException nse) {
				System.out.println("There was no or incorrect user input.");
			} catch (ArrayIndexOutOfBoundsException ioo) {
				System.out.println("The end of the map was reached. Turn another way.");
			}
		}
		if (player.getWon() == false) {
			System.out.println("You ran out of energy.");
		} else {
			System.out.println("You won.");
		}
		Scores.showHighscore();
		score1.printToFile();
		score1.compareToHighscore();
		MainGame.num = 3;
		this.play();
	}

	/*
	 * The main methods creates a new instance of the MainGame class, which starts
	 * the Game.
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainGame();
			}
		});
	}
}
