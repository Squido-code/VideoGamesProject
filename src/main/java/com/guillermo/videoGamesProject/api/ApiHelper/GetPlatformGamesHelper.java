package com.guillermo.videoGamesProject.api.ApiHelper;

import com.guillermo.videoGamesProject.domain.Videogame;
import lombok.Data;


import java.util.List;
@Data
public class GetPlatformGamesHelper {
    private List<Videogame> results;
}
