package com.bonesignited.iridescence;

import android.support.v7.widget.TooltipCompat;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class ResponseJson {
    @JSONField(name = "images")
    private List<Picture> images;

    @JSONField(serialize = false)
    private List<String> tooltips;

    public ResponseJson() {
    }

    public ResponseJson(List<Picture> images, List<String> tooltips) {
        this.images = images;
        this.tooltips = tooltips;
    }

    public ResponseJson(List<Picture> images) {
        this.images = images;
    }

    public List<Picture> getImages() {
        return images;
    }

    public void setImages(List<Picture> images) {
        this.images = images;
    }

    public List<String> getTooltips() {
        return tooltips;
    }

    public void setTooltips(List<String> tooltips) {
        this.tooltips = tooltips;
    }
}
