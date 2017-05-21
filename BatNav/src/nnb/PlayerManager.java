package nnb;

public class PlayerManager {

	
	private int actualPlayer = 1; // 0 = AI, 1 = p1, 2 = p2
	
	private String[] names = new String[3];
    private FleetManager[] playersFleets = new FleetManager[3];

    

    public PlayerManager(String p1, String p2) {
    	names[1] = p1;
        playersFleets[1] = new FleetManager();
        names[2] = p2;
        playersFleets[2] = new FleetManager();
    }

    public PlayerManager() {
    	names[1] = "Joueur";
        playersFleets[1] = new FleetManager();
        names[2] = null;
        playersFleets[0] = new FleetManager();
        names[0] = "AI";
    }

    public String getActualPlayerName() {
    	return names[actualPlayer];
    }

    public String getEnemyPlayerName() {
        if (actualPlayer == 0 || actualPlayer == 2)
            return names[0];
        else if (actualPlayer == 1 && names[2] == null)
            return names[0];
        else
            return names[2];
    }

    public FleetManager getCurrentPlayerFleet() {
        return playersFleets[actualPlayer];
    }
    
    public FleetManager getEnemyPlayerFleet() {
        if (actualPlayer == 0 || actualPlayer == 2)
            return playersFleets[1];
        else if (actualPlayer == 1 && names[2] == null)
            return playersFleets[0];
        else
            return playersFleets[2];
    }

    public void SwitchPlayers() {
        if (actualPlayer != 1) {
            actualPlayer = 1;
        } else if (names[2] == null)
            actualPlayer = 0;
        else
            actualPlayer = 2;
    }

}
