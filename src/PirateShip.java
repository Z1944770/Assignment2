import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class PirateShip implements Observer {
    private Point shipPosition; 
    private Point pirateShipLocation; 
    private OceanMap oceanMap;
    private int[][] mapGrid;
    private Random rand = new Random();
    
    public Point getPirateShipLocation() {
        return this.pirateShipLocation;
    }
    

    public void placePirateShip() {
        int gridSize = oceanMap.getGridSize();
        do {
            int xPosition = rand.nextInt(gridSize);
            int yPosition = rand.nextInt(gridSize);
            if (mapGrid[xPosition][yPosition] == 0) {
                mapGrid[xPosition][yPosition] = 3; 
                this.pirateShipLocation = new Point(xPosition, yPosition);
                return; 
            }
        } while (true); 
    }
    public PirateShip(OceanMap oceanMap) {
        this.oceanMap = oceanMap;
        this.mapGrid = oceanMap.getMap();
    }
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Ship) {
            this.shipPosition = ((Ship) o).getShipLocation();
            movePirateShip();
        }
    }

    public void movePirateShip() {
        int shipX = shipPosition.x;
        int shipY = shipPosition.y;
        int pirateShipX = pirateShipLocation.x;
        int pirateShipY = pirateShipLocation.y;

        int dx = Integer.compare(shipX, pirateShipX);
        int dy = Integer.compare(shipY, pirateShipY);

        int newPirateShipX = pirateShipX + dx;
        int newPirateShipY = pirateShipY + dy;

        if (isValidMove(newPirateShipX, newPirateShipY)) {
            pirateShipLocation.x = newPirateShipX;
            pirateShipLocation.y = newPirateShipY;
        }
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < oceanMap.getGridSize() && y >= 0 && y < oceanMap.getGridSize() && mapGrid[x][y] != 1;
    }

}
