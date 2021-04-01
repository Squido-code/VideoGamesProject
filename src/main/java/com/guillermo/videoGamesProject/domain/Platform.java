package com.guillermo.videoGamesProject.domain;

import lombok.Builder;
import lombok.Data;

@Data

@Builder
public class Platform {
    private String id;
    private String name;
    private String games_count;
    private String image_background;

    public String toString(){

        return name;
    }
}
