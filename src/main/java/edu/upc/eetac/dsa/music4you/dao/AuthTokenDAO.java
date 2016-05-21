package edu.upc.eetac.dsa.music4you.dao;

import edu.upc.eetac.dsa.music4you.auth.UserInfo;
import edu.upc.eetac.dsa.music4you.entity.AuthToken;

import java.sql.SQLException;

/**
 * Created by juan on 30/09/15.
 */
public interface AuthTokenDAO {
    public UserInfo getUserByAuthToken(String token) throws SQLException;
    public AuthToken createAuthToken(String userid) throws SQLException;
    public void deleteToken(String userid) throws  SQLException;
}
