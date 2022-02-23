package io.github.ruben.alumnosEstudios.infrastructure.controller;

import io.github.ruben.alumnosEstudios.application.AlumnosEstudiosService;
import io.github.ruben.alumnosEstudios.infrastructure.controller.dto.input.AlumnosEstudiosInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("alumnosestudios")
@RestController
public class CreateAlumnosEstudiosController {

    @Autowired
    AlumnosEstudiosService alumnosEstudiosService;

    @PostMapping
    public AlumnosEstudiosInputDto aniadirPesona(@Valid @RequestBody AlumnosEstudiosInputDto alumnosEstudiosInputDto){
        return alumnosEstudiosService.aniadirAlumnosEstudios(alumnosEstudiosInputDto);
    }
}
