package com.hollysmart.platformsdk.editmenu;

import java.io.Serializable;
import java.util.List;

public class ConfigInfo implements Serializable {
    private List<FunctionItem> selData;

    public ConfigInfo() {
    }

    public ConfigInfo(List<FunctionItem> selData) {
        this.selData = selData;
    }

    public List<FunctionItem> getSelData() {
        return selData;
    }

    public void setSelData(List<FunctionItem> selData) {
        this.selData = selData;
    }
}
