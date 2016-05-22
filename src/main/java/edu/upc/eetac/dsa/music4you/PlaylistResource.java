package edu.upc.eetac.dsa.music4you;

import edu.upc.eetac.dsa.music4you.dao.PlaylistDAO;
import edu.upc.eetac.dsa.music4you.entity.AuthToken;
import edu.upc.eetac.dsa.music4you.dao.PlaylistDAOImpl;
import edu.upc.eetac.dsa.music4you.entity.Playlist;
import edu.upc.eetac.dsa.music4you.entity.PlaylistCollection;
<<<<<<< HEAD

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
=======
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by juan on 14/10/15.
 */
<<<<<<< HEAD
@Path("posts")
=======
@Path("songs")

>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
public class PlaylistResource {
    @Context
    private SecurityContext securityContext;

<<<<<<< HEAD
    @POST
    public Response createPost(@FormParam("artist") String artist, @FormParam("title") String title,
                               @FormParam("youtubelink") String youtubelink, @FormParam("audio") String audio,
                               @FormParam("genre") String genre,
=======
    /* OK */

    @POST
    //@Consumes (MediaType.MULTIPART_FORM_DATA)
    //@Produces(Music4youMediaType.MUSIC4YOU_PLAYLIST)
    public Response createPlay(@FormDataParam("artist") String artist, @FormDataParam("title") String title,
                               @FormDataParam("youtubelink") String youtubelink, @FormDataParam("audio") String audio,
                               @FormDataParam("genre") String genre, @FormDataParam("year") String year,
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
                               @Context UriInfo uriInfo) throws URISyntaxException {
        if(artist==null || title == null)
            throw new BadRequestException("all parameters are mandatory");
        PlaylistDAO playlistDAO = new PlaylistDAOImpl();
        Playlist playlist = null;
        AuthToken authenticationToken = null;
        try {
<<<<<<< HEAD
            playlist = playlistDAO.createPlay(securityContext.getUserPrincipal().getName(), artist, title, youtubelink, audio, genre);
=======
            playlist = playlistDAO.createPlay(securityContext.getUserPrincipal().getName(), artist, title, youtubelink, audio, genre, year);
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + playlist.getId());
        return Response.created(uri).type(Music4youMediaType.MUSIC4YOU_PLAYLIST).entity(playlist).build();
    }

<<<<<<< HEAD
    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_PLAYLIST_COLLECTION)
    public PlaylistCollection getPosts(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
=======
    /* OK */

    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_PLAYLIST_COLLECTION)
    public PlaylistCollection getPlaylist(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
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

<<<<<<< HEAD
    @RolesAllowed("admin")
    @Path("/byartist")
    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_PLAYLIST_COLLECTION)
    public PlaylistCollection getPostsByArtist(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
=======
    /* OK */

    @Path("/byartist/{artista}")
    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_PLAYLIST_COLLECTION_ARTIST)
    public PlaylistCollection getPostsByArtist(@PathParam("artista") String artista, @QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        PlaylistCollection playlistCollection = null;
        PlaylistDAO playlistDAO = new PlaylistDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            playlistCollection = playlistDAO.getPlaylistbyArtist(artista, timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return playlistCollection;
    }

    /* OK */

    @Path("/bygenre/{genre}")
    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_PLAYLIST_COLLECTION_GENRE)
    public PlaylistCollection getPlaylistByGenre(@PathParam("genre") String genre, @QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
        PlaylistCollection playlistCollection = null;
        PlaylistDAO playlistDAO = new PlaylistDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
<<<<<<< HEAD
            playlistCollection = playlistDAO.getPlaylistbyArtist(timestamp, before);
=======
            playlistCollection = playlistDAO.getPlaylistbyGenre(genre, timestamp, before);
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return playlistCollection;
    }

<<<<<<< HEAD
    @Path("/{id}")
    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_PLAYLIST)
    public Playlist getPost(@PathParam("id") String id){
=======
    /* OK */

    @Path("/{id}")
    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_PLAYLIST)
    public Playlist getPlay(@PathParam("id") String id){
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
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
<<<<<<< HEAD
=======

>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
    @Path("/{id}")
    @PUT
    @Consumes(Music4youMediaType.MUSIC4YOU_PLAYLIST)
    @Produces(Music4youMediaType.MUSIC4YOU_PLAYLIST)
<<<<<<< HEAD
    public Playlist updatePost(@PathParam("id") String id, Playlist playlist) {
=======
    public Playlist updatePlay(@PathParam("id") String id, Playlist playlist) {
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
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
<<<<<<< HEAD
    @Path("/{id}")
    @DELETE
    public void deletePost(@PathParam("id") String id) {
=======

    @Path("/{id}")
    @DELETE
    public void deletePlay(@PathParam("id") String id) {
>>>>>>> 8bdc9d41746e1bff39f607e5562a94d572657ca7
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


