package com.guillermo.videoGamesProject.domain;

import lombok.Builder;
import lombok.Data;

import java.net.URI;

@Data

@Builder
public class Platform {
    private int id;
    private String name;
    private int games_count;
    private URI image_background;

    public String toString(){

        return name;
    }
}
