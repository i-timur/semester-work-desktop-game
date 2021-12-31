package ru.kpfu.itis.ibragimov.sprite;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import ru.kpfu.itis.ibragimov.util.Direction;

public class EnemyTank extends Tank {

  private int SPEED = 1;

  public EnemyTank() {
    super();
  }

  @Override
  public void setImage() {
    imagePattern = new ImagePattern(new Image("/image/red-tank-up.gif"));
  }

  public void changeDirection(Direction direction) {
    switch (direction) {
      case UP:
        rectangle.setFill(new ImagePattern(new Image("/image/red-tank-up.gif")));
        break;
      case DOWN:
        rectangle.setFill(new ImagePattern(new Image("/image/red-tank-down.gif")));
        break;
      case RIGHT:
        rectangle.setFill(new ImagePattern(new Image("/image/red-tank-right.gif")));
        break;
      case LEFT:
        rectangle.setFill(new ImagePattern(new Image("/image/red-tank-left.gif")));
        break;
    }
  }

  public void changeDirectionAndMove(Direction direction) {
    super.direction = direction;
    switch (direction) {
      case UP:
        rectangle.setFill(new ImagePattern(new Image("/image/red-tank-up.gif")));
        velocityX = 0;
        velocityY = -SPEED;
        break;
      case DOWN:
        rectangle.setFill(new ImagePattern(new Image("/image/red-tank-down.gif")));
        velocityX = 0;
        velocityY = SPEED;
        break;
      case LEFT:
        rectangle.setFill(new ImagePattern(new Image("/image/red-tank-left.gif")));
        velocityX = -SPEED;
        velocityY = 0;
        break;
      case RIGHT:
        rectangle.setFill(new ImagePattern(new Image("/image/red-tank-right.gif")));
        velocityX = SPEED;
        velocityY = 0;
        break;
    }
  }
}
