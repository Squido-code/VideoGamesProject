package com.guillermo.videoGamesProject.api.ApiHelper;

import com.guillermo.videoGamesProject.domain.Console;
import lombok.Data;

import java.util.List;
/**
 * Este metodo lo he creado debido a que la APi me devuelve un objeto con el atributo results,
 * alli se encuentra toda la informacion relevante que se pretende buscar.
 */
@Data
public class GetAllPlatformsHelper {
    private List<Console> results;
}
