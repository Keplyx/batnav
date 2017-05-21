package nnb;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIManager {

    private List<Point> shotsList = new ArrayList<Point>();
    private List<Point> currentHitsList = new ArrayList<Point>();
    private int streakOrientation; // 0 = none, 1 = hori, 2 = verti

    private Point getRdmAroundPoint(Point coord, int orientation) {
        Point initial = new Point(coord);
        int counter = 0; // Evite les boucles infinies
        do {
            int offset = new Random().nextInt(3) - 1;
            int newX = initial.x + offset;
            int newY = initial.y + offset;
            switch (orientation) {
            case 0: {
                if (new Random().nextBoolean())
                    coord.move(newX, initial.y);
                else
                    coord.move(initial.x, newY);
                break;
            }
            case 1: {
                coord.move(newX, initial.y);
                break;
            }
            case 2: {
                coord.move(initial.x, newY);
                break;
            }
            }
            counter++;
        } while (!checkPoint(coord) && counter < 10);

        if (checkPoint(coord))
            return coord;
        else
            return new Point(-1, -1);
    }

    public Point getFireCoords() {
        Point newShot = new Point();
        Point lastHit = new Point();
        if (!currentHitsList.isEmpty())
    		lastHit = currentHitsList.get(currentHitsList.size() - 1);
    	else
    		lastHit = new Point(-1, -1);
        if (!isPointValid(lastHit)) {
            do {
                newShot = getRdmCoords();
            } while (!checkPoint(newShot));
        } else {
            newShot = getRdmAroundPoint(new Point(lastHit), streakOrientation);
            if (!isPointValid(newShot))
                newShot = getRdmAroundPoint(new Point(currentHitsList.get(0)), streakOrientation);
            if (!isPointValid(newShot)) {
                do {
                    newShot = getRdmCoords();
                } while (!checkPoint(newShot));
            }
        }
        shotsList.add(newShot);
        return newShot;
    }

    private boolean checkPoint(Point newPoint) {
        for (Point p : shotsList) {
            if (p.equals(newPoint)) {
                return false;
            }
        }
        return true;
    }

    public void setLastShot(int value) {
    	Point lastHit;
    	if (!currentHitsList.isEmpty())
    		lastHit = currentHitsList.get(currentHitsList.size() - 1);
    	else
    		lastHit = new Point(-1, -1);
        if (value == 1 && !shotsList.isEmpty() && isPointValid(lastHit)) { // retouche

            Point thisHit = shotsList.get(shotsList.size() - 1);
            if (thisHit.y == lastHit.y)
                streakOrientation = 1;
            else
                streakOrientation = 2;
            currentHitsList.add(new Point(thisHit));
        }
        if (value == 0 && !shotsList.isEmpty() && isPointValid(lastHit)) { // rate
            lastHit = new Point(currentHitsList.get(0));
        } else if (value == 1 && !shotsList.isEmpty() && !isPointValid(lastHit)) { // touche
            streakOrientation = 0;
            currentHitsList.add(shotsList.get(shotsList.size() - 1));
        } else if (value == 2) {// Coule
            streakOrientation = 0;
            currentHitsList.clear();
        }
    }

    private boolean isPointValid(Point p){
    	return !(p.x == -1 || p.y == -1);
    }
    
    
    public Point getRdmCoords() {
        return new Point(new Random().nextInt(10), new Random().nextInt(10));
    }

    public boolean getShipOrientation() {
        return new Random().nextBoolean();
    }

    public int getShipSize() {
        return new Random().nextInt(5) + 1;
    }

}
