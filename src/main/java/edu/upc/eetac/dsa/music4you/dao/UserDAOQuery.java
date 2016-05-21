package edu.upc.eetac.dsa.music4you.dao;

/**
 * Created by juan on 30/09/15.
 */
public interface UserDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_USER = "insert into users (id, loginid, password, email, fullname) values (UNHEX(?), ?, UNHEX(MD5(?)), ?, ?);";
    public final static String UPDATE_USER = "update users set email=?, fullname=? where id=unhex(?)";
    public final static String ASSIGN_ROLE_REGISTERED = "insert into user_roles (userid, role) values (UNHEX(?), 'registered')";
    public final static String GET_USER_BY_ID = "select hex(u.id) as id, u.loginid, u.email, u.fullname from users u where id=unhex(?)";
    public final static String GET_USER_BY_USERNAME = "select hex(u.id) as id, u.loginid, u.email, u.fullname from users u where u.loginid=?";
    public final static String DELETE_USER = "delete from users where id=unhex(?)";
    public final static String GET_PASSWORD =  "select hex(password) as password from users where id=unhex(?)";
    public final static String CREATE_MSG = "insert into mensajeria (id,userid,destinatario,text) values (unhex(?),unhex(?),unhex(?),?)";
    public final static String CHECK_MSGS ="select hex(userid) as userid, users.fullname as fromusername, count(*) as count,creation_timestamp from mensajeria left join users ON users.id=messages.userid where destinatario=unhex(?) group by userid order by creation_timestamp desc";
    public final static String READ_MSGS ="select creation_timestamp, hex(userid) as userid, users.fullname as fromusername,hex(touser) as destinatario,text from mensajeria left join users ON users.id=mensajeria.userid where (destinatario=unhex(?) and userid=unhex(?)) or (destinatario=unhex(?) and userid=unhex(?)) order by creation_timestamp desc;";
}
