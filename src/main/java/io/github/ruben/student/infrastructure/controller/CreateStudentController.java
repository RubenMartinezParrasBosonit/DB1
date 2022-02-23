package io.github.ruben.student.infrastructure.controller;

import io.github.ruben.student.application.StudentService;
import io.github.ruben.student.infrastructure.controller.dto.input.StudentInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("student")
@RestController
public class CreateStudentController {
    @Autowired
    StudentService studentService;

    @PostMapping
    public StudentInputDto aniadirPesona(@Valid @RequestBody StudentInputDto studentInputDto){
        return studentService.aniadirStudent(studentInputDto);
    }
}
