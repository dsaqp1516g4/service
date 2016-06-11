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
public class User {
    @InjectLinks({
            @InjectLink(resource = Music4youRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Beeter Root API"),
            @InjectLink(resource = PlaylistResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-stings", title = "Current stings", type= Music4youMediaType.MUSIC4YOU_PLAYLIST_COLLECTION),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = PlaylistResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-sting", title = "Create sting", type= Music4youMediaType.MUSIC4YOU_PLAYLIST),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "self user-profile", title = "User profile", type= Music4youMediaType.MUSIC4YOU_USER, bindings = @Binding(name = "id", value = "${instance.id}"))
    })

    private List<Link> links;

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

        public String getLoginid() {
            return loginid;
        }

        public void setLoginid(String loginid) {
            this.loginid = loginid;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        private String id;
        private String loginid;
        private String email;
        private String fullname;
        private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
