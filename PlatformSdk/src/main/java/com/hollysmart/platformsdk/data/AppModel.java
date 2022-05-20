package com.hollysmart.platformsdk.data;


import java.io.Serializable;
import java.util.List;

public class AppModel implements Serializable {
    private List<AppItem> vos;
    private List<Custom> customGrouping;

    public List<AppItem> getVos() {
        return vos;
    }

    public void setVos(List<AppItem> vos) {
        this.vos = vos;
    }

    public List<Custom> getCustomGrouping() {
        return customGrouping;
    }

    public void setCustomGrouping(List<Custom> customGrouping) {
        this.customGrouping = customGrouping;
    }

    public class Custom implements Serializable {
        public String id;
        public String name;
        public List<AppItem> vos;
    }
}
