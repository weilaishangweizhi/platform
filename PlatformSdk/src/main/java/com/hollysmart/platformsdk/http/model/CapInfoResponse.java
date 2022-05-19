package com.hollysmart.platformsdk.http.model;
/**
 * 首信请求头
 */
public class CapInfoResponse<T>{
    public int code;
    public String message;
    public long timestamp;
    public T data;
    @Override
    public String toString() {
        return "CapInfoResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", data=" + data +
                '}';
    }
}

