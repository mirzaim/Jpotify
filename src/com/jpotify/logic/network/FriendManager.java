package com.jpotify.logic.network;

import com.jpotify.logic.Music;
import com.jpotify.logic.network.message.CommandMessage;
import com.jpotify.logic.network.message.CommandType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FriendManager implements Runnable {
    private static final int PORT = 7878;

    private String username;
    private List<String> ips;
    private FriendManagerListener listener;

    private List<Friend> onlineFriends;

    public FriendManager(String username, String[] ips, FriendManagerListener listener) {
        this.username = username;
        this.ips = new LinkedList<>(Arrays.asList(ips));
        this.listener = listener;
        onlineFriends = new LinkedList<>();
    }

    @Override
    public void run() {
        for (String ip : ips) {
            try {
                Socket socket = new Socket(ip, PORT);
                Friend friend = new Friend(socket);
                new Thread(friend).start();
                onlineFriends.add(friend);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        for (Friend friend : onlineFriends) {
            try {
                friend.reportClosingConnection();
                friend.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (onlineFriends.size() > 0)
            ;//handle error.

    }

    public void broadCastActivity(Music music, boolean started) {
        CommandMessage message;
        if (started)
            message = new CommandMessage(username, CommandType.START_ACTIVITY, music);
        else
            message = new CommandMessage(username, CommandType.END_ACTIVITY, music);

        for (Friend friend : onlineFriends)
            friend.sendMessage(message);
    }

    private class Friend implements Runnable {
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private boolean listen = false;

        public Friend(Socket socket) throws IOException {
            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            while (listen) {
                try {
                    CommandMessage message = (CommandMessage) in.readObject();
                    handleCommand(message);
                } catch (IOException e) {
                    if (!socket.isClosed())
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleCommand(CommandMessage message) throws IOException {
            switch (message.getCommandType()) {
                case SHARED_PLAYLIST_REQUEST:
                    sendMessage(new CommandMessage(username,
                            CommandType.SHARED_PLAYLIST_DATA, listener.getSharePlayList()));
                    break;
                case MUSIC_REQUEST:
                    //TO DO
                    //Not implemented yet
                    System.out.println("Come soon!");
                    break;
                case CLOSE_CONNECTION:
                    closeConnection();
                    onlineFriends.remove(this);
                    System.out.println(this + "closed Connection");
                    break;
                default:
                    System.out.println(message.getSenderUsername() + " " + message.getCommandType());
            }
        }

        void sendMessage(CommandMessage message) {
            try {
                out.writeObject(message);
            } catch (IOException e) {
                if (!socket.isClosed())
                    e.printStackTrace();
                else
                    System.out.println("Connection Closed");
            }
        }

        void reportClosingConnection() {
            sendMessage(new CommandMessage(username, CommandType.CLOSE_CONNECTION));
        }

        void closeConnection() throws IOException {
            listen = false;
            in.close();
            out.close();
            socket.close();
        }
    }
}
