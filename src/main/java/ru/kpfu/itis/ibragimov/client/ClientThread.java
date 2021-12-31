package ru.kpfu.itis.ibragimov.client;

import javafx.application.Platform;
import ru.kpfu.itis.ibragimov.MultiPlayer;
import ru.kpfu.itis.ibragimov.util.Direction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ClientThread implements Runnable {

  private final BufferedReader input;

  private final BufferedWriter output;

  private final GameClient client;

  public ClientThread(BufferedReader input, BufferedWriter output, GameClient client) {
    this.input = input;
    this.output = output;
    this.client = client;
  }

  public BufferedReader getInput() {
    return input;
  }

  public BufferedWriter getOutput() {
    return output;
  }

  @Override
  public void run() {
    try {
      while (true) {
        String message = input.readLine();

        // instructions[0](String) type of object
        // instructions[1](double) x location
        // instructions[2](double) y location
        // instructions[3](String) direction
        // instructions[4](String) player status
        String[] instructions = message.split(" ");

        String objectType = instructions[0];

        if ("enemy".equals(objectType)) {
          client.getGame().setEnemyTankPosition(Double.parseDouble(instructions[1]), Double.parseDouble(instructions[2]));
          Direction direction = null;
          switch (instructions[3]) {
            case "up":
              direction = Direction.UP;
              break;
            case "down":
              direction = Direction.DOWN;
              break;
            case "left":
              direction = Direction.LEFT;
              break;
            case "right":
              direction = Direction.RIGHT;
              break;
          }
          client.getGame().setEnemyTankDirection(direction);
        } else if ("bullet".equals(objectType)) {
          Platform.runLater(() -> client.getGame().shoot(client.getGame().getEnemyTank()));
        } else if ("status".equals(objectType)) {
          Platform.runLater(() -> client.getGame().getPlayField().getChildren().remove(client.getGame().getEnemyTank().getRectangle()));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
