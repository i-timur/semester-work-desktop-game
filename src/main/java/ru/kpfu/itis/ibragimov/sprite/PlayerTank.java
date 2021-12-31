package ru.kpfu.itis.ibragimov.sprite;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import ru.kpfu.itis.ibragimov.util.Direction;

public class PlayerTank extends Tank {


  private int SPEED = 1;

  public PlayerTank() {
    super();
  }

  @Override
  public void setImage() {
    imagePattern = new ImagePattern(new Image("/image/green-tank-up.gif"));
  }

  public void changeDirectionAndMove(Direction direction) {
    super.direction = direction;
    switch (direction) {
      case UP:
        rectangle.setFill(new ImagePattern(new Image("/image/green-tank-up.gif")));
        velocityX = 0;
        velocityY = -SPEED;
        break;
      case DOWN:
        rectangle.setFill(new ImagePattern(new Image("/image/green-tank-down.gif")));
        velocityX = 0;
        velocityY = SPEED;
        break;
      case LEFT:
        rectangle.setFill(new ImagePattern(new Image("/image/green-tank-left.gif")));
        velocityX = -SPEED;
        velocityY = 0;
        break;
      case RIGHT:
        rectangle.setFill(new ImagePattern(new Image("/image/green-tank-right.gif")));
        velocityX = SPEED;
        velocityY = 0;
        break;
    }
  }
}
