package io.github.ruben.profesor.infrastructure.controller;

import io.github.ruben.profesor.application.ProfesorService;
import io.github.ruben.profesor.infrastructure.controller.dto.output.ProfesorOutputDto;
import io.github.ruben.profesor.infrastructure.controller.dto.output.ProfesorOutputDtoList;
import io.github.ruben.profesor.infrastructure.feing.IFeingProfesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("profesor")
@RestController
public class ReadProfesorController {
    @Autowired
    ProfesorService profesorService;

    @Autowired
    IFeingProfesor iFeingProfesor;

    @GetMapping
    public ProfesorOutputDtoList findAll(){
        return profesorService.findAll();
    }

    @GetMapping("{id}")
    public ProfesorOutputDto getProfesorById(@PathVariable String id){
        return profesorService.filtrarProfesorPorId(id);
    }

    @GetMapping("/template/{id}")
    ResponseEntity<ProfesorOutputDto> getProfesorRestTemplate(@PathVariable String id){
        System.out.println("En client Resttemplate. Antes de llamada a server Profesor: "+id);
        ResponseEntity<ProfesorOutputDto> rs = new RestTemplate().getForEntity("http://localhost:8081/profesor/"+id,ProfesorOutputDto.class);
        System.out.println("En client Resttemplate. Despues de llamada a server");
        return ResponseEntity.ok(rs.getBody());
    }

    @GetMapping("/feing/{id}")
    ResponseEntity<ProfesorOutputDto> getProfesorFeing(@PathVariable String id){
        System.out.println("En client Feing. Antes de llamada a server Profesor: "+id);
        ResponseEntity<ProfesorOutputDto> rsFeing = iFeingProfesor.callProfesor(id);
        System.out.println("En client Feing. Despues de llamada a server");
        return rsFeing;
    }
}
