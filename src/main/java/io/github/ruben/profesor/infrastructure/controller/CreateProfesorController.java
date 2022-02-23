package io.github.ruben.profesor.infrastructure.controller;

import io.github.ruben.profesor.application.ProfesorService;
import io.github.ruben.profesor.infrastructure.controller.dto.input.ProfesorInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("profesor")
@RestController
public class CreateProfesorController {
    @Autowired
    ProfesorService profesorService;

    @PostMapping
    public ProfesorInputDto aniadirPesona(@Valid @RequestBody ProfesorInputDto profesorInputDto){
        return profesorService.aniadirProfesor(profesorInputDto);
    }
}
