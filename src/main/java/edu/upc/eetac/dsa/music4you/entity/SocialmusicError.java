package edu.upc.eetac.dsa.music4you.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by juan on 28/09/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocialmusicError {
    private int status;
    private String reason;

    public SocialmusicError() {
    }

    public SocialmusicError(int status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
