package com.rgxcp.jakfood;

public class ZomatoPhoto {

    private String thumb_url, url;

    ZomatoPhoto() {
        //
    }

    public ZomatoPhoto(String thumb_url, String url) {
        this.thumb_url = thumb_url;
        this.url = url;
    }

    String getThumb_url() {
        return thumb_url;
    }

    String getUrl() {
        return url;
    }

    void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    void setUrl(String url) {
        this.url = url;
    }
}
