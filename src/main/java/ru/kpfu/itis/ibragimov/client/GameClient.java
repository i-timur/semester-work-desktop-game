package ru.kpfu.itis.ibragimov.client;

import ru.kpfu.itis.ibragimov.MultiPlayer;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class GameClient {

  private Socket socket;
  private ClientThread clientThread;

  private final MultiPlayer game;

  public GameClient(MultiPlayer game) {
    this.game = game;
  }

  public MultiPlayer getGame() {
    return game;
  }

  public void sendMessage(String message) {
    try {
      clientThread.getOutput().write(message + "\n");
      clientThread.getOutput().flush();
      System.out.println("Sent message: " + message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void start() throws IOException {
    String host = game.getServerConfig().getHost();
    int port = game.getServerConfig().getPort();

    socket = new Socket(host, port);

    BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

    clientThread = new ClientThread(input, output, this);

    new Thread(clientThread).start();
  }
}
