package edu.upc.eetac.dsa.music4you;

import edu.upc.eetac.dsa.music4you.dao.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.music4you.dao.UserAlreadyExistsException;
import edu.upc.eetac.dsa.music4you.dao.UserDAO;
import edu.upc.eetac.dsa.music4you.dao.UserDAOImpl;
import edu.upc.eetac.dsa.music4you.entity.AuthToken;
import edu.upc.eetac.dsa.music4you.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by juan on 05/10/15.
 */
@Path("users")
public class UserResource {


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(Music4youMediaType.BEETER_AUTH_TOKEN)
    public Response registerUser(@FormParam("loginid") String loginid, @FormParam("password")
    String password, @FormParam("email") String email, @FormParam("fullname") String fullname, @Context UriInfo uriInfo)
            throws URISyntaxException {
        if(loginid == null || password == null || email == null || fullname == null)
            throw new BadRequestException("all parameters are mandatory");
        UserDAO userDAO = new UserDAOImpl();
        User user = null;
        AuthToken authenticationToken = null;
        try{
            user = userDAO.createUser(loginid, password, email, fullname);
            authenticationToken = (new AuthTokenDAOImpl()).createAuthToken(user.getId());
        }catch (UserAlreadyExistsException e){
            throw new WebApplicationException("loginid already exists", Response.Status.CONFLICT);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + user.getId());
        return Response.created(uri).type(Music4youMediaType.BEETER_AUTH_TOKEN).entity(authenticationToken).build();
    }

    @Path("/{id}")
    @GET
    @Produces(Music4youMediaType.SOCIALMUSIC_USER)
    public User getUser(@PathParam("id") String id) {
        User user = null;
        try {
            user = (new UserDAOImpl()).getUserById(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(user == null)
            throw new NotFoundException("User with id = "+id+" doesn't exist");
        return user;
    }

    @Context
    private SecurityContext securityContext;
    @Path("/{id}")
    @PUT
    @Consumes(Music4youMediaType.SOCIALMUSIC_USER)
    @Produces(Music4youMediaType.SOCIALMUSIC_USER)
    public User updateUser(@PathParam("id") String id, User user) {
        if(user == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(user.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(id))
            throw new ForbiddenException("operation not allowed");

        UserDAO userDAO = new UserDAOImpl();
        try {
            user = userDAO.updateProfile(userid, user.getEmail(), user.getFullname());
            if(user == null)
                throw new NotFoundException("User with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return user;
    }

    @Path("/{id}")
    @DELETE
    public void deleteUser(@PathParam("id") String id){
        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(id))
            throw new ForbiddenException("operation not allowed");
        UserDAO userDAO = new UserDAOImpl();
        try {
            if(!userDAO.deleteUser(id))
                throw new NotFoundException("User with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

}
