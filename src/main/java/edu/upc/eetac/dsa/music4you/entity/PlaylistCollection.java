package edu.upc.eetac.dsa.music4you.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.music4you.PlaylistResource;
import edu.upc.eetac.dsa.music4you.Music4youMediaType;
import edu.upc.eetac.dsa.music4you.Music4youRootAPIResource;
import edu.upc.eetac.dsa.music4you.LoginResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan on 28/09/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistCollection {
    @InjectLinks({
            @InjectLink(resource = Music4youRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Music4You Root API"),
            @InjectLink(resource = PlaylistResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-playlist", title = "Current playlist", type= Music4youMediaType.MUSIC4YOU_PLAYLIST_COLLECTION),
            @InjectLink(resource = PlaylistResource.class, method = "getPlaylist", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newer playlist", type= Music4youMediaType.MUSIC4YOU_PLAYLIST_COLLECTION, bindings = {@Binding(name = "timestamp", value = "${instance.newestTimestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = PlaylistResource.class, method = "getPlaylist", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older playlist", type= Music4youMediaType.MUSIC4YOU_PLAYLIST_COLLECTION, bindings = {@Binding(name = "timestamp", value = "${instance.oldestTimestamp}"), @Binding(name = "before", value = "true")}),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout")
    })
    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Playlist> playlists = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public long getNewestTimestamp() {
        return newestTimestamp;
    }

    public void setNewestTimestamp(long newestTimestamp) {
        this.newestTimestamp = newestTimestamp;
    }

    public long getOldestTimestamp() {
        return oldestTimestamp;
    }

    public void setOldestTimestamp(long oldestTimestamp) {
        this.oldestTimestamp = oldestTimestamp;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }
}
