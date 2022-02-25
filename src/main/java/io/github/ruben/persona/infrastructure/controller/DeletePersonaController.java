package io.github.ruben.persona.infrastructure.controller;

import io.github.ruben.persona.application.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("persona")
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.DELETE})
public class DeletePersonaController {

    @Autowired
    PersonaService personaService;

    @DeleteMapping("{id}")
    public void borrarPersona(@PathVariable Integer id){
            personaService.borrarPersona(id);
    }
}
