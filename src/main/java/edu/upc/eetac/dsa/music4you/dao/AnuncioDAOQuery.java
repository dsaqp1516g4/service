package edu.upc.eetac.dsa.music4you.dao;

/**
 * Created by pauli on 29/03/2016.
 */
public interface AnuncioDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_STING = "insert into ads (id, userid, subject, description, image, precio, type) values (UNHEX(?), unhex(?), ?, ?, ?, ?, ?)";
    public final static String GET_STING_BY_ID = "select hex(s.id) as id, hex(s.userid) as userid, s.description, s.subject, s.creation_timestamp, s.last_modified, u.fullname from ads s, users u where s.id=unhex(?) and u.id=s.userid";
    public final static String GET_STINGS = "select hex(id) as id, hex(userid) as userid, subject, precio, type, creation_timestamp, last_modified from ads";
    public final static String UPDATE_STING = "update ads set subject=?, desccription=? where id=unhex(?) ";
    public final static String DELETE_STING = "delete from ads where id=unhex(?)";

}

