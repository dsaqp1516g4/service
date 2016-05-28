package edu.upc.eetac.dsa.music4you;

import edu.upc.eetac.dsa.music4you.dao.AnuncioDAO;
import edu.upc.eetac.dsa.music4you.dao.AnuncioDAOImpl;
import edu.upc.eetac.dsa.music4you.entity.Anuncio;
import edu.upc.eetac.dsa.music4you.entity.AnuncioCollection;
import edu.upc.eetac.dsa.music4you.entity.AuthToken;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by pauli on 30/03/2016.
 */
@Path("anuncio")
public class AnuncioResource {
    @Context
    private SecurityContext securityContext;
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(Music4youMediaType.MUSIC4YOU_Anuncio)
    public Response createAnuncio(@FormDataParam("subject") String subject, @FormDataParam("description") String description, @FormDataParam("precio") Long precio, @FormDataParam("type") int type,@Context UriInfo uriInfo) throws URISyntaxException {
        if(subject==null || description == null || type == 0)
            throw new BadRequestException("all parameters are mandatory");
        AnuncioDAO stingDAO = new AnuncioDAOImpl();
        Anuncio sting = null;
        AuthToken authenticationToken = null;
        try {
            sting = stingDAO.createAnuncio(securityContext.getUserPrincipal().getName(), subject, description,precio, type);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + sting.getId());
        return Response.created(uri).type(Music4youMediaType.MUSIC4YOU_Anuncio).entity(sting).build();
    }
    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_Anuncio_Collection)
    public AnuncioCollection getAnuncios(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        AnuncioCollection anuncioCollection = null;
        AnuncioDAO stingDAO = new AnuncioDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            anuncioCollection = stingDAO.getAnuncios();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return anuncioCollection;
    }
    @Path("/{id}")
    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_Anuncio)
    public Response getAnuncio(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Anuncio sting = null;
        AnuncioDAO stingDAO = new AnuncioDAOImpl();
        try {
            sting = stingDAO.getAnuncioById(id);
            if (sting == null)
                throw new NotFoundException("Anuncio with id = " + id + " doesn't exist");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(sting.getLastModified()));

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
            rb = Response.ok(sting).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
    @Path("/{id}")
    @PUT
    @Consumes(Music4youMediaType.MUSIC4YOU_Anuncio)
    @Produces(Music4youMediaType.MUSIC4YOU_Anuncio)
    public Anuncio updateAnuncio(@PathParam("id") String id, Anuncio sting) {
        if(sting == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(sting.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(sting.getUserid()))
            throw new ForbiddenException("operation not allowed");

        AnuncioDAO stingDAO = new AnuncioDAOImpl();
        try {
            sting = stingDAO.updateAnuncio(id, sting.getSubject(), sting.getDescription());
            if(sting == null)
                throw new NotFoundException("Anuncio with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return sting;
    }

    @Path("/{id}")
    @DELETE
    public void deleteSting(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        AnuncioDAO stingDAO = new AnuncioDAOImpl();
        try {
            String ownerid = stingDAO.getAnuncioById(id).getUserid();
            if(!userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if(!stingDAO.deleteAnuncio(id))
                throw new NotFoundException("Sting with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}
