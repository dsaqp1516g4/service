package edu.upc.eetac.dsa.music4you;

import edu.upc.eetac.dsa.music4you.entity.Music4youRootAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by juan on 14/10/15.
 */
@Path("/")
public class Music4youRootAPIResource {
    @Context
    private SecurityContext securityContext;

    private String userid;

    @GET
    @Produces(Music4youMediaType.MUSIC4YOU_ROOT)
    public Music4youRootAPI getRootAPI() {
        if(securityContext.getUserPrincipal()!=null)
            userid = securityContext.getUserPrincipal().getName();
        Music4youRootAPI music4youRootAPI = new Music4youRootAPI();

        return music4youRootAPI;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

