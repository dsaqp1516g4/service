package edu.upc.eetac.dsa.music4you;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Main class.
 *
 */
public class Main {
    public static boolean Windows;
    public static boolean Linux;

    // Base URI the Grizzly HTTP server will listen on
    private static String baseURI;

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    /* public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in edu.upc.eetac.dsa.music4you package
        final ResourceConfig rc = new ResourceConfig().packages("edu.upc.eetac.dsa.music4you");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(getBaseURI()), rc);
    } */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in edu.upc.eetac.dsa.music4you package
        final ResourceConfig rc = new Music4youResourceConfig();

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(getBaseURI()), rc);
    }
    public final static String getBaseURI() {
        if (baseURI == null) {
            PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("music4you");
            baseURI = prb.getString("music4you.context");
        }
        return baseURI;
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (System.getProperty("os.name").contains("Windows")) {
            Windows=true;
        }
        if(System.getProperty("os.name").contains("Linux")) {
            Linux=true;
        }

        final HttpServer server = startServer();
        System.out.println("Ejecutando en sistema:" + "\n Linux: " + Linux + "\n Windows: " + Windows);
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", getBaseURI()));
        System.in.read();
        server.shutdownNow();
    }
}

