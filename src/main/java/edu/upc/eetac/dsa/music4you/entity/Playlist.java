package edu.upc.eetac.dsa.music4you.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.music4you.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by juan on 28/09/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Playlist {
    @InjectLinks({
            @InjectLink(resource = Music4youRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Beeter Root API"),
            @InjectLink(resource = PlaylistResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-playlist", title = "Current playlist"),
            @InjectLink(resource = PlaylistResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-play", title = "Create play", type= Music4youMediaType.MUSIC4YOU_PLAYLIST),
            @InjectLink(resource = PlaylistResource.class, method = "getPlay", style = InjectLink.Style.ABSOLUTE, rel = "self play", title = "Playlist", type= Music4youMediaType.MUSIC4YOU_PLAYLIST, bindings = @Binding(name = "id", value = "${instance.id}")),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", type= Music4youMediaType.MUSIC4YOU_USER, bindings = @Binding(name = "id", value = "${instance.userid}")),
            @InjectLink(resource = PlaylistResource.class, method = "getPlaylist", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newer plays",  type= Music4youMediaType.MUSIC4YOU_PLAYLIST_COLLECTION, bindings = {@Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = PlaylistResource.class, method = "getPlaylist", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older plays", type= Music4youMediaType.MUSIC4YOU_PLAYLIST_COLLECTION, bindings = {@Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "true")}),
    })
    private List<Link> links;
    private String id;
    private String userid;
    private String artist;
    private String title;
    private String audio;
    private String youtubelink;
    private String genre;
    private long creationTimestamp;
    private long lastModified;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getYoutubelink() {
        return youtubelink;
    }

    public void setYoutubelink(String youtubelink) {
        this.youtubelink = youtubelink;
    }
}
