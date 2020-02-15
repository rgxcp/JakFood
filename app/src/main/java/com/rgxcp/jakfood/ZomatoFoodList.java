package com.rgxcp.jakfood;

public class ZomatoFoodList {

    private Integer id;
    private String aggregate_rating, name, locality_verbose, thumb;

    public ZomatoFoodList() {
        //
    }

    public ZomatoFoodList(Integer id, String aggregate_rating, String name, String locality_verbose, String thumb) {
        this.id = id;
        this.aggregate_rating = aggregate_rating;
        this.name = name;
        this.locality_verbose = locality_verbose;
        this.thumb = thumb;
    }

    public Integer getId() {
        return id;
    }

    public String getAggregate_rating() {
        return aggregate_rating;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAggregate_rating(String aggregate_rating) {
        this.aggregate_rating = aggregate_rating;
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
