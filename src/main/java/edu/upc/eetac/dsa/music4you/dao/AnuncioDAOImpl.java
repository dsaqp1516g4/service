package edu.upc.eetac.dsa.music4you.dao;

import edu.upc.eetac.dsa.music4you.entity.Anuncio;
import edu.upc.eetac.dsa.music4you.entity.AnuncioCollection;

import javax.imageio.ImageIO;
import javax.ws.rs.InternalServerErrorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Created by pauli on 29/03/2016.
 */
public class AnuncioDAOImpl implements AnuncioDAO {
    @Override
    public Anuncio createAnuncio(String userid, String subject, String description, long precio, int type, InputStream image) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        UUID uuid = writeAndConvertImage(image);
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(AnuncioDAOQuery.CREATE_AD);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, subject);
            stmt.setString(4, description);
            stmt.setLong(5, precio);
            stmt.setInt(6, type);
            stmt.setString(7, uuid.toString());
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
        return getAnuncioById(id);
    }

    private UUID writeAndConvertImage(InputStream file){
        BufferedImage image = null;
        try{
            image= ImageIO.read(file);
        }catch(IOException E){
            throw new InternalServerErrorException("error");
        }
        UUID uuid = UUID.randomUUID();
        String filename = uuid.toString() +".png";
        try{
            PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("music4you");
            ImageIO.write(image, "png", new File(prb.getString("uploadFolder") + filename));
            //System.out.println("File Written:"+funciona);
        }catch(IOException e){
            throw  new InternalServerErrorException("error");
        }
        return uuid;
    }

    @Override
    public Anuncio getAnuncioById(String id) throws SQLException {
        Anuncio anuncio = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(AnuncioDAOQuery.GET_STING_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                anuncio = new Anuncio();
                anuncio.setId(rs.getString("id"));
                anuncio.setUserid(rs.getString("userid"));
                anuncio.setCreator(rs.getString("fullname"));
                anuncio.setSubject(rs.getString("subject"));
                anuncio.setDescription(rs.getString("description"));
                anuncio.setPrecio(rs.getLong("precio"));
                anuncio.setType(rs.getInt("type"));
                anuncio.setImage(rs.getString("image"));
                anuncio.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                anuncio.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return anuncio;
    }

    @Override
    public AnuncioCollection getAnuncios() throws SQLException {
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
                sting.setPrecio(rs.getLong("precio"));
                sting.setType(rs.getInt("type"));
                sting.setImage(rs.getString("image"));
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
    public Anuncio updateAnuncio(String id, String subject, String description, long precio) throws SQLException {

        Anuncio sting = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(AnuncioDAOQuery.UPDATE_STING);
            stmt.setString(1, subject);
            stmt.setString(2, description);
            stmt.setString(3, String.valueOf(precio));
            stmt.setString(4, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                sting = getAnuncioById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return sting;
    }

    @Override
    public boolean deleteAnuncio(String id) throws SQLException {
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

