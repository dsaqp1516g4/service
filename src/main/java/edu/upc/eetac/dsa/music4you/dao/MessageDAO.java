package edu.upc.eetac.dsa.music4you.dao;

import edu.upc.eetac.dsa.music4you.entity.MessageCollection;

import java.sql.SQLException;

/**
 * Created by juan on 13/06/16.
 */
public interface MessageDAO {
    MessageCollection getMessages() throws SQLException;
}
