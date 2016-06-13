package edu.upc.eetac.dsa.music4you.dao;

/**
 * Created by juan on 13/06/16.
 */
public class MessageDAOQuery {
    public final static String CREATE_MSG = "insert into mensajeria (id,userid,destinatario,text) values (unhex(?),unhex(?),unhex(?),?)";
    public final static String GET_MSGS = "select hex(m.id) as id, hex(m.userid) as userid, hex(m.destinatario) as destinatario, m.text, m.creation_timestamp, m.last_modified, hex(u.id) as id, u.fullname from mensajeria m, users u where u.id=m.destinatario";
}
