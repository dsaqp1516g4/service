package edu.upc.eetac.dsa.music4you.dao;

import edu.upc.eetac.dsa.music4you.entity.User;

import java.sql.SQLException;

/**
 * Created by juan on 30/09/15.
 */
public interface UserDAO {
    User createUser(String loginid, String password, String email, String fullname) throws SQLException, UserAlreadyExistsException;
    User updateProfile(String id, String email, String fullname) throws SQLException;
    User getUserById(String id) throws SQLException;
    User getUserByLoginid(String loginid) throws SQLException;
    boolean deleteUser(String id) throws SQLException;
    boolean checkPassword(String id, String password) throws SQLException;
}
