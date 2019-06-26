package com.jpotify.logic.network;

import com.jpotify.logic.Music;
import com.jpotify.logic.network.message.CommandMessage;
import com.jpotify.logic.network.message.CommandType;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


/**
 * This class is for finding your friend on your network and share your activities with them
 *
 * @author Morteza Mirzai
 * @see FriendManagerListener
 * @see Server
 */
public class FriendManager implements Runnable {
    private static final int PORT = 7878;
    private static final int FILE_PORT = 8787;

    private String username;
    private List<String> ips;
    private FriendManagerListener listener;

    private List<Friend> onlineFriends;
    private HashMap<Friend, String> friendIpMap;
    private boolean flag = true;

    public FriendManager(String username, String[] ips, FriendManagerListener listener) {
        this.username = username;
        this.ips = new LinkedList<>(Arrays.asList(ips));
        this.listener = listener;
        onlineFriends = new LinkedList<>();
        friendIpMap = new HashMap<>();
    }

    @Override
    public void run() {
        while (flag) {
            for (String ip : getNotConnectedFriends()) {
                try {
                    System.out.println("Try to connect to " + ip);
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, PORT), 5 * 1000);
                    Friend friend = new Friend(socket);
                    new Thread(friend).start();
                    onlineFriends.add(friend);
                    friendIpMap.put(friend, ip);
                    System.out.println("Connected to " + ip);
                } catch (IOException e) {
                    System.out.println("Failed to connect to " + ip);
                }
            }
            try {
                System.out.println("Thread sleeping");
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                flag = false;
            }
        }

    }

    /**
     * It stops sharing data with your friend and close connection with them
     */
    public void stop() {
        flag = false;
        Iterator<Friend> iterator = onlineFriends.iterator();
        while (iterator.hasNext())
            try {
                Friend friend = iterator.next();
                friend.reportClosingConnection();
                friend.closeConnection();
                iterator.remove();
            } catch (IOException e) {
                e.printStackTrace();
            }

        if (onlineFriends.size() > 0)
            ;//handle error.

    }

    /**
     * This method broadcast your share activity with your friends
     *
     * @param music   music that you played
     * @param started true if you staring that music or false for you stop that
     */
    public void broadCastActivity(Music music, boolean started) {
        CommandMessage message;
        if (started)
            message = new CommandMessage(username, CommandType.START_ACTIVITY, music);
        else
            message = new CommandMessage(username, CommandType.END_ACTIVITY, music);

        for (Friend friend : onlineFriends)
            friend.sendMessage(message);
    }


    private String[] getNotConnectedFriends() {
        List<String> onlineFriendsIP = new LinkedList<>();
        for (Friend friend : onlineFriends)
            if (friendIpMap.containsKey(friend))
                onlineFriendsIP.add(friendIpMap.get(friend));

        List<String> offlineFriend = new LinkedList<>(ips);
        offlineFriend.removeAll(onlineFriendsIP);
        return offlineFriend.toArray(new String[0]);
    }


    private class Friend implements Runnable {
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private boolean listen = true;

        private Friend(Socket socket) throws IOException {
            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            sendIntroductionMessage();
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
                    System.out.println("Invalid input!");
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
                    System.out.println("Sending...");
                    sendMessage(new CommandMessage(username,
                            CommandType.MUSIC_COME, message.getMusic()));


                    sendMusic(message.getMusic());
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

        private void sendMessage(CommandMessage message) {
            try {
                out.writeObject(message);
            } catch (IOException e) {
                if (!socket.isClosed())
                    e.printStackTrace();
                else
                    System.out.println("Connection Closed");
            }
        }

        private void sendMusic(Music music) {
            try {
                ServerSocket serverSocket = new ServerSocket(FILE_PORT);
                Socket socket = serverSocket.accept();
                FileInputStream fileInputStream = new FileInputStream(music.getFilePath());
                OutputStream outputStream = socket.getOutputStream();
                byte[] bytes = new byte[16 * 1024];
                int count;
                while ((count = fileInputStream.read(bytes)) > 0) {
                    outputStream.write(bytes, 0, count);
                }
                fileInputStream.close();
                socket.close();
                serverSocket.close();
                System.out.println("Finished sending.");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String getIP() {
            return socket.getInetAddress().toString();
        }

        private void sendIntroductionMessage() {
            sendMessage(new CommandMessage(username, CommandType.INTRODUCTION));
        }

        private void reportClosingConnection() {
            sendMessage(new CommandMessage(username, CommandType.CLOSE_CONNECTION));
        }

        private void closeConnection() throws IOException {
            listen = false;
            in.close();
            out.close();
            socket.close();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Friend friend = (Friend) o;
            return Objects.equals(socket.getInetAddress(), friend.socket.getInetAddress());
        }

        @Override
        public int hashCode() {
            return Objects.hash(socket.getInetAddress());
        }
    }
}
