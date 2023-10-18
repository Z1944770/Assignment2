import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.awt.Point;

public class OceanExplorer extends Application {

    final int dim = 10;
    final int scale = 50;
    final int islandCount = 12;
    Pane root;
    Scene scene;
    Image shipImage, pirateShipImage, island;
    ImageView shipImageView, pirateShip1ImageView, pirateShip2ImageView;
    Ship ship;
    Point startPosition;
    int[][] islandMap;
    OceanMap oceanMap;
    Button button;
    PirateShip pirateShip1;
    PirateShip pirateShip2;

    boolean foundTarget = false;

    @Override
    public void start(Stage oceanStage) throws Exception {
        foundTarget = false;
        oceanMap = new OceanMap(dim, islandCount);
        islandMap = oceanMap.getMap();
        root = new AnchorPane();
        drawMap();

        ship = new Ship(oceanMap);

        pirateShip1 = new PirateShip(oceanMap);
        pirateShip2 = new PirateShip(oceanMap);

        pirateShip1.placePirateShip();
        pirateShip2.placePirateShip();

        ship.registerObserver(pirateShip1);
        ship.registerObserver(pirateShip2);

        button = new Button("RESET");
        button.setPrefSize(50, 50);
        button.setLayoutX(250);
        button.setLayoutY(500);
        button.setOnAction(event -> {
            try {
                start(oceanStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        loadShipImage();

        scene = new Scene(root, 500, 550);
        oceanStage.setTitle("Christopher Columbus Sails The Blue Ocean");
        oceanStage.setScene(scene);
        oceanStage.show();
        startSailing();
    }

    public void drawMap() {
        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                Rectangle rect = new Rectangle(x * scale, y * scale, scale, scale);
                rect.setStroke(Color.BLACK);

                if (x == 0 && y == 0) {
                    
                    island = new Image(getClass().getResource("/pirateIsland.PNG").toExternalForm(), 50, 50, true, true);
                    ImagePattern imagePattern = new ImagePattern(island);
                    rect.setFill(imagePattern);
                } else if (x == 1 && y == 0) {
                    
                    island = new Image(getClass().getResource("/island.jpg").toExternalForm(), 50, 50, true, true);
                    ImagePattern imagePattern = new ImagePattern(island);
                    rect.setFill(imagePattern);
                } else if (islandMap[x][y] == 1) {
                    
                    island = new Image(getClass().getResource("/pirateIsland.PNG").toExternalForm(), 50, 50, true, true);
                    ImagePattern imagePattern = new ImagePattern(island);
                    rect.setFill(imagePattern);
                } else if (islandMap[x][y] == 2) {
                   
                    island = new Image(getClass().getResource("/island.jpg").toExternalForm(), 50, 50, true, true);
                    ImagePattern imagePattern = new ImagePattern(island);
                    rect.setFill(imagePattern);
                } else {
                    rect.setFill(Color.PALETURQUOISE);
                }

                root.getChildren().add(rect);
            }
        }
    }


    public void loadShipImage() {
        shipImage = new Image(getClass().getResource("/ship.png").toExternalForm(), 50, 50, true, true);
        shipImageView = new ImageView(shipImage);
        shipImageView.setX(oceanMap.getShipLocation().x * scale);
        shipImageView.setY(oceanMap.getShipLocation().y * scale);

        pirateShipImage = new Image(getClass().getResource("/pirateShip.PNG").toExternalForm(), 50, 50, true, true);
        pirateShip1ImageView = new ImageView(pirateShipImage);
        pirateShip1ImageView.setX(pirateShip1.getPirateShipLocation().x * scale);
        pirateShip1ImageView.setY(pirateShip1.getPirateShipLocation().y * scale);
        root.getChildren().add(pirateShip1ImageView);

        pirateShip2ImageView = new ImageView(pirateShipImage);
        pirateShip2ImageView.setX(pirateShip2.getPirateShipLocation().x * scale);
        pirateShip2ImageView.setY(pirateShip2.getPirateShipLocation().y * scale);
        root.getChildren().add(pirateShip2ImageView);

        root.getChildren().add(shipImageView);
        root.getChildren().add(button);
    }

    private void startSailing() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (!foundTarget) {
                    switch (ke.getCode()) {
                        case RIGHT:
                            ship.goEast();
                            break;
                        case LEFT:
                            ship.goWest();
                            break;
                        case UP:
                            ship.goNorth();
                            break;
                        case DOWN:
                            ship.goSouth();
                            break;
                        default:
                            break;
                    }
                    shipImageView.setX(ship.getShipLocation().x * scale);
                    shipImageView.setY(ship.getShipLocation().y * scale);
                    pirateShip1ImageView.setX(pirateShip1.getPirateShipLocation().x * scale);
                    pirateShip1ImageView.setY(pirateShip1.getPirateShipLocation().y * scale);
                    pirateShip2ImageView.setX(pirateShip2.getPirateShipLocation().x * scale);
                    pirateShip2ImageView.setY(pirateShip2.getPirateShipLocation().y * scale);

                    if (ship.getShipLocation().equals(pirateShip1.getPirateShipLocation()) ||
                            ship.getShipLocation().equals(pirateShip2.getPirateShipLocation())) {
                        foundTarget = true;
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
