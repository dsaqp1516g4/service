package edu.upc.eetac.dsa.music4you;

import edu.upc.eetac.dsa.music4you.dao.*;
import edu.upc.eetac.dsa.music4you.entity.Anuncio;
import edu.upc.eetac.dsa.music4you.entity.Messages;
import edu.upc.eetac.dsa.music4you.entity.User;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

/**
 * Created by hixam on 22/05/16.
 */


@Path("anuncio/{anuncioid}/email")
public class Email {
    @Context
    private SecurityContext securityContext;

    @Context
    private Application app;

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    @POST
    public Response createEmail(@PathParam("anuncioid") String anuncioId, @FormParam("text") String bodytext, @Context UriInfo uriInfo) throws AddressException, MessagingException,URISyntaxException {
        Messages email = new Messages();
        String userid=securityContext.getUserPrincipal().getName();
        Anuncio ads = null;
        AnuncioDAO adsDAO = new AnuncioDAOImpl();
        User touser = null;
        String id = null;
        UserDAO userDAO = new UserDAOImpl();
        User fromuser = null;
        Connection conn = null;
        PreparedStatement stmt = null;

        String user = null;
        String contrasenya = null;


        try {
            ads = adsDAO.getAnuncioById(anuncioId);
            if (ads == null)
                throw new NotFoundException("Room with id = " + anuncioId + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        try {
            touser = userDAO.getUserById(ads.getUserid());
            if (touser == null)
                throw new NotFoundException("User with id = " + ads.getUserid() + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        try {
            fromuser = userDAO.getUserById(userid);
            if (fromuser == null)
                throw new NotFoundException("User with id = " + userid + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        mailServerProperties.put("mail.smtp.starttls.required", "true");
        mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);

        generateMailMessage.setReplyTo(new InternetAddress[] {new InternetAddress(fromuser.getEmail().toString())});
        generateMailMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(touser.getEmail().toString()));
        generateMailMessage.addRecipient(javax.mail.Message.RecipientType.BCC, new InternetAddress("j0rd1984@hotmail.com"));
        generateMailMessage.addRecipient(javax.mail.Message.RecipientType.BCC, new InternetAddress("ruben.molina.daza@gmail.com"));
        generateMailMessage.addRecipient(javax.mail.Message.RecipientType.BCC, new InternetAddress("marcelus.adolfo.zeron@estudiant.upc.edu"));
        generateMailMessage.addRecipient(javax.mail.Message.RecipientType.CC, new InternetAddress(fromuser.getEmail().toString()));
        generateMailMessage.setSubject("ApartmentShare Messages - No Reply");
        String emailBody = "Hola "+ touser.getFullname()+"," + "<br><br>" + fromuser.getFullname() + " esta interesado/a en la habitación <a href="+app.getProperties().get("imgBaseURL").toString()+ ads.getCreator()+">Clicar aquí</a> y te acaba de enviar un mensaje:<br><br><i>"+bodytext+"</i><br><br>Atentamente Music4You Staff";
        generateMailMessage.setContent(emailBody, "text/html");

        Transport transport = getMailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", user, contrasenya);
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();

        email.setText(bodytext);
        email.setDestinatario(fromuser.getEmail().toString());
        email.setUserid(touser.getEmail().toString());

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
            conn = Database.getConnection();
            stmt = conn.prepareStatement(UserDAOQuery.CREATE_MSG);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, touser.getId());
            stmt.setString(4, bodytext);
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

        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + email.getDestinatario());
        return Response.created(uri).type(Music4youMediaType.MUSIC4YOU_SEND_EMAIL).entity(email).build();
    }


}
