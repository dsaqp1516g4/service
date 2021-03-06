package edu.upc.eetac.dsa.music4you.entity;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan on 21/02/16.
 */
public class CommentCollection {
    //@InjectLinks({
    //        @InjectLink(resource = CommentResource.class, style = Style.ABSOLUTE, rel = "create-comment", title = "Create comment", type = KujosaMediaType.KUJOSA_COMMENT),
    //        @InjectLink(value = "/comments/{eventid}", style = Style.ABSOLUTE, rel = "comments", title = "Latest comments", type = KujosaMediaType.KUJOSA_COMMENT_COLLECTION, bindings = @Binding(name = "eventid", value = "${instance.comments.get(0).getId()}"))
    //})

    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public long getNewestTimestamp() {
        return newestTimestamp;
    }

    public void setNewestTimestamp(long newestTimestamp) {
        this.newestTimestamp = newestTimestamp;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public long getOldestTimestamp() {
        return oldestTimestamp;
    }

    public void setOldestTimestamp(long oldestTimestamp) {
        this.oldestTimestamp = oldestTimestamp;
    }
}
