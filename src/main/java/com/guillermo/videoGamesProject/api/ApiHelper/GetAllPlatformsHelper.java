package com.guillermo.videoGamesProject.api.ApiHelper;

import com.guillermo.videoGamesProject.domain.Console;
import lombok.Data;

import java.util.List;
@Data
public class GetAllPlatformsHelper {
    private List<Console> results;
}
