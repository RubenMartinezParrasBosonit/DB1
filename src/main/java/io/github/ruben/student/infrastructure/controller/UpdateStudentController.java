package io.github.ruben.student.infrastructure.controller;

import io.github.ruben.profesor.infrastructure.controller.dto.input.ProfesorInputDto;
import io.github.ruben.profesor.infrastructure.controller.dto.output.ProfesorOutputDto;
import io.github.ruben.student.application.StudentService;
import io.github.ruben.student.infrastructure.controller.dto.input.StudentInputDto;
import io.github.ruben.student.infrastructure.controller.dto.output.StudentOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("student")
@RestController
public class UpdateStudentController {
    @Autowired
    StudentService studentService;

    @PutMapping("{id}")
    public StudentOutputDto modificarStudent(@PathVariable String id, @RequestBody StudentInputDto studentInputDto) {

        return studentService.modificarStudent(id, studentInputDto);

    }

    @PutMapping("{id}/asignar")
    public List<String> asignarAsignaturas(@PathVariable String id, @RequestBody List<String> estudios){
        return studentService.asignarAsignaturas(id, estudios);
    }

    @PutMapping("{id}/desasignar")
    public List<String> desasignarAsignaturas(@PathVariable String id, @RequestBody List<String> estudios){
        return studentService.desasignarAsignaturas(id, estudios);
    }
}
