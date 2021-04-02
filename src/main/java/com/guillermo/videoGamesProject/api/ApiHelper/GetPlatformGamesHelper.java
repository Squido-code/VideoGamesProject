package com.guillermo.videoGamesProject.api.ApiHelper;

import com.guillermo.videoGamesProject.domain.Videogame;
import lombok.Data;

import java.util.List;

/**
 * Este metodo lo he creado debido a que la APi me devuelve un objeto con el atributo results,
 * alli se encuentra toda la informacion relevante que se pretende buscar.
 */
@Data
public class GetPlatformGamesHelper {
    private List<Videogame> results;
}
