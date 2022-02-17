package io.github.ruben.controladores;

import io.github.ruben.dtos.PersonaDTO;
import io.github.ruben.dtos.PersonaInputDTO;
import io.github.ruben.dtos.PersonaOutputDTO;
import io.github.ruben.interfaces.IPersona;
import io.github.ruben.objetos.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("persona")
@RestController
public class ControladorPersona {

    @Autowired
    IPersona personaService;

    @GetMapping("/allpersonas")
    public List<PersonaOutputDTO> getAllPersonas(){
        return personaService.todasLasPersonas();
    }

    @GetMapping("/id/{id}")
    public PersonaOutputDTO getPersonaById(@PathVariable Integer id){
        return personaService.filtrarPersonasPorId(id);
    }

    @GetMapping("/usuario/{usuario}")
    public List<PersonaOutputDTO> getPersonaByUsuario(@PathVariable String usuario){
        return personaService.filtrarPersonaPorNombreUsuario(usuario);
    }

    @PostMapping("/addpersona")
    public PersonaOutputDTO aniadirPesona(@RequestBody PersonaInputDTO personaInputDTO) throws Exception {
        return personaService.aniadirPersona(personaInputDTO);
    }

    @PutMapping("/modificarpersona/{id}")
    public PersonaOutputDTO modificarPersona(@PathVariable Integer id, @RequestBody PersonaInputDTO personaInputDTO) throws Exception {
        return personaService.modificarPersona(id, personaInputDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String borrarPersona(@PathVariable Integer id) throws Exception{
        return personaService.borrarPersona(id);
    }
}
