package io.github.ruben.persona.infrastructure.controller;

import io.github.ruben.persona.application.PersonaService;
import io.github.ruben.persona.domain.Persona;
import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("persona")
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.POST})
public class CreatePersonaController {

    @Autowired
    PersonaService personaService;

    @PostMapping
    public PersonaOutputDto aniadirPesona(@RequestBody PersonaInputDto personaInputDto) throws Exception {
        return personaService.aniadirPersona(personaInputDto);
    }
}
