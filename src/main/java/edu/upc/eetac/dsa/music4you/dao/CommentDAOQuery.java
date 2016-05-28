package edu.upc.eetac.dsa.music4you.dao;

/**
 * Created by juan on 10/03/16.
 */
public class CommentDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String GET_COMMENTS_QUERY = "select hex(c.id) as id, hex(c.userid) as userid, hex(c.anuncioid) as anuncioid, c.content, c.creation_timestamp, c.last_modified, u.fullname from comments c, users u where u.id=c.userid and c.creation_timestamp < ifnull(?, now()) and c.anuncioid = unhex(?) order by creation_timestamp";
    public final static String GET_COMMENTS_QUERY_FROM_LAST = "select hex(c.id) as id, hex(c.userid) as userid, hex(c.anuncioid) as anuncioid, c.content, c.creation_timestamp, c.last_modified, u.fullname from comments c, users u where u.id=c.userid and c.creation_timestamp < ifnull(?, now()) and c.anuncioid = unhex(?) order by creation_timestamp";
    public final static String GET_COMMENT_BY_ID_QUERY = "select HEX(c.id) as id, hex(c.userid) as userid, hex(c.eventid) as eventid, hex(c.anuncioid) as anuncioid, c.content, c.creation_timestamp, c.last_modified, u.fullname from comments c, users u where c.id=unhex(?) and u.id=c.userid";
    public final static String INSERT_COMMENT_QUERY = "insert into comments (id, userid, eventid, anuncioid, content) values (unhex(?), unhex(?), unhex(?), unhex(?), ?)";
    public final static String UPDATE_COMMENT_QUERY = "update comments set content=? where id = unhex(?)";
    public final static String DELETE_COMMENT_QUERY = "delete from comments where id = unhex(?)";
    public final static String GET_COMMENTS_EVENT_QUERY = "select hex(c.id) as id, hex(c.userid) as userid, hex(c.eventid) as eventid, c.content, c.creation_timestamp, c.last_modified from comments c where c.creation_timestamp < ifnull(?, now()) and c.eventid = unhex(?) order by creation_timestamp";
    public final static String GET_COMMENTS_EVENT_QUERY_FROM_LAST="select hex(c.id) as id, hex(c.userid) as userid, hex(c.eventid) as eventid, c.content, c.creation_timestamp, c.last_modified from comments c where c.creation_timestamp < ifnull(?, now()) and c.eventid = unhex(?) order by creation_timestamp";
}
