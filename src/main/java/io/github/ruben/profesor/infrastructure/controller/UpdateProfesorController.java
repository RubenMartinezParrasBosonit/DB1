package io.github.ruben.profesor.infrastructure.controller;

import io.github.ruben.profesor.application.ProfesorService;
import io.github.ruben.profesor.infrastructure.controller.dto.input.ProfesorInputDto;
import io.github.ruben.profesor.infrastructure.controller.dto.output.ProfesorOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
