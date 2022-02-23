package io.github.ruben.profesor.application;

import io.github.ruben.profesor.infrastructure.controller.dto.input.ProfesorInputDto;
import io.github.ruben.profesor.infrastructure.controller.dto.output.ProfesorOutputDto;
import io.github.ruben.profesor.infrastructure.controller.dto.output.ProfesorOutputDtoList;


public interface ProfesorService {
    public ProfesorOutputDtoList findAll();
    public ProfesorOutputDto filtrarProfesorPorId(String id);
    public ProfesorInputDto aniadirProfesor(ProfesorInputDto profesorInputDto);
    public ProfesorOutputDto modificarProfesor(String id, ProfesorInputDto profesorInputDto);
    public void borrarProfesor(String id);
}
