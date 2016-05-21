package edu.upc.eetac.dsa.music4you;

/**
 * Created by juan on 10/03/16.
 */
import edu.upc.eetac.dsa.music4you.dao.CommentDAO;
import edu.upc.eetac.dsa.music4you.dao.CommentDAOImpl;
import edu.upc.eetac.dsa.music4you.entity.AuthToken;
import edu.upc.eetac.dsa.music4you.entity.Comment;
import edu.upc.eetac.dsa.music4you.entity.CommentCollection;

import javax.ws.rs.*;
        import javax.ws.rs.core.*;
        import java.net.URI;
        import java.net.URISyntaxException;
        import java.sql.*;

@Path("comments")
public class CommentResource {
    @Context
    private SecurityContext securityContext;

    /*** OK ***/

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(Music4youMediaType.MUSIC4YOU_COMMENT)
    public Response createComment(@FormParam("content") String content,
                                  @FormParam("eventid") String eventid,
                                  @FormParam("anuncioid") String anuncioid,
                                  @Context UriInfo uriInfo) throws URISyntaxException {
        if (content == null || (eventid == null && anuncioid == null))
            throw new BadRequestException("all parameters are mandatory");
        CommentDAO commentDAO = new CommentDAOImpl();
        Comment comment = null;
        AuthToken authToken = null;
        try {
            comment = commentDAO.createComment(securityContext.getUserPrincipal().getName(), anuncioid, eventid, content);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + comment.getId());
        return Response.created(uri).type(Music4youMediaType.MUSIC4YOU_COMMENT).entity(comment).build();
    }

    /***OK***/

    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_COMMENT_COLLECTION)
    public CommentCollection getComments(@QueryParam("length") int length,
                                         @QueryParam("eventid") String eventid,
                                         @QueryParam("anuncioid") String anuncioid,
                                         @QueryParam("before") long before,
                                         @QueryParam("after") long after) {
        CommentCollection commentCollection = null;
        CommentDAO stingDAO = new CommentDAOImpl();
        try {
            commentCollection = stingDAO.getComments(length, eventid, anuncioid, before, after);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return commentCollection;
    }

    /*** OK ***/

    @Path("/{id}")
    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_COMMENT)
    public Response getComment(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Comment comment = null;
        CommentDAO commentDAO = new CommentDAOImpl();
        try {
            comment = commentDAO.getCommentById(id);
            if (comment == null)
                throw new NotFoundException("Comment with id = " + id + " doesn't exist");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(comment.getLastModified()));

            // Verify if it matched with etag available in http request
            Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);

            // If ETag matches the rb will be non-null;
            // Use the rb to return the response without any further processing
            if (rb != null) {
                return rb.cacheControl(cacheControl).tag(eTag).build();
            }

            // If rb is null then either it is first time request; or resource is
            // modified
            // Get the updated representation and return with Etag attached to it
            rb = Response.ok(comment).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    /*** OK ***/

    @Path("/{id}")
    @PUT
    @Consumes(Music4youMediaType.MUSIC4YOU_COMMENT)
    @Produces(Music4youMediaType.MUSIC4YOU_COMMENT)
    public Comment updateSting(@PathParam("id") String id, Comment comment) {
        if (comment == null)
            throw new BadRequestException("entity is null");
        if (!id.equals(comment.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if (!userid.equals(comment.getUserid()))
            throw new ForbiddenException("operation not allowed");

        CommentDAO commentDAO = new CommentDAOImpl();

        try {
            comment = commentDAO.updateComment(id, comment.getContent());
            if (comment == null)
                throw new NotFoundException("Sting with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return comment;
    }

    /*** OK ***/

    @Path("/{id}")
    @DELETE
    public void deletecomment(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        CommentDAO commentDAO = new CommentDAOImpl();
        //UserDAO us = new UserDAOImpl();
        try {
            String ownerid = commentDAO.getCommentById(id).getUserid();
            if (!userid.equals(ownerid)){
                throw new ForbiddenException("operation not allowed");
            }
            if (!commentDAO.deleteComment(id)){
                throw new NotFoundException("User with id = " + id + " doesn't exist");
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

}

