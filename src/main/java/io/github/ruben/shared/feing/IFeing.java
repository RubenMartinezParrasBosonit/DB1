package io.github.ruben.shared.feing;

import io.github.ruben.profesor.infrastructure.controller.dto.output.ProfesorOutputDto;
//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="simpleFeign",url="http://localhost:8081/")
public interface IFeing {

    @GetMapping("/profesor/{id}")
    ResponseEntity<ProfesorOutputDto> callProfesor(@PathVariable("id") String id);
}
