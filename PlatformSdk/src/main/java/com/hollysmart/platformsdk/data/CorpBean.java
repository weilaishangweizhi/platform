package com.hollysmart.platformsdk.data;


public class CorpBean {

    private String corpId;
    private String corpName;
    private String code;
    private String corpUrl;
    private String isMainCorp;

    private boolean isSelect;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCorpUrl() {
        return corpUrl;
    }

    public void setCorpUrl(String corpUrl) {
        this.corpUrl = corpUrl;
    }

    public String getIsMainCorp() {
        return isMainCorp;
    }

    public void setIsMainCorp(String isMainCorp) {
        this.isMainCorp = isMainCorp;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
