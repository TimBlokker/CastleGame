package logic;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

/*
 * The class scores contains 6  static methods (the only static methods in the game except the main method). These methods were chosen to be static
 * because they handle the Highscore of the game. The Highscore is not necessarily equal to the current instance of Scores and thus it was deemed more 
 * logical to set the methods and variables for the Highscore to static.
 * The methods in this class purely handling the score of the current instance are not static.
 */
public class Scores {
	/*
	 * The fileName of the textfile which stores the name and the score of every
	 * player.
	 */
	private final static String fileName = "Highscores.txt";
	/*
	 * The static variable Highscore is used to store the Highscore of the game.
	 */
	private static int highscore;
	/*
	 * The Name the player entered when playing the Highscore Game.
	 */
	private static String NameHighscore;
	/*
	 * The score of the current game for the current player.
	 */
	private int score;
	/*
	 * An instance of the class Player.
	 */
	private Character player;
	/*
	 * An instance of the class Map.
	 */
	private Map map;

	/*
	 * Constructor for the objects of type Scores. Instances of type Player and of
	 * type Map are passed to this class.
	 */
	public Scores(Character player1, Map map) {
		this.player = player1;
		this.map = map;
	}

	/*
	 * This method is returning the current Highscore and the name of the player who
	 * has set the Highscore. The score is read from a file with the fileName
	 * specified in the variable fileName and returned. Exceptions are handled in
	 * this method.
	 */
	public static String showHighscore() {
		try (Scanner file = new Scanner(new FileReader(Scores.getFileName()));) {
			file.useDelimiter(Pattern.compile("(, )|(\r\n)"));
			file.useLocale(Locale.ENGLISH);
			while (file.hasNext()) {
				String a = file.next();
				int i = file.nextInt();
				if (i > Scores.getHighscore()) {
					Scores.setNameHighscore(a);
					Scores.setHighscore(i);
				}
			}
		} catch (FileNotFoundException fnf) {
			System.out.println("This file does not exist.");
		} catch (InputMismatchException ime) {
			System.out.println("The highscore-file is formatted incorrectly.");
		} catch (NoSuchElementException nee) {
			System.out.println("The highscore-file is formatted incorrectly.");
		}
		return "The highscore is currently " + Scores.getHighscore() + " set by " + Scores.getNameHighscore();
	}

	/*
	 * Returns the name of the player who set the current Highscore. This method is
	 * static because it is not related to the current instance of Scores.
	 */
	private static String getNameHighscore() {
		return Scores.NameHighscore;
	}

	/*
	 * This method sets the name of the player who has set the highest score this
	 * method is static because it is not related to the current instance of Scores.
	 */
	private static void setNameHighscore(String NameHighscore) {
		Scores.NameHighscore = NameHighscore;
	}

	/*
	 * Returns the current Highscore. This method is static because it is not
	 * related to the current instance of Scores.
	 */
	public static int getHighscore() {
		return Scores.highscore;
	}

	/*
	 * Sets the current Highscore this method is static because it is not related to
	 * the current instance of Scores.
	 */
	public static void setHighscore(int highscore) {
		Scores.highscore = highscore;
	}

	/*
	 * This method returns the filename used to store the scores in. This method is
	 * static because it is not related to the current instance of Scores.
	 */
	public static String getFileName() {
		return fileName;
	}

	/*
	 * This method calculates the score for each game depending on the NumberOfMoves
	 * made, the prices won, the difficulty of the game, the number of keys found
	 * and of course whether the player has won the game.
	 */
	public int calculateScore() {
		this.score = player.getNumberOfMoves() * 2 + player.getWonPrices() * map.getDifficulty()
				+ player.getNumberOfKeys();
		if (player.getWon() == true) {
			this.score = this.score * 2;
		}
		return this.score;
	}

	/*
	 * This method compares the score of the player at the end with the Highscore
	 * stored in the
	 */
	public void compareToHighscore() {
		this.printToFile();
		JOptionPane.showMessageDialog(null, "Score: " + this.getScore() + ".");
		if (this.getScore() > Scores.getHighscore()) {
			JOptionPane.showMessageDialog(null, "You broke the old highscore of " + Scores.getHighscore() + ".");
		} else {
			JOptionPane.showMessageDialog(null, "Bummer, not the highscore.");
		}
	}

	/*
	 * Makes the score available via a accessor method outside of this class.
	 */
	public int getScore() {
		return this.score;
	}

	/*
	 * Printing the score together with the name chosen by the player to a .txt
	 * file. The name of the file via the constant fileName which is defined as a
	 * static string in the Player class.
	 */
	public void printToFile() {
		this.calculateScore();
		try {
			FileOutputStream Highscore = new FileOutputStream(Scores.getFileName(), true);
			PrintWriter print = new PrintWriter(Highscore, true);
			print.format("\r\n" + player.getName() + ", " + this.getScore());
			print.close();

		} catch (Exception e) {
			System.out.println("There was an error with the export of the Highscores");
		}
	}

}
