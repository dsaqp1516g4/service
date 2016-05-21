package edu.upc.eetac.dsa.music4you.dao;

import edu.upc.eetac.dsa.music4you.entity.Playlist;
import edu.upc.eetac.dsa.music4you.entity.PlaylistCollection;

import java.sql.SQLException;

/**
 * Created by juan on 30/09/15.
 */
public interface PlaylistDAO {
<<<<<<< HEAD
    Playlist createPlay(String userid, String subject, String content, String youtubelink, String audio, String genre) throws SQLException;
=======
    Playlist createPlay(String userid, String subject, String content, String youtubelink, String audio, String genre, String year) throws SQLException;
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
    Playlist getPlayById(String id) throws SQLException;
    PlaylistCollection getPlaylist(long timestamp, boolean before) throws SQLException;
    Playlist updatePlay(String id, String subject, String content) throws SQLException;
    boolean deletePlay(String id) throws SQLException;
<<<<<<< HEAD
    PlaylistCollection getPlaylistbyArtist(long timestamp, boolean before) throws SQLException;
=======
    PlaylistCollection getPlaylistbyArtist(String artist, long timestamp, boolean before) throws SQLException;
    PlaylistCollection getPlaylistbyGenre(String genre, long timestamp, boolean before) throws SQLException;
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
}
