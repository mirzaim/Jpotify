package com.jpotify.logic.network;

import com.jpotify.logic.network.message.AbstractMessage;
import com.jpotify.logic.network.message.CommandMessage;
import com.jpotify.logic.network.message.CommandType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Objects;

public class Server implements Runnable {
    private static final int PORT = 7878;

    private String username;
    private ServerListener listener;

    private ServerSocket serverSocket;
    private LinkedList<FriendHandler> friendHandlers;
    private boolean flag = true;

    private Server(String username, int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        friendHandlers = new LinkedList<>();
    }

    public Server(String username, ServerListener listener) {
        this(username, PORT);
        if (listener == null)
            throw new NullPointerException();
        this.listener = listener;
    }

    @Override
    public void run() {
        while (flag)
            try {
                Socket socket = serverSocket.accept();
                FriendHandler friend = new FriendHandler(socket);
                friendHandlers.add(friend);
                new Thread(friend).start();
                System.out.println("A friend connect to you with ip address: " + socket.getInetAddress());
            } catch (IOException e) {
                if (!serverSocket.isClosed())
                    e.printStackTrace();
            }
    }

    public void closeServer() {
        flag = false;
        try {
            for (FriendHandler friend : friendHandlers) {
                friend.reportClosingConnection();
                friend.closeConnection();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private class FriendHandler implements Runnable {
//        private String username;

        private ObjectInputStream in;
        private ObjectOutputStream out;
        private Socket socket;
        private boolean listen = true;

        private FriendHandler(Socket clientSocket) throws IOException {
            socket = clientSocket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            while (listen)
                try {
                    CommandMessage command = (CommandMessage) in.readObject();
                    handleCommand(command);
                } catch (IOException e) {
                    if (!socket.isClosed())
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
        }

        private CommandMessage readMessage() throws IOException, ClassNotFoundException {
            return (CommandMessage) in.readObject();
        }

        private void sendMessage(AbstractMessage message) {
            try {
                out.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void closeConnection() throws IOException {
            listen = false;
            in.close();
            out.close();
            socket.close();
        }

        private void handleCommand(CommandMessage message) throws IOException {
            switch (message.getCommandType()) {
                case INTRODUCTION:
                    listener.friendBecomeOnline(message.getSenderUsername());
                    break;
                case START_ACTIVITY:
                    listener.friendMusicStarted(message.getSenderUsername(), message.getMusic());
                    break;
                case END_ACTIVITY:
                    listener.friendMusicEnded(message.getSenderUsername(), message.getMusic());
                    break;
                case SHARED_PLAYLIST_DATA:
                    listener.sharedPlayListData(message.getSenderUsername(), message.getSharedPlayList());
                    break;
                case MUSIC_COME:
                    //TO DO
                    //Not implemented yet
                    System.out.println("Downloading Music...");
                    System.out.println("Come soon!");
                    break;
                case CLOSE_CONNECTION:
                    closeConnection();
                    friendHandlers.remove(this);
                    System.out.println(this + "closed Connection");
                    break;
                default:
            }
        }

        public void reportClosingConnection() {
            sendMessage(new CommandMessage(username, CommandType.CLOSE_CONNECTION));
        }

//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            FriendHandler that = (FriendHandler) o;
//            return Objects.equals(username, that.username);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(username);
//        }
    }
}
