package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logic.Character;
import logic.Map;
import logic.Scores;
import manager.MainGame;

/*
 * This class contains three overloaded constructors and also 3 methods besides the 3 constructors, each is used to create frame. 
 * 		- guiOrText() method creates the first frame being created is giving the user the choice to play either a text-based or a GUI based game.
 * 		- selectionScreen() method is creating a frame giving the user the possibility to enter his/her name, choose the difficult and skill/energy ratio as well as 
 * 		  the character.
 * 		- the third method is creating a frame letting the user choose between exiting the game or playing another time.
 * Lastly, this class is extending JFrame and thus this class is-a JFrame.
 */
@SuppressWarnings("serial")
public class SelectionGui extends JFrame {
	/*
	 * Instance of the class Map.
	 */
	private Map map;
	/*
	 * Instance of the class Character.
	 */
	private Character player;
	/*
	 * Instance of the class MainGame.
	 */
	private MainGame self;

	/*
	 * Constructor used to construct an instance of the class used for creating the
	 * Frame used for deciding between GUI and text-based.
	 */
	public SelectionGui(Map map, Character player, MainGame self) {
		this.self = self;
		this.map = map;
		this.player = player;
		this.guiOrText();
	}

	/*
	 * Constructor used to construct an instance of the class used for creating the
	 * Frame used for getting the user input for the player and map attributes.
	 */
	public SelectionGui(MainGame self, Map map, Character player, String title, String welcomeText, String energyText,
			String difficultyText, String nameText, String a, String m, String w, String highscoreButtonText,
			String startButtonText) {
		this.self = self;
		this.map = map;
		this.player = player;
		Scores.showHighscore();
		this.selectionScreen(title, welcomeText, energyText, difficultyText, nameText, a, m, w, highscoreButtonText,
				startButtonText);
	}

	/*
	 * Constructor used to construct an instance of the class used for creating the
	 * Frame used for asking the user whether another Game should be started or the
	 * program should be exited.
	 */
	public SelectionGui(Map map, Character player, MainGame self, String yes, String no) {
		this.self = self;
		this.map = map;
		this.player = player;
		this.anotherOne(yes, no);
	}

	/*
	 * Method used to create the frame giving the user the choice between a
	 * text-based version or a GUI version.
	 */
	private void guiOrText() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Text or GUI");
		setPreferredSize(new Dimension(250, 400));
		// GridLayout making one column with three row. Every label/button on the grid
		// will occupy the same space.
		JPanel decisionPanel = new JPanel(new GridLayout(3, 1, 0, 10));
		getContentPane().add(decisionPanel);
		// some HTML commands in the label text to make it nicer to look at
		JLabel chooseLabel = new JLabel(
				"<html><div><h4><center>Castle Game</h4>Please choose how you would like to play: <ul><li>Old-school text based</li><li>GUI version</li></center></div></html>");
		decisionPanel.add(chooseLabel);
		JButton textbasedButton = new JButton("Text based!");
		decisionPanel.add(textbasedButton);
		JButton guiButton = new JButton("I want a GUI!");
		decisionPanel.add(guiButton);
		/*
		 * Inner class adds an Actionlistener to the button text-based, pressing the
		 * button calls the method platTextBased which starts the textbased verison of
		 * the game.
		 */
		textbasedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				self.playTextBased();
			}
		});
		/*
		 * Inner class adding an ActionListener to the guiButton, which starts the GUI
		 * version of the game when the button is pressed by calling the play() method
		 * in the MainGame class.
		 */
		guiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainGame.num++;
				dispose();
				self.play();
			}
		});
		pack();
		// centers the frame on the screen
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/*
	 * Method used to create the selection frame, used to select the difficulty,
	 * energy, skill, name and character of the game/player.
	 */
	public void selectionScreen(String title, String welcomeText, String energyText, String difficultyText,
			String nameText, String a, String m, String w, String highscoreButtonText, String startButtonText) {

		// Frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(title);
		// Borderlayout is used on the frame, setting the panels to the North, Center
		// and South
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(350, 600));

		// welcomePanel for welcome text and welcome Image
		JPanel welcomePanel = new JPanel();
		// panel is set to the upper part of the frame
		getContentPane().add(welcomePanel, BorderLayout.NORTH);
		// within the welcomeLabel also a BorderLayout is used allowing for
		welcomePanel.setLayout(new BorderLayout());
		JLabel welcomeLabel = new JLabel(welcomeText);
		// label is set to the center of the panel
		welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

		// nameCharacterPanel for Name and Character
		JPanel nameCharacterPanel = new JPanel();
		nameCharacterPanel.setPreferredSize(new Dimension(50, 50));
		// the nameCharacterPanel is set to the center of the frame.
		getContentPane().add(nameCharacterPanel, BorderLayout.CENTER);
		// The panel gets a GridLayout with 2 rows and 3 columns.
		nameCharacterPanel.setLayout(new GridLayout(2, 3, 0, 0));
		JLabel nameLabel = new JLabel(nameText);
		nameCharacterPanel.add(nameLabel);
		JTextField nameField = new JTextField();
		nameCharacterPanel.add(nameField);
		JLabel spacerLabel = new JLabel();
		nameCharacterPanel.add(spacerLabel);
		JRadioButton RbuttonA = new JRadioButton(a);
		JRadioButton RbuttonW = new JRadioButton(w);
		JRadioButton RbuttonM = new JRadioButton(m);
		nameCharacterPanel.add(RbuttonA);
		nameCharacterPanel.add(RbuttonW);
		nameCharacterPanel.add(RbuttonM);
		RbuttonA.setActionCommand(a);
		RbuttonW.setActionCommand(w);
		RbuttonM.setActionCommand(m);
		ButtonGroup g = new ButtonGroup();
		// Buttongroup to use in actionlistener
		g.add(RbuttonA);
		g.add(RbuttonM);
		g.add(RbuttonW);
		// default selection
		RbuttonW.setSelected(true);

		// sliderPanel for a energySlider and a difficultySlider
		JPanel sliderPanel = new JPanel();
		// sliderPanel is set to the buttom of the frame
		getContentPane().add(sliderPanel, BorderLayout.SOUTH);
		// slidePanel gets a GridLayout with 3 rows and 2 columns, the last two numbers
		// are used to set the space between rows and columns
		sliderPanel.setLayout(new GridLayout(3, 2, 10, 20));
		JSlider energySlider = new JSlider(0, player.getDefaultEnergy() * 2, player.getDefaultEnergy());
		// Hashtable to set the labels of the energySlider
		Hashtable<Integer, JLabel> labels = new Hashtable<>();
		labels.put(0, new JLabel("Skill"));
		labels.put(map.getAllowedSkillEnergy(), new JLabel("Energy"));
		energySlider.setLabelTable(labels);
		energySlider.setPaintLabels(true);
		energySlider.setPreferredSize(new Dimension(150, 50));
		energySlider.setPaintTrack(true);
		energySlider.setPaintTicks(true);
		energySlider.setPaintLabels(true);
		energySlider.setMajorTickSpacing(5);
		energySlider.setMinorTickSpacing(1);
		energySlider.setPaintLabels(true);
		energySlider.setValue(map.getAllowedSkillEnergy() / 2);
		sliderPanel.add(energySlider);
		JLabel energyLabel = new JLabel();
		energyLabel.setText("<html><div>Energy=" + energySlider.getValue() + "<br>Skill= "
				+ (map.getAllowedSkillEnergy() - energySlider.getValue()) + "</div></html>");
		sliderPanel.add(energyLabel);
		/*
		 * Inner class listening to changes of the energySlider
		 */
		energySlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				energyLabel.setText("<html><div>Energy=" + energySlider.getValue() + "<br>Skill= "
						+ (map.getAllowedSkillEnergy() - energySlider.getValue()) + "</div></html>");
				energySlider.setValue((int) energySlider.getValue());
			}
		});

		JSlider difficultySlider = new JSlider(2, map.getMaxDifficulty(), map.getDefaultDifficulty());
		difficultySlider.setPaintLabels(true);
		difficultySlider.setPreferredSize(new Dimension(150, 50));
		difficultySlider.setPaintTrack(true);
		difficultySlider.setPaintTicks(true);
		difficultySlider.setPaintLabels(true);
		difficultySlider.setMajorTickSpacing(5);
		difficultySlider.setMinorTickSpacing(1);
		difficultySlider.setPaintLabels(true);
		difficultySlider.setValue(map.getDefaultDifficulty());
		sliderPanel.add(difficultySlider);
		JLabel difficultyLabel = new JLabel();
		difficultyLabel.setText("Difficulty= " + difficultySlider.getValue());
		sliderPanel.add(difficultyLabel);
		// Hashtable to set the labels of the energySlider
		Hashtable<Integer, JLabel> labelsDiff = new Hashtable<>();
		labelsDiff.put(2, new JLabel("Low"));
		labelsDiff.put(map.getDefaultDifficulty(), new JLabel("Medium"));
		labelsDiff.put(map.getMaxDifficulty(), new JLabel("High"));
		difficultySlider.setLabelTable(labelsDiff);

		/*
		 * Inner class listening to changes of the difficultySlider
		 */
		difficultySlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				difficultyLabel.setText("Difficulty= " + difficultySlider.getValue());
				difficultySlider.setValue((int) difficultySlider.getValue());
			}
		});

		// highscore button
		JButton highscoreButton = new JButton(highscoreButtonText);
		JButton startButton = new JButton(startButtonText);
		sliderPanel.add(highscoreButton);
		sliderPanel.add(startButton);
		/*
		 * Inner class listening to a pressed highscoreButton
		 */
		highscoreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, Scores.showHighscore());
			}
		});
		/*
		 * Inner class listening to a pressed startButton
		 */
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				map.chooseDifficulty(difficultySlider.getValue());
				map.setNumberOfRows();
				map.setNumberOfColumns();
				map.createMap();
				player.chooseCharacter(g.getSelection().getActionCommand());
				player.setName(nameField.getText());
				player.chooseEnergy(energySlider.getValue());
				MainGame.num++;
				self.play();
				setVisible(false);
			}
		});
		pack();
		// centers the frame on screen
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/*
	 * Method creating the frame which gives the user the choice between starting a
	 * new game or exiting.
	 */
	private void anotherOne(String yes, String no) {
		// frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Text or GUI");
		setPreferredSize(new Dimension(250, 400));
		// panel with gridlayout, 3 rows and 1 column
		JPanel decisionPanel = new JPanel(new GridLayout(3, 1, 0, 10));
		getContentPane().add(decisionPanel);
		JLabel chooseLabel = new JLabel("<html><div><center><h4>Castle Game</center></h4>Play again?</div></html>");
		chooseLabel.setPreferredSize(new Dimension(50, 100));
		decisionPanel.add(chooseLabel);
		JButton yesButton = new JButton(yes);
		yesButton.setPreferredSize(new Dimension(50, 100));
		decisionPanel.add(yesButton);
		JButton noButton = new JButton(no);
		noButton.setPreferredSize(new Dimension(50, 80));
		decisionPanel.add(noButton);

		/*
		 * Inner class listening to the yesButton being pressed
		 */
		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.print(MainGame.num);
				MainGame.num = 5;
				dispose();
				self.play();
			}
		});
		/*
		 * Inner class listening to the noButton being pressed
		 */
		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainGame.num++;
				dispose();
				self.play();
			}
		});
		pack();
		// centers frame on screen
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
