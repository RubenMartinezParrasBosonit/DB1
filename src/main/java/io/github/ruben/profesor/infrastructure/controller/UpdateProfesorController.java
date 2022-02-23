package io.github.ruben.profesor.infrastructure.controller;

import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import io.github.ruben.profesor.application.ProfesorService;
import io.github.ruben.profesor.infrastructure.controller.dto.input.ProfesorInputDto;
import io.github.ruben.profesor.infrastructure.controller.dto.output.ProfesorOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("profesor")
@RestController
public class UpdateProfesorController {
    @Autowired
    ProfesorService profesorService;

    @PutMapping("{id}")
    public ProfesorOutputDto modificarProfesor(@PathVariable String id, @RequestBody ProfesorInputDto profesorInputDto) {

        return profesorService.modificarProfesor(id, profesorInputDto);

    }
}
