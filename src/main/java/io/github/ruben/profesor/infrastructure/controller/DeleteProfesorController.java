package io.github.ruben.profesor.infrastructure.controller;

import io.github.ruben.profesor.application.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("profesor")
@RestController
public class DeleteProfesorController {
    @Autowired
    ProfesorService profesorService;

    @DeleteMapping("{id}")
    public void borrarProfesor(@PathVariable String id){
        profesorService.borrarProfesor(id);
    }
}
