package ru.kpfu.itis.ibragimov;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.kpfu.itis.ibragimov.model.Bullet;
import ru.kpfu.itis.ibragimov.model.EnemyTank;
import ru.kpfu.itis.ibragimov.model.PlayerTank;
import ru.kpfu.itis.ibragimov.util.Block;
import ru.kpfu.itis.ibragimov.util.Direction;
import ru.kpfu.itis.ibragimov.util.Map;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

  private final static double BUTTON_HEIGHT = 55.0;
  private final static double BUTTON_WIDTH = 82.0;
  private final static double BUTTON_X = 259.0;

  private int score = 0;
  private int record = 0;

  public void setScore(int score) {
    if (score > record) {
      record = score;
    }
    this.score = score;
  }

  Game game;

  Stage stage;

  private AnchorPane root;
  private Button single;
  private Button multi;
  private Button chat;
  private Button quit;
  private Text recordText;
  private Font font = new Font(18);

  public void initMenu() {
    root = new AnchorPane();
    root.setPrefWidth(600);
    root.setPrefHeight(400);

    single = new Button("Single");
    single.setFont(font);
    single.setLayoutX(BUTTON_X);
    single.setLayoutY(56.0);
    single.setPrefWidth(BUTTON_WIDTH);
    single.setPrefHeight(BUTTON_HEIGHT);
    single.setFocusTraversable(false);
    root.getChildren().add(single);
    multi = new Button("Multi");
    multi.setFont(font);
    multi.setLayoutX(BUTTON_X);
    multi.setLayoutY(123.0);
    multi.setPrefWidth(BUTTON_WIDTH);
    multi.setPrefHeight(BUTTON_HEIGHT);
    multi.setFocusTraversable(false);
    root.getChildren().add(multi);
    chat = new Button("Chat");
    chat.setFont(font);
    chat.setLayoutX(BUTTON_X);
    chat.setLayoutY(189.0);
    chat.setPrefWidth(BUTTON_WIDTH);
    chat.setPrefHeight(BUTTON_HEIGHT);
    chat.setFocusTraversable(false);
    root.getChildren().add(chat);
    quit = new Button("Quit");
    quit.setFont(font);
    quit.setLayoutX(BUTTON_X);
    quit.setLayoutY(256.0);
    quit.setPrefWidth(BUTTON_WIDTH);
    quit.setPrefHeight(BUTTON_HEIGHT);
    quit.setFocusTraversable(false);
    root.getChildren().add(quit);
    recordText = new Text("Your record is " + score);
    recordText.setFont(font);
    recordText.setX(241.0);
    recordText.setY(355.0);
    recordText.setStrokeType(StrokeType.OUTSIDE);

    root.getChildren().add(recordText);

    single.setOnAction(event -> {
      game = new Game();
      game.setMain(this);
      try {
        stage.setScene(game.start());
        stage.show();
        System.out.println("Changed scene!");
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    });
    multi.setOnAction(event -> {

    });
    chat.setOnAction(event -> {

    });
    quit.setOnAction(event -> {
      stage.close();
    });
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;
    initMenu();
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  public void backToMenu() {
    initMenu();
    stage.setScene(new Scene(root));
    stage.show();
    System.out.println("Showed menu!");
  }
}
