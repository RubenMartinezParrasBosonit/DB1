package io.github.ruben.alumnosEstudios.infrastructure.controller;

import io.github.ruben.alumnosEstudios.application.AlumnosEstudiosService;
import io.github.ruben.alumnosEstudios.infrastructure.controller.dto.input.AlumnosEstudiosInputDto;
import io.github.ruben.alumnosEstudios.infrastructure.controller.dto.output.AlumnosEstudiosOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("alumnosestudios")
@RestController
public class UpdateAlumnosEstudiosController {
    @Autowired
    AlumnosEstudiosService alumnosEstudiosService;

    @PutMapping("{id}")
    public AlumnosEstudiosOutputDto modificarProfesor(@PathVariable String id, @RequestBody AlumnosEstudiosInputDto alumnosEstudiosInputDto) {

        return alumnosEstudiosService.modificarAlumnosEstudios(id, alumnosEstudiosInputDto);

    }
}
