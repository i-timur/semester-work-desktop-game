package ru.kpfu.itis.ibragimov.sprite;

import javafx.scene.shape.Rectangle;
import ru.kpfu.itis.ibragimov.util.Direction;

public class Bullet extends Sprite {

  private int SPEED = 3;

  public Bullet() {
    super();
  }

  public void move(Direction direction) {
    switch (direction) {
      case UP:
        velocityX = 0;
        velocityY = -SPEED;
        break;
      case DOWN:
        velocityX = 0;
        velocityY = SPEED;
        break;
      case LEFT:
        velocityX = -SPEED;
        velocityY = 0;
        break;
      case RIGHT:
        velocityX = SPEED;
        velocityY = 0;
        break;
    }
  }

  public void intersects(Rectangle[][] grid) {
    for (int i = 0; i < grid.length; i++) {
      for(int j = 0; j < grid.length; j++) {
        if (grid[i][j] != null && intersects(grid[i][j])) {
          setAlive(false);
        }
      }
    }
  }

  @Override
  public void setImage() {
    imagePattern = null;
  }
}
