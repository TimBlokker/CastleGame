package gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import logic.Character;
import logic.Map;
import logic.Scores;

@SuppressWarnings("serial")
public class MapButtons extends JPanel {

	/*
	 * Instance of the Map class
	 */
	private Map map;
	/*
	 * Instance of the class Character
	 */
	private Character player;
	/*
	 * Instance of the class Scores
	 */
	private Scores score;
	/*
	 * Instance of the class MaprFame
	 */
	private MapFrame mapFrame;

	/*
	 * Constructor to construct an instance of the class MapFrame.
	 */
	public MapButtons(Map map1, Character player1, Scores score1, MapFrame mapFrame) {
		this.map = map1;
		this.player = player1;
		this.score = score1;
		this.mapFrame = mapFrame;
		this.createAndShowButtons();
	}

	/*
	 * This method creates the buttons (Highscore and Quit) and the labels which are
	 * added to the buttom of the MapFrame frame. The name, difficulty, energy,
	 * skill, score, found keys, made moves and amount of won prices is displayed.
	 */
	public void createAndShowButtons() {
		// repainting images to update after player was moved
		@SuppressWarnings("unused")
		Graphics g = getGraphics();
		removeAll();
		revalidate();
		repaint();

		setLayout(new GridLayout(1, 6, 100, 0));
		JButton highscoreButton = new JButton("Highscore");
		add(highscoreButton);

		/*
		 * Inner class listening to the highscore button being pressed.
		 */
		highscoreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, Scores.showHighscore());
				mapFrame.createAndShowGui();
			}
		});

		JLabel name = new JLabel(
				"<html><div>Name: " + player.getName() + "<br> Difficulty: " + map.getDifficulty() + "</div></html>");
		add(name);
		JLabel skill = new JLabel(
				"<html><div> Energy: " + player.getEnergy() + " <br> Skill: " + player.getSkill() + "</div></html>");
		add(skill);
		JLabel currentScore = new JLabel("<html><div> Score: " + score.getScore() + "<br> Key(s): "
				+ player.getNumberOfKeys() + "/" + map.getNUMBER_OF_REQUIRED_KEYS() + "</div></html>");
		add(currentScore);
		JLabel numberOfMoves = new JLabel("<html><div> Moves: " + player.getNumberOfMoves() + "<br> Prices: "
				+ player.getWonPrices() + "</div></html>");
		add(numberOfMoves);
		JButton quitButton = new JButton("Quit");
		add(quitButton);

		/*
		 * Inner class listening to the Quit button being pressed.
		 */
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Bye, come back soon!");
				System.exit(1);
			}
		});
	}
}
