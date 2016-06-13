package edu.upc.eetac.dsa.music4you.dao;

import edu.upc.eetac.dsa.music4you.entity.Message;
import edu.upc.eetac.dsa.music4you.entity.MessageCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by juan on 13/06/16.
 */
public class MessageDAOImpl implements MessageDAO{
    @Override
    public MessageCollection getMessages() throws SQLException {
        MessageCollection stingCollection = new MessageCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(MessageDAOQuery.GET_MSGS);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Message sting = new Message();
                sting.setId(rs.getString("id"));
                sting.setUserid(rs.getString("userid"));
                sting.setText(rs.getString("text"));
                sting.setFromusername(rs.getString("fullname"));
                sting.setDestinatario(rs.getString("destinatario"));
                sting.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                sting.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    stingCollection.setNewestTimestamp(sting.getLastModified());
                    first = false;
                }
                stingCollection.setOldestTimestamp(sting.getLastModified());
                stingCollection.getMessages().add(sting);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return stingCollection;
    }
}
