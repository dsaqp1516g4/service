package edu.upc.eetac.dsa.music4you.dao;

import edu.upc.eetac.dsa.music4you.entity.Event;
import edu.upc.eetac.dsa.music4you.entity.EventCollection;

import javax.ws.rs.NotFoundException;
import java.sql.*;

/**
 * Created by juan on 02/03/16.
 */
public class EventDAOImpl implements EventDAO{
    @Override
    public Event createEvent(String userid, String titol, String text, double lat, double lon, String startDate, String endDate) throws SQLException{
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

            stmt = connection.prepareStatement(EventDAOQuery.CREATE_EVENT);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, titol);
            stmt.setString(4, text);
            stmt.setDouble(5,lat);
            stmt.setDouble(6,lon);
            stmt.setString(7,startDate);
            stmt.setString(8,endDate);
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
        return getEvent(id);
    }


    @Override
    public Event getEventByRatio(int ratio) throws SQLException {
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

            stmt = connection.prepareStatement(EventDAOQuery.GET_EVENT_BY_RATIO);
            stmt.setInt(1, ratio);
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
        return getEvent(id);
    }

    @Override
    public Event getEvent(String id) throws SQLException {
        Event event = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(EventDAOQuery.GET_EVENT_BY_ID_QUERY);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event();
                event.setId(rs.getString("id"));
                event.setUserid(rs.getString("userid"));
                event.setTitol(rs.getString("titol"));
                event.setText(rs.getString("text"));
                event.setLat(rs.getDouble("lat"));
                event.setLon(rs.getDouble("lon"));
                event.setStartDate(rs.getDate("start_date"));
                event.setEndDate(rs.getDate("end_date"));
                event.setLastModified(rs.getTimestamp("last_modified").getTime());

            } else {
                throw new NotFoundException();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return event;
    }

    @Override
    public EventCollection getEventsNow(int userid) throws SQLException {
        EventCollection events = new EventCollection();
        events = getEventsNowUser(events, userid);
        if (events.getEvents().size() == 0)
            throw new NotFoundException("There aren't events");
        return events;
    }

    @Override
    public EventCollection getEventsNowUser(EventCollection events, int userid) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(EventDAOQuery.GET_EVENTS_NOW_USER_QUERY);
            stmt.setInt(1, Integer.valueOf(userid));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getString("id"));
                event.setUserid(rs.getString("userid"));
                event.setTitol(rs.getString("titol"));
                event.setStartDate(rs.getDate("start_date"));
                event.setEndDate(rs.getDate("end_date"));
                event.setLastModified(rs.getTimestamp("last_modified")
                        .getTime());
                events.addEvent(event);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return events;
    }

    @Override
    public EventCollection getEvents(int length, long before, long after) throws SQLException {
        EventCollection eventCollection = new EventCollection();
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            boolean updateFromLast;
            if (!(after == 0)) {
                updateFromLast = true;
            } else {
                updateFromLast = false;
            }
            if (updateFromLast = true) {
                stmt = connection.prepareStatement(EventDAOQuery.GET_EVENT_FROM_LAST);
            } else if(updateFromLast=false){
                stmt = connection.prepareStatement(EventDAOQuery.GET_EVENT_FROM_FIRST);
            }
            if (updateFromLast) {
                stmt.setTimestamp(1, new Timestamp(after));
            } else {
                if (before > 0)
                    stmt.setTimestamp(1, new Timestamp(before));
                else
                    stmt.setTimestamp(1, null);
                length = (length <= 0) ? 10 : length;
                stmt.setInt(2, length);
            }
            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            Date oldestTimestamp = null;
            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getString("id"));
                event.setTitol(rs.getString("titol"));
                event.setText(rs.getString("text"));
                event.setLat(rs.getLong("lat"));
                event.setLon(rs.getLong("lon"));
                event.setStartDate(rs.getDate("start_date"));
                event.setEndDate(rs.getDate("end_date"));
                oldestTimestamp = rs.getDate("start_date");
                if (first) {
                    first = false;
                    eventCollection.setFirstTimestamp(event.getStartDate());
                }
                eventCollection.addEvent(event);
            }
            eventCollection.setLastTimestamp(oldestTimestamp);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return eventCollection;
    }

    @Override
    public Event updateEvent(String id, String titol, String text, Date startDate, Date endDate) throws SQLException{
        Event event = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(EventDAOQuery.UPDATE_EVENT_QUERY);
            stmt.setString(1, titol);
            stmt.setString(2,text);
            stmt.setDate(3, startDate);
            stmt.setDate(4, endDate);
            stmt.setString(5, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                event = getEvent(id);
            else {
                throw new NotFoundException("There's no event with eventid = "+ id);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return event;
    }

    @Override
    public boolean deleteEvent(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(EventDAOQuery.DELETE_EVENT_QUERY);
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
}
