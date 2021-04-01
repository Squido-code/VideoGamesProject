package com.guillermo.videoGamesProject.domain;

import lombok.Builder;
import lombok.Data;



@Data
@Builder

public class Videogame {
    private String id;
    private String name;
    private String metacritic;
    private String released;
    private String background_image;

    public String toString() {
        return name;
    }
}
