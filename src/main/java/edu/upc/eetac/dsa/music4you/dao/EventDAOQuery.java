package edu.upc.eetac.dsa.music4you.dao;

/**
 * Created by juan on 02/03/16.
 */
public class EventDAOQuery {

<<<<<<< HEAD
    public final static String GET_EVENT_BY_ID_QUERY = "select hex(id) as id, hex(userid)as userid, titol, text, lat, lon, start_date, end_date, last_modified, ratio from events where id = unhex(?)";
    public final static String GET_EVENTS_NOW_USER_QUERY = "select * from events where startdate < now() and now() < enddate and userid = unhex(?) order by startdate asc";
    public final static String UPDATE_EVENT_QUERY = "update events set titol = ifnull(?, titol), text = ifnull(?, text), start_date = ifnull(?, start_date), end_date = ifnull(?, end_date) where id = unhex(?)";
    public final static String DELETE_EVENT_QUERY = "delete from events where id = unhex(?)";
    public final static String CREATE_EVENT = "insert into events (id, userid, titol, text, lat, lon, start_date, end_date, ratio) values (unhex(?),unhex(?),?,?,?,?,?,?,?)";
    public final static String GET_EVENT_FROM_LAST="SELECT hex(e.id) as id, hex(e.userid) as userid, e.titol, e.text, e.lat, e.lon, e.start_date, e.end_date, e.last_modified, e.ratio FROM events e WHERE start_date > ? ORDER BY start_date DESC";
    public final static String GET_EVENT_FROM_FIRST="SELECT hex(e.id) as id, hex(e.userid) as userid, e.titol, e.text, e.lat, e.lon, e.start_date, e.end_date, e.last_modified, e.ratio FROM events e WHERE start_date < ifnull(?, now()) ORDER BY start_date DESC LIMIT ?";
    public final static String GET_EVENT_BY_RATIO="SELECT hex(e.id) as id, hex(e.userid) as userid, e.titol, e.text, e.lat, e.lon, e.start_date, e.end_date, e.last_modified, e.ratio FROM events e WHERE ratio >?";
=======
    public final static String GET_EVENT_BY_ID_QUERY = "select hex(id) as id, hex(userid)as userid, titol, text, lat, lon, start_date, end_date, last_modified from events where id = unhex(?)";
    public final static String GET_EVENTS_NOW_USER_QUERY = "select * from events where startdate < now() and now() < enddate and userid = unhex(?) order by startdate asc";
    public final static String UPDATE_EVENT_QUERY = "update events set titol = ifnull(?, titol), text = ifnull(?, text), start_date = ifnull(?, start_date), end_date = ifnull(?, end_date) where id = unhex(?)";
    public final static String DELETE_EVENT_QUERY = "delete from events where id = unhex(?)";
    public final static String CREATE_EVENT = "insert into events (id, userid, titol, text, lat, lon, start_date, end_date) values (unhex(?),unhex(?),?,?,?,?,?,?)";
    public final static String GET_EVENT_FROM_LAST="SELECT hex(e.id) as id, hex(e.userid) as userid, e.titol, e.text, e.lat, e.lon, e.start_date, e.end_date, e.last_modified FROM events e WHERE start_date > ? ORDER BY start_date DESC";
    public final static String GET_EVENT_FROM_FIRST="SELECT hex(e.id) as id, hex(e.userid) as userid, e.titol, e.text, e.lat, e.lon, e.start_date, e.end_date, e.last_modified FROM events e WHERE start_date < ifnull(?, now()) ORDER BY start_date DESC LIMIT ?";
    public final static String GET_EVENT_BY_RATIO="SELECT hex(e.id) as id, hex(e.userid) as userid, e.titol, e.text, e.lat, e.lon, e.start_date, e.end_date, e.last_modified FROM events e WHERE ratio >?";
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
}
