package edu.upc.eetac.dsa.music4you.dao;

import edu.upc.eetac.dsa.music4you.entity.Comment;
import edu.upc.eetac.dsa.music4you.entity.CommentCollection;

import java.sql.SQLException;

/**
 * Created by juan on 10/03/16.
 */
public interface CommentDAO {
    Comment createComment(String userid, String eventid, String content) throws SQLException ;
    Comment getCommentById(String id) throws SQLException;
    CommentCollection getComments(int length, String eventid, long before, long after) throws SQLException;
    Comment updateComment(String id, String content) throws SQLException;
    boolean deleteComment(String id) throws SQLException;
}
