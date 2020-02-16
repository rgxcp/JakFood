package com.rgxcp.jakfood;

public class ZomatoFoodList {

    private String aggregate_rating, id, name, locality_verbose, thumb;

    ZomatoFoodList() {
        //
    }

    public ZomatoFoodList(String aggregate_rating, String id, String name, String locality_verbose, String thumb) {
        this.aggregate_rating = aggregate_rating;
        this.id = id;
        this.name = name;
        this.locality_verbose = locality_verbose;
        this.thumb = thumb;
    }

    String getAggregate_rating() {
        return aggregate_rating;
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getLocality_verbose() {
        return locality_verbose;
    }

    String getThumb() {
        return thumb;
    }

    void setAggregate_rating(String aggregate_rating) {
        this.aggregate_rating = aggregate_rating;
    }

    void setId(String id) {
        this.id = id;
    }

    void setName(String name) {
        this.name = name;
    }

    void setLocality_verbose(String locality_verbose) {
        this.locality_verbose = locality_verbose;
    }

    void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
