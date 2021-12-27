package ru.kpfu.itis.ibragimov;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ru.kpfu.itis.ibragimov.model.Bullet;
import ru.kpfu.itis.ibragimov.model.EnemyTank;
import ru.kpfu.itis.ibragimov.model.PlayerTank;
import ru.kpfu.itis.ibragimov.util.Block;
import ru.kpfu.itis.ibragimov.util.Direction;
import ru.kpfu.itis.ibragimov.util.Map;

import java.util.ArrayList;
import java.util.List;

public class Game {

  Main main;

  public void setMain(Main main) {
    this.main = main;
  }

  Scene scene;
  BorderPane root;
  Pane playField;
  Rectangle[][] grid;

  AnimationTimer animationTimer;


  private static final long ENEMY_RESPAWN_TIME = 5000000000l;

  long enemyRespawnStartTime;

  private int score = 0;

  PlayerTank playerTank;

  List<EnemyTank> enemies = new ArrayList<>();

  long enemyMoveStartTime;

  private int SIZE = 400;
  private int SPOTS = 10;
  private int SQUARE_SIZE = SIZE / SPOTS;

  List<Bullet> playerBullets = new ArrayList<>();
  List<Bullet> enemiesBullets = new ArrayList<>();

  private void spawnTank() {
    boolean spawnTank = true;
    EnemyTank enemyTank = null;
    int x, y;
    while (spawnTank) {
      x = (int) (Math.random() * Map.map.length);
      y = (int) (Math.random() * Map.map.length);
      if (Map.map[x][y] == Block.EMPTY) {
        enemyTank = new EnemyTank();
        enemyTank.setX(x * SQUARE_SIZE);
        enemyTank.setY(y * SQUARE_SIZE);
        enemyTank.setWidth(SQUARE_SIZE);
        enemyTank.setHeight(SQUARE_SIZE);
        enemyTank.createRectangle();
        playField.getChildren().add(enemyTank.getRectangle());
        spawnTank = false;
      }
    }
    enemies.add(enemyTank);
  }

  private void initPlayField() {
    score = 0;
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
    spawnTank();
    playField.setStyle("-fx-background-color: #000");

    root.setCenter(playField);
  }

  public void initEventHandlers() {
    scene.setOnKeyPressed((event) -> {
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
      }
    });

    scene.setOnKeyReleased(event -> {
      playerTank.stop();
    });
  }

  public Scene start() throws Exception {

    System.out.println("Game is started!");
    initPlayField();
    scene = new Scene(root);

    initEventHandlers();



    animationTimer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        update();
      }
    };

    enemyMoveStartTime = System.nanoTime();
    enemyRespawnStartTime = System.nanoTime();
    animationTimer.start();

    return scene;
  }

  public void update() {
    if (System.nanoTime() - enemyMoveStartTime > 1000000000l) {
      enemies.forEach(enemy -> {
        Direction direction = Direction.values()[(int) (Math.random() * Direction.values().length)];
        enemy.changeDirectionAndMove(direction);
        shoot(enemy);
      });
      enemyMoveStartTime = System.nanoTime();
    }

    if (System.nanoTime() - enemyRespawnStartTime > ENEMY_RESPAWN_TIME) {
      spawnTank();
      enemyRespawnStartTime = System.nanoTime();
    }


    enemies.forEach(enemy -> {
      enemy.update();
      enemy.intersects(grid);
      enemy.getRectangle().setX(enemy.getX());
      enemy.getRectangle().setY(enemy.getY());
    });

    playerTank.update();
    playerTank.intersects(grid);
    playerTank.getRectangle().setX(playerTank.getX());
    playerTank.getRectangle().setY(playerTank.getY());
    playerBullets.forEach(bullet -> {
      if (bullet.isAlive) {
        bullet.intersects(grid);
        enemies.forEach(enemy -> {
          if (enemy != null) {
            if (bullet.intersects(enemy.getRectangle())) {
              playField.getChildren().remove(enemy.getRectangle());
              enemies.remove(enemy);
              bullet.setAlive(false);
              playField.getChildren().remove(bullet.getRectangle());
              score += 1;
            }
          }
        });

        bullet.update();
        bullet.getRectangle().setX(bullet.getX());
        bullet.getRectangle().setY(bullet.getY());
      } else {
        playField.getChildren().remove(bullet.getRectangle());
      }
    });
    enemiesBullets.forEach(bullet -> {
      if (bullet.isAlive) {
        bullet.intersects(grid);
        if (bullet.intersects(playerTank.getRectangle())) {
          playField.getChildren().remove(playerTank.getRectangle());
          // TODO

          animationTimer.stop();
          main.setScore(score);
          main.backToMenu();
        }

        bullet.update();
        bullet.getRectangle().setX(bullet.getX());
        bullet.getRectangle().setY(bullet.getY());
      } else {
        playField.getChildren().remove(bullet.getRectangle());
      }
    });
  }

  public void shoot(EnemyTank who) {
    Bullet enemyBullet = new Bullet();
    if (who.getDirection() == Direction.UP || who.getDirection() == Direction.DOWN) {
      enemyBullet.setX(who.getX() + who.getWidth() / 2 - 5);
      enemyBullet.setY(who.getY());
      enemyBullet.setWidth(10);
      enemyBullet.setHeight(20);
    } else if (who.getDirection() == Direction.RIGHT || who.getDirection() == Direction.LEFT) {
      enemyBullet.setX(who.getX());
      enemyBullet.setY(who.getY() + who.getHeight() / 2 - 5);
      enemyBullet.setWidth(20);
      enemyBullet.setHeight(10);
    }
    enemyBullet.createRectangle();
    enemyBullet.getRectangle().setFill(Color.RED);
    enemyBullet.move(who.getDirection());
    enemiesBullets.add(enemyBullet);
    playField.getChildren().add(enemyBullet.getRectangle());
  }
}
