package com.guillermo.videoGamesProject.api.ApiHelper;

import com.guillermo.videoGamesProject.domain.Platform;
import lombok.Data;

import java.util.List;
@Data
public class GetAllPlatformsHelper {
    private List<Platform> results;
}
