package nnb.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import nnb.GameController;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = -211308132756540185L;
    private final String gameName = "Bataille Navale";
    private final int GRID_SIZE = 50;

    private Point selectedCoords = new Point();

    private ImageIcon backgroundImage;
    private ImageIcon selectedImage;
    private ImageIcon shipImage;
    private ImageIcon expImage;
    private ImageIcon missImage;

    private GameController controller;

    private JPanel cards;
    private CardLayout cl;

    // New game selector UI
    private JPanel newGameSelectorPanel;
    private JLabel titleLabel;
    private JLabel titleAILabel;
    private JButton playButtonAI;
    private JLabel titleHumanLabel;
    private JTextField player1TextField;
    private JLabel vsLabel;
    private JTextField player2TextField;
    private JButton playButtonHuman;

    // First round UI
    private JPanel firstRoundPanel;
    private JLabel firstplayerLabel;
    private JLabel firstroundLabel;
    private JLabel shipsLabel;
    private ButtonGroup shipsButtonGroup;
    private JRadioButton[] shipsRadioButtons = new JRadioButton[5];
    private JLabel orientationLabel;
    private ButtonGroup orientationButtonGroup;
    private JRadioButton horizonRodioButton;
    private JRadioButton verticalRadioButton;
    private JLabel gridLabel;
    private JButton endfirstRoundButton;
    private JButton placeShipButton;
    private JButton undoButton;
    private JToggleButton[][] firstRoundGridTButton = new JToggleButton[10][10];

    // Round UI
    private JPanel roundPanel;
    private JLabel playerLabel;
    private JLabel roundLabel;
    private JLabel ownGridLabel;
    private JLabel ownMissLabel;
    private JLabel ownHitLabel;
    private JToggleButton lastCaseToggleButton;
    private JLabel enemyGridLabel;
    private JLabel enemyMissLabel;
    private JLabel enemyHitLabel;
    private JButton endRoundButton;
    private JToggleButton[][] ownGridTButton = new JToggleButton[10][10];
    private JToggleButton[][] enemyGridTButton = new JToggleButton[10][10];

    // Inter round UI
    private JPanel interRoundPanel;
    private JLabel roundNumLabel;
    private JLabel playerNameLabel;
    private JButton startButton;

    // End game UI
    private JPanel endGamePanel;
    private JLabel endTitleLabel;
    private JLabel descriptionLabel;
    private JLabel creditsLabel;
    private JButton restartButton;

    // Menu bar
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem newGameMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu helpMenu;
    private JMenuItem aboutMenuItem;

    public MainFrame() {
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLookAndFeel();
        loadImages();
        controller = new GameController(this);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        createMenu();
        createUI();
        addCards();

        pack();
        setLocationRelativeTo(null);

        showNewGameSelectorUI();
    }

    private void createUI() {
        createNewGameSelectorUI();
        createFirstRoundUI();
        createRoundUI();
        createInterRoundUI();
        createEndGameUI();
    }

    private JPanel createPanel(JPanel panel) {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(1100, 700));
        panel.setMinimumSize(new Dimension(1100, 700));
        return panel;
    }

    private void createNewGameSelectorUI() {
        newGameSelectorPanel = createPanel(newGameSelectorPanel);

        titleLabel = new JLabel("Mode de jeu");
        titleLabel.setFont(new Font("Sans-Serif", Font.BOLD, 30));
        titleLabel.setBounds(0, 0, 1100, 100);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        newGameSelectorPanel.add(titleLabel);

        titleAILabel = new JLabel("Contre ordinateur");
        titleAILabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        titleAILabel.setBounds(0, 100, 550, 50);
        titleAILabel.setHorizontalAlignment(JLabel.CENTER);
        newGameSelectorPanel.add(titleAILabel);

        playButtonAI = new JButton("Jouer");
        playButtonAI.setBounds(225, 200, 100, 50);
        playButtonAI.addActionListener(controller);
        playButtonAI.setActionCommand("start_ai");
        newGameSelectorPanel.add(playButtonAI);

        titleHumanLabel = new JLabel("Joueur contre Joueur");
        titleHumanLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        titleHumanLabel.setBounds(550, 100, 550, 50);
        titleHumanLabel.setHorizontalAlignment(JLabel.CENTER);
        newGameSelectorPanel.add(titleHumanLabel);

        player1TextField = new JTextField("JOUEUR1");
        player1TextField.setBounds(700, 150, 250, 50);
        player1TextField.setHorizontalAlignment(JLabel.CENTER);
        newGameSelectorPanel.add(player1TextField);

        vsLabel = new JLabel("VS");
        vsLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        vsLabel.setBounds(550, 200, 550, 50);
        vsLabel.setHorizontalAlignment(JLabel.CENTER);
        newGameSelectorPanel.add(vsLabel);

        player2TextField = new JTextField("JOUEUR2");
        player2TextField.setBounds(700, 250, 250, 50);
        player2TextField.setHorizontalAlignment(JLabel.CENTER);
        newGameSelectorPanel.add(player2TextField);

        playButtonHuman = new JButton("Jouer Local");
        playButtonHuman.setBounds(775, 300, 100, 50);
        playButtonHuman.addActionListener(controller);
        playButtonHuman.setActionCommand("start_human");
        newGameSelectorPanel.add(playButtonHuman);

    }

    public void showServerCreationError() {
        JOptionPane.showMessageDialog(this,
                "Impossible de créer le serveur\nVeuillez vérifier que vous possédez les autorisations necessaires.",
                "Erreur", JOptionPane.WARNING_MESSAGE);
    }

    public void showServerConnectionError() {
        JOptionPane.showMessageDialog(this,
                "Impossible de joindre le serveur\nVeuillez vérifier que l'adresse entrée est correcte.", "Erreur",
                JOptionPane.WARNING_MESSAGE);
    }

    private void createFirstRoundUI() {
        firstRoundPanel = createPanel(firstRoundPanel);

        firstplayerLabel = new JLabel();
        firstplayerLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        firstplayerLabel.setBounds(0, 0, 100, 50);
        firstplayerLabel.setHorizontalAlignment(JLabel.CENTER);
        firstRoundPanel.add(firstplayerLabel);

        firstroundLabel = new JLabel("Round 0: Préparation!");
        firstroundLabel.setFont(new Font("Sans-Serif", Font.BOLD, 20));
        firstroundLabel.setBounds(300, 0, 500, 50);
        firstroundLabel.setHorizontalAlignment(JLabel.CENTER);
        firstRoundPanel.add(firstroundLabel);

        shipsLabel = new JLabel("Vos navires");
        shipsLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        shipsLabel.setBounds(50, 50, 200, 50);
        firstRoundPanel.add(shipsLabel);

        shipsButtonGroup = new ButtonGroup();

        shipsRadioButtons[4] = new JRadioButton("Porte-Avion");
        shipsRadioButtons[4].setFont(new Font("Sans-Serif", Font.PLAIN, 15));
        shipsRadioButtons[4].setBounds(50, 100, 200, 50);
        firstRoundPanel.add(shipsRadioButtons[4]);
        shipsButtonGroup.add(shipsRadioButtons[4]);

        shipsRadioButtons[3] = new JRadioButton("Croiseur");
        shipsRadioButtons[3].setFont(new Font("Sans-Serif", Font.PLAIN, 15));
        shipsRadioButtons[3].setBounds(50, 150, 200, 50);
        firstRoundPanel.add(shipsRadioButtons[3]);
        shipsButtonGroup.add(shipsRadioButtons[3]);

        shipsRadioButtons[2] = new JRadioButton("Contre torpilleur");
        shipsRadioButtons[2].setFont(new Font("Sans-Serif", Font.PLAIN, 15));
        shipsRadioButtons[2].setBounds(50, 200, 200, 50);
        firstRoundPanel.add(shipsRadioButtons[2]);
        shipsButtonGroup.add(shipsRadioButtons[2]);

        shipsRadioButtons[1] = new JRadioButton("Sous-marin");
        shipsRadioButtons[1].setFont(new Font("Sans-Serif", Font.PLAIN, 15));
        shipsRadioButtons[1].setBounds(50, 250, 200, 50);
        firstRoundPanel.add(shipsRadioButtons[1]);
        shipsButtonGroup.add(shipsRadioButtons[1]);

        shipsRadioButtons[0] = new JRadioButton("Torpilleur");
        shipsRadioButtons[0].setFont(new Font("Sans-Serif", Font.PLAIN, 15));
        shipsRadioButtons[0].setBounds(50, 300, 200, 50);
        firstRoundPanel.add(shipsRadioButtons[0]);
        shipsButtonGroup.add(shipsRadioButtons[0]);

        orientationLabel = new JLabel("Orientation");
        orientationLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        orientationLabel.setBounds(50, 350, 200, 50);
        firstRoundPanel.add(orientationLabel);

        orientationButtonGroup = new ButtonGroup();

        horizonRodioButton = new JRadioButton("Horizontal");
        horizonRodioButton.setFont(new Font("Sans-Serif", Font.PLAIN, 15));
        horizonRodioButton.setBounds(50, 400, 100, 50);
        firstRoundPanel.add(horizonRodioButton);
        orientationButtonGroup.add(horizonRodioButton);
        horizonRodioButton.setSelected(true);

        verticalRadioButton = new JRadioButton("Vertical");
        verticalRadioButton.setFont(new Font("Sans-Serif", Font.PLAIN, 15));
        verticalRadioButton.setBounds(50, 450, 100, 50);
        firstRoundPanel.add(verticalRadioButton);
        orientationButtonGroup.add(verticalRadioButton);

        gridLabel = new JLabel("Votre flotte");
        gridLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        gridLabel.setBounds(500, 50, 200, 50);
        firstRoundPanel.add(gridLabel);
        createGridFleet(500, 100, firstRoundPanel, 0);

        placeShipButton = new JButton("Placer");
        placeShipButton.setBounds(450, 625, 100, 50);
        placeShipButton.addActionListener(controller);
        placeShipButton.setActionCommand("place_ship");
        firstRoundPanel.add(placeShipButton);

        undoButton = new JButton("Annuler");
        undoButton.setBounds(600, 625, 100, 50);
        undoButton.addActionListener(controller);
        undoButton.setActionCommand("undo_ship");
        firstRoundPanel.add(undoButton);

        endfirstRoundButton = new JButton("Terminé");
        endfirstRoundButton.setBounds(950, 625, 100, 50);
        endfirstRoundButton.addActionListener(controller);
        endfirstRoundButton.setActionCommand("confrim_fleet");
        firstRoundPanel.add(endfirstRoundButton);
    }

    public boolean getIsShipIsHorizontal() {
        return horizonRodioButton.isSelected();
    }

    public int getSelectedShipSize() {
        for (int i = 0; i < shipsRadioButtons.length; i++) {
            if (shipsRadioButtons[i].isSelected())
                return i + 1;
        }
        return -1;
    }

    public void showPlacementError(String error) {
        JOptionPane.showMessageDialog(this, "Impossible de placer le bateau à  ces coordonnées\n" + error, "Erreur",
                JOptionPane.WARNING_MESSAGE);
    }

    public void confirmFleetMessage() {
        int answer = JOptionPane.showConfirmDialog(this,
                "Êtes-vous sûr de vouloir garder cette configuration?\nVous ne pourrez plus la modifier",
                "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (answer == JOptionPane.YES_OPTION)
            controller.endRound();
    }

    public void showFleetError() {
        JOptionPane.showMessageDialog(this,
                "Vous n'avez pas placé tous vos bateux\nVeuillez coninuer une fois les 5 bateaux positionnés sur la grille",
                "Confirmation", JOptionPane.WARNING_MESSAGE);
    }

    private void createRoundUI() {
        roundPanel = createPanel(roundPanel);

        playerLabel = new JLabel();
        playerLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        playerLabel.setBounds(0, 0, 100, 50);
        playerLabel.setHorizontalAlignment(JLabel.CENTER);
        roundPanel.add(playerLabel);

        roundLabel = new JLabel();
        roundLabel.setFont(new Font("Sans-Serif", Font.BOLD, 20));
        roundLabel.setBounds(300, 0, 500, 50);
        roundLabel.setHorizontalAlignment(JLabel.CENTER);
        roundPanel.add(roundLabel);

        ownGridLabel = new JLabel("Votre flotte");
        ownGridLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        ownGridLabel.setBounds(25, 50, 200, 50);
        roundPanel.add(ownGridLabel);
        createGridFleet(25, 100, roundPanel, 1);

        ownMissLabel = new JLabel("", backgroundImage, JLabel.LEFT);
        ownMissLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        ownMissLabel.setBounds(25, 625, 100, 50);
        roundPanel.add(ownMissLabel);

        ownHitLabel = new JLabel("", selectedImage, JLabel.LEFT);
        ownHitLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        ownHitLabel.setBounds(125, 625, 100, 50);
        roundPanel.add(ownHitLabel);

        enemyGridLabel = new JLabel("Zone adverse");
        enemyGridLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        enemyGridLabel.setBounds(575, 50, 200, 50);
        roundPanel.add(enemyGridLabel);
        createGridFleet(575, 100, roundPanel, 2);

        enemyMissLabel = new JLabel("", backgroundImage, JLabel.LEFT);
        enemyMissLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        enemyMissLabel.setBounds(575, 625, 100, 50);
        roundPanel.add(enemyMissLabel);

        enemyHitLabel = new JLabel("", selectedImage, JLabel.LEFT);
        enemyHitLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        enemyHitLabel.setBounds(675, 625, 100, 50);
        roundPanel.add(enemyHitLabel);

        endRoundButton = new JButton("Tirer!");
        endRoundButton.setBounds(950, 625, 100, 50);
        endRoundButton.addActionListener(controller);
        endRoundButton.setActionCommand("fire");
        roundPanel.add(endRoundButton);
    }

    private void createGridFleet(int posX, int posY, JPanel panel, int type) {
        final int width = 10;
        final int height = 10;
        JPanel p = new JPanel(new GridLayout(width, height));

        for (int i = 0; i < height * width; i++) {
            final int h = height;
            final int w = width;
            final int j = i;
            final int t = type;
            if (type == 0) {
                firstRoundGridTButton[i % width][i / height] = new JToggleButton(backgroundImage);
                firstRoundGridTButton[i % width][i / height].setPreferredSize(new Dimension(GRID_SIZE, GRID_SIZE));
                firstRoundGridTButton[i % width][i / height].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        selectCell(j % w, j / h, t);
                    }
                });
                p.add(firstRoundGridTButton[i % width][i / height]);
            } else if (type == 1) {
                ownGridTButton[i % width][i / height] = new JToggleButton(backgroundImage);
                ownGridTButton[i % width][i / height].setPreferredSize(new Dimension(GRID_SIZE, GRID_SIZE));
                p.add(ownGridTButton[i % width][i / height]);
            } else if (type == 2) {
                enemyGridTButton[i % width][i / height] = new JToggleButton(backgroundImage);
                enemyGridTButton[i % width][i / height].setPreferredSize(new Dimension(GRID_SIZE, GRID_SIZE));
                enemyGridTButton[i % width][i / height].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        selectCell(j % w, j / h, t);
                    }
                });
                p.add(enemyGridTButton[i % width][i / height]);
            }
        }
        p.setBounds(posX, posY, GRID_SIZE * width, GRID_SIZE * height);
        panel.add(p);
    }

    public Point getSelectedCoords() {
        return selectedCoords;
    }

    private void selectCell(int x, int y, int type) {
        if (lastCaseToggleButton != null)
            lastCaseToggleButton.setIcon(backgroundImage);
        if (type == 0) {
            if (firstRoundGridTButton[x][y].getIcon() == backgroundImage) {
                System.out.println("Clicked: (" + x + " " + y + ")");
                firstRoundGridTButton[x][y].setIcon(selectedImage);
                lastCaseToggleButton = firstRoundGridTButton[x][y];
            }
        } else if (type == 2) {
            if (enemyGridTButton[x][y].getIcon() == backgroundImage) {
                System.out.println("Clicked: (" + x + " " + y + ")");
                enemyGridTButton[x][y].setIcon(selectedImage);
                lastCaseToggleButton = enemyGridTButton[x][y];
            }
        }
        selectedCoords = new Point(x, y);
    }

    public void showRoundRecap(String message) {
        JOptionPane.showConfirmDialog(this, message + "\n\nCliquez sur OK pour terminer le tour", "Récapitulatif",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        controller.endRound();
    }

    private void createInterRoundUI() {
        interRoundPanel = createPanel(interRoundPanel);

        roundNumLabel = new JLabel();
        roundNumLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 30));
        roundNumLabel.setBounds(0, 50, 1100, 100);
        roundNumLabel.setHorizontalAlignment(JLabel.CENTER);
        interRoundPanel.add(roundNumLabel);

        playerNameLabel = new JLabel();
        playerNameLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        playerNameLabel.setBounds(0, 100, 1100, 100);
        playerNameLabel.setHorizontalAlignment(JLabel.CENTER);
        interRoundPanel.add(playerNameLabel);

        startButton = new JButton("Commencer");
        startButton.setBounds(475, 200, 150, 50);
        startButton.addActionListener(controller);
        startButton.setActionCommand("start_round");
        interRoundPanel.add(startButton);
    }

    private void createEndGameUI() {
        endGamePanel = createPanel(endGamePanel);

        endTitleLabel = new JLabel("Partie terminée");
        endTitleLabel.setFont(new Font("Sans-Serif", Font.BOLD, 50));
        endTitleLabel.setForeground(Color.BLUE);
        endTitleLabel.setBounds(300, 100, 500, 100);
        endTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        endGamePanel.add(endTitleLabel);

        descriptionLabel = new JLabel("Le joueur XXX à  éliminé la flotte de XXX");
        descriptionLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 30));
        descriptionLabel.setBounds(200, 200, 700, 300);
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
        endGamePanel.add(descriptionLabel);

        creditsLabel = new JLabel("Bien joué!");
        creditsLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        creditsLabel.setBounds(300, 500, 500, 50);
        creditsLabel.setHorizontalAlignment(JLabel.CENTER);
        endGamePanel.add(creditsLabel);

        restartButton = new JButton("Rejouer");
        restartButton.setBounds(500, 600, 100, 50);
        restartButton.addActionListener(controller);
        restartButton.setActionCommand("restart");
        endGamePanel.add(restartButton);
    }

    private void createMenu() {
        menuBar = new JMenuBar();
        add(menuBar);

        menu = new JMenu("Menu");
        menuBar.add(menu);
        newGameMenuItem = new JMenuItem("Nouvelle Partie");
        newGameMenuItem.addActionListener(controller);
        newGameMenuItem.setActionCommand("confirm_restart");
        menu.add(newGameMenuItem);

        exitMenuItem = new JMenuItem("Quitter");
        exitMenuItem.addActionListener(controller);
        exitMenuItem.setActionCommand("exit");
        menu.add(exitMenuItem);

        helpMenu = new JMenu("Aide");
        menuBar.add(helpMenu);
        aboutMenuItem = new JMenuItem("A propos");
        aboutMenuItem.addActionListener(controller);
        aboutMenuItem.setActionCommand("credits");
        helpMenu.add(aboutMenuItem);

        setJMenuBar(menuBar);
    }

    private void addCards() {
        cards = new JPanel(new CardLayout());
        cards.add(newGameSelectorPanel, "newGameSelector");
        cards.add(firstRoundPanel, "firstRound");
        cards.add(roundPanel, "round");
        cards.add(interRoundPanel, "interRound");
        cards.add(endGamePanel, "endGame");
        add(cards);

        cl = (CardLayout) cards.getLayout();
    }

    public void showNewGameSelectorUI() {
        setTitle(gameName + " | Selection du mode de jeu");
        cl.show(cards, "newGameSelector");
    }

    public void showFirstRoundUI(String playerName) {
        setTitle(gameName + " | Préparation");
        firstplayerLabel.setText(playerName);
        resetShipButtons();
        resetShipsButtons();
        cl.show(cards, "firstRound");
    }

    private void resetShipButtons() {
        for (JRadioButton b : shipsRadioButtons) {
            b.setEnabled(true);
        }
    }

    public void setShipButtonsState(int[] placed) {
        resetShipButtons();
        for (int s : placed) {
            shipsRadioButtons[s - 1].setEnabled(false);
            shipsButtonGroup.clearSelection();
        }
    }

    public void resetShipsButtons() {
        for (JToggleButton[] button : firstRoundGridTButton) {
            for (JToggleButton b : button) {
                b.setEnabled(true);
            }
        }
    }

    public void updateShipsFirstRound(List<Point[]> shipList) {
        resetShipsButtons();
        for (Point[] coord : shipList) {
            for (int i = 0; i < coord.length; i++) {
                int x = coord[i].x;
                int y = coord[i].y;
                firstRoundGridTButton[x][y].setDisabledIcon(shipImage);
                firstRoundGridTButton[x][y].setDisabledSelectedIcon(shipImage);
                firstRoundGridTButton[x][y].setEnabled(false);
            }
        }
    }

    public void updateShipsRound(List<Point[]> shipList) {
        for (JToggleButton[] button : ownGridTButton) {
            for (JToggleButton b : button) {
                b.setEnabled(true);
            }
        }
        for (Point[] coord : shipList) {
            for (Point p : coord) {
                if (p.x != -1 && p.y != -1) {
                    ownGridTButton[p.x][p.y].setDisabledIcon(shipImage);
                    ownGridTButton[p.x][p.y].setDisabledSelectedIcon(shipImage);
                    ownGridTButton[p.x][p.y].setEnabled(false);
                }
            }
        }
    }

    public void updateHitsOwn(List<Point> hits, List<Point> miss) {
        for (Point p : hits) {
            ownGridTButton[p.x][p.y].setDisabledIcon(expImage);
            ownGridTButton[p.x][p.y].setDisabledSelectedIcon(expImage);
            ownGridTButton[p.x][p.y].setEnabled(false);
        }
        for (Point p : miss) {
            ownGridTButton[p.x][p.y].setDisabledIcon(missImage);
            ownGridTButton[p.x][p.y].setDisabledSelectedIcon(missImage);
            ownGridTButton[p.x][p.y].setEnabled(false);
        }
    }

    public void updateHitsEnemy(List<Point> hits, List<Point> miss) {
        for (Point p : hits) {
            enemyGridTButton[p.x][p.y].setDisabledIcon(expImage);
            enemyGridTButton[p.x][p.y].setDisabledSelectedIcon(expImage);
            enemyGridTButton[p.x][p.y].setEnabled(false);
        }
        for (Point p : miss) {
            enemyGridTButton[p.x][p.y].setDisabledIcon(missImage);
            enemyGridTButton[p.x][p.y].setDisabledSelectedIcon(missImage);
            enemyGridTButton[p.x][p.y].setEnabled(false);
        }
    }

    public void resetRoundGrids() {
        for (JToggleButton[] button : ownGridTButton) {
            for (JToggleButton b : button) {
                b.setEnabled(true);
            }
        }
        for (JToggleButton[] button : enemyGridTButton) {
            for (JToggleButton b : button) {
                b.setEnabled(true);
            }
        }
    }

    public void showRoundUI(String playerName, int round, List<Point> hitO, List<Point> missO, List<Point> hitE,
            List<Point> missE, List<Point[]> shipList) {
        setTitle(gameName + " | Round - " + playerName);
        playerLabel.setText(playerName);
        roundLabel.setText("Round " + round + ": Détruisez la flotte adverse!");
        ownMissLabel.setText(missO.size() + "");
        ownHitLabel.setText(hitO.size() + "");
        enemyMissLabel.setText(missE.size() + "");
        enemyHitLabel.setText(hitE.size() + "");

        resetRoundGrids();
        updateShipsRound(shipList);
        updateHitsOwn(hitO, missO);
        updateHitsEnemy(hitE, missE);

        cl.show(cards, "round");
    }

    public void showInterRoundUI(String playerName, int round) {
        setTitle(gameName + " | Changement de joueur");
        roundNumLabel.setText("Round " + round);
        playerNameLabel.setText("Tour de : " + playerName);
        cl.show(cards, "interRound");
    }

    public void showEndGameUI(String winner, String loser, boolean draw) {
        setTitle(gameName + " | Fin de partie");
        if (!draw)
            descriptionLabel.setText(winner + " a  éliminé la flotte de " + loser);
        else
            descriptionLabel.setText("Egalité! Pas de vainqueur.");
        cl.show(cards, "endGame");
    }

    public String getPlayer1Name() {
        return player1TextField.getText();
    }

    public String getPlayer2Name() {
        return player2TextField.getText();
    }

    public void showCredits() {
        JOptionPane.showMessageDialog(this,
                "Bataille navale\nProjet ISN 2016-2017\nCréé par Arnaud, Guilhem et Antoine", "Credits",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void newGameConfirm() {
        int answer = JOptionPane.showConfirmDialog(this,
                "Voulez vous vraiment commencer une nouvelle partie?\nTout progrès sera perdu", "Nouvelle partie?",
                JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION)
            controller.restart();
    }

    private void loadImages() {
        selectedImage = new ImageIcon(getClass().getResource("/resources/water_selected.png"));
        backgroundImage = new ImageIcon(getClass().getResource("/resources/water.png"));
        shipImage = new ImageIcon(getClass().getResource("/resources/ship.png"));
        expImage = new ImageIcon(getClass().getResource("/resources/explosion.png"));
        missImage = new ImageIcon(getClass().getResource("/resources/missed.png"));
    }

    private void setLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
        }
    }
}
