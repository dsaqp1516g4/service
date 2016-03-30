package edu.upc.eetac.dsa.music4you.dao;

import edu.upc.eetac.dsa.music4you.entity.Anuncio;
import edu.upc.eetac.dsa.music4you.entity.AnuncioCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by pauli on 29/03/2016.
 */
public class AnuncioDAOImpl implements AnuncioDAO {
    @Override
    public Anuncio createSting(String userid, String subject, String description, String image, long precio, int type) throws SQLException {
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

            stmt = connection.prepareStatement(AnuncioDAOQuery.CREATE_STING);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, subject);
            stmt.setString(4, description);
            stmt.setString(5, image);
            stmt.setLong(6, precio);
            stmt.setInt(7, type);
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
        return getStingById(id);
    }

    @Override
    public Anuncio getStingById(String id) throws SQLException {
        Anuncio sting = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(AnuncioDAOQuery.GET_STING_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sting = new Anuncio();
                sting.setId(rs.getString("id"));
                sting.setUserid(rs.getString("userid"));
                sting.setCreator(rs.getString("fullname"));
                sting.setSubject(rs.getString("subject"));
                sting.setDescription(rs.getString("description"));
                sting.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                sting.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return sting;
    }

    @Override
    public AnuncioCollection getStings() throws SQLException {
        AnuncioCollection stingCollection = new AnuncioCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(AnuncioDAOQuery.GET_STINGS);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Anuncio sting = new Anuncio();
                sting.setId(rs.getString("id"));
                sting.setUserid(rs.getString("userid"));
                sting.setSubject(rs.getString("subject"));
                sting.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                sting.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    stingCollection.setNewestTimestamp(sting.getLastModified());
                    first = false;
                }
                stingCollection.setOldestTimestamp(sting.getLastModified());
                stingCollection.getStings().add(sting);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return stingCollection;
    }

    @Override
    public Anuncio updateSting(String id, String subject, String description) throws SQLException {

        Anuncio sting = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(AnuncioDAOQuery.UPDATE_STING);
            stmt.setString(1, subject);
            stmt.setString(2, description);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                sting = getStingById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return sting;
    }

    @Override
    public boolean deleteSting(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(AnuncioDAOQuery.DELETE_STING);
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

