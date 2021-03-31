package com.guillermo.videoGamesProject.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.net.URI;



@Data
@Builder
@ToString
public class Videogame {
    private int id;
    private String name;
    private String description;
    private int metacritic;
    private String released;
    private URI background_image;

}
