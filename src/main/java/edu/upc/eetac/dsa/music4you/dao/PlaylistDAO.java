package edu.upc.eetac.dsa.music4you.dao;

import edu.upc.eetac.dsa.music4you.entity.Playlist;
import edu.upc.eetac.dsa.music4you.entity.PlaylistCollection;

import java.sql.SQLException;

/**
 * Created by juan on 30/09/15.
 */
public interface PlaylistDAO {
    Playlist createPlay(String userid, String subject, String content, String youtubelink, String audio, String genre) throws SQLException;
    Playlist getPlayById(String id) throws SQLException;
    PlaylistCollection getPlaylist(long timestamp, boolean before) throws SQLException;
    Playlist updatePlay(String id, String subject, String content) throws SQLException;
    boolean deletePlay(String id) throws SQLException;
    PlaylistCollection getPlaylistbyArtist(long timestamp, boolean before) throws SQLException;
}
