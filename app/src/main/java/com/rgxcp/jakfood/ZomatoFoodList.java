package com.rgxcp.jakfood;

public class ZomatoFoodList {

    private String aggregate_rating, id, name, locality_verbose, thumb;

    public ZomatoFoodList() {
        //
    }

    public ZomatoFoodList(String aggregate_rating, String id, String name, String locality_verbose, String thumb) {
        this.aggregate_rating = aggregate_rating;
        this.id = id;
        this.name = name;
        this.locality_verbose = locality_verbose;
        this.thumb = thumb;
    }

    public String getAggregate_rating() {
        return aggregate_rating;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocality_verbose() {
        return locality_verbose;
    }

    public String getThumb() {
        return thumb;
    }

    public void setAggregate_rating(String aggregate_rating) {
        this.aggregate_rating = aggregate_rating;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocality_verbose(String locality_verbose) {
        this.locality_verbose = locality_verbose;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
