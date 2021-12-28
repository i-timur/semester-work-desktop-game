package ru.kpfu.itis.ibragimov.server;

import java.io.*;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GameServer {

  public static void main(String[] args) throws IOException {
    GameServer server = new GameServer();
    server.start();
  }

  private static final int PORT = 5555;
  private ServerSocket socket;
  private final List<ClientThread> clients = new ArrayList<>();

  public void start() throws IOException {
    socket = new ServerSocket(PORT);
    System.out.println("Listening to " +  PORT + " port");

    while (true) {
      Socket clientSocket = socket.accept();

      BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
      BufferedWriter output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));

      ClientThread clientThread = new ClientThread(input, output, this);
      clients.add(clientThread);

      new Thread(clientThread).start();
    }
  }

  public void sendMessage(String message, ClientThread sender) throws IOException {
    for (ClientThread client : clients) {
      if (client.equals(sender)) {
        continue;
      }

      System.out.println("Sending message: " + message);
      client.getOutput().write(message + "\n");
      client.getOutput().flush();
    }
  }

  public void removeClient(ClientThread client) {
    clients.remove(client);
  }
}
