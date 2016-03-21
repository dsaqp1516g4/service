package edu.upc.eetac.dsa.music4you;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by juan on 28/09/15.
 */
public class Music4youResourceConfig extends ResourceConfig {
    public Music4youResourceConfig() {
        packages("edu.upc.eetac.dsa.socialmusic");
        packages("edu.upc.eetac.dsa.socialmusic.auth");
        register(RolesAllowedDynamicFeature.class);
        register(DeclarativeLinkingFeature.class);
        packages("edu.upc.eetac.dsa.socialmusic.cors");
    }
}

