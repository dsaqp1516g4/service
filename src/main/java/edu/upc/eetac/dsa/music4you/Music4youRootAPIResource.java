package edu.upc.eetac.dsa.music4you;

import edu.upc.eetac.dsa.music4you.entity.SocialmusicRootAPI;

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
    @Produces(Music4youMediaType.BEETER_ROOT)
    public SocialmusicRootAPI getRootAPI() {
        if(securityContext.getUserPrincipal()!=null)
            userid = securityContext.getUserPrincipal().getName();
        SocialmusicRootAPI socialmusicRootAPI = new SocialmusicRootAPI();

        return socialmusicRootAPI;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

