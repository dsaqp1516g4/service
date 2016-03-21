package edu.upc.eetac.dsa.music4you.entity;

/**
 * Created by juan on 28/09/15.
 */

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public enum Role {
        registered, admin
    }
