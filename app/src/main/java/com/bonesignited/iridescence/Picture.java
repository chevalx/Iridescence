package com.bonesignited.iridescence;


import com.alibaba.fastjson.annotation.JSONField;

public class Picture {

    @JSONField(name = "startdate")
    private String startDate;

    @JSONField(name = "fullstartdate")
    private String fullStartDate;

    @JSONField(name = "enddate")
    private String endDate;

    @JSONField(name = "url")
    private String url;

    @JSONField(serialize = false)
    private String urlBase;

    @JSONField(name = "copyright")
    private String copyright;

    @JSONField(name = "copyrightlink")
    private String copyrightLink;

    @JSONField(serialize = false)
    private String title;

    @JSONField(serialize = false)
    private String quiz;

    @JSONField(serialize = false)
    private boolean wp;

    @JSONField(serialize = false)
    private String hsh;

    @JSONField(serialize = false)
    private int drk;

    @JSONField(serialize = false)
    private int top;

    @JSONField(serialize = false)
    private int bot;

    @JSONField(serialize = false)
    private int[] hs;


    public Picture(String startDate, String fullStartDate, String endDate, String url, String copyright, String copyrightLink) {
        this.startDate = startDate;
        this.fullStartDate = fullStartDate;
        this.endDate = endDate;
        this.url = url;
        this.copyright = copyright;
        this.copyrightLink = copyrightLink;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFullStartDate() {
        return fullStartDate;
    }

    public void setFullStartDate(String fullStartDate) {
        this.fullStartDate = fullStartDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getCopyrightLink() {
        return copyrightLink;
    }

    public void setCopyrightLink(String copyrightLink) {
        this.copyrightLink = copyrightLink;
    }

}
