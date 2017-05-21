package nnb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nnb.ui.MainFrame;

public class GameController implements ActionListener {

    private PlayerManager playerMan;
    private AIManager aiMan;
    private MainFrame ui;

    private int switchCounter;

    public GameController(MainFrame frame) {
        ui = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {
        case "start_ai":
            startGame();
            break;
        case "start_human":
            startGame(ui.getPlayer1Name(), ui.getPlayer2Name());
            break;
        case "start_round":
            startRound();
            break;
        case "confrim_fleet":
            if (playerMan.getCurrentPlayerFleet().getShipPlacedNumber() < 5)
            ui.showFleetError();
            else
            ui.confirmFleetMessage();
            break;
        case "confirm_restart":
            ui.newGameConfirm();
            break;
        case "place_ship":
            selectPlacementError(playerMan.getCurrentPlayerFleet().placeShip(ui.getSelectedCoords(),
                    ui.getSelectedShipSize(), ui.getIsShipIsHorizontal()));
            ui.updateShipsFirstRound(playerMan.getCurrentPlayerFleet().getShipList());
            ui.setShipButtonsState(playerMan.getCurrentPlayerFleet().getShipsPlacedLenght());
            break;
        case "undo_ship":
            if (playerMan.getCurrentPlayerFleet().removeLastShip()) {
                ui.updateShipsFirstRound(playerMan.getCurrentPlayerFleet().getShipList());
                ui.setShipButtonsState(playerMan.getCurrentPlayerFleet().getShipsPlacedLenght());
            }
            break;
        case "fire":
            ui.showRoundRecap(setRoundRecapMessage(playerMan.getEnemyPlayerFleet().checkCell(ui.getSelectedCoords())));
            break;
        case "credits":
            ui.showCredits();
            break;
        case "restart":
            restart();
            break;
        case "exit":
            ui.dispose();
            break;
        }
    }

    private String setRoundRecapMessage(int val) {
        String message = "";
        switch (val) {
        case 0:
            message = "Dommage\nVous avez raté votre tir";
            break;
        case 1:
            message = "Bravo\nVous avez touché un bateau adverse";
            break;
        case 2:
            message = "Excellent\nVous avez coulé un bateau adverse";
            break;
        }
        return message;
    }

    private void selectPlacementError(int value) {
        switch (value) {
        case 1:
            ui.showPlacementError("Place insuffisante\nVeuillez selectionner une autre case.");
            break;
        case 2:
            ui.showPlacementError("Pas de bateau sélectionné");
            break;
        }
    }

    public void restart() {
        ui.showNewGameSelectorUI();
    }

    private void startGame() {
        switchCounter = 0;
        playerMan = new PlayerManager();
        aiMan = new AIManager();
        ui.showFirstRoundUI(playerMan.getActualPlayerName());
    }

    private void startGame(String player1, String player2) {
        switchCounter = 0;
        playerMan = new PlayerManager(player1, player2);
        ui.showFirstRoundUI(playerMan.getActualPlayerName());
    }

    public void endRound() {
        // Test if game is ended
        switchPlayers();
        if (playerMan.getActualPlayerName() == "AI") {
            // Tour AI
            if (getCurrentRound() == 0)
                placeAIShips();
            else
                aiMan.setLastShot(playerMan.getEnemyPlayerFleet().checkCell(aiMan.getFireCoords()));

            switchPlayers();
            startRound();
        } else
            ui.showInterRoundUI(playerMan.getActualPlayerName(), getCurrentRound());

        if (switchCounter % 2 == 0)
            checkGameEnd();
    }

    private void placeAIShips() {
        while (playerMan.getCurrentPlayerFleet().getShipPlacedNumber() < 5) {
            playerMan.getCurrentPlayerFleet().placeShip(aiMan.getRdmCoords(), aiMan.getShipSize(),
                    aiMan.getShipOrientation());
        }
        System.out.println("AI placed " + playerMan.getCurrentPlayerFleet().getShipPlacedNumber() + " ships");
    }

    private void startRound() {
        if (getCurrentRound() == 0) {
            ui.resetShipsButtons();
            ui.showFirstRoundUI(playerMan.getActualPlayerName());
        } else
            ui.showRoundUI(playerMan.getActualPlayerName(), getCurrentRound(),
                    playerMan.getCurrentPlayerFleet().getHits(), playerMan.getCurrentPlayerFleet().getMiss(),
                    playerMan.getEnemyPlayerFleet().getHits(), playerMan.getEnemyPlayerFleet().getMiss(),
                    playerMan.getCurrentPlayerFleet().getShipList());
    }

    private void switchPlayers() {
        switchCounter++;
        playerMan.SwitchPlayers();
    }

    private void checkGameEnd() {
        if (playerMan.getCurrentPlayerFleet().isFleetDefeated())
            ui.showEndGameUI(playerMan.getEnemyPlayerName(), playerMan.getActualPlayerName(),
                    playerMan.getCurrentPlayerFleet().isFleetDefeated()
                            && playerMan.getEnemyPlayerFleet().isFleetDefeated());
        else if (playerMan.getEnemyPlayerFleet().isFleetDefeated())
            ui.showEndGameUI(playerMan.getActualPlayerName(), playerMan.getEnemyPlayerName(),
                    playerMan.getCurrentPlayerFleet().isFleetDefeated()
                            && playerMan.getEnemyPlayerFleet().isFleetDefeated());
    }

    private int getCurrentRound() {
        return switchCounter / 2;
    }

}
