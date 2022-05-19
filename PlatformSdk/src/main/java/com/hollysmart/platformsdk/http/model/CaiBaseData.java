package com.hollysmart.platformsdk.http.model;

import java.io.Serializable;
import java.util.List;

public class CaiBaseData <T> implements Serializable {
    public int pageNo;
    public int pageSize;
    public int count;
    public int totalPage;
    public List<T> list;
}
