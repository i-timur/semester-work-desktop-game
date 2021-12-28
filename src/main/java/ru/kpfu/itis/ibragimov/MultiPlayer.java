package ru.kpfu.itis.ibragimov;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ru.kpfu.itis.ibragimov.client.GameClient;
import ru.kpfu.itis.ibragimov.sprite.Bullet;
import ru.kpfu.itis.ibragimov.sprite.EnemyTank;
import ru.kpfu.itis.ibragimov.sprite.PlayerTank;
import ru.kpfu.itis.ibragimov.util.Block;
import ru.kpfu.itis.ibragimov.util.Direction;
import ru.kpfu.itis.ibragimov.util.Map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MultiPlayer {

  Main main;

  public void setMain(Main main) {
    this.main = main;
  }

  private ClientConfig clientConfig;

  private GameClient gameClient;

  public ClientConfig getServerConfig() {
    return clientConfig;
  }

  private Scene playFieldScene;
  private AnchorPane clientConfigRoot;
  private Text host;
  private TextField hostField;
  private Text port;
  private TextField portField;
  private Button submit;
  private Button back;
  private Font font = new Font(18);

  Scene scene;
  BorderPane root;
  Pane playField;
  Rectangle[][] grid;

  AnimationTimer animationTimer;

  PlayerTank playerTank;
  EnemyTank enemyTank;

  List<Bullet> playerBullets = new ArrayList<>();
  List<Bullet> enemyBullets = new ArrayList<>();

  private int SIZE = 400;
  private int SPOTS = 10;
  private int SQUARE_SIZE = SIZE / SPOTS;

  private void initClientConfig() {
    clientConfig = new ClientConfig();
    clientConfigRoot = new AnchorPane();
    clientConfigRoot.setPrefHeight(400.0);
    clientConfigRoot.setPrefWidth(600.0);
    host = new Text("Host");
    host.setStrokeType(StrokeType.OUTSIDE);
    host.setFont(font);
    host.setLayoutX(279.0);
    host.setLayoutY(98.0);
    clientConfigRoot.getChildren().add(host);
    port = new Text("Port");
    port.setStrokeType(StrokeType.OUTSIDE);
    port.setFont(font);
    port.setLayoutX(279.0);
    port.setLayoutY(171.0);
    clientConfigRoot.getChildren().add(port);
    hostField = new TextField();
    hostField.setLayoutX(219.0);
    hostField.setLayoutY(116.0);
    clientConfigRoot.getChildren().add(hostField);
    portField = new TextField();
    portField.setLayoutX(219.0);
    portField.setLayoutY(187.0);
    clientConfigRoot.getChildren().add(portField);
    submit = new Button("Подтвердить");
    submit.setFont(font);
    submit.setLayoutX(235.0);
    submit.setLayoutY(232.0);
    submit.setOnAction(event -> {
      clientConfig.setHost(hostField.getText());
      System.out.println(clientConfig.getHost());
      clientConfig.setPort(Integer.parseInt(portField.getText()));
      System.out.println(clientConfig.getPort());
      initPlayField();
      playFieldScene = new Scene(root);
      initEventHandlers();
      main.stage.setScene(playFieldScene);

      try {
        gameClient.start();
      } catch (IOException e) {
        e.printStackTrace();
      }
      enemyTank = new EnemyTank();
      enemyTank.setWidth(SQUARE_SIZE);
      enemyTank.setHeight(SQUARE_SIZE);
      enemyTank.createRectangle();
      playField.getChildren().add(enemyTank.getRectangle());

      animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
          update();
        }
      };
      animationTimer.start();
    });
    clientConfigRoot.getChildren().add(submit);
    back = new Button("В главное меню");
    back.setFont(font);
    back.setLayoutX(223.0);
    back.setLayoutY(294.0);
    back.setOnAction(event -> {
      main.backToMenu();
    });
    clientConfigRoot.getChildren().add(back);
  }

  public void setEnemyTankPosition(double x, double y) {
    enemyTank.setX(x);
    enemyTank.setY(y);
  }

  private void initPlayField() {
    root = new BorderPane();
    playField = new Pane();
    playField.setPrefWidth(400);
    playField.setPrefHeight(400);
    grid = new Rectangle[SPOTS][SPOTS];
    for (int i = 0; i < SIZE; i += SQUARE_SIZE) {
      for (int j = 0; j < SIZE; j += SQUARE_SIZE) {
        switch (Map.map[i / SQUARE_SIZE][j / SQUARE_SIZE]) {
          case STONE:
            Rectangle stone = new Rectangle(i, j, SQUARE_SIZE, SQUARE_SIZE);
            grid[i / SQUARE_SIZE][j / SQUARE_SIZE] = stone;
            stone.setFill(new ImagePattern(new Image("/image/stone.gif")));
            playField.getChildren().add(stone);
            break;
        }
      }
    }

    boolean spawnTank = true;
    int x;
    int y;
    while (spawnTank) {
      x = (int) (Math.random() * Map.map.length);
      y = (int) (Math.random() * Map.map.length);
      if (Map.map[x][y] == Block.EMPTY) {
        playerTank = new PlayerTank();
        playerTank.setX(x * SQUARE_SIZE);
        playerTank.setY(y * SQUARE_SIZE);
        playerTank.setWidth(SQUARE_SIZE);
        playerTank.setHeight(SQUARE_SIZE);
        playerTank.createRectangle();
        playerTank.getRectangle().setX(playerTank.getX());
        playerTank.getRectangle().setY(playerTank.getY());
        playField.getChildren().add(playerTank.getRectangle());
        spawnTank = false;
      }
    }
    playField.setStyle("-fx-background-color: #000");

    root.setCenter(playField);
  }

  public void initEventHandlers() {
    playFieldScene.setOnKeyPressed((event) -> {
      if (event.getCode() == KeyCode.A) {
        playerTank.changeDirectionAndMove(Direction.LEFT);
      } else if (event.getCode() == KeyCode.D) {
        playerTank.changeDirectionAndMove(Direction.RIGHT);
      } else if (event.getCode() == KeyCode.W) {
        playerTank.changeDirectionAndMove(Direction.UP);
      } else if (event.getCode() == KeyCode.S) {
        playerTank.changeDirectionAndMove(Direction.DOWN);
      } else if (event.getCode() == KeyCode.SPACE) {
        Bullet bullet = new Bullet();
        if (playerTank.getDirection() == Direction.UP || playerTank.getDirection() == Direction.DOWN) {
          bullet.setX(playerTank.getX() + playerTank.getWidth() / 2 - 5);
          bullet.setY(playerTank.getY());
          bullet.setWidth(10);
          bullet.setHeight(20);
        } else if (playerTank.getDirection() == Direction.RIGHT || playerTank.getDirection() == Direction.LEFT) {
          bullet.setX(playerTank.getX());
          bullet.setY(playerTank.getY() + playerTank.getHeight() / 2 - 5);
          bullet.setWidth(20);
          bullet.setHeight(10);
        }
        bullet.createRectangle();
        bullet.move(playerTank.getDirection());
        playerBullets.add(bullet);
        playField.getChildren().add(bullet.getRectangle());
      } else if (event.getCode() == KeyCode.ESCAPE) {
        animationTimer.stop();
        main.backToMenu();
      }
    });

    playFieldScene.setOnKeyReleased(event -> {
      playerTank.stop();
    });
  }

  public Scene start() throws Exception {

    System.out.println("Game is started!");
    initClientConfig();
    scene = new Scene(clientConfigRoot);

    gameClient = new GameClient(this);

    return scene;
  }

  public void update() {
    playerTank.update();
    playerTank.intersects(grid);
    playerTank.getRectangle().setX(playerTank.getX());
    playerTank.getRectangle().setY(playerTank.getY());

    enemyTank.getRectangle().setX(enemyTank.getX());
    enemyTank.getRectangle().setY(enemyTank.getY());

    String message = "enemy " + playerTank.getX() + " " + playerTank.getY();
    gameClient.sendMessage(message);


    playerBullets.forEach(bullet -> {
      if (bullet.isAlive) {
        bullet.intersects(grid);

        bullet.update();
        bullet.getRectangle().setX(bullet.getX());
        bullet.getRectangle().setY(bullet.getY());
      } else {
        playField.getChildren().remove(bullet.getRectangle());
      }
    });
  }
}
