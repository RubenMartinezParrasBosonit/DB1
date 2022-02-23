package io.github.ruben.alumnosEstudios.application;

import io.github.ruben.alumnosEstudios.infrastructure.controller.dto.input.AlumnosEstudiosInputDto;
import io.github.ruben.alumnosEstudios.infrastructure.controller.dto.output.AlumnosEstudiosOutputDto;
import io.github.ruben.alumnosEstudios.infrastructure.controller.dto.output.AlumnosEstudiosOutputDtoList;

import java.util.List;

public interface AlumnosEstudiosService {
    public AlumnosEstudiosOutputDtoList findAll();
    public AlumnosEstudiosOutputDto filtrarAlumnosEstudiosPorId(String id);
    public AlumnosEstudiosInputDto aniadirAlumnosEstudios(AlumnosEstudiosInputDto alumnosEstudiosInputDto);
    public AlumnosEstudiosOutputDto modificarAlumnosEstudios(String id, AlumnosEstudiosInputDto alumnosEstudiosInputDto);
    public void borrarAlumnosEstudios(String id);
}
