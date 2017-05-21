package nnb;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class FleetManager {

    private List<Point[]> shipList = new ArrayList<>();
    private List<Point> miss = new ArrayList<>();
    private List<Point> hits = new ArrayList<>();

    // Tableau du nombre de bateaux dispos pour chaque taille

    public int placeShip(Point coords, int shipLength, boolean isHorizontal) {
        if (shipLength == -1)
            return 2;

        // Test si bateau deja posÃ©
        for (Point[] ship : shipList) {
            if (ship.length == shipLength)
                return 2;
        }

        Point[] shipCoords = new Point[shipLength];
        for (int i = 0; i < shipCoords.length; i++) {
            shipCoords[i] = new Point();
        }

        // Créer les coords
        for (int i = 0; i < shipCoords.length; i++) {
            if (isHorizontal)
                shipCoords[i].move(coords.x + i, coords.y);
            else
                shipCoords[i].move(coords.x, coords.y + i);

            if (shipCoords[i].x > 9 || shipCoords[i].y > 9)
                return 1;
        }

        // Test collisions avec autres bateaux
        for (Point[] ship : shipList) {
            for (int i = 0; i < shipCoords.length; i++) {
                for (int j = 0; j < ship.length; j++) {
                    if (shipCoords[i].equals(ship[j]))
                        return 1;
                }
            }
        }
        // Ajoute bateau dans la liste
        shipList.add(shipCoords);

        System.out.println("Bateau placÃ© | Taille : " + shipLength);
        return 0;
    }

    public int[] getShipsPlacedLenght() {
        int[] sizes = new int[shipList.size()];
        for (int i = 0; i < shipList.size(); i++) {
            sizes[i] = shipList.get(i).length;
        }
        return sizes;
    }

    public int getShipPlacedNumber() {
        return shipList.size();
    }

    public List<Point[]> getShipList() {
        return shipList;
    }

    public boolean removeLastShip() {
        if (shipList.size() == 0)
            return false;
        else {
            shipList.remove(shipList.size() - 1);
            return true;
        }
    }

    public int checkCell(Point coords) {
        // vÃ©rifier coordonnÃ©es en fonction de la grille des bateaux

        for (Point[] ship : shipList) {
            for (int i = 0; i < ship.length; i++) {
                if (ship[i].equals(coords)) {
                    ship[i].move(-1, -1);
                    hits.add(coords);
                    boolean sank = false;
                    for (Point p : ship) {
                        sank = p.equals(new Point(-1, -1));
                        if (!sank)
                            break;
                    }
                    if (sank)
                        return 2;
                    else
                        return 1;
                }
            }
        }
        // 0 = ratÃ©, 1 = touchÃ©, 2 = coulÃ©
        miss.add(coords);
        return 0;
    }

    public List<Point> getHits() {
        return hits;
    }

    public List<Point> getMiss() {
        return miss;
    }

    public boolean isFleetDefeated() {
        for (Point[] ship : shipList) {
            for (Point p : ship) {
                if (p.x != -1 && p.y != -1)
                    return false;
            }
        }
        return true;
    }
}
