package ru.kpfu.itis.ibragimov.sprite;

import javafx.scene.shape.Rectangle;

public abstract class Tank extends Sprite {

  public Tank() {
    super();
  }

  public void intersects(Rectangle[][] grid) {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid.length; j++) {
        if (grid[i][j] != null && intersects(grid[i][j])) {
          x = prevX;
          y = prevY;
        }
      }
    }
  }
}
