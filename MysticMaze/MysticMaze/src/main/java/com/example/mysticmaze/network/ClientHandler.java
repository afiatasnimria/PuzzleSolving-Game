package com.example.mysticmaze.network;

import com.example.mysticmaze.models.Message;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String roomId;
    private String username;

    private static final Gson gson = new Gson();
    private static final Map<String, Set<ClientHandler>> rooms = GameServer.getRooms();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String input;
            while ((input = in.readLine()) != null) {
                Message message = gson.fromJson(input, Message.class);

                switch (message.getType()) {
                    case "join":
                        roomId = message.getRoomId();
                        username = message.getSender();
                        rooms.putIfAbsent(roomId, ConcurrentHashMap.newKeySet());

                        if (rooms.get(roomId).size() < 3) {
                            rooms.get(roomId).add(this);
                            System.out.println(username + " joined room " + roomId);
                            broadcastToRoom(roomId, input, this);
                        } else {
                            out.println(gson.toJson(new Message("error", "Server", roomId, "Room full")));
                        }
                        break;

                    case "chat":
                    case "progress":
                    case "notify":
                        broadcastToRoom(roomId, input, this);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (roomId != null && rooms.containsKey(roomId)) {
                    rooms.get(roomId).remove(this);
                    broadcastToRoom(roomId, gson.toJson(new Message("notify", "Server", roomId, username + " left the room")), this);
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcastToRoom(String roomId, String message, ClientHandler exclude) {
        for (ClientHandler client : rooms.getOrDefault(roomId, Set.of())) {
            if (client != exclude) {
                client.out.println(message);
            }
        }
    }
}
