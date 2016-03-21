package edu.upc.eetac.dsa.music4you.entity;

import edu.upc.eetac.dsa.music4you.*;
import edu.upc.eetac.dsa.socialmusic.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by juan on 14/10/15.
 */
public class Music4youRootAPI {
    @InjectLinks({
            @InjectLink(resource = Music4youRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "self bookmark home", title = "Beeter Root API"),
            @InjectLink(resource = PlaylistResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-stings", title = "Current stings", type= Music4youMediaType.SOCIALMUSIC_POST_COLLECTION),
            @InjectLink(resource = UserResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-user", title = "Register", type= Music4youMediaType.BEETER_AUTH_TOKEN),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout", condition="${!empty resource.userid}"),
            @InjectLink(resource = PlaylistResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-sting", title = "Create sting", condition="${!empty resource.userid}", type= Music4youMediaType.SOCIALMUSIC_POST),
            @InjectLink(resource = UserResource.class, method="getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", condition="${!empty resource.userid}", type= Music4youMediaType.SOCIALMUSIC_USER, bindings = @Binding(name = "id", value = "${resource.userid}"))
    })
    private List<Link> links;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
