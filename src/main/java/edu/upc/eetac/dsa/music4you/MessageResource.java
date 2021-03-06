package edu.upc.eetac.dsa.music4you;

import edu.upc.eetac.dsa.music4you.dao.*;
import edu.upc.eetac.dsa.music4you.entity.AuthToken;
import edu.upc.eetac.dsa.music4you.entity.Message;
import edu.upc.eetac.dsa.music4you.entity.MessageCollection;
import edu.upc.eetac.dsa.music4you.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hixam on 21/05/16.
 */

@Path("message")
public class MessageResource {
    @Context
    private SecurityContext securityContext;

    @POST
    public Response createMsg(@FormParam("loginid") String loginid,@FormParam("destinatario") String destinatario, @FormParam("text") String text, @Context UriInfo uriInfo) throws URISyntaxException {
        if(loginid==null || text == null || destinatario == null)
            throw new BadRequestException("Debe rellenar todos los campos");
        UserDAO userDAO = new UserDAOImpl();
        UserDAO destino = new UserDAOImpl();
        AuthToken authenticationToken = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        Connection conn = null;
        String userid = securityContext.getUserPrincipal().getName();
        Date date= new Date();
        User user =null;
        String dst = null;

        try {
            user = userDAO.getUserByLoginid(loginid);
            if (user==null)
                throw new NotFoundException("LoginID "+loginid+" doesn't exist");

            dst = destino.getUserByLoginid(destinatario).getId();
            System.out.print(dst);
            if (dst==null)
                throw new NotFoundException("Destinatario "+destinatario+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        try {
            conn = Database.getConnection();

            stmt = conn.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();


        } catch (SQLException e) {
            throw new ServerErrorException("Could not connect to the database",
                    Response.Status.SERVICE_UNAVAILABLE);
        }

        try {

            stmt = conn.prepareStatement(MessageDAOQuery.CREATE_MSG);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, dst);
            stmt.setString(4, text);

            System.out.print(stmt);

            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new ServerErrorException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }

        System.out.print(stmt);

        Message msg = new Message();
        msg.setId(id);
        msg.setUserid(loginid);
        msg.setDestinatario(dst);
        msg.setText(text);
        msg.setCreationTimestamp(date.getTime());

        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + msg.getId());
        return Response.created(uri).type(Music4youMediaType.MUSIC4YOU_MESSAGE).entity(msg).build();
    }

    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_MESSAGE_COLLECTION)
    public MessageCollection getMessages(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        MessageCollection messageCollection = null;
        MessageDAO stingDAO = new MessageDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            messageCollection = stingDAO.getMessages();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return messageCollection;
    }

    /* @GET
    @Produces(Music4youMediaType.MUSIC4YOU_MESSAGE_COLLECTION)
    public MessageCollection getMessages(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        MessageCollection allmsg = new MessageCollection();
        String userid = securityContext.getUserPrincipal().getName();
        Connection connection = null;
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = Database.getConnection();
            stmt = conn.prepareStatement(UserDAOQuery.CHECK_MSGS);
            stmt.setString(1, userid);
            stmt.executeQuery();

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Message msg = new Message();
                msg.setUserid(rs.getString("userid"));
                msg.setFromusername(rs.getString("fromusername"));
                msg.setNummsgs(rs.getInt("count"));
                msg.setText(rs.getString("text"));
                msg.setCreationTimestamp(rs.getString("creation_timestamp"));
                allmsg.getMessages().add(msg);
            }
        } catch (SQLException e) {
            throw new ServerErrorException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
        return allmsg;
    } */


}
