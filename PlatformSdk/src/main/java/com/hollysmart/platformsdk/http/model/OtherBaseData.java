package com.hollysmart.platformsdk.http.model;

import java.io.Serializable;
import java.util.List;

public class OtherBaseData<T> implements Serializable {

    //待办使用到的参数
    public int pageIndex;
    public int pageSize;
    public int total;
    public List<T> data;
}
