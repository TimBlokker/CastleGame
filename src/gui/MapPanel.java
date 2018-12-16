package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.InputMismatchException;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import logic.Character;
import logic.Map;

/*
 * This class is extending JPanel, making it a JPanel, an instance of this class can thus be added to a frame as being a JPanel.
 * This class is creating a JPanel which contains the JLabels which in turn contain the images.
 * Images are assigned to each JLabel depending on the position of the JLabel in the 2D array and depending on the corresponding tilevalue in the 2D array finalMapWithTiles.
 */
@SuppressWarnings("serial")
public class MapPanel extends JPanel {
	/*
	 * Variable used to set the Height of each image
	 */
	private static int TILE_HEIGHT;
	/*
	 * Variable used to set the Width of each image
	 */
	private static int TILE_WIDTH;
	/*
	 * labelGrid is a 2D array of JLabels which gets assigned images, depending on
	 * the corresponding value in the 2D array of the finalMapWithTiles it is
	 * decided on which image has to be assigned to which JLabel
	 */
	private JLabel[][] labelGrid;
	/*
	 * this ImageIcon contains the image of one of the three character
	 */
	private ImageIcon image;
	/*
	 * this ImageIcon contains the image which should be set on locations where the
	 * tileValue in the 2D array finalMapWithTiles is 0
	 */
	private ImageIcon image0;
	/*
	 * this ImageIcon contains the image which should be set on locations where the
	 * tileValue in the 2D array finalMapWithTiles is 1
	 */
	private ImageIcon image1;
	/*
	 * this ImageIcon contains the image which should be set on locations where the
	 * tileValue in the 2D array finalMapWithTiles is 2
	 */
	private ImageIcon image2;
	/*
	 * this ImageIcon contains the image which should be set on locations where the
	 * tileValue in the 2D array finalMapWithTiles is 3
	 */
	private ImageIcon image3;
	/*
	 * this ImageIcon contains the image which should be set on locations where the
	 * tileValue in the 2D array finalMapWithTiles is 4
	 */
	private ImageIcon image4;
	/*
	 * this ImageIcon contains the image which should be set on locations where the
	 * tileValue in the 2D array finalMapWithTiles is 4
	 */
	private ImageIcon image5;
	/*
	 * this ImageIcon contains the image which should be set on locations where the
	 * tileValue in the 2D array finalMapWithTiles is 6
	 */
	private ImageIcon image6;
	/*
	 * this ImageIcon contains the image which should be set on locations where the
	 * tileValue in the 2D array finalMapWithTiles is 7
	 */
	private ImageIcon image7;
	/*
	 * this ImageIcon contains the image which should be set on locations where the
	 * tileValue in the 2D array finalMapWithTiles is 50 (meaning it is not visible)
	 */
	private ImageIcon image50;
	/*
	 * instance of the class Map.
	 */
	private Map map;
	/*
	 * instance of the class Character.
	 */
	private Character player;
	/*
	 * Instance of the class MapFrame.
	 */
	private MapFrame mapFrame;

	/*
	 * This constructor contains the keyListener for the JPanel containing the
	 * labelGrid, this listens to the player attempting to move the player.
	 */
	public MapPanel(Map map1, Character player, MapFrame mapFrame1) {
		this.map = map1;
		this.player = player;
		this.mapFrame = mapFrame1;
		// initializing the labelGrid
		labelGrid = new JLabel[player.getFinalMapWithTiles().length][player.getFinalMapWithTiles()[0].length];
		// setting the Tile size, this is static and in case the frame is resized, the
		// labels are automatically resized but the images are not
		TILE_HEIGHT = 1300 / map.getDifficulty();
		TILE_WIDTH = 700 / map.getDifficulty();
		// needed to make the JLabel listen to key input
		setFocusable(true);
		/*
		 * Inner class listening to key input, actions are performed when using the
		 * arrow keys
		 */
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				try {
					if (key == KeyEvent.VK_UP) {
						player.movePlayer(8);
						mapFrame.createAndShowGui();
					} else if (key == KeyEvent.VK_DOWN) {
						player.movePlayer(5);
						mapFrame.createAndShowGui();
					} else if (key == KeyEvent.VK_LEFT) {
						player.movePlayer(4);
						mapFrame.createAndShowGui();
					} else if (key == KeyEvent.VK_RIGHT) {
						player.movePlayer(6);
						mapFrame.createAndShowGui();
					} else if (key == KeyEvent.VK_S) {
						player.movePlayer(1);
						mapFrame.createAndShowGui();
					} else if (key == KeyEvent.VK_E) {
						player.movePlayer(2);
						mapFrame.createAndShowGui();
					}
				} catch (ArrayIndexOutOfBoundsException ooe) {
					JOptionPane.showMessageDialog(null, "You reached a wall");
				} catch (InputMismatchException ime) {
					JOptionPane.showMessageDialog(null, "Please enter a valid input");
				}
			}

			// implements the methods from the KeyListener
			@Override
			public void keyReleased(KeyEvent e) {
			}

			// implements the method from the KeyListener
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		this.createAndShowMap();
	}

	/*
	 * This method is assigning images to ImageIcons, these ImageIcons are
	 */
	public void createAndShowMap() {
		/*
		 * This part of the code is used to repaint the panel after the player has
		 * moved, so that the user can see the player moving.
		 */
		@SuppressWarnings("unused")
		Graphics g = getGraphics();
		removeAll();
		revalidate();
		repaint();

		// assigning images to new ImageIcons
		if (player.getCharacter() == "Warrior") {
			image = new ImageIcon(getClass().getResource("/images/braveheart1.png"));
		} else if (player.getCharacter() == "Archer") {
			image = new ImageIcon(getClass().getResource("/images/archer1.png"));
		} else if (player.getCharacter() == "Monk") {
			image = new ImageIcon(getClass().getResource("/images/monk4.jpg"));
		}
		image0 = new ImageIcon(getClass().getResource("/images/grass1.png"));
		image1 = new ImageIcon(getClass().getResource("/images/stone3.png"));
		image2 = new ImageIcon(getClass().getResource("/images/book.jpg"));
		image3 = new ImageIcon(getClass().getResource("/images/tournament.jpg"));
		image4 = new ImageIcon(getClass().getResource("/images/opponent2.jpg"));
		image5 = new ImageIcon(getClass().getResource("/images/potion.jpg"));
		image6 = new ImageIcon(getClass().getResource("/images/key.jpg"));
		image7 = new ImageIcon(getClass().getResource("/images/castle.png"));
		image50 = new ImageIcon(getClass().getResource("/images/black.jpg"));

		// making Images out of the imageIcons
		Image img = image.getImage();
		Image img0 = image0.getImage();
		Image img1 = image1.getImage();
		Image img2 = image2.getImage();
		Image img3 = image3.getImage();
		Image img4 = image4.getImage();
		Image img5 = image5.getImage();
		Image img6 = image6.getImage();
		Image img7 = image7.getImage();
		Image img50 = image50.getImage();

		/*
		 * resizing image to approximate label size and store image in newImg variables.
		 * This allows the images to be the correct size in full-screen mode, if the
		 * screen is resized the images, however, stay the same way.
		 */
		Image newImg0 = img0.getScaledInstance(TILE_HEIGHT, TILE_WIDTH, Image.SCALE_SMOOTH);
		ImageIcon image0 = new ImageIcon(newImg0);
		Image newImg1 = img1.getScaledInstance(TILE_HEIGHT, TILE_WIDTH, Image.SCALE_SMOOTH);
		ImageIcon image1 = new ImageIcon(newImg1);
		Image newImg2 = img2.getScaledInstance(TILE_HEIGHT, TILE_WIDTH, Image.SCALE_SMOOTH);
		ImageIcon image2 = new ImageIcon(newImg2);
		Image newImg3 = img3.getScaledInstance(TILE_HEIGHT, TILE_WIDTH, Image.SCALE_SMOOTH);
		ImageIcon image3 = new ImageIcon(newImg3);
		Image newImg4 = img4.getScaledInstance(TILE_HEIGHT, TILE_WIDTH, Image.SCALE_SMOOTH);
		ImageIcon image4 = new ImageIcon(newImg4);
		Image newImg5 = img5.getScaledInstance(TILE_HEIGHT, TILE_WIDTH, Image.SCALE_SMOOTH);
		ImageIcon image5 = new ImageIcon(newImg5);
		Image newImg6 = img6.getScaledInstance(TILE_HEIGHT, TILE_WIDTH, Image.SCALE_SMOOTH);
		ImageIcon image6 = new ImageIcon(newImg6);
		Image newImg7 = img7.getScaledInstance(TILE_HEIGHT, TILE_WIDTH, Image.SCALE_SMOOTH);
		ImageIcon image7 = new ImageIcon(newImg7);
		Image newImg50 = img50.getScaledInstance(TILE_HEIGHT, TILE_WIDTH, Image.SCALE_SMOOTH);
		ImageIcon image50 = new ImageIcon(newImg50);

		/*
		 * Loop to iterate over the finalMapWithTiles 2D array and depending on the
		 * tilevalue at a given row or column one of the 9 switch cases is invoked. In
		 * the switch cases new JLabels are created, added to the labelGrid array and
		 * the resized images are added to the JLabel.
		 */
		setLayout(new GridLayout(player.getFinalMapWithTiles().length, player.getFinalMapWithTiles()[0].length));
		for (int r = 0; r < player.getFinalMapWithTiles().length; r++) {
			for (int c = 0; c < player.getFinalMapWithTiles()[r].length; c++) {
				int tilevalue = player.getFinalMapWithTiles()[r][c];
				switch (tilevalue) {

				case 0:
					labelGrid[r][c] = new JLabel(image0, JLabel.CENTER);
					labelGrid[r][c].setIcon(image0);
					break;
				case 1:
					labelGrid[r][c] = new JLabel(image1, JLabel.CENTER);
					labelGrid[r][c].setIcon(image1);
					break;
				case 2:
					labelGrid[r][c] = new JLabel(image2, JLabel.CENTER);
					labelGrid[r][c].setIcon(image2);
					break;
				case 3:
					labelGrid[r][c] = new JLabel(image3, JLabel.CENTER);
					labelGrid[r][c].setIcon(image3);
					break;
				case 4:
					labelGrid[r][c] = new JLabel(image4, JLabel.CENTER);
					labelGrid[r][c].setIcon(image4);
					break;
				case 5:
					labelGrid[r][c] = new JLabel(image5, JLabel.CENTER);
					labelGrid[r][c].setIcon(image5);
					break;
				case 6:
					labelGrid[r][c] = new JLabel(image6, JLabel.CENTER);
					labelGrid[r][c].setIcon(image6);
					break;
				case 7:
					labelGrid[r][c] = new JLabel(image7, JLabel.CENTER);
					labelGrid[r][c].setIcon(image7);
					break;
				// invisible part of map
				case 50:
					labelGrid[r][c] = new JLabel(image50, JLabel.CENTER);
					labelGrid[r][c].setIcon(image50);
					break;
				}
				/*
				 * Assigning the player image to the map, based on the location returned by the
				 * getRow() and getColumn() methods.
				 */
				labelGrid[player.getRow()][player.getColumn()] = new JLabel(image, JLabel.CENTER);
				Image newImg = img.getScaledInstance(TILE_HEIGHT, TILE_WIDTH, Image.SCALE_SMOOTH);
				ImageIcon image = new ImageIcon(newImg);
				labelGrid[player.getRow()][player.getColumn()].setIcon(image);

				// setting a white border around tiles
				labelGrid[r][c].setBorder(BorderFactory.createLineBorder(Color.white));
				// add the labelGrid containing the JLabels, containing the images to the JPanel
				// (which is the instance of this class).
				add(labelGrid[r][c]);
			}
		}
	}
}
