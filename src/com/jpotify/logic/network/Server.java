package com.jpotify.logic.network;

import com.jpotify.logic.Music;
import com.jpotify.logic.exceptions.NoTagFoundException;
import com.jpotify.logic.network.message.AbstractMessage;
import com.jpotify.logic.network.message.CommandMessage;
import com.jpotify.logic.network.message.CommandType;
import mpatric.mp3agic.InvalidDataException;
import mpatric.mp3agic.UnsupportedTagException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server implements Runnable {
    private static final int PORT = 7878;
    private static final int FILE_PORT = 8787;

    private String username;
    private ServerListener listener;

    private ServerSocket serverSocket;
    private List<FriendHandler> friendHandlers;
    private boolean flag = true;

    private Server(String username, int port) throws IOException {
        serverSocket = new ServerSocket(port);
        this.username = username;

        friendHandlers = new LinkedList<>();
    }

    public Server(String username, ServerListener listener) throws IOException {
        this(username, PORT);
        if (listener == null)
            throw new NullPointerException();
        this.listener = listener;
    }

    @Override
    public void run() {
        while (flag)
            try {
                System.out.println("Waiting for friends...");
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

    public void sendSharedPlayListRequest(String username) {
        for (FriendHandler friendHandler : friendHandlers)
            if (friendHandler.getUsername().equals(username)) {
                friendHandler.sendMessage(new CommandMessage(this.username, CommandType.SHARED_PLAYLIST_REQUEST));
                System.out.println("Request sent.");
                break;
            }
    }

    public void sendMusicRequest(Music music) {
        for (FriendHandler friendHandler : friendHandlers)
            if (friendHandler.getUsername().equals(username)) {
                friendHandler.sendMessage(new CommandMessage(this.username, CommandType.MUSIC_REQUEST, music));
                break;
            }
    }


    private class FriendHandler implements Runnable {
        private String username;

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
                    System.out.println("Invalid input!");
                }
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
                    username = message.getSenderUsername();
                    listener.friendBecomeOnline(message.getSenderUsername());
                    break;
                case START_ACTIVITY:
                    listener.friendMusicStarted(message.getSenderUsername(), message.getMusic());
                    break;
                case END_ACTIVITY:
                    listener.friendMusicEnded(message.getSenderUsername(), message.getMusic());
                    break;
                case SHARED_PLAYLIST_DATA:
                    System.out.println("SHARED_PLAYLIST_DATA");
                    listener.sharedPlayListData(message.getSenderUsername(), message.getSharedPlayList());
                    break;
                case MUSIC_COME:
                    System.out.println("Downloading Music...");
                    receiveMusic(message.getMusic());
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

        public void receiveMusic(Music music) {
            File file = new File(System.getProperty("user.dir") + "/downloaded_musics/", music.getTitle() + ".mp3");
            byte[] bytes = new byte[16 * 1024];
            int count;
            try {
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdir();
                file.createNewFile();
                Socket socket = new Socket(this.socket.getInetAddress(), FILE_PORT);
                InputStream inputStream = socket.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                while ((count = inputStream.read(bytes)) > 0)
                    fileOutputStream.write(bytes, 0, count);

                Music receivedMusic = new Music(file);
                System.out.println(receivedMusic.getTitle());
                fileOutputStream.close();
                inputStream.close();
                socket.close();
                System.out.println("Download Finished");

                listener.musicDownloaded(receivedMusic);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedTagException e) {
                e.printStackTrace();
            } catch (NoTagFoundException e) {
                e.printStackTrace();
            } catch (InvalidDataException e) {
                e.printStackTrace();
            }
        }

        public String getUsername() {
            return username;
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
