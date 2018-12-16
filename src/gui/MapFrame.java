package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import logic.Character;
import logic.Map;
import logic.Scores;
import manager.MainGame;

/*
 * This class is the JFrame to which the MapPanel instance is added, a Borderlayout sets the MapPanel instance to the top of the frame and the MapButtons instance to the
 * Bottom of the frame (the frame being an instance of this class).
 * Part of the flow of the GUI based game is also managed in this class via a while loop.
 */
@SuppressWarnings("serial")
public class MapFrame extends JFrame {

	/*
	 * Instance of the Map class
	 */
	private Map map;
	/*
	 * Instance of the Character class
	 */
	private Character player;
	/*
	 * Instance of the Scores class
	 */
	private Scores score;
	/*
	 * Instance of the MainGame class
	 */
	private MainGame self;
	/*
	 * Instance of the MapPanel class
	 */
	private MapPanel guiMap;
	/*
	 * Instance of the MapButtons class
	 */
	private MapButtons mapButtons;

	/*
	 * Constructing an instance of the MapFrame class (which is-a) Frame.
	 */
	public MapFrame(Map map1, Character player1, Scores score1, MainGame self1) {
		this.map = map1;
		this.player = player1;
		this.score = score1;
		this.self = self1;
		setTitle("Castle Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// full-screen
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		// preferred size
		setPreferredSize(new Dimension(1300, 700));
		// setLocationByPlatform(true);
		setLayout(new BorderLayout());
		guiMap = new MapPanel(map, player, this);
		mapButtons = new MapButtons(map, player, score, this);
		add(mapButtons, BorderLayout.SOUTH);
		add(guiMap, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		this.createAndShowGui();
	}

	/*
	 * This method is used to control the flow of the game when being inside the
	 * MapFrame, which means, the player moves, the map is updated , the impact of
	 * the tiles on the player is evaluated, the score is calculated as long as the
	 * player has energy and has not won yet.
	 */
	public void createAndShowGui() {
		if (player.getEnergy() >= 0 & player.getWon() == false) {
			player.storeMap();
			player.tileImpact();
			score.calculateScore();
			guiMap.createAndShowMap();
			mapButtons.createAndShowButtons();
		} else {
			if (player.getWon() == false) {
				JOptionPane.showMessageDialog(null, "You ran out of energy and lost the game.");
				Scores.showHighscore();
				score.compareToHighscore();
				dispose();
				MainGame.num++;
				self.play();
			} else if (player.getWon() == true) {
				Scores.showHighscore();
				score.compareToHighscore();
				dispose();
				MainGame.num++;
				self.play();
			}
		}
	}
}
