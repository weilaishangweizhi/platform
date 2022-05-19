
package com.hollysmart.platformsdk.http.model;

import java.io.Serializable;

/**
 * cai
 * {
 *   "code": "",
 *   "data": {
 *     "wpsGroupId": "金山群组id"
 *   },
 *   "msg": "OK",
 *   "status": 200,
 *   "timestamp": 1595256249706
 * }
 */
public class CaiResponse<T> implements Serializable {
    private static final long serialVersionUID = 5213230387175987834L;
    public String code;
    public String msg;
    public int status;
    public long timestamp;
    public T data;

//    @Override
//    public String toString() {
//        return "CaiResponse{" +
//                "result=" + status +
//                ", message='" + msg + '\'' +
//                ", data=" + data +
//                '}';
//    }


    @Override
    public String toString() {
        return "CaiResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", status=" + status +
                ", timestamp=" + timestamp +
                ", data=" + data +
                '}';
    }
}
