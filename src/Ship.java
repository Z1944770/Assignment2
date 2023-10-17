import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Ship extends Observable {
    private List<Observer> observers = new LinkedList<>();
    private Point shipLocation;
    private OceanMap oceanMap;
    private int[][] mapGrid;

    public Ship(OceanMap oceanMap) {
        this.oceanMap = oceanMap;
        this.shipLocation = oceanMap.getShipLocation();
        this.mapGrid = oceanMap.getMap();
    }

    public Point getShipLocation() {
        return this.shipLocation;
    }

    public void move(int dx, int dy) {
        int newShipX = shipLocation.x + dx;
        int newShipY = shipLocation.y + dy;

        if (oceanMap.isValidMove(newShipX, newShipY)) {
            shipLocation.setLocation(newShipX, newShipY);
            notifyObservers();
        }
    }


    public void goEast() {
        move(1, 0);
    }

    public void goWest() {
        move(-1, 0);
    }

    public void goNorth() {
        move(0, -1);
    }

    public void goSouth() {
        move(0, 1);
    }


    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        if (observers.contains(o)) {
            observers.remove(o);
        }
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this, observer);
        }
    }
}
