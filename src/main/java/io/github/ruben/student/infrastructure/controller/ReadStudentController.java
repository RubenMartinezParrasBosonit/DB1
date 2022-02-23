package io.github.ruben.student.infrastructure.controller;

import io.github.ruben.student.application.StudentService;
import io.github.ruben.student.infrastructure.controller.dto.output.StudentOutputDto;
import io.github.ruben.student.infrastructure.controller.dto.output.StudentOutputDtoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RequestMapping("student")
@RestController
public class ReadStudentController {
    @Autowired
    StudentService studentService;

    @GetMapping
    public StudentOutputDtoList findAll(){
        return studentService.findAll();
    }

    @GetMapping("{id}")
    public StudentOutputDto getProfesorById(@PathVariable String id, @Value("simple") @RequestParam(name = "outputType", defaultValue = "simple", required = false)
            String outputType){
        return studentService.filtrarStudentPorId(id, outputType);
    }
}
