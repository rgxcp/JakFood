package com.rgxcp.jakfood;

class GetStarted {

    // Setup constructor
    private Integer background;
    private String heading, description;

    // Constructor
    GetStarted(Integer mBackground, String mHeading, String mDescription) {
        background = mBackground;
        heading = mHeading;
        description = mDescription;
    }

    // Getter
    Integer getBackground() {
        return background;
    }

    String getHeading() {
        return heading;
    }

    String getDescription() {
        return description;
    }
}
