package link.smartwall.kygj.questionbank.domain;


import org.xutils.http.annotation.HttpResponse;

import link.smartwall.kygj.questionbank.http.JsonResponseParser;

@HttpResponse(parser = JsonResponseParser.class)
public class Result {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
