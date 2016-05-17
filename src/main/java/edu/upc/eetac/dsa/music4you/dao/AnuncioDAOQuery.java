package edu.upc.eetac.dsa.music4you.dao;

/**
 * Created by pauli on 29/03/2016.
 */
public interface AnuncioDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    //public final static String CREATE_AD = "insert into ads (id, userid, subject, description, image, precio, type) values (UNHEX(?), unhex(?), ?, ?, ?, ?, ?)";
    String CREATE_AD = "insert into ads (id, userid, subject, description, precio, type, image) values (UNHEX(?), unhex(?), ?, ?, ?, ?, ?)";
    String GET_STING_BY_ID = "select hex(s.id) as id, hex(s.userid) as userid, s.description, s.subject, s.precio, s.type, s.image, s.creation_timestamp, s.last_modified, u.fullname from ads s, users u where s.id=unhex(?) and u.id=s.userid";
    String GET_STINGS = "select hex(id) as id, hex(userid) as userid, subject, description, precio, type, image, creation_timestamp, last_modified from ads";
    String UPDATE_STING = "update ads set subject=?, description=?, precio=? where id=unhex(?) ";
    String DELETE_STING = "delete from ads where id=unhex(?)";

}

