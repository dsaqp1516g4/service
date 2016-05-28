package edu.upc.eetac.dsa.music4you.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by hixam on 21/05/16.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Messages {

    private String id;
    private String text;
    private String userid;
    private String destinatario;
    private String fromusername;
    private String tousername;
    private String creationTimestamp;
    private int nummsgs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public int getNummsgs() {
        return nummsgs;
    }

    public void setNummsgs(int nummsgs) {
        this.nummsgs = nummsgs;
    }

    public String getFromusername() {
        return fromusername;
    }

    public void setFromusername(String fromusername) {
        this.fromusername = fromusername;
    }

    public String getTousername() {
        return tousername;
    }

    public void setTousername(String tousername) {
        this.tousername = tousername;
    }
}