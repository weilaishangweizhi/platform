
package com.hollysmart.platformsdk.http.model;

import java.io.Serializable;


public class InvitationCodeResponse<T> implements Serializable {
    private static final long serialVersionUID = 5213230387175987834L;
    public int status;
    public String msg;
    public String code;
    public long timestamp;
    public T data;


    @Override
    public String toString() {
        return "CaiResponse{" +
                "code='" + code + '\'' +
                "message='" + msg + '\'' +
                ", timestamp=" + timestamp +
                ", data=" + data +
                '}';
    }
}
