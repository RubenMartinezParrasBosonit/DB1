package io.github.ruben.alumnosEstudios.infrastructure.controller;

import io.github.ruben.alumnosEstudios.application.AlumnosEstudiosService;
import io.github.ruben.alumnosEstudios.infrastructure.controller.dto.output.AlumnosEstudiosOutputDto;
import io.github.ruben.alumnosEstudios.infrastructure.controller.dto.output.AlumnosEstudiosOutputDtoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("alumnosestudios")
@RestController
public class ReadAlumnosEstudiosController {
    @Autowired
    AlumnosEstudiosService alumnosEstudiosService;

    @GetMapping
    public AlumnosEstudiosOutputDtoList findAll(){
        return alumnosEstudiosService.findAll();
    }

    @GetMapping("{id}")
    public AlumnosEstudiosOutputDto getAlumnosEstudiosById(@PathVariable String id){
        return alumnosEstudiosService.filtrarAlumnosEstudiosPorId(id);
    }
}
