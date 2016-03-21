package edu.upc.eetac.dsa.music4you;

/**
 * Created by juan on 05/10/15.
 */
public interface Music4youMediaType {
    String BEETER_AUTH_TOKEN = "application/vnd.dsa.socialmusic.auth-token+json";
    String SOCIALMUSIC_USER = "application/vnd.dsa.socialmusic.user+json";
    String SOCIALMUSIC_POST = "application/vnd.dsa.socialmusic.post+json";
    String SOCIALMUSIC_POST_COLLECTION = "application/vnd.dsa.socialmusic.post.collection+json";
    String BEETER_ROOT = "application/vnd.dsa.socialmusic.root+json";
    String SOCIALMUSIC_COMMENT  = "application/vnd.dsa.socialmusic.comment+json";
    String SOCIALMUSIC_COMMENT_COLLECTION = "application/vnd.dsa.socialmusic.comment.collection+json";
    String SOCIALMUSIC_EVENT = "application/vnd.dsa.socialmusic.event+json";
    String SOCIALMUSIC_EVENT_COLLECTION = "application/vnd.dsa.socialmusic.event.collection+json";
}
