<!-- # Jpotify -->
<p align="center">
  <img src="logo.png" alt="Jpotify"/>
</p>

Jpotify is a simple Java-based music player designed to organize and play local music files. It offers features similar to popular music players, providing an intuitive and easy-to-use interface.

## Features

- **Play Music**: Play songs from local files.
- **Playlist Management**: Create and manage custom playlists.
- **Music Library**: Browse and organize your music library by artist, album, or song.
- **Media Controls**: Basic playback controls (play, pause, next, previous).
- **P2P Music Sharing**: Share music and playlists with other users over a network using Java sockets.
- **Drag-and-Drop Interface**: Easily create and manage playlists with drag-and-drop functionality.
- **User Interface**: Simple graphical user interface built with Java Swing.

## Architecture

- **Model-View-Controller (MVC)**: The application is developed using the MVC architecture, which modularizes components and enhances the maintainability of the code.
- **Peer-to-Peer (P2P) Networking**: Implemented using Java sockets to enable users to share music and playlists over a local network, allowing a collaborative music experience.

## Libraries Used

- **JLayer**: Used for playing music, supporting the playback of MP3 files.
- **mp3agic**: A library for reading and manipulating MP3 metadata, specifically used for reading ID3 tags (song information such as artist, album, etc.).