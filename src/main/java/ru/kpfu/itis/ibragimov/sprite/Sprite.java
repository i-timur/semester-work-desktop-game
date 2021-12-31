package ru.kpfu.itis.ibragimov.sprite;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import ru.kpfu.itis.ibragimov.util.Direction;

public abstract class Sprite {

  ImagePattern imagePattern;
  protected Rectangle rectangle;
  protected double x;
  protected double prevX;
  protected double y;
  protected double prevY;
  protected double width;
  protected double height;
  protected double velocityX;
  protected double velocityY;
  protected Direction direction;

  public boolean isAlive = true;

  public void setAlive(boolean alive) {
    isAlive = alive;
  }

  public Sprite() {
    x = 0;
    y = 0;
    velocityX = 0;
    velocityY = 0;
    direction = Direction.UP;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public double getWidth() {
    return width;
  }

  public double getHeight() {
    return height;
  }

  public void createRectangle() {
    rectangle = new Rectangle(x, y, width, height);
    setImage();
    rectangle.setFill(imagePattern);
    if (imagePattern == null) {
      rectangle.setFill(Color.GREEN);
    }
  }

  public double getPrevX() {
    return prevX;
  }

  public void setPrevX(double prevX) {
    this.prevX = prevX;
  }

  public double getPrevY() {
    return prevY;
  }

  public void setPrevY(double prevY) {
    this.prevY = prevY;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public abstract void setImage();


  public Rectangle getRectangle() {
    return rectangle;
  }

  public void update() {
    prevX = x;
    prevY = y;
    x += velocityX;
    y += velocityY;
  }

  public void stop() {
    velocityX = 0;
    velocityY = 0;
  }

  public boolean intersects(Rectangle rectangle) {
    if (this.x + this.width <= rectangle.getX() ||
      rectangle.getX() + rectangle.getWidth() <= this.x ||
      this.y + this.height <= rectangle.getY() ||
      rectangle.getY() + rectangle.getHeight() <= this.y
    ) {
      return false;
    }
    return true;
  }
}
