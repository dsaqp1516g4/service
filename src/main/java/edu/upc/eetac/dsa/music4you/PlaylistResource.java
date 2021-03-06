package edu.upc.eetac.dsa.music4you;

import edu.upc.eetac.dsa.music4you.dao.PlaylistDAO;
import edu.upc.eetac.dsa.music4you.entity.AuthToken;
import edu.upc.eetac.dsa.music4you.dao.PlaylistDAOImpl;
import edu.upc.eetac.dsa.music4you.entity.Playlist;
import edu.upc.eetac.dsa.music4you.entity.PlaylistCollection;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by juan on 14/10/15.
 */
@Path("songs")
public class PlaylistResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(Music4youMediaType.MUSIC4YOU_PLAYLIST)
    public Response createPlay(@FormDataParam("artist") String artist, @FormDataParam("title") String title,
                               @FormDataParam("youtubelink") String youtubelink,
                               @FormDataParam("audio") InputStream audio, @FormDataParam("audio") FormDataContentDisposition fileDetail,
                               @FormDataParam("genre") String genre,
                               @Context UriInfo uriInfo) throws URISyntaxException {
        if(artist==null || title == null)
            throw new BadRequestException("all parameters are mandatory");
        PlaylistDAO playlistDAO = new PlaylistDAOImpl();
        Playlist playlist = null;
        AuthToken authenticationToken = null;
        try {
            playlist = playlistDAO.createPlay(securityContext.getUserPrincipal().getName(), artist, title, youtubelink, audio);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + playlist.getId());
        return Response.created(uri).type(Music4youMediaType.MUSIC4YOU_PLAYLIST).entity(playlist).build();
    }

    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_PLAYLIST_COLLECTION)
    public PlaylistCollection getPosts(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        PlaylistCollection playlistCollection = null;
        PlaylistDAO playlistDAO = new PlaylistDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            playlistCollection = playlistDAO.getPlaylist(timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return playlistCollection;
    }

    @RolesAllowed("admin")
    @Path("/byartist")
    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_PLAYLIST_COLLECTION)
    public PlaylistCollection getPostsByArtist(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        PlaylistCollection playlistCollection = null;
        PlaylistDAO playlistDAO = new PlaylistDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            playlistCollection = playlistDAO.getPlaylistbyArtist(timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return playlistCollection;
    }

    @Path("/{id}")
    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_PLAYLIST)
    public Playlist getPost(@PathParam("id") String id){
        Playlist playlist = null;
        PlaylistDAO playlistDAO = new PlaylistDAOImpl();
        try {
            playlist = playlistDAO.getPlayById(id);
            if(playlist == null)
                throw new NotFoundException("Playlist with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return playlist;
    }
    @Path("/{id}")
    @PUT
    @Consumes(Music4youMediaType.MUSIC4YOU_PLAYLIST)
    @Produces(Music4youMediaType.MUSIC4YOU_PLAYLIST)
    public Playlist updatePost(@PathParam("id") String id, Playlist playlist) {
        if(playlist == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(playlist.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(playlist.getUserid()))
            throw new ForbiddenException("operation not allowed");

        PlaylistDAO playlistDAO = new PlaylistDAOImpl();
        try {
            playlist = playlistDAO.updatePlay(id, playlist.getArtist(), playlist.getTitle());
            if(playlist == null)
                throw new NotFoundException("Playlist with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return playlist;
    }
    @Path("/{id}")
    @DELETE
    public void deletePost(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        PlaylistDAO playlistDAO = new PlaylistDAOImpl();
        try {
            String ownerid = playlistDAO.getPlayById(id).getUserid();
            if(!userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if(!playlistDAO.deletePlay(id))
                throw new NotFoundException("Playlist with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

}


