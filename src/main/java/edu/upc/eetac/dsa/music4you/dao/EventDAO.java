package edu.upc.eetac.dsa.music4you.dao;

import edu.upc.eetac.dsa.music4you.entity.Event;
import edu.upc.eetac.dsa.music4you.entity.EventCollection;

import java.sql.Date;
import java.sql.SQLException;

/**
 * Created by juan on 02/03/16.
 */
public interface EventDAO {
    Event createEvent(String userid, String titol, String text, double lat, double lon, String startDate, String endDate) throws SQLException;
    Event getEventByRatio(int ratio) throws SQLException;
    Event getEvent(String eventid) throws SQLException;
    EventCollection getEventsNow(int userid) throws SQLException;
    EventCollection getEventsNowUser(EventCollection events, int userid) throws SQLException;
    EventCollection getEvents(int length, long before, long after) throws SQLException;
    Event updateEvent(String id, String titol, String text, Date startDate, Date endDate) throws SQLException;
    boolean deleteEvent(String id) throws SQLException;
    //UserCollection getUsersofEventId(String id) throws SQLException;
}