package com.rgxcp.jakfood;

public class ZomatoReview {

    private Integer rating;
    private String name, profile_image, review_text, review_time_friendly;

    ZomatoReview() {
        //
    }

    public ZomatoReview(Integer rating, String name, String profile_image, String review_text, String review_time_friendly) {
        this.rating = rating;
        this.name = name;
        this.profile_image = profile_image;
        this.review_text = review_text;
        this.review_time_friendly = review_time_friendly;
    }

    Integer getRating() {
        return rating;
    }

    String getName() {
        return name;
    }

    String getProfile_image() {
        return profile_image;
    }

    String getReview_text() {
        return review_text;
    }

    String getReview_time_friendly() {
        return review_time_friendly;
    }

    void setRating(Integer rating) {
        this.rating = rating;
    }

    void setName(String name) {
        this.name = name;
    }

    void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    void setReview_time_friendly(String review_time_friendly) {
        this.review_time_friendly = review_time_friendly;
    }
}
