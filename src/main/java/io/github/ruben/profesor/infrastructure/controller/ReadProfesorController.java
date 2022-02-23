package io.github.ruben.profesor.infrastructure.controller;

import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import io.github.ruben.profesor.application.ProfesorService;
import io.github.ruben.profesor.infrastructure.controller.dto.output.ProfesorOutputDto;
import io.github.ruben.profesor.infrastructure.controller.dto.output.ProfesorOutputDtoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("profesor")
@RestController
public class ReadProfesorController {
    @Autowired
    ProfesorService profesorService;

    @GetMapping
    public ProfesorOutputDtoList findAll(){
        return profesorService.findAll();
    }

    @GetMapping("{id}")
    public ProfesorOutputDto getProfesorById(@PathVariable String id){
        return profesorService.filtrarProfesorPorId(id);
    }
}
