package edu.upc.eetac.dsa.music4you;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by juan on 28/09/15.
 */
public class Music4youResourceConfig extends ResourceConfig {
    public Music4youResourceConfig() {
        packages("edu.upc.eetac.dsa.music4you");
        packages("edu.upc.eetac.dsa.music4you.auth");
        register(RolesAllowedDynamicFeature.class);
        register(DeclarativeLinkingFeature.class);
        register(JacksonFeature.class);
        register(MultiPartFeature.class);
        packages("edu.upc.eetac.dsa.music4you.cors");
    }
}

