package ru.kpfu.itis.ibragimov;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

  SinglePlayer singlePlayer;
  MultiPlayer multiPlayer;

  Stage stage;

  private AnchorPane root;
  private Button single;
  private Button multi;
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
    single.setLayoutY(106.0);
    single.setPrefWidth(BUTTON_WIDTH);
    single.setPrefHeight(BUTTON_HEIGHT);
    single.setFocusTraversable(false);
    root.getChildren().add(single);
    multi = new Button("Multi");
    multi.setFont(font);
    multi.setLayoutX(BUTTON_X);
    multi.setLayoutY(173.0);
    multi.setPrefWidth(BUTTON_WIDTH);
    multi.setPrefHeight(BUTTON_HEIGHT);
    multi.setFocusTraversable(false);
    root.getChildren().add(multi);
    quit = new Button("Quit");
    quit.setFont(font);
    quit.setLayoutX(BUTTON_X);
    quit.setLayoutY(238.0);
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
      singlePlayer = new SinglePlayer();
      singlePlayer.setMain(this);
      try {
        stage.setScene(singlePlayer.start());
        stage.show();
        System.out.println("Showed single player!");
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    });
    multi.setOnAction(event -> {
      multiPlayer = new MultiPlayer();
      multiPlayer.setMain(this);
      try {
        stage.setScene(multiPlayer.start());
        stage.show();
        System.out.println("Showed multi player!");
      } catch (Exception e) {
        e.printStackTrace();
      }
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
