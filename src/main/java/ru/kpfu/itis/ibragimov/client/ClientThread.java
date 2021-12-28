package ru.kpfu.itis.ibragimov.client;

import ru.kpfu.itis.ibragimov.MultiPlayer;

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
        //TODO

        // instructions[0](String) type of object
        // instructions[1](double) x location
        // instructions[2](double) y location
        String[] instructions = message.split(" ");

        System.out.println(instructions[1] + " " + instructions[2]);
        client.getGame().setEnemyTankPosition(Double.parseDouble(instructions[1]), Double.parseDouble(instructions[2]));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
