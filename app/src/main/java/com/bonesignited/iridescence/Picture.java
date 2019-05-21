package com.bonesignited.iridescence;

import com.google.gson.annotations.SerializedName;

public class Picture {
    @SerializedName("start_date")
    private String startDate;

    @SerializedName("end_date")
    private String endDate;

    private String url;

    private String copyright;

    @SerializedName("copyright_link")
    private String copyrightLink;

    public Picture() {
    }

    public Picture(String startDate, String endDate, String url, String copyright, String copyrightLink) {
        this.startDate = startDate;
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
