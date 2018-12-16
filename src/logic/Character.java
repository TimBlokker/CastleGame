package logic;

import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JOptionPane;

/*
 * This class is inheriting from Player and has methods which allow the user to choose a character.
 * The methods chooseCharacter and chooseCharacter(String character) are implemented here. Additionally,
 * the getCharacter() method is implemented here and overrides the abstract method in the Player superclass.
 */
public class Character extends Player {
	/*
	 * A scanner used for the text-based version to get the user input.
	 */
	static Scanner scan = new Scanner(System.in);
	/*
	 * String of the character chosen by the user (Monk, Archer or Warrior).
	 */
	private String character;

	/*
	 * Constructor for the objects of type Character. It only contains the super()
	 * keyword calling the constructor of the superclass.
	 */
	public Character(int x, int y, Map map) {
		super(x, y, map);
	}

	/*
	 * This choose character method is used for the text-based version of the game
	 * and asks for user to choose the character of the game. The while loop is used
	 * to force the player to enter exactly one of the Strings requested and
	 * otherwise not proceed with the game.
	 */
	public void chooseCharacter() {
		this.character = "";
		while ((this.character.equals("Monk") == false & this.character.equals("Archer") == false
				& this.character.equals("Warrior") == false)) {
			System.out.println(
					"Start with choosing your character but choose wisely. You can be a Monk, an Archer or a Warrior. \nThe Archer can see further"
							+ " than the other, \nthe Warrior has more Energy and \nthe Monk can swap Energy points to Skill points and back.\n"
							+ "\nEnter [Monk], [Archer] or [Warrior]:");
			try {
				this.character = scan.nextLine();
				if ((character.equals("Monk") == false & this.character.equals("Archer") == false
						& this.character.equals("Warrior") == false)) {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException ime) {
				System.out.print("You entered an not existing character name. ");
			}
		}
		if (this.character.equals("Monk")) {
			System.out.println(
					"You chose to be a Monk, may god be with you. Do not forget about your special ability which you can use entering [99] when it is time to move.");
		} else if (this.character.equals("Archer")) {
			System.out.println("You chose to be an Archer, may you never run out of Arrows. Use your eagle eyes.");
		} else if (this.character.equals("Warrior")) {
			System.out.println("You chose to be a Warrior, may your muscles never grow weak. Use you bonus energy!");
		}
	}

	/*
	 * This method is used to to set the character of the game for the GUI version.
	 * Here no while loop is needed since the RadioButtons will always return the
	 * exact representation of one of the Strings.
	 */
	public void chooseCharacter(String character) {

		this.character = character;
		while ((this.character.equals("Monk") == false & this.character.equals("Archer") == false
				& this.character.equals("Warrior") == false)) {
			try {
				this.character = scan.nextLine();
				if ((character.equals("Monk") == false & this.character.equals("Archer") == false
						& this.character.equals("Warrior") == false)) {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException ime) {
				JOptionPane.showMessageDialog(null, "You entered an not existing character name. ");
			}
		}
		if (this.character.equals("Monk")) {
			JOptionPane.showMessageDialog(null,
					"<html><div>You chose to be a Monk, may god be with you.<br>"
							+ "Do not forget about your special ability which you can use entering E or S <br>"
							+ "allowing you you to swap Energy (E) for Skill (S).");
		} else if (this.character.equals("Archer")) {
			JOptionPane.showMessageDialog(null,
					"You chose to be an Archer, may you never run out of Arrows. Use your eagle eyes.");
		} else if (this.character.equals("Warrior")) {
			JOptionPane.showMessageDialog(null,
					"You chose to be a Warrior, may your muscles never grow weak. Energy +4!");
		}
	}

	/*
	 * This method overrides the abstract implementation of the getCharacter()
	 * method in the Player class. It returns the String of the chosen Character
	 * 
	 * (non-Javadoc)
	 * 
	 * @see logic.Player#getCharacter()
	 */
	@Override
	public String getCharacter() {
		return this.character;
	}
}
