package edu.upc.eetac.dsa.music4you.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hixam on 3/04/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class MensajeriaCollection {
    @InjectLinks({})

    private List<Link> links;
    //private long newestTimestamp;
    private long creationTimestamp;
    private List<Mensajeria> messages = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public List<Mensajeria> getMessages() {
        return messages;
    }

    public void setMessages(List<Mensajeria> messages) {
        this.messages = messages;
    }
}
