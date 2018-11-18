package com.weeclo.Sessions.response;

import java.io.Serializable;

public class WeeCloSessionNotFoundResponse implements Serializable {
    private String message = "WeeClo session not found";
    private long responseTime;
    private int code = 40401;

    public WeeCloSessionNotFoundResponse(){
        this.responseTime = System.currentTimeMillis();
    }


    public String getMessage() {
        return message;
    }

    public long getResponseTime() {
        return responseTime;
    }
    public int getCode(){
        return code;
    }
}
