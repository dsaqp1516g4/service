package edu.upc.eetac.dsa.music4you.dao;

/**
 * Created by juan on 30/09/15.
 */
public class PlaylistDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_PLAY = "insert into playlist (id, userid, artist, title, audio, youtubelink, genre, publication_yr) values (UNHEX(?), unhex(?), ?, ?, ?, ?, ?, ?)";
    public final static String GET_PLAY_BY_ID = "select hex(p.id) as id, hex(p.userid) as userid, p.title, p.artist, p.creation_timestamp, p.last_modified, u.id from playlist p, users u where p.id=unhex(?) and u.id=p.userid";
    public final static String GET_PLAYLIST_BY_USER = "select hex(p.id) as id, hex(p.userid) as userid, p.artist, p.creation_timestamp, p.last_modified, u.id from playlist p, users u where creation_timestamp < ? and u.id=p.userid order by creation_timestamp desc limit 5";
    public final static String GET_PLAYLIST_AFTER_BY_USER = "select hex(p.id) as id, hex(p.userid) as userid, p.artist, p.creation_timestamp, p.last_modified, u.id from playlist p, users u where creation_timestamp > ? and u.id=p.userid order by creation_timestamp desc limit 5";
    public final static String GET_PLAYLIST = "select hex(id) as id, hex(userid) as userid, artist, creation_timestamp, last_modified from playlist where creation_timestamp < ? order by creation_timestamp desc limit 5";
    public final static String GET_PLAYLIST_AFTER = "select hex(id) as id, hex(userid) as userid, artist, creation_timestamp, last_modified from playlist where creation_timestamp > ? order by creation_timestamp desc limit 5";
    public final static String UPDATE_PLAY = "update playlist set artist=?, title=? where id=unhex(?) ";
    public final static String DELETE_PLAY = "delete from playlist where id=unhex(?)";
}
