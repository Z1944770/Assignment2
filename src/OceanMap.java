import java.awt.Point;
import java.util.Random;

public class OceanMap {
    private int gridSize;
    private int islandCount;
    private int[][] mapGrid;
    private Random random = new Random();
    private Point shipLocation;

    public OceanMap(int gridSize, int islandCount) {
        this.gridSize = gridSize;
        this.islandCount = islandCount;
        this.mapGrid = new int[this.gridSize][this.gridSize];
        this.shipLocation = placeShip(); 

        for (int x = 0; x < this.gridSize; x++) {
            for (int y = 0; y < this.gridSize; y++) {
                this.mapGrid[x][y] = 0; 
            }
        }

        placeIslands(); 
    }

    public int[][] getMap() {
        return mapGrid;
    }
    public boolean isValidMove(int x, int y) {
        return x >= 0 && x < gridSize && y >= 0 && y < gridSize && mapGrid[x][y] != 1;
    }


    public int getGridSize() {
        return gridSize;
    }

    public void placeIslands() {
        int count = this.islandCount;
        while (count > 0) {
            int x = random.nextInt(this.gridSize);
            int y = random.nextInt(this.gridSize);
            if (mapGrid[x][y] == 0) {
                mapGrid[x][y] = 1; 
                count--;
            }
        }
    }

    public Point placeShip() {
        boolean isShipPlaced = false;
        int xPosition = 0;
        int yPosition = 0;
        while (!isShipPlaced) {
            xPosition = random.nextInt(this.gridSize);
            yPosition = random.nextInt(this.gridSize);
            if (mapGrid[xPosition][yPosition] == 0) {
                isShipPlaced = true;
                mapGrid[xPosition][yPosition] = 2; 
            }
        }
        return new Point(xPosition, yPosition);
    }

    public Point getShipLocation() {
        return shipLocation;
    }
}
