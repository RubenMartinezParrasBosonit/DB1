package io.github.ruben.alumnosEstudios.infrastructure.controller;

import io.github.ruben.alumnosEstudios.application.AlumnosEstudiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("alumnosestudios")
@RestController
public class DeleteAlumnosEstudiosController {
    @Autowired
    AlumnosEstudiosService alumnosEstudiosService;

    @DeleteMapping("{id}")
    public void borrarAlumnosEstudios(@PathVariable String id){
        alumnosEstudiosService.borrarAlumnosEstudios(id);
    }
}
