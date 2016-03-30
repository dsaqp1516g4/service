package edu.upc.eetac.dsa.music4you;

/**
 * Created by juan on 05/10/15.
 */
public interface Music4youMediaType {
    String MUSIC4YOU_AUTH_TOKEN = "application/vnd.dsa.music4you.auth-token+json";
    String MUSIC4YOU_USER = "application/vnd.dsa.music4you.user+json";
    String MUSIC4YOU_PLAYLIST = "application/vnd.dsa.music4you.post+json";
    String MUSIC4YOU_PLAYLIST_COLLECTION = "application/vnd.dsa.music4you.post.collection+json";
    String MUSIC4YOU_ROOT = "application/vnd.dsa.music4you.root+json";
    String MUSIC4YOU_COMMENT = "application/vnd.dsa.music4you.comment+json";
    String MUSIC4YOU_COMMENT_COLLECTION = "application/vnd.dsa.music4you.comment.collection+json";
    String MUSIC4YOU_EVENT = "application/vnd.dsa.music4you.event+json";
    String MUSIC4YOU_EVENT_COLLECTION = "application/vnd.dsa.music4you.event.collection+json";
}
