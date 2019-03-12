package com.weeclo.Sessions.response;

import com.weeclo.Sessions.session.token.Token;

public class WeeCloSessionCreationSuccessResponse {
    private String message = "Weeclo Session creation successful.";
    private Long successTime;
    private Token token;
    public WeeCloSessionCreationSuccessResponse(){
        this.successTime = System.currentTimeMillis();
    }

    public WeeCloSessionCreationSuccessResponse(Token token){
        this.successTime = System.currentTimeMillis();
        this.token = token;
    }

    public String getMessage() {
        return this.message;
    }

    public Long getSuccessTime() {
        return this.successTime;
    }

    public Token getToken(){
       return this.token;
    }
    public void setToken(Token token){
        this.token = token;
    }

}
