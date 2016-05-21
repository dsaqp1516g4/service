package edu.upc.eetac.dsa.music4you.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pauli on 29/03/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnuncioCollection {
    @InjectLinks({})

    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Anuncio> stings = new ArrayList<>();

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

    public List<Anuncio> getStings() {
        return stings;
    }

    public void setStings(List<Anuncio> stings) {
        this.stings = stings;
    }
}
