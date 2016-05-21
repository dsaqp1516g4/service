package edu.upc.eetac.dsa.music4you.dao;

import edu.upc.eetac.dsa.music4you.Music4youMediaType;
import edu.upc.eetac.dsa.music4you.entity.Playlist;
import edu.upc.eetac.dsa.music4you.entity.PlaylistCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;

/**
 * Created by juan on 30/09/15.
 */
public class PlaylistDAOImpl implements PlaylistDAO {

    @Override
<<<<<<< HEAD
    public Playlist createPlay(String userid, String subject, String content, String youtubelink, String audio, String genre) throws SQLException {
=======
    public Playlist createPlay(String userid, String subject, String content, String youtubelink, String audio, String genre, String year) throws SQLException {
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(PlaylistDAOQuery.CREATE_PLAY);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, subject);
            stmt.setString(4, content);
            stmt.setString(5,youtubelink);
            stmt.setString(6,audio);
            stmt.setString(7,genre);
<<<<<<< HEAD
=======
            stmt.setString(8,year);
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getPlayById(id);
    }

    @Override
    public Playlist getPlayById(String id) throws SQLException {
        Playlist playlist = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(PlaylistDAOQuery.GET_PLAY_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                playlist = new Playlist();
                playlist.setId(rs.getString("id"));
                playlist.setUserid(rs.getString("userid"));
<<<<<<< HEAD
                playlist.setGenre(rs.getString("fullname"));
                playlist.setArtist(rs.getString("subject"));
                playlist.setTitle(rs.getString("content"));
=======
                playlist.setGenre(rs.getString("genre"));
                playlist.setArtist(rs.getString("artist"));
                playlist.setTitle(rs.getString("title"));
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
                playlist.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                playlist.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return playlist;
    }

    @Path("/{id}")
    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_PLAYLIST)
<<<<<<< HEAD
    public Response getSting(@PathParam("id") String id, @Context Request request) {
=======
    public Response getPlay(@PathParam("id") String id, @Context Request request) {
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Playlist playlist = null;
        PlaylistDAO playlistDAO = new PlaylistDAOImpl();
        try {
            playlist = playlistDAO.getPlayById(id);
            if (playlist == null)
                throw new NotFoundException("Playlist with id = " + id + " doesn't exist");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(playlist.getLastModified()));

            // Verify if it matched with etag available in http request
            Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);

            // If ETag matches the rb will be non-null;
            // Use the rb to return the response without any further processing
            if (rb != null) {
                return rb.cacheControl(cacheControl).tag(eTag).build();
            }

            // If rb is null then either it is first time request; or resource is
            // modified
            // Get the updated representation and return with Etag attached to it
            rb = Response.ok(playlist).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @Override
    public PlaylistCollection getPlaylist(long timestamp, boolean before) throws SQLException {
        PlaylistCollection playlistCollection = new PlaylistCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            if(before)
                stmt = connection.prepareStatement(PlaylistDAOQuery.GET_PLAYLIST);
            else
                stmt = connection.prepareStatement(PlaylistDAOQuery.GET_PLAYLIST_AFTER);
            stmt.setTimestamp(1, new Timestamp(timestamp));

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Playlist playlist = new Playlist();
                playlist.setId(rs.getString("id"));
                playlist.setUserid(rs.getString("userid"));
<<<<<<< HEAD
                playlist.setArtist(rs.getString("subject"));
=======
                playlist.setArtist(rs.getString("artist"));
                playlist.setTitle(rs.getString("title"));
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
                playlist.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                playlist.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    playlistCollection.setNewestTimestamp(playlist.getLastModified());
                    first = false;
                }
                playlistCollection.setOldestTimestamp(playlist.getLastModified());
                playlistCollection.getPlaylists().add(playlist);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return playlistCollection;
    }

    @Override
    public Playlist updatePlay(String id, String subject, String content) throws SQLException {
        Playlist playlist = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(PlaylistDAOQuery.UPDATE_PLAY);
            stmt.setString(1, subject);
            stmt.setString(2, content);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                playlist = getPlayById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return playlist;
    }

    @Override
    public boolean deletePlay(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(PlaylistDAOQuery.DELETE_PLAY);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {


            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @Override
<<<<<<< HEAD
    public PlaylistCollection getPlaylistbyArtist(long timestamp, boolean before) throws SQLException {
=======
    public PlaylistCollection getPlaylistbyArtist(String artist, long timestamp, boolean before) throws SQLException {
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
        PlaylistCollection playlistCollection = new PlaylistCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            if(before)
<<<<<<< HEAD
                stmt = connection.prepareStatement(PlaylistDAOQuery.GET_PLAYLIST_BY_USER);
            else
                stmt = connection.prepareStatement(PlaylistDAOQuery.GET_PLAYLIST_AFTER_BY_USER);
            //stmt.setString(1, loginid);
=======
                stmt = connection.prepareStatement(PlaylistDAOQuery.GET_PLAYLIST_BY_ARTIST);
            else
                stmt = connection.prepareStatement(PlaylistDAOQuery.GET_PLAYLIST_BY_ARTIST);
            stmt.setString(1, artist);
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
            stmt.setTimestamp(2, new Timestamp(timestamp));

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Playlist playlist = new Playlist();
                playlist.setId(rs.getString("id"));
                playlist.setUserid(rs.getString("userid"));
<<<<<<< HEAD
                playlist.setArtist(rs.getString("subject"));
=======
                playlist.setArtist(rs.getString("artist"));
                playlist.setTitle(rs.getString("title"));
                playlist.setGenre(rs.getString("genre"));
                playlist.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                playlist.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    playlistCollection.setNewestTimestamp(playlist.getLastModified());
                    first = false;
                }
                playlistCollection.setOldestTimestamp(playlist.getLastModified());
                playlistCollection.getPlaylists().add(playlist);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return playlistCollection;
    }

    @Override
    public PlaylistCollection getPlaylistbyGenre(String genre, long timestamp, boolean before) throws SQLException {
        PlaylistCollection playlistCollection = new PlaylistCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            if(before)
                stmt = connection.prepareStatement(PlaylistDAOQuery.GET_PLAYLIST_BY_GENRE);
            else
                stmt = connection.prepareStatement(PlaylistDAOQuery.GET_PLAYLIST_BY_GENRE);
            stmt.setString(1, genre);
            stmt.setTimestamp(2, new Timestamp(timestamp));

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Playlist playlist = new Playlist();
                playlist.setId(rs.getString("id"));
                playlist.setUserid(rs.getString("userid"));
                playlist.setArtist(rs.getString("artist"));
                playlist.setTitle(rs.getString("title"));
                playlist.setGenre(rs.getString("genre"));
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
                playlist.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                playlist.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    playlistCollection.setNewestTimestamp(playlist.getLastModified());
                    first = false;
                }
                playlistCollection.setOldestTimestamp(playlist.getLastModified());
                playlistCollection.getPlaylists().add(playlist);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return playlistCollection;
    }
}