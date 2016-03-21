package edu.upc.eetac.dsa.music4you.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by juan on 28/09/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Music4youError {
    private int status;
    private String reason;

    public Music4youError() {
    }

    public Music4youError(int status, String reason) {
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
